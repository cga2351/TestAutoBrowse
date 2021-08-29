#ifndef __MUTEXT_H__
#define __MUTEXT_H__

#include "bits/pthread_types.h"

class CMutexLock {
public:
    CMutexLock(pthread_mutex_t *pMutex, const char *pLockName);

    ~CMutexLock();

private:
    pthread_mutex_t *m_pMutex;
    const char *m_pLockName;
};


#endif
