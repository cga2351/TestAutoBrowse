//
// Created by cga2351 on 2018/6/19.
//

#ifndef MQMS_AGENT_LOGGER_H
#define MQMS_AGENT_LOGGER_H

#include <queue>

//#define MAX_LOG_LENGTH 1024*1024
#define MAX_LOG_LENGTH 1024*20

class Logger {
private:
    static pthread_t m_logWriteThread;
    static pthread_mutex_t m_messageQueueMutex;
    static std::queue<std::string> m_logQueue;
    static bool m_isExit;
    static std::string m_logFilePath;
    static int curLogFileDay;

public:
    Logger() {}

    virtual ~Logger() {
    }

    static void init();
    static void unInit();
    static void *logWriteThreadProc(void* pData);

    static void debug(const char *tag, char *fmt, ...);
    static void error(const char *tag, char *fmt, ...);
    static void warn(const char *tag, char *fmt, ...);
    static void writeToFile(const char *logInfo, const char* tag, int level);

    static std::string getCurrentLocalFormatDate(const char* format);
    static std::string getCurrentLocalFormatTime(const char* format);
    static std::string getLevelString(int level);
    static std::string getExePath();

    static void startLogFileCheckTimer();
    static void logFileCheckTimerProc(sigval_t sigVal);
};


#endif //MQMS_AGENT_LOGGER_H

