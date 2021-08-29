//
// Created by cga2351 on 2019/2/14.
//

#ifndef __UTILS_H__
#define __UTILS_H__

#include "common.h"

extern const char* const getVersion();
extern bool compareMagicNum(PCOMMAND pCmdMsg);
extern void callback(void* content);
extern bool isEmulator();
extern int getSdkVersion();
extern std::string shellCmd(std::string cmd);

#endif