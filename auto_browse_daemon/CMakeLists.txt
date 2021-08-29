cmake_minimum_required(VERSION 3.4.1)

aux_source_directory( ${PROJECT_SOURCE_DIR}/src/main/cpp/src SOURCES)
add_executable( auto_browse_daemon
                ${SOURCES}
                )
# add flavor macro define
if("${FLAVOR}" STREQUAL "GQMS")
    add_definitions(-DGQMS)
elseif("${FLAVOR}" STREQUAL "UFT")
    add_definitions(-DUFT)
else()
    add_definitions(-DGQMS)
endif()

#add_executable( auto_browse_daemon
#                   src/main/cpp/src/CmdHeartBeatHandler.cpp
#                   src/main/cpp/src/CmdRestartAutoAgentHandler.cpp
#                   src/main/cpp/src/CommandHandler.cpp
#                   src/main/cpp/src/CommandHandlerFactory.cpp
#                   src/main/cpp/src/CommandSocket.cpp
#                   src/main/cpp/src/DaemonAgent.cpp
#                   src/main/cpp/src/main.cpp
#                   src/main/cpp/src/mqms-command_types.cpp
#                   src/main/cpp/src/Mutex.cpp
#                   src/main/cpp/src/Options.cpp
#                   src/main/cpp/src/utils.cpp
#                   src/main/cpp/src/Logger.cpp
#                    )

include_directories(
                    ${PROJECT_SOURCE_DIR}/src/main/cpp/inc/
                    ${PROJECT_SOURCE_DIR}/src/main/cpp/src/inc/
                    )

find_library( log-lib
              log )

target_link_libraries(  auto_browse_daemon
                       ${log-lib} )

SET(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/build/bin/${ANDROID_ABI}/)