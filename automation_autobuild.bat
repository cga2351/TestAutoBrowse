

@echo off

:: -------------------------- build auto browse jar --------------------------
SET FLAVOR=GQMS
SET ANDROID_SDK_PATH=%ANDROID_HOME%
SET LIB_PATH=%CD%\test_auto_browse\build\libs
SET OUTPUT_FILE_NAME=test_auto_browse
SET OUTPUT_FILE_NAME_FLAVOR=%OUTPUT_FILE_NAME%_%FLAVOR%

:: device udid param
if "%1"=="" ( 
	SET DEVICES_UDID_PARAM=
) else (
	SET DEVICES_UDID_PARAM=-s %1
)
echo DEVICES_UDID_PARAM=%DEVICES_UDID_PARAM%

:: android sdk version param
for /f %%i in ('adb %DEVICES_UDID_PARAM% shell "getprop ro.build.version.sdk"') do (SET SDK_VERSION=%%i)
SET ANDROID_TEST_LIB=
if %SDK_VERSION% GEQ 30 (
	SET ANDROID_TEST_LIB=/system/framework/android.test.base.jar
)

echo ANDROID_SDK_PATH=%ANDROID_SDK_PATH%
echo LIB_PATH=%LIB_PATH%
echo SDK_VERSION=%SDK_VERSION%
echo ANDROID_TEST_LIB=%ANDROID_TEST_LIB%


echo build jar...
del /q %LIB_PATH%\*
call gradlew -x test test_auto_browse:build -PFLAVOR=%FLAVOR%

::echo gen dex...
::call %ANDROID_SDK_PATH%\build-tools\25.0.2\dx --dex --output=%LIB_PATH%\%OUTPUT_FILE_NAME_FLAVOR%_dex.jar %LIB_PATH%\%OUTPUT_FILE_NAME_FLAVOR%.jar

echo push to device
call adb %DEVICES_UDID_PARAM% push %LIB_PATH%\%OUTPUT_FILE_NAME_FLAVOR%_dex.jar /data/local/tmp/%OUTPUT_FILE_NAME%.jar


:: -------------------------- build daemon --------------------------

echo build daemon...
call gradlew -x test auto_browse_daemon:build -PFLAVOR=%FLAVOR%

echo push file...
call adb %DEVICES_UDID_PARAM% push .\auto_browse_daemon\build\bin\armeabi-v7a\auto_browse_daemon /data/local/tmp
call adb %DEVICES_UDID_PARAM% shell chmod 777 /data/local/tmp/auto_browse_daemon

:: -------------------------- launch uiautomator and daemon --------------------------

:: kill and re-launch uiautomator
echo kill uiautomator
call adb %DEVICES_UDID_PARAM% shell "ps -e|grep uiautomator|/data/local/tmp/busybox awk '{print$2}'|/data/local/tmp/busybox xargs kill -9 $1"
call adb %DEVICES_UDID_PARAM% shell "ps|grep uiautomator|/data/local/tmp/busybox awk '{print$2}'|/data/local/tmp/busybox xargs kill -9 $1"

echo run uiautomator
::call adb %DEVICES_UDID_PARAM% shell "nohup uiautomator runtest %ANDROID_TEST_LIB% %OUTPUT_FILE_NAME%.jar -c com.example.test_auto_browse.AutomationAgentBootstrap"


:: kill and re-launch daemon
echo kill auto_browse_daemon...
call adb %DEVICES_UDID_PARAM% shell "ps -e|grep auto_browse_daemon|/data/local/tmp/busybox awk '{print$2}'|/data/local/tmp/busybox xargs kill -9 $1"
call adb %DEVICES_UDID_PARAM% shell "ps|grep auto_browse_daemon|/data/local/tmp/busybox awk '{print$2}'|/data/local/tmp/busybox xargs kill -9 $1"

echo run auto_browse_daemon...
call adb %DEVICES_UDID_PARAM% shell "nohup /data/local/tmp/auto_browse_daemon"


echo completed...
