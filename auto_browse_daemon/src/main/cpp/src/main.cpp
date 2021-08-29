//
// Created by cga2351 on 2018/5/2.
//

#include <unistd.h>
#include <Logger.h>
#include "common.h"
#include "DaemonAgent.h"

#if defined(GQMS)
char* FLAVOR = "GQMS";
#elif defined(UFT)
char* FLAVOR = "UFT";
#else
char* FLAVOR = "GQMS";
#endif

int main(int argc, char* const argv[]) {
    Logger::init();

    LOGD("auto browse daemon start, flavor=%s", FLAVOR);

    DaemonAgent* pDaemonAgent = DaemonAgent::getInstance();
    pDaemonAgent->run();

    DaemonAgent::unInit();

    LOGD("auto browse daemon exit");

    Logger::unInit();
	
    return 0;
}