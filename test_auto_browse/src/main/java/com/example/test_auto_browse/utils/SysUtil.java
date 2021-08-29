package com.example.test_auto_browse.utils;

import android.app.UiAutomation;
import android.text.TextUtils;
import android.util.Size;

import com.example.test_auto_browse.AutoBrowseApp;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2017/10/13
 * description :
 */

public class SysUtil {
    private static boolean isEmulator = false;
    private static boolean initIsEmulator = false;
    private static Size screenSize = null;
    private static String modelName = "";

    // all test apps' launcher name
    private static HashMap<String, String> packageLauncherNames = new HashMap<>();

    // all test apps' package name
    private static ArrayList<String> allAppsPackageName = new ArrayList<>();

    public static void init() {
        // init all apps' launcher name
        packageLauncherNames.put(Constant.PKG_NAME_TOU_TIAO_LITE, Constant.PKG_LAUNCHER_TOU_TIAO_LITE);
        packageLauncherNames.put(Constant.PKG_NAME_DIAN_TAO, Constant.PKG_LAUNCHER_DIAN_TAO);
        packageLauncherNames.put(Constant.PKG_NAME_WEI_SHI, Constant.PKG_LAUNCHER_WEI_SHI);
        packageLauncherNames.put(Constant.PKG_NAME_KUAI_SHOU, Constant.PKG_LAUNCHER_KUAI_SHOU);
        packageLauncherNames.put(Constant.PKG_NAME_JING_DONG, Constant.PKG_LAUNCHER_JING_DONG);
        packageLauncherNames.put(Constant.PKG_NAME_QU_KAN, Constant.PKG_LAUNCHER_QU_KAN);
        packageLauncherNames.put(Constant.PKG_NAME_QI_YI, Constant.PKG_LAUNCHER_QI_YI);
        packageLauncherNames.put(Constant.PKG_NAME_DOU_YIN, Constant.PKG_LAUNCHER_DOU_YIN);

        // init all apps' package name
        allAppsPackageName.add(Constant.PKG_NAME_TOU_TIAO_LITE);
        allAppsPackageName.add(Constant.PKG_NAME_DIAN_TAO);
        allAppsPackageName.add(Constant.PKG_NAME_WEI_SHI);
        allAppsPackageName.add(Constant.PKG_NAME_KUAI_SHOU);
        allAppsPackageName.add(Constant.PKG_NAME_JING_DONG);
        allAppsPackageName.add(Constant.PKG_NAME_QU_KAN);
        allAppsPackageName.add(Constant.PKG_NAME_QI_YI);
        allAppsPackageName.add(Constant.PKG_NAME_DOU_YIN);

        allAppsPackageName.add(Constant.PKG_NAME_ALI_PAY);
        allAppsPackageName.add(Constant.PKG_NAME_K_GE);
        allAppsPackageName.add(Constant.PKG_NAME_WAN_DOU_JIA);
        allAppsPackageName.add(Constant.PKG_NAME_XIMALAYA);
        allAppsPackageName.add(Constant.PKG_NAME_BAIDU);
        allAppsPackageName.add(Constant.PKG_NAME_WEI_XIN);
    }

    public static boolean isEmulator() {
        if (!initIsEmulator) {
            initIsEmulator = true;
            String result = ShellUtil.command(false, "getprop|grep ro.product.cpu.abi]");
            if (result.contains("x86")) {
                isEmulator = true;
            } else {
                isEmulator = false;
            }
        }
        return isEmulator;
    }

    public static void resetUiAutomationConnection() {
        Logger.debug("-mqmsdebug", "resetUiAutomationConnection(), entry");
        try {
            UiAutomation originUiAutomation = AutoBrowseApp.getInstance().getOriginUiAutomation();
            Class UiAutomationClass = originUiAutomation.getClass();
            Method disconnect = UiAutomationClass.getDeclaredMethod("disconnect");
            Method connect = UiAutomationClass.getDeclaredMethod("connect");
            if (null != disconnect && null != connect) {
                disconnect.invoke(originUiAutomation);
                connect.invoke(originUiAutomation);
                Logger.debug("-mqmsdebug", "resetUiAutomationConnection(), reset connection success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.debug("-mqmsdebug", "resetUiAutomationConnection(), exit");
    }

    public static Size getScreenSize() {
        if (null == screenSize) {
            String result = ShellUtil.command(false, "wm size");
            int startIndex = result.indexOf("Override size: ");
            if (startIndex < 0) {
                startIndex = result.indexOf("Physical size: ");
                startIndex = startIndex + "Physical size: ".length();
            } else {
                startIndex = startIndex + "Override size: ".length();
            }

            String size = result.substring(startIndex, result.length() - 1);
            String[] sizes = size.split("x");
            Logger.debug("getScreenSize(), result=" + result + ", startIndex=" + startIndex + ", sizes=" + Arrays.toString(sizes));

            if (sizes.length >= 2) {
                screenSize = new Size(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]));
                Logger.debug("getScreenSize(), success, width=" + screenSize.getWidth() + ", height=" + screenSize.getHeight());
            } else {
                Logger.debug("getScreenSize(), failed, return default size of 1080x1920");
                screenSize = new Size(1080, 1920);
            }
        }

        return screenSize;
    }

    public static void killAllAppsExclude(String excludeApp) {
        Logger.debug("SysUtil.killAllAppsExclude(), entry, excludeApp=" + excludeApp);

        // kill all apps exclude the current task
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < allAppsPackageName.size(); i++) {
            if (!allAppsPackageName.get(i).equals(excludeApp)) {
                SysUtil.killApp(allAppsPackageName.get(i));
            }
        }

        Logger.debug("SysUtil.killAllAppsExclude(), exit, time = " + (System.currentTimeMillis() - startTime));
    }

