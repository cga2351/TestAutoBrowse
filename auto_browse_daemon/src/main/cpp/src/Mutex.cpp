#include <pthread.h>
#include "HelperDefine.h"
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <common.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include "Mutex.h"
#include <errno.h>


CMutexLock::CMutexLock(pthread_mutex_t *pMutex, const char *pLockName)
        : m_pMutex(pMutex), m_pLockName(pLockName) {
    bool bRet = false;
    LINUX_VERIFY(0 == pthread_mutex_lock(m_pMutex));
}

CMutexLock::~CMutexLock() {
    bool bRet = false;
    LINUX_VERIFY(0 == pthread_mutex_unlock(m_pMutex));
}
