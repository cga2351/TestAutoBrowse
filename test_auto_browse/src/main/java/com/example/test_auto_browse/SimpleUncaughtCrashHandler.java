package com.example.test_auto_browse;

import android.os.Environment;

import com.example.test_auto_browse.utils.DateUtil;
import com.example.test_auto_browse.utils.SysUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2017/7/31
 * description :
 */

public class SimpleUncaughtCrashHandler implements UncaughtExceptionHandler {

    //    private static final String CRASH_LOG_FILE_PATH = "/sdcard/mqms/crashLog/";
    private static final String CRASH_LOG_FILE_PATH =
            Environment.getExternalStorageDirectory().getPath()
                    + Constant.SEPARATOR
                    + "testAutoBrowse" + Constant.SEPARATOR
                    + "crashLog" + Constant.SEPARATOR;
    private static SimpleUncaughtCrashHandler instance = null;
    private Thread.UncaughtExceptionHandler defaultHandler;

    private SimpleUncaughtCrashHandler() {
    }

    public void init() {
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static SimpleUncaughtCrashHandler getInstance() {
        if (null == instance) {
            synchronized (SimpleUncaughtCrashHandler.class) {
                if (null == instance) {
                    instance = new SimpleUncaughtCrashHandler();
                }
            }
        }

        return instance;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (null != defaultHandler) {
            saveLogFile(t, e);

            defaultHandler.uncaughtException(t, e);
        }
    }

    public void saveLogFile(Thread thread, Throwable throwable) {
        if (!new File(CRASH_LOG_FILE_PATH).exists()) {
            new File(CRASH_LOG_FILE_PATH).mkdirs();
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        try {
            // -------------------------------------------------------------------------------------
            writer.write("---divider:getCause:\n");
            Throwable cause = throwable.getCause();
            while (null != cause) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            writer.write("\n");

            // -------------------------------------------------------------------------------------
            writer.write("---divider:getLocalizedMessage:\n");
            String localizedMessage = throwable.getLocalizedMessage();
            writer.write(localizedMessage);
            writer.write("\n");

            // -------------------------------------------------------------------------------------
            writer.write("---divider:getMessage:\n");
            String message = throwable.getMessage();
            writer.write(message);
            writer.write("\n");

            // -------------------------------------------------------------------------------------
            writer.write("---divider:direct printStackTrace:\n");
            throwable.printStackTrace(printWriter);

            // write to file
            String logFile = CRASH_LOG_FILE_PATH + DateUtil.getFormatDate(
                    DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_UNDERLINE, System.currentTimeMillis()) + ".txt";
            FileOutputStream fileOutputStream = new FileOutputStream(logFile);
            fileOutputStream.write(writer.toString().getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
