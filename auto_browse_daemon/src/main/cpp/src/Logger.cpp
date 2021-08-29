//
// Created by cga2351 on 2018/6/19.
//
#include <thread>
#include <common.h>
#include "Logger.h"
#include "utils.h"
#include <unistd.h>
#include <Mutex.h>
#include <HelperDefine.h>

pthread_t Logger::m_logWriteThread = NULL;
pthread_mutex_t Logger::m_messageQueueMutex;
std::queue<std::string> Logger::m_logQueue;
bool Logger::m_isExit = false;
std::string Logger::m_logFilePath = "";
int Logger::curLogFileDay = 0;

void Logger::init() {
    int bRet = 0;
    int err = 0;

    // init log file name
    m_logFilePath = LOG_FILE_PATH;
    m_logFilePath += getCurrentLocalFormatDate(DATA_FORMAT_UNDERLINE);
    m_logFilePath += LOG_FILE_SUFFIX;

    // record current log file date
    time_t timeStamp;
    tm* Date;
    time(&timeStamp);
    Date = localtime(&timeStamp);
    curLogFileDay = Date->tm_mday;

    // start new log file check timer
    startLogFileCheckTimer();

    // create log file write thread
    err = pthread_create(&m_logWriteThread, NULL, Logger::logWriteThreadProc, NULL);
    LINUX_VERIFY(err >= 0);

}

void Logger::unInit() {
    void* ret = NULL;
    m_isExit = true;
    pthread_join(m_logWriteThread, &ret);
}

void Logger::debug(const char* tag, char* fmt, ...) {
    char pInfo[MAX_LOG_LENGTH] = {0};

    va_list va;
    va_start(va, fmt);
    vsprintf(pInfo, fmt, va);
    va_end(va);

    __android_log_print(ANDROID_LOG_DEBUG, tag, "%s", pInfo);
    writeToFile(pInfo, tag, ANDROID_LOG_DEBUG);
}
void Logger::error(const char* tag, char* fmt, ...) {
    char pInfo[MAX_LOG_LENGTH] = {0};

    va_list va;
    va_start(va, fmt);
    vsprintf(pInfo, fmt, va);
    va_end(va);

    __android_log_print(ANDROID_LOG_ERROR, tag, "%s", pInfo);
    writeToFile(pInfo, tag, ANDROID_LOG_ERROR);
}
void Logger::warn(const char* tag, char* fmt,...) {
    char pInfo[MAX_LOG_LENGTH] = {0};

    va_list va;
    va_start(va, fmt);
    vsprintf(pInfo, fmt, va);
    va_end(va);

    __android_log_print(ANDROID_LOG_WARN, tag, "%s", pInfo);
    writeToFile(pInfo, tag, ANDROID_LOG_WARN);
}

void* Logger::logWriteThreadProc(void* pData) {
    // write to file
    std::string initLog = "\nstart write a new log--------------------------------------------------------\n";
    FILE* logFile = fopen(m_logFilePath.c_str(), "a");
    if (NULL != logFile) {
        fwrite(initLog.c_str(), initLog.size(), 1, logFile);
        fclose(logFile);
    }

    while (!m_isExit) {
        std::string formatLog = "";
        if (!m_logQueue.empty()) {
            CMutexLock messageQueueLock(&m_messageQueueMutex, "m_logQueue read lock");
            formatLog = m_logQueue.front();
            m_logQueue.pop();
        }
        if (formatLog.length() > 0) {
            // write to file
            formatLog += "\n";
            FILE* logFile = fopen(m_logFilePath.c_str(), "a");
            if (NULL != logFile) {
                fwrite(formatLog.c_str(), formatLog.size(), 1, logFile);
                fclose(logFile);
            }
        }
        usleep(1000 * 100);
    }

    return NULL;
}

