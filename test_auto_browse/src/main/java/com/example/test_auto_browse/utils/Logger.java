package com.example.test_auto_browse.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.example.test_auto_browse.Constant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by XiangNing on 2017/6/23.
 */

public class Logger {

    private static String prefix;

    private static final int MSG_WRITE = 0;
    private static final int MSG_DELETE_OLD_LOG_FILES = 1;
    private static WriterThread writerThread = null;
    private static WriterThread.WriterHandler handler = null;
    private static String logFileName = "";
    private static Calendar logFileCreateDate;

    private static final String LEVEL_DEBUG = "D";
    private static final String LEVEL_ERROR = "E";
    private static final String LEVEL_INFO = "I";

    private static final long LOG_FILE_KEEP_PERIOD = 5 * 24 * 60 * 60 * 1000;   // 5 days
    private static final long LOG_FILE_CHECK_INTERVAL = 60 * 60 * 1000;   // 1 hour
    private static final long LOG_FILE_MAX_SIZE = 1024 * 1024 * 100;   // 100MB

//    //test
//    private static final long LOG_FILE_CHECK_INTERVAL = 1000 * 10;
//    private static final long LOG_FILE_MAX_SIZE = 1024 * 10;

    static {
        Logger.prefix = "[AUTO-BROWSE]";
    }

    public static void init() {
//        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter());
//        com.orhanobut.logger.Logger.addLogAdapter(new DiskLogAdapter());

        Calendar curDay = Calendar.getInstance();
        curDay.set(Calendar.HOUR_OF_DAY, 0);
        curDay.set(Calendar.MINUTE, 0);
        curDay.set(Calendar.SECOND, 0);
        curDay.set(Calendar.MILLISECOND, 0);

        logFileName = DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_UNDERLINE, curDay.getTimeInMillis());
        logFileName += Constant.AUTO_AGENT_LOG_FILE_SUFFIX;
        logFileCreateDate = Calendar.getInstance();

        writerThread = new WriterThread();
        writerThread.start();

        // timer to check if write log to new log file
        startCheckTimer();

    }

    private static void startCheckTimer() {
        Calendar nextCheckTime = Calendar.getInstance();
        nextCheckTime.set(Calendar.HOUR_OF_DAY, nextCheckTime.get(Calendar.HOUR_OF_DAY) + 1);
        nextCheckTime.set(Calendar.MINUTE, 0);
        nextCheckTime.set(Calendar.SECOND, 0);
        nextCheckTime.set(Calendar.MILLISECOND, 0);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = MSG_DELETE_OLD_LOG_FILES;
                handler.sendMessage(msg);
            }
        }, new Date(nextCheckTime.getTimeInMillis()), LOG_FILE_CHECK_INTERVAL);

        Logger.debug("next check new log file time is:" + DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_HYPHEN, nextCheckTime.getTimeInMillis()));
    }

    private static void checkOldLogFiles() {
        Logger.debug("checkOldLogFiles(), check new log file, cur time:" + DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_UNDERLINE, System.currentTimeMillis()));

        Calendar currentTime = Calendar.getInstance();
        if (logFileCreateDate.get(Calendar.DAY_OF_MONTH) != currentTime.get(Calendar.DAY_OF_MONTH)) {
            // time goes to the second day, save log to new file
            currentTime.set(Calendar.HOUR_OF_DAY, 0);
            currentTime.set(Calendar.MINUTE, 0);
            currentTime.set(Calendar.SECOND, 0);
            currentTime.set(Calendar.MILLISECOND, 0);
            String newLogFileName = DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_UNDERLINE, currentTime.getTimeInMillis());
            newLogFileName += Constant.AUTO_AGENT_LOG_FILE_SUFFIX;
            logFileName = newLogFileName;
            logFileCreateDate = currentTime;

            Logger.debug("checkOldLogFiles(), time goes to the second day, save log to new file, logFileName=" + logFileName);

            // delete in java agent
            //delete old files
//            CommonUtil.deleteOldFiles();
        }
    }