    public static void killAllApps() {
        Logger.debug("SysUtil.killAllApps(), entry");

        // switch to phone home page first
        UiDriver.pressHome();

        // kill all apps exclude the current task
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < allAppsPackageName.size(); i++) {
            SysUtil.killApp(allAppsPackageName.get(i));
        }

        Logger.debug("SysUtil.killAllApps(), exit, time = " + (System.currentTimeMillis() - startTime));
    }

    public static void killApp(String packageName) {
        ShellUtil.command(false, "am force-stop " + packageName);
    }

    //
    public static boolean launchApp(String packageName) {
        Logger.debug("SysUtil.launchApp(), packageName=" + packageName);
        boolean result = false;
        String checkPackageInstall = ShellUtil.command(false, "pm list package " + packageName);
        if (checkPackageInstall.contains(packageName)) {
            // kill app first
            killApp(packageName);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // launch
            String launcherName = packageLauncherNames.get(packageName);
            if (!TextUtils.isEmpty(launcherName)) {
                ShellUtil.command(false, "am start " + launcherName);
                result = true;
            } else {
                Logger.debug("SysUtil.launchApp(), get launcherName failed, packageName=" + packageName);
            }
        } else {
            Logger.debug("SysUtil.launchApp(), app not installed, package name:" + packageName);
        }

        return result;
    }

    public static String getModelName() {
        if (TextUtils.isEmpty(modelName)) {
            modelName = ShellUtil.command(false, "getprop ro.product.model");
        }
        return modelName;
    }

    public static int getBatteryLevel() {
        int batteryLevel = 0;
        String batteryInfo = ShellUtil.command(false, "dumpsys battery|grep level");
        if (!TextUtils.isEmpty(batteryInfo)) {
            batteryInfo = batteryInfo.trim();
            String[] infos = batteryInfo.split(":");
            if (infos.length >= 2) {
                batteryLevel = Integer.parseInt(infos[1].trim());
            } else {
                Logger.debug("getBatteryLevel(), parse battery level info failed");
            }
        } else {
            Logger.debug("getBatteryLevel(), get battery level info failed");
        }

        return batteryLevel;
    }

    public static int getBatteryVoltage() {
        int batteryLevel = 0;
//        String batteryInfo = ShellUtil.command(false, "dumpsys battery|grep voltage");
        String batteryInfo = ShellUtil.command(false, "dumpsys battery|grep -w \"^  voltage\"");
        if (!TextUtils.isEmpty(batteryInfo)) {
            batteryInfo = batteryInfo.trim();
            String[] infos = batteryInfo.split(":");
            if (infos.length >= 2) {
                batteryLevel = Integer.parseInt(infos[1].trim());
            } else {
                Logger.debug("getBatteryVoltage(), parse battery voltage info failed");
            }
        } else {
            Logger.debug("getBatteryVoltage(), get battery voltage info failed");
        }

        return batteryLevel;
    }

    public static int getBatteryTemperature() {
        int temperature = 0;

        String result = ShellUtil.command(false, "dumpsys battery|grep temperature");
        Logger.debug("getBatteryTemperature(), temperature result=" + result);

        if (!TextUtils.isEmpty(result)) {
            String[] resultInfo = result.trim().split(":");
            if (null != resultInfo && resultInfo.length >= 2) {
                temperature = Integer.parseInt(resultInfo[1].trim());
            } else {
                Logger.debug("getBatteryTemperature(), parse battery temperature failed");
            }
        } else {
            Logger.debug("getBatteryTemperature(), get battery temperature failed");
        }

        return temperature;
    }
}
