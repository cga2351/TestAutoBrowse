//
// Created by cga2351 on 2018/5/2.
//

#ifndef __COMMON_H__
#define __COMMON_H__
#include "android/log.h"
#include <string>
#include "Logger.h"
#include <linux/in.h>
#include <stdlib.h>
#include <getopt.h>
#include <string.h>
#include <endian.h>
#include <netinet/in.h>


#define LOG_TAG_AUTO_BROWSE_DAEMON_AGENT   "[AUTO-BROWSE-DAEMON]"

//#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG  , LOG_TAG_AUTO_BROWSE_DAEMON_AGENT, __VA_ARGS__)
//#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR , LOG_TAG_AUTO_BROWSE_DAEMON_AGENT, __VA_ARGS__)
//#define LOGW(...) __android_log_print(ANDROID_LOG_WARN , LOG_TAG_AUTO_BROWSE_DAEMON_AGENT, __VA_ARGS__)

#define LOGD(...) Logger::debug(LOG_TAG_AUTO_BROWSE_DAEMON_AGENT, __VA_ARGS__)
#define LOGE(...) Logger::error(LOG_TAG_AUTO_BROWSE_DAEMON_AGENT, __VA_ARGS__)
#define LOGW(...) Logger::warn(LOG_TAG_AUTO_BROWSE_DAEMON_AGENT, __VA_ARGS__)

#ifndef false
#  define false 0
#  define true -1
#endif

#define AUTOMATION_AGENT_START_COMMAND   "uiautomator runtest /system/framework/android.test.base.jar mqms_automation_agent.jar -c com.navercorp.ncp.mqms.automationagent.AutomationAgentBootstrap&"
#define CHECK_AUTO_BROWSE_UIAUTOMATOR_COMMAND   "ps|/data/local/tmp/busybox grep uiautomator"
#define AUTO_BROWSE_UIAUTOMATOR_STOP_COMMAND   "ps|/data/local/tmp/busybox grep uiautomator|/data/local/tmp/busybox awk '{print$2}'|/data/local/tmp/busybox xargs kill -9 $1"
#define AUTO_BROWSE_UIAUTOMATOR_START_COMMAND   "nohup uiautomator runtest  test_auto_browse.jar -c com.example.test_auto_browse.AutomationAgentBootstrap > /dev/null 2>&1 &"
#define MQMS_DAEMON_AGENT_PORT     65300

#ifndef _countof
#  define _countof(array) (sizeof(array)/sizeof(array[0]))
#endif

#define MQMS_MAGIC_NUM_M 'M'
#define MQMS_MAGIC_NUM_Q 'Q'
#define MQMS_MAGIC_NUM_S 'S'

#define INVALID_THREAD_ID	(pthread_t(-1))
#define INVALID_SOCKET      (-1)
#define INVALID_VALUE       (uint32_t(-1))
#define INVALID_COMMAND_ID  ((unsigned int)(-1))

#define REDIRECT_PORT   65000
#define AGENT_LOCAL_HOST "127.0.0.1"

#define DEFAULT_DISPLAY_ID 0
#define DEFAULT_JPG_QUALITY 80
#define FULL_SCALE      100
#define HALF_SCALE		50

#define MAX_SHOTSCREEN_BYTES_NUM 307200 //300k
#define TEMP_SCREENSHOT_LENGHT  MAX_SHOTSCREEN_BYTES_NUM / sizeof(char)

#define SOCKET  int

#define LOCK(mutex) pthread_mutex_lock(&(mutex))
#define UNLOCK(mutex) pthread_mutex_unlock(&(mutex))
#define MUTEX(mutex) pthread_mutex_t (mutex)
#define INIT_MUTEX(mutex) pthread_mutex_init(&(mutex),NULL)
#define TINI_MUTEX(mutex) pthread_mutex_destroy(&(mutex))
#define TSIGNAL(cond) pthread_cond_signal(&(cond))
#define WAIT(cond,mutex) pthread_cond_wait(&(cond),&(mutex))
#define TIMEDWAIT(cond,mutex,abstime) pthread_cond_timedwait(&(cond),&(mutex),&(abstime))
#define COND(cond) pthread_cond_t (cond)
#define INIT_COND(cond) pthread_cond_init(&(cond),NULL)
#define TINI_COND(cond) pthread_cond_destroy(&(cond))
#define IF_PTHREADS(x) x

#define FRAME_BUFFER_UPDATE_ON		1
#define FRAME_BUFFER_UPDATE_OFF		0

#define SAFE_FREE(p) if(NULL != p) { free(p); p = NULL; }
#define SAFE_DELETE(p) if(NULL != p) { delete p; p = NULL; }
#define SAFE_DELETE_ARRAY(p) if(NULL != p) { delete [] p; p = NULL; }
#define SAFE_CLOSE_SOCKET(s) if(NULL != s) { s->Close(); delete s; s = NULL; }

//#define PERFORMANCE_REPORT
#define ELAPSED(start,end) ((end.tv_sec-start.tv_sec) * 1000000 + (end.tv_usec - start.tv_usec))

#define MQMS_COMMAND \
    unsigned char mqms_M1; \
    unsigned char mqms_Q; \
    unsigned char mqms_M2; \
    unsigned char mqms_S; \
	unsigned int id; \
	unsigned int size;

typedef struct tagCOMMAND {
    MQMS_COMMAND
    char* data;  //point to a common buffer, don't release it
} COMMAND, *PCOMMAND;

#define COMMAND_HEADER_SIZE			12

//default max command buffer size
#define MAX_COMMAND_BUFFER_SIZE		(32 * 1024)

#define DATE_FORMAT_HYPHEN      "%Y-%m-%d %H:%M:%S" //2017-7-10 11:41:22
#define DATA_FORMAT_UNDERLINE   "%Y_%m_%d_%H_%M_%S" //2017_7_10_11_41_22
#define DATA_FORMAT_DOT         "%Y%m%d.%H%M%S" //20170710.114122

#define LOG_FILE_PATH           "/sdcard/mqms/log/"
#define LOG_FILE_SUFFIX         "_daemon_log.txt"

#define TIMER_ID_LOG_FILE_CHECK     0
#define TIMER_ID_HEART_BEAT_CHECK   1

#endif