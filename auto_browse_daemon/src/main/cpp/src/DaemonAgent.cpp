//
// Created by cga2351 on 2018/5/8.
//

#include <signal.h>
#include <linux/time.h>
#include <common.h>
#include <cstdlib>
#include <HelperDefine.h>
#include <Mutex.h>
#include <assert.h>
#include <pthread.h>
#include "DaemonAgent.h"
#include "utils.h"
#include <unistd.h>

pthread_mutex_t DaemonAgent::m_instanceInitMutex;
DaemonAgent* DaemonAgent::m_pDaemonAgent = NULL;

DaemonAgent::DaemonAgent() {
    m_isMQMSAgentAlive = true;
    m_serverListenThread = INVALID_THREAD_ID;
    m_clientThread = INVALID_THREAD_ID;
    m_pCmdBuffer = new char[MAX_COMMAND_BUFFER_SIZE];
    pthread_mutex_init(&m_clientCreateMutex, NULL);
    pthread_mutex_init(&m_isMQMSAgentAliveMutex, NULL);
    pthread_mutex_init(&m_instanceInitMutex, NULL);
}
DaemonAgent::~DaemonAgent() {
    delete[] m_pCmdBuffer;
    pthread_mutex_destroy(&m_clientCreateMutex);
    pthread_mutex_destroy(&m_isMQMSAgentAliveMutex);
    pthread_mutex_destroy(&m_instanceInitMutex);
}

DaemonAgent* DaemonAgent::getInstance() {
    if (NULL == m_pDaemonAgent) {
        CMutexLock lock(&m_instanceInitMutex, "DaemonAgent init");
        if (NULL == m_pDaemonAgent) {
            m_pDaemonAgent = new DaemonAgent();
        }
    }
    return m_pDaemonAgent;
}

void DaemonAgent::unInit() {
    delete m_pDaemonAgent;
    m_pDaemonAgent = NULL;
}

int DaemonAgent::run() {
    // start java agent heart beat check timer
    startHeartBeatCheckTimer();

    // start listen thread
    startServerListening();
}

void DaemonAgent::startAutoBrowseUiAutomator() {
    LOGD("-mqmsdebug, startAutoBrowseUiAutomator(), entry");
//    system(AUTO_BROWSE_UIAUTOMATOR_STOP_COMMAND);
//    sleep(2);
    system(AUTO_BROWSE_UIAUTOMATOR_START_COMMAND);
//    LOGD("-mqmsdebug, startAutoBrowseUiAutomator(), exit");
}

void DaemonAgent::signalProc(int signal) {
//    LOGD("-mqmsdebug, signalProc(), entry, signal=%d", signal);
//    DaemonAgent* pDaemonAgent = DaemonAgent::getInstance();
//    switch (signal) {
//        case SIGALRM:
//            if (!pDaemonAgent->getIsMQMSAgentAlive()) {
//                LOGD("-mqmsdebug, mqms agent is not alive any more, start mqms agent");
//                pDaemonAgent->startAutoBrowseUiAutomator();
//            } else {
//                // reset m_isMQMSAgentAlive
//                pDaemonAgent->setIsMQMSAgentAlive(false);
//            }
//            break;
//        default:
//            break;
//    }
}

void DaemonAgent::startHeartBeatCheckTimer() {
    signal(SIGALRM, DaemonAgent::signalProc);

    // check mqms agent heart beat message per 20s
    struct itimerval interval;
    interval.it_interval.tv_sec = 20;
    interval.it_interval.tv_usec = 0;
    interval.it_value.tv_sec = 20;
    interval.it_value.tv_usec = 0;

    setitimer(ITIMER_REAL, &interval, NULL);
}

void DaemonAgent::startServerListening() {
    void* ret = 0;
    int bRet = 0;
    int err = 0;

    err = pthread_create(&m_serverListenThread, NULL, DaemonAgent::serverListenThreadProc, this);
    LINUX_VERIFY(err >= 0);

    pthread_join(m_serverListenThread, &ret);
}

void* DaemonAgent::serverListenThreadProc(void* pData) {
    DaemonAgent* pDaemonAgent = reinterpret_cast<DaemonAgent*>(pData);
    pDaemonAgent->innerServerListenThreadProc();
    return NULL;
}

void* DaemonAgent::innerServerListenThreadProc() {
    LOGD("DaemonAgent::innerServerListenThreadProc(), entry");

    int err = -1;
    bool bRet = false;

    int startResult = system(AUTO_BROWSE_UIAUTOMATOR_START_COMMAND);
    LOGD("DaemonAgent::innerServerListenThreadProc(), startResult = %d", startResult);

    while (true) {
        bool isAlive = checkUiAutomatorAlive();
        if (!isAlive) {
            startResult = system(AUTO_BROWSE_UIAUTOMATOR_START_COMMAND);
            LOGD("DaemonAgent::innerServerListenThreadProc(), uiautomator exit, re-start, startResult = %d", startResult);
        }
        sleep(60);
    }

    LOGD("DaemonAgent::innerServerListenThreadProc(), exit");

    return NULL;
}

bool DaemonAgent::checkUiAutomatorAlive() {
    LOGD("DaemonAgent::checkUiAutomatorAlive(), entry");
    bool result = false;
    std::string uiautomatorProc = shellCmd("ps|/data/local/tmp/busybox grep uiautomator");
    int pos = uiautomatorProc.find("uiautomator");
    if (pos == std::string::npos) {
        result = false;
    } else {
        result = true;
    }
    LOGD("DaemonAgent::checkUiAutomatorAlive(), exit, uiautomator is alive = %d", result);

    return result;
}

void* DaemonAgent::clientThreadProc(void* pData) {
    DaemonAgent* pDaemonAgent = reinterpret_cast<DaemonAgent*>(pData);
    pDaemonAgent->innerClientThreadProc();
    return NULL;
}

void* DaemonAgent::innerClientThreadProc() {
    LOGD("DaemonAgent.innerClientThreadProc() entry");

    bool bRet = false;

    while (true) {
        sleep(5);
    }

    LOGD("DaemonAgent.innerClientThreadProc() exit");

    return NULL;
}

bool DaemonAgent::getIsMQMSAgentAlive() const {
    return m_isMQMSAgentAlive;
}

void DaemonAgent::setIsMQMSAgentAlive(bool isMQMSAgentAlive) {
    CMutexLock(&m_isMQMSAgentAliveMutex, "m_isMQMSAgentAliveMutex");
    m_isMQMSAgentAlive = isMQMSAgentAlive;
}

void DaemonAgent::exit() {
    LOGD("DaemonAgent::exit()");
}