void Logger::writeToFile(const char *logInfo, const char* tag, int level) {
    if (NULL != logInfo && strlen(logInfo) > 0) {
        CMutexLock messageQueueLock(&m_messageQueueMutex, "m_logQueue write lock");

        char pFormatLog[MAX_LOG_LENGTH] = {0};
        std::string formatTime = getCurrentLocalFormatTime(DATE_FORMAT_HYPHEN);
        std::string exePath = getExePath();
        std::string levelStr = getLevelString(level);

        sprintf(pFormatLog, "%s %d-%d %s %s/%s  %s",
                formatTime.c_str(),
                getpid(),
                gettid(),
                exePath.c_str(),
                levelStr.c_str(),
                tag,
                logInfo
        );

        m_logQueue.push(std::string(pFormatLog));
    }
}

std::string Logger::getLevelString(int level) {
    std::__ndk1::string levelStr;
    switch (level) {
            case ANDROID_LOG_DEBUG :
                levelStr = "D";
                break;
            case ANDROID_LOG_ERROR :
                levelStr = "E";
                break;
            case ANDROID_LOG_WARN :
                levelStr = "W";
                break;
            default:
                levelStr = "D";
                break;
        }
    return levelStr;
}

std::string Logger::getExePath() {
    std::string exePath;
    char exePathBuf[255] = {0};
    ssize_t count = readlink("/proc/self/exe", exePathBuf, 255);
    if (count < 0 || count >= 255) {
        exePath = "";
    } else {
        exePath = exePathBuf;
        exePath += '\0';
    }
    return exePath;
}

std::string Logger::getCurrentLocalFormatDate(const char* format) {
    std::string formatTime = "";
    if (NULL != format) {
        char formatTimeBuf[255] = {0};
        time_t timeStamp;
        tm* date;
        time(&timeStamp);
        date = localtime(&timeStamp);
        date->tm_hour = 0;
        date->tm_min = 0;
        date->tm_sec = 0;
        strftime(formatTimeBuf, 255, format, date);
        formatTime = formatTimeBuf;
    }

    return formatTime;
}
std::string Logger::getCurrentLocalFormatTime(const char* format) {
    std::string formatTime = "";
    if (NULL != format) {
        char formatTimeBuf[255] = {0};
        time_t timeStamp;
        tm* Date;
        time(&timeStamp);
        Date = localtime(&timeStamp);
        strftime(formatTimeBuf, 255, format, Date);
        formatTime = formatTimeBuf;
    }

    return formatTime;
}

void Logger::logFileCheckTimerProc(sigval_t sigVal) {
    time_t timeStamp;
    tm* Date;
    time(&timeStamp);
    Date = localtime(&timeStamp);

    if (curLogFileDay != Date->tm_mday) {
        // a new day, write log to a new file
        m_logFilePath = LOG_FILE_PATH;
        m_logFilePath += getCurrentLocalFormatDate(DATA_FORMAT_UNDERLINE);
        m_logFilePath += LOG_FILE_SUFFIX;
        curLogFileDay = Date->tm_mday;
    }
}

void Logger::startLogFileCheckTimer() {
    timer_t timerId = NULL;
    sigevent_t sigEvent;
    memset(&sigEvent, 0, sizeof(sigevent_t));

    sigEvent.sigev_value.sival_int = TIMER_ID_LOG_FILE_CHECK;
    sigEvent.sigev_notify = SIGEV_THREAD;
    sigEvent.sigev_notify_function = Logger::logFileCheckTimerProc;

    if (-1 == timer_create(CLOCK_REALTIME, &sigEvent, &timerId)) {
        LOGE("-mqmsdebug, startLogFileCheckTimer(), timer_create failed");
    } else {
        // check if create new log file per 1 minute
        itimerspec timerSpec;
        timerSpec.it_interval.tv_sec = 60;
        timerSpec.it_interval.tv_nsec = 0;
        timerSpec.it_value.tv_sec = 60;
        timerSpec.it_value.tv_nsec = 0;
        if (-1 == timer_settime(timerId, 0, &timerSpec, NULL)) {
            LOGE("-mqmsdebug, startLogFileCheckTimer(), timer_settime failed");
        }
    }
}