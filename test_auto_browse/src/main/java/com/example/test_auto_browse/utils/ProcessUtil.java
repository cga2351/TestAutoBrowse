package com.example.test_auto_browse.utils;

import android.content.Context;
import android.os.Build;

import com.example.test_auto_browse.Constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ProcessUtil {

    public static void processKillByPid(Context context, String packageName) {
        try {
            int pid = getTargetAppPid(packageName);
            if (pid > 0) {
                android.os.Process.killProcess(pid);
            }
        } catch (Exception e) {
            Logger.error("error", "processKillByPid " + e.getMessage(), e);
        }
    }

    public static boolean isProcessAlive(String packageName) {
        boolean isAlive = false;
        try {
            int pid = getTargetAppPid(packageName);
            if (pid > 0) {
                isAlive = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error("-mqmsdebug", "isProcessAlive(), exception, info:" + e.getMessage());
        }
        return isAlive;
    }

    public static int getTargetAppPid(String packageName) {
        Logger.debug("-mqmsdebug", "getTargetAppPid(), packageName=" + packageName);
        int result = 0;
        try {
            File file = new File(Constant.SHELL_PS_INFO);
            if (file.exists()) {
                Logger.debug("-mqmsdebug", "getTargetAppPid(), delete old procee info file");
                file.delete();
            }

            String psCmd = "ps ";
            if (Build.VERSION.SDK_INT >= 26) {
                psCmd += "-e ";
            }
            Logger.debug("-mqmsdebug", "getTargetAppPid(), psCmd=" + psCmd);
            String psCmdResult = ShellUtil.command(false, psCmd + "|/data/local/tmp/busybox grep " + packageName + " > " + Constant.SHELL_PS_INFO);
            Logger.debug("-mqmsdebug", "getTargetAppPid(), psCmdResult=" + psCmdResult);

            if (!file.exists()) {
                return 0;
            }

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(Constant.SHELL_PS_INFO), "UTF-8"));
                String temp = null;
                while ((temp = reader.readLine()) != null) {
                    String[] line = temp.split("[\\s]+");
                    String pid = line[1].trim();
                    String name = line[8].trim();
                    //Log.d("Debug", "pid:" + pid + " | name:" + name);

                    if (name != null && name.equals(packageName)) {
                        result = Integer.parseInt(pid);
                        break;
                    }
                }
            } catch (Exception e) {
                Logger.error("error", "getTargetAppPid " + e.getMessage(), e);
            } finally {
                reader.close();
            }
        } catch (Exception e) {
            Logger.error("error", "getTargetAppPid " + e.getMessage(), e);
        }
        return result;
    }
}
