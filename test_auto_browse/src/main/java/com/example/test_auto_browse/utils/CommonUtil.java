package com.example.test_auto_browse.utils;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;

import com.example.test_auto_browse.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2017/12/1
 * description :
 */

public class CommonUtil {

//    private static final long SCENARIO_SCRIPT_KEEP_PERIOD = 30 * 24 * 60 * 60 * 1000L;  //30 days
    private static final long SCENARIO_RESULT_KEEP_PERIOD = 20 * 60 * 1000L;   //10 minutes
    private static final long EMULATOR_SCENARIO_RESULT_KEEP_PERIOD = 20 * 60 * 1000L;   //10 minutes

    private static final long SCREENSHOTS_FILES_KEEP_PERIOD = 5 * 24 * 60 * 60 * 1000L;  //5 days

    public static void deleteOldFiles() {
        // delete old log files
        Logger.deleteOldLogFile();

        // delete old screenshots files
        deleteOldScreenshotsFiles();

        // delete old scenario script
//        deleteOldScenarioScripts();

        // delete old scenario result and debug result
        // schedule scenario result delete by connector
//		deleteOldScenarioResults(Constant.SD_PATH_MQMS_SCENARIO_RESULT);
//        deleteOldScenarioResults(Constant.SD_PATH_MQMS_DEBUG_RESULT);

    }

    public static void deleteOldScreenshotsFiles() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long curTimeStamp = calendar.getTimeInMillis();

        // delete failed screenshot files
        File failedScreenShotsDir = new File(Constant.SD_PATH_FAILED_SCREENSHOTS);
        File[] failedScreenshotsFiles = failedScreenShotsDir.listFiles();
        if (null != failedScreenshotsFiles) {
            long timeToDelete = curTimeStamp - SCREENSHOTS_FILES_KEEP_PERIOD;
            for (File screenshotFile : failedScreenshotsFiles) {
                long modifyTime = screenshotFile.lastModified();
                if (modifyTime < timeToDelete) {
                    if (screenshotFile.isDirectory()) {
                        FileUtil.deleteDir(screenshotFile);
                    } else {
                        screenshotFile.delete();
                    }
                }
            }
        }

        // delete debug screenshot files
        File debugScreenshotDir = new File(Constant.SD_PATH_DEBUG_SCREENSHOTS);
        File[] debugScreenshotFiles = debugScreenshotDir.listFiles();
        if (null != debugScreenshotFiles) {
            long timeToDelete = curTimeStamp - SCREENSHOTS_FILES_KEEP_PERIOD;
            for (File screenshotFile : debugScreenshotFiles) {
                long modifyTime = screenshotFile.lastModified();
                if (modifyTime < timeToDelete) {
                    if (screenshotFile.isDirectory()) {
                        FileUtil.deleteDir(screenshotFile);
                    } else {
                        screenshotFile.delete();
                    }
                }
            }
        }
    }

//    public static void deleteOldScenarioScripts() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        long curTimeStamp = calendar.getTimeInMillis();
//
//        File scenarioScriptDir = new File(Constant.SD_PATH_MQMS_SCENARIO_SCRIPT);
//        File[] scriptFiles = scenarioScriptDir.listFiles();
//        long timeToDeleteScript = curTimeStamp - SCENARIO_SCRIPT_KEEP_PERIOD;
//        for (File scriptFile : scriptFiles) {
//            long modifyTime = scriptFile.lastModified();
//            if (modifyTime < timeToDeleteScript) {
//                if (scriptFile.isDirectory()) {
//                    FileUtil.deleteDir(scriptFile);
//                } else {
//                    scriptFile.delete();
//                }
//            }
//        }
//    }
//
//    private static void deleteOldScenarioResults(String resultDir) {
//        File scenarioResultDir = new File(resultDir);
//        File[] jobFiles = scenarioResultDir.listFiles();
//        long timeToDeleteResult = 0;
//        if (SysUtil.isEmulator()) {
//            timeToDeleteResult = SysUtil.currentTimeMillis() - EMULATOR_SCENARIO_RESULT_KEEP_PERIOD;
//        } else {
//            timeToDeleteResult = SysUtil.currentTimeMillis() - SCENARIO_RESULT_KEEP_PERIOD;
//        }
//        long modifyTime;
//
//        for (File jobFile : jobFiles) {
//            if (jobFile.isDirectory()) {
//                // job directory, traverse all job results and delete old results
//                File[] resultFiles = jobFile.listFiles();
//                for (File resultFile : resultFiles) {
//                    modifyTime = resultFile.lastModified();
//                    if (modifyTime < timeToDeleteResult) {
//                        if (resultFile.isDirectory()) {
//                            FileUtil.deleteDir(resultFile);
//                        } else {
//                            resultFile.delete();
//                        }
//                    }
//                }
//                if (jobFile.listFiles() == null || jobFile.listFiles().length == 0) {
//                    FileUtil.deleteDir(jobFile);
//                }
//            } else {
//                modifyTime = jobFile.lastModified();
//                // job zip files, delete directly
//                if (modifyTime < timeToDeleteResult) {
//                    jobFile.delete();
//                }
//            }
//        }
//    }

