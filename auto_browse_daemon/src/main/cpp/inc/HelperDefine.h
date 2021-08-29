#ifndef HELPER_DEFINE_H
#define HELPER_DEFINE_H

#include <errno.h>

#define DEBUG

#define _QUOTE(x) #x
#define QUOTE(x) _QUOTE(x)
#define __FILE__LINE__ __FILE__ "(" QUOTE(__LINE__) ") : "

#ifdef DEBUG
#   define ERROR_REPORT(fmt, ...) LOGE(("-mqmsdebug, %s(%d): " fmt), \
	__FILE__, __LINE__, ##__VA_ARGS__);
#   define MQMS_ASSERT(x) \
    if(!(x)){ \
        ERROR_REPORT("!!!Assert Fail for %s", #x); \
    }
#else       
#   define ERROR_REPORT(...)
#   define MQMS_ASSERT(...)
#endif

#define LINUX_VERIFY(x) \
	errno = 0; \
	bRet = (x); \
	if(!bRet){ \
        ERROR_REPORT("!!!Error happen for %s, reason(%d) is \'%s\'", #x, errno, strerror(errno)); \
	}

#define LINUX_VERIFY_EXCEPT1(x, err1) \
    errno = 0; \
    bRet = (x); \
    if(!bRet && errno != err1){ \
        ERROR_REPORT("!!!Error happen for %s, reason(%d) is \'%s\'", #x, errno, strerror(errno)); \
    }


#endif //HELPER_DEFINE_H