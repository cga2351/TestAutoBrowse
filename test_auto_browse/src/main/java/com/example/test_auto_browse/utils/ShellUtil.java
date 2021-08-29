package com.example.test_auto_browse.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2018/8/30
 * description :
 */
public class ShellUtil {

    public static String command(boolean needRoot, String cmd) {
        return commandRun(needRoot, cmd);
    }

    public static String command(boolean needRoot, String cmd, String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(cmd).append(" ");
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
//        return commandRun(needRoot, sb.toString());
        ShellCmdResult shellCmdResult = commandRunEx(needRoot, sb.toString());
        return shellCmdResult.output;
    }

    private static String commandRun(boolean needRoot, String cmd) {
        ShellCmdResult shellCmdResult = commandRunEx(needRoot, cmd);
        return shellCmdResult.output;
    }

    public static ShellCmdResult commandRunEx(boolean needRoot, String cmd) {
        String tmp = "";
        String error = "";
        String result = "";
        ShellCmdResult shellCmdResult = new ShellCmdResult();
        shellCmdResult.output = "";
        shellCmdResult.error = "";

        try {
            Process proc = Runtime.getRuntime().exec(getShell(needRoot));
            DataOutputStream dos = new DataOutputStream(proc.getOutputStream());
            BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
            BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(proc.getErrorStream(), "UTF-8"));

            dos.writeBytes(cmd);
            dos.flush();
            dos.close();

            try {
                try {
                    proc.getOutputStream().close();
                } catch (IOException e) {
                }

                proc.waitFor();
            } catch (InterruptedException e) {
                proc.destroy();
            }

            tmp = errorBufferedReader.readLine();
            while (null != tmp) {
                error += tmp + "\n";
                tmp = errorBufferedReader.readLine();
            }
            Logger.debug("-mqmsdebug", "cmd:" + cmd + ", error=" + error);

            tmp = inputBufferedReader.readLine();
            while (null != tmp) {
                result += tmp + "\n";
                tmp = inputBufferedReader.readLine();
            }
            if (!cmd.startsWith("pm list package")) {
                Logger.debug("-mqmsdebug", "cmd:" + cmd + ", result=" + result);
            }

            shellCmdResult.error = error;
            shellCmdResult.output = result;

            inputBufferedReader.close();
            errorBufferedReader.close();
            proc.getInputStream().close();
            proc.getErrorStream().close();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(" # commandRun(IOException): " + e.getMessage());
        }

        return shellCmdResult;
    }

    public static String getShell(boolean needRoot){
        if(needRoot &&
                (new File("/system/xbin/su").exists() ||
                        new File("/system/bin/su").exists() ||
                        new File("/su").exists() ||
                        new File("/sbin/su").exists() )) {
            return "su";
        } else {
            return "sh";
        }
    }

    public static class ShellCmdResult {
        public String output;
        public String error;
    }
}