//    @NonNull
//    public static String getImageBase64Data(String imageFile) {
//        String image = "";
//        if (null != imageFile && imageFile.length() > 0) {
//            try {
//                FileInputStream fileInputStream = new FileInputStream(imageFile);
//                int fileLen = fileInputStream.available();
//                int readLen = 0;
//                byte[] imageData = new byte[fileLen];
//                while (readLen < fileLen) {
//                    readLen += fileInputStream.read(imageData, readLen, fileLen - readLen);
//                }
//
//                byte[] imageEncodedData = Base64.encode(imageData, Base64.DEFAULT);
//                image = new String(imageEncodedData, Charset.defaultCharset());
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return image;
//    }
//
//    @NonNull
//    public static void saveImageBase64DataToFile(String imageData, String imageFile) {
//        if (!TextUtils.isEmpty(imageData) && !TextUtils.isEmpty(imageFile)) {
//            try {
//                byte[] imageDecodedData = Base64.decode(imageData, Base64.DEFAULT);
//                FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
//                fileOutputStream.write(imageDecodedData);
//                fileOutputStream.flush();
//                fileOutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void adjustRectIntoScreen(Rect rect) {
//        if (null != rect) {
//            DisplayManager displayManager =
//                    (DisplayManager) MQMSApplication.getApplication().getSystemService(Context.DISPLAY_SERVICE);
//            Display display = displayManager.getDisplay(0);
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            display.getRealMetrics(displayMetrics);
//            if (rect.left < 0 || rect.left > displayMetrics.widthPixels) {
//                rect.left = 0;
//            }
//            if (rect.right < 0 || rect.right > displayMetrics.widthPixels) {
//                rect.right = displayMetrics.widthPixels;
//            }
//            if (rect.top < 0 || rect.top > displayMetrics.heightPixels) {
//                rect.top = 0;
//            }
//            if (rect.bottom < 0 || rect.bottom > displayMetrics.heightPixels) {
//                rect.bottom = displayMetrics.heightPixels;
//            }
//            if (rect.right - rect.left <= 0) {
//                rect.left = 0;
//                rect.right = displayMetrics.widthPixels;
//            }
//            if (rect.bottom - rect.top <= 0) {
//                rect.top = 0;
//                rect.bottom = displayMetrics.heightPixels;
//            }
//        }
//    }

//    public static boolean useMQMSSpace() {
//        return true;
////        return false;
////        return MQMSApplication.getApplication().getAgentConfig().getBooleanProperty(Constant.PROP_USE_MQMS_HOOK);
//    }

    public static String getThrowableString(Throwable ex) {
        if (ex == null) {
            return "";
        }
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }

    public static int index2Count(int index) {
        return index + 1;
    }

    public static void copyScriptFile(String srcScriptFilePath, String dstScriptFileDir) {
        String dstScriptFileName = srcScriptFilePath.substring(
                srcScriptFilePath.lastIndexOf(Constant.SEPARATOR) + 1);

        copyScriptFile(srcScriptFilePath, dstScriptFileDir, dstScriptFileName);
    }

    public static void copyScriptFile(String srcScriptFilePath, String dstScriptFileDir, String dstScriptFileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                Files.copy(new File(srcScriptFilePath).toPath(),
                        new File(dstScriptFileDir + dstScriptFileName).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.error("-mqmsdebug", "copyScriptFile(), copy file error, info:" + e.getMessage());
            }
        } else {
            // cp /data/local/tmp/Scenario_SendImageMessage.zip /sdcard/mqms/ScenarioScript/
            ShellUtil.command(false, "cp " + srcScriptFilePath + " " + dstScriptFileDir + dstScriptFileName);
        }

        // delete src script file
//		new File(srcScriptFilePath).delete();
    }

//    public static boolean unZipScriptFile(String scenarioZipFile, String scenarioDestPath, boolean deleteOldDir) {
//        boolean unzipResult = false;
//        int tryCount = 0;
//        long startTime = SysUtil.currentTimeMillis();
//        while (!unzipResult && (SysUtil.currentTimeMillis() - startTime < 5000)) {
//            unzipResult = FileUtil.unZip(scenarioZipFile, scenarioDestPath, deleteOldDir);
//            tryCount++;
//
//            // unzip failed, re-try after 100ms
//            if (!unzipResult) {
//                Logger.error("-mqmsdebug", "CommonUtil.unZipScriptFile(), failed, retry, cur tryCount=" + tryCount);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        if (unzipResult) {
//            Logger.debug("-mqmsdebug",
//                    "CommonUtil.unZipScriptFile(), unzip script file success"
//                            + ", unzip dest path = " + scenarioDestPath
//                            + ", tryCount = " + tryCount);
//        } else {
//            Logger.error("-mqmsdebug", "CommonUtil.unZipScriptFile() "
//                    + ", unzip script failed, tryCount = " + tryCount);
//        }
//
//        return unzipResult;
//    }
}
