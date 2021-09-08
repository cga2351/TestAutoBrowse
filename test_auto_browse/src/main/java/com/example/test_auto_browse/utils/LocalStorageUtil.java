package com.example.test_auto_browse.utils;

import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.bean.TaskExecCount;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class LocalStorageUtil {
    // cached task exec count
    private static TaskExecCount cachedTaskExecCount;

    public static void init() {
        initAllLocalStorageFiles();

        // init cached task exec count
        initCachedTaskExecCount();
    }

    private static void initAllLocalStorageFiles() {
        Logger.debug("initAllLocalStorageFiles(), entry");
        // check task exec count file
        File taskExecCountFile = new File(Constant.SD_PATH_TASK_EXEC_COUNT_FILE);
        if (!taskExecCountFile.exists()) {
            // create new task exec count file, and init all count to default value
            try {
                taskExecCountFile.createNewFile();

                TaskExecCount taskExecCount = new TaskExecCount();
                taskExecCount.setSaveDate(System.currentTimeMillis());

                writeTaskExecCount(taskExecCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // check other local storage file
        if (!new File(Constant.SD_PATH_FAILED_SCREENSHOTS).exists()) {
            new File(Constant.SD_PATH_FAILED_SCREENSHOTS).mkdirs();
        }

        // check other local storage file
        if (!new File(Constant.SD_PATH_DEBUG_SCREENSHOTS).exists()) {
            new File(Constant.SD_PATH_DEBUG_SCREENSHOTS).mkdirs();
        }
    }

    public static void initCachedTaskExecCount() {
        Logger.debug("initCachedTaskExecCount(), entry");
        TaskExecCount taskExecCount = readTaskExecCount();
        Calendar now = Calendar.getInstance();
        Calendar taskExecCountSaveDate = Calendar.getInstance();
        taskExecCountSaveDate.setTimeInMillis(taskExecCount.getSaveDate());
        if (now.get(Calendar.DAY_OF_YEAR) != taskExecCountSaveDate.get(Calendar.DAY_OF_YEAR)) {
            // reset all tasks exec count to 0
            taskExecCount = new TaskExecCount();
            taskExecCount.setSaveDate(now.getTimeInMillis());
            taskExecCount.setNextTaskIndex(1);
            LocalStorageUtil.writeTaskExecCount(taskExecCount);
        }

        cachedTaskExecCount = taskExecCount;
    }

    public static void resetCachedTaskExecCount() {
        Logger.debug("resetCachedTaskExecCount(), entry");
        updateCachedTaskExecCount(new TaskExecCount());
    }

    public static void updateCachedTaskExecCount(TaskExecCount taskExecCount) {
        Logger.debug("updateCachedTaskExecCount(), entry");
        cachedTaskExecCount = new TaskExecCount(taskExecCount);

        // save to file
        writeTaskExecCount(cachedTaskExecCount);
    }

    public static TaskExecCount getCachedTaskExecCount() {
        Logger.debug("getCachedTaskExecCount(), entry");
        // check if time goes to a new day
        Calendar now = Calendar.getInstance();
        Calendar cachedDate = Calendar.getInstance();
        cachedDate.setTimeInMillis(cachedTaskExecCount.getSaveDate());
        if (now.get(Calendar.DAY_OF_YEAR) != cachedDate.get(Calendar.DAY_OF_YEAR)) {
            // reset all tasks exec count to 0
            cachedTaskExecCount = new TaskExecCount();
            cachedTaskExecCount.setSaveDate(now.getTimeInMillis());
            cachedTaskExecCount.setNextTaskIndex(1);
            LocalStorageUtil.writeTaskExecCount(cachedTaskExecCount);

//            updateCachedTaskExecCount(new TaskExecCount().setSaveDate(now.getTimeInMillis()));
        }

        return cachedTaskExecCount;
    }

    private static TaskExecCount readTaskExecCount() {
        String savedTaskExecCount = FileUtil.readFileString(Constant.SD_PATH_TASK_EXEC_COUNT_FILE);
        return GsonUtil.fromJson(savedTaskExecCount, TaskExecCount.class);
    }

    private static void writeTaskExecCount(TaskExecCount taskExecCount) {
        Logger.debug("writeTaskExecCount(), taskExecCount = " + taskExecCount);
        FileUtil.saveFileString(Constant.SD_PATH_TASK_EXEC_COUNT_FILE, GsonUtil.toJson(taskExecCount));
    }
}
