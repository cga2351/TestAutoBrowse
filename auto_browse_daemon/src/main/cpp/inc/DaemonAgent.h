//
// Created by cga2351 on 2018/5/8.
//

#ifndef MQMS_AGENT_DAEMONAGENT_H
#define MQMS_AGENT_DAEMONAGENT_H


class DaemonAgent {

private:
    DaemonAgent();
    virtual ~DaemonAgent();

public:
    static DaemonAgent* getInstance();
    static void unInit();

    int run();
    void startHeartBeatCheckTimer();
    void startServerListening();
    static void signalProc(int signal);
    void startAutoBrowseUiAutomator();
    static void* serverListenThreadProc(void* pData);
    void* innerServerListenThreadProc();
    static void* clientThreadProc(void* pData);
    void* innerClientThreadProc();

    bool getIsMQMSAgentAlive() const;
    void setIsMQMSAgentAlive(bool isMQMSAgentAlive);
    void exit();

private:

    // new add
    bool m_isMQMSAgentAlive;
    pthread_t m_serverListenThread;
    pthread_t m_clientThread;
    pthread_mutex_t m_clientCreateMutex;
    pthread_mutex_t m_isMQMSAgentAliveMutex;
    static pthread_mutex_t m_instanceInitMutex;
    char* m_pCmdBuffer;
    static DaemonAgent* m_pDaemonAgent;

    bool checkUiAutomatorAlive();
};


#endif //MQMS_AGENT_DAEMONAGENT_H
