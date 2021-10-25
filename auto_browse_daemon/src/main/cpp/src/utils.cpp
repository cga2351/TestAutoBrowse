//
// Created by cga2351 on 2018/5/2.
//

#include <endian.h>
#include "common.h"

bool compareMagicNum(PCOMMAND pCmdMsg) {
    int size = ntohl(pCmdMsg->size);
    int id = ntohl(pCmdMsg->id);

    if(pCmdMsg->mqms_M1 == MQMS_MAGIC_NUM_M
       && pCmdMsg->mqms_Q == MQMS_MAGIC_NUM_Q
       && pCmdMsg->mqms_M2 == MQMS_MAGIC_NUM_M
       && pCmdMsg->mqms_S == MQMS_MAGIC_NUM_S) {
//        LOGD("-mqmsdebug, compare magic num success: M1 = %c, MQ = %c, M2 = %c, MS = %c, size = %d, id = %d",
//             pCmdMsg->mqms_M1, pCmdMsg->mqms_Q, pCmdMsg->mqms_M2, pCmdMsg->mqms_S, size, id);
        return true;
    }

    LOGE("-mqmsdebug, compare magic num failed: M1 = %c, MQ = %c, M2 = %c, MS = %c, size = %d, id = %d",
         pCmdMsg->mqms_M1, pCmdMsg->mqms_Q, pCmdMsg->mqms_M2, pCmdMsg->mqms_S, size, id);

    return false;
}

void callback(void* content) {
    char msg[512];
    memset(msg, 0x00, sizeof(msg));
    strcpy(msg, (char *)content);
    LOGE("-mqmsdebug, agent crash crashcallbacksuccessful, msg:%s", msg);
}

const char* const getVersion(){
    return "";
}

int getSdkVersion() {
    FILE *cmdOutputStream;
    char cmdOutput[1024] = {0};
    int version = 0;
    std::string cmd = "getprop ro.build.version.sdk";
    cmdOutputStream = popen(cmd.c_str(), "r");    // ends with uiautomator
    fread(cmdOutput, sizeof(char), sizeof(cmdOutput), cmdOutputStream);

    if (strlen(cmdOutput) > 0) {
        sscanf(cmdOutput, "%d", &version);
        LOGD("-mqmsdebug, getSdkVersion, version=%d", version);
    } else {
        LOGE("-mqmsdebug, getSdkVersion failed");
    }

    pclose(cmdOutputStream);

    return version;
}

bool isEmulator() {
    FILE *cmdOutputStream;
    char cmdOutput[1024] = {0};
    bool isEmulator = false;

    cmdOutputStream = popen("getprop|/data/local/tmp/busybox grep ro.product.cpu.abi]", "r");    // ends with "ro.product.cpu.abi]"
    fread(cmdOutput, sizeof(char), sizeof(cmdOutput), cmdOutputStream);
    LOGD("-mqmsdebug, isEmulator(), get abi result:%s", cmdOutput);
    if (strstr(cmdOutput, "x86")) {
        isEmulator = true;
    }

    pclose(cmdOutputStream);
    return isEmulator;
}

std::string shellCmd(std::string cmd) {
    FILE *cmdOutputStream;
    char cmdOutput[1024] = {0};

    cmdOutputStream = popen(cmd.c_str(), "r");    // ends with uiautomator
    fread(cmdOutput, sizeof(char), sizeof(cmdOutput), cmdOutputStream);
    LOGD("shellCmd(), cmd: %s, result:%s", cmd.c_str(), cmdOutput);

    pclose(cmdOutputStream);

    return std::string(cmdOutput);
}