//    public static void deleteOldLogFile() {
//        // delete log file 5 days ago
//        File logDir = new File(Constant.SD_PATH_MQMS_LOG);
//        File[] logFiles = logDir.listFiles();
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        long curTimeStamp = calendar.getTimeInMillis();
//        long timeToDelete = curTimeStamp - LOG_FILE_KEEP_PERIOD; // last update time is 5 days ago. to delete
//        FileInputStream logFileInputStream;
//        long modifyTime = 0;
//
//        for (File logFile : logFiles) {
//            try {
//                modifyTime = logFile.lastModified();
//                logFileInputStream = new FileInputStream(logFile);
//
//                if ((logFile.getName().endsWith(Constant.AUTO_AGENT_LOG_FILE_SUFFIX)) &&
//                        (modifyTime < timeToDelete || logFileInputStream.available() > LOG_FILE_MAX_SIZE)) {
//                    logFile.delete();
//                }
//
//                logFileInputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void debug(final String des) {
        Log.d(Logger.prefix, des);
//        System.out.println(Logger.prefix + " [debug] " + des);
//        com.orhanobut.logger.Logger.d(des);
        writeToFile(des, LEVEL_DEBUG, Logger.prefix);
    }

    public static void debug(final String subTag, String des) {
        Log.d(Logger.prefix, String.format("  %s : %s", subTag, des));
//        System.out.println(Logger.prefix + " [debug] " + des);
//        com.orhanobut.logger.Logger.d(String.format("  %s : %s", subTag, des));
//        writeToFile(Logger.prefix + String.format("  %s : %s", subTag, des));
        writeToFile(String.format("  %s : %s", subTag, des), LEVEL_DEBUG, Logger.prefix);
    }

    public static void error(final String des) {
        Log.e(Logger.prefix, des);
//        System.out.println(Logger.prefix + " [error] " + des);
//        com.orhanobut.logger.Logger.e(des);
//        writeToFile(Logger.prefix + des);
        writeToFile(des, LEVEL_ERROR, Logger.prefix);
    }

    public static void error(final String subTag, final String des) {
        Log.e(Logger.prefix, String.format("  %s : %s", subTag, des));
//        System.out.println(Logger.prefix + " [error] " + String.format("  %s : %s", subTag, des));
//        com.orhanobut.logger.Logger.e(String.format("  %s : %s", subTag, des));
//        writeToFile(Logger.prefix + String.format("  %s : %s", subTag, des));
        writeToFile(String.format("  %s : %s", subTag, des), LEVEL_ERROR, Logger.prefix);
    }

    public static void error(final String des, final Throwable ex) {
        Log.e(Logger.prefix, des, ex);
//        System.out.println(Logger.prefix + " [error] " + des);
//        System.out.println(Logger.prefix + " [error] " + ex.toString());
//        com.orhanobut.logger.Logger.e(ex, des);
        writeToFile(des + ex.getMessage(), LEVEL_ERROR, Logger.prefix);
    }

    public static void error(final String subTag, final String des, final Throwable ex) {
        Log.e(Logger.prefix, String.format("  %s : %s", subTag, des), ex);
//        System.out.println(Logger.prefix + " [error] " + String.format("  %s : %s", subTag, des));
//        System.out.println(Logger.prefix + " [error] " + ex.toString());
//        com.orhanobut.logger.Logger.e(ex, String.format("  %s : %s", subTag, des));
//        writeToFile(Logger.prefix + String.format("  %s : %s", subTag, des) + ex.getMessage());
        writeToFile(String.format("  %s : %s", subTag, des) + ex.getMessage(), LEVEL_ERROR, Logger.prefix);
    }

    public static void info(final String des) {
        Log.i(Logger.prefix, des);
//        System.out.println(Logger.prefix + " [info] " + des);
//        com.orhanobut.logger.Logger.i(des);
//        writeToFile(Logger.prefix + des);
        writeToFile(des, LEVEL_INFO, Logger.prefix);
    }

    public static void info(final String subTag, final String des) {
        Log.i(Logger.prefix, String.format("  %s : %s", subTag, des));
//        System.out.println(Logger.prefix + " [info] " + String.format("  %s : %s", subTag, des));
//        com.orhanobut.logger.Logger.i(String.format("  %s : %s", subTag, des));
        writeToFile(String.format("  %s : %s", subTag, des), LEVEL_INFO, Logger.prefix);
    }


    private static void writeToFile(String content, String level, String moduleName) {
//        08-29 18:38:59.694 18962-19148/com.navercorp.ncp.mqms.mqmsagent D/[MQMS-AGENT]:
        if (null != handler) {
            String prefix = DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_HYPHEN, System.currentTimeMillis());
            prefix += " " + Process.myPid() + "-" + Thread.currentThread().getId();
            prefix += " " + "mqms_automation_agent";
            prefix += " " + level + "/" + moduleName;
            content = prefix + " " + content;

            Message msg = Message.obtain();
            msg.what = MSG_WRITE;
            msg.obj = content;
            handler.sendMessage(msg);
        }
    }

    public static class WriterThread extends Thread {

        public class WriterHandler extends Handler {

            WriterHandler(Looper looper) {
                super(looper);
            }

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_WRITE:
                        writeFile(msg);
                        break;
                    case MSG_DELETE_OLD_LOG_FILES:
                        checkOldLogFiles();
                        break;
                    default:
                        break;
                }
            }
        }

        @Override
        public void run() {

            Looper.prepare();
            writeLogToFile("\nstart write a new log--------------------------------------------------------");
            handler = new WriterHandler(Looper.myLooper());

            Logger.debug("log WriterThread thread start loop, thread id:" + currentThread().getId());
            Looper.loop();
            Logger.debug("log WriterThread thread exit, thread id:" + currentThread().getId());
        }
    }

    private static void writeFile(Message msg) {
        String content = (String) msg.obj;
        if (!TextUtils.isEmpty(content)) {
            writeLogToFile(content);
        }
    }

    private static void writeLogToFile(String content) {
        FileWriter fileWriter = null;
        try {
            String logFilePath = Constant.SD_PATH_LOG_DIR + logFileName;
            if (!new File(logFilePath).exists()) {
                new File(Constant.SD_PATH_LOG_DIR).mkdirs();
                new File(logFilePath).createNewFile();
            }
            fileWriter = new FileWriter(logFilePath, true);
            fileWriter.append(content);
            fileWriter.append("\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


//
//    public static void debug(final String des) {
//        Log.d(Logger.prefix, des);
//        System.out.println(Logger.prefix + " [debug] " + des);
//    }
//
//    public static void debug(final String subTag, String des) {
//        Log.d(Logger.prefix, String.format("  %s : %s", subTag, des));
//        System.out.println(Logger.prefix + " [debug] " + des);
//    }
//
//    public static void error(final String des) {
//        Log.e(Logger.prefix, des);
//        System.out.println(Logger.prefix + " [error] " + des);
//    }
//
//    public static void error(final String subTag, final String des) {
//        Log.e(Logger.prefix, String.format("  %s : %s", subTag, des));
//        System.out.println(Logger.prefix + " [error] " + String.format("  %s : %s", subTag, des));
//    }
//
//    public static void error(final String des, final Throwable ex) {
//        Log.e(Logger.prefix, des, ex);
//        System.out.println(Logger.prefix + " [error] " + des);
//        System.out.println(Logger.prefix + " [error] " + ex.toString());
//    }
//
//    public static void error(final String subTag, final String des, final Throwable ex) {
//        Log.e(Logger.prefix, String.format("  %s : %s", subTag, des), ex);
//        System.out.println(Logger.prefix + " [error] " + String.format("  %s : %s", subTag, des));
//        System.out.println(Logger.prefix + " [error] " + ex.toString());
//    }
//
//    public static void info(final String des) {
//        Log.i(Logger.prefix, des);
//        System.out.println(Logger.prefix + " [info] " + des);
//    }
//
//    public static void info(final String subTag, final String des) {
//        Log.i(Logger.prefix, String.format("  %s : %s", subTag, des));
//        System.out.println(Logger.prefix + " [info] " + String.format("  %s : %s", subTag, des));
//    }
}
