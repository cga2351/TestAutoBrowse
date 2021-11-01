package com.example.test_auto_browse.task;

import android.text.TextUtils;

import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.utils.DateUtil;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class BrowseBaseTask implements IBrowseTask {
    protected abstract boolean autoBrowse() throws InterruptedException;

//    protected String taskPackageName = "";

    // to check task execute count
    protected int taskStartDay = 0;
    boolean isForceStop = false;

    // kill other apps periodically thread
    private ExecutorService killOtherAppsService = Executors.newSingleThreadExecutor();
    private Future<?> killJob = null;
    private boolean killJobRunning = false;

    @Override
    public String getTargetAppPackageName() { return ""; }

    @Override
    public int waitTaskEndMaxTime() {
        // default wait 30s
        return 1000 * 30;
    }

    @Override
    public void increaseExecCount() {
        // do nothing
//        curExecCountToday++;
    }

    @Override
    public boolean isTaskAvailable() {
        Logger.debug("BrowseBaseTask.isTaskAvailable(), entry");
        boolean isAvailable = true;
        Calendar calendar = Calendar.getInstance();
        int curDay = calendar.get(Calendar.DAY_OF_YEAR);
        int curTime = calendar.get(Calendar.HOUR_OF_DAY);

        if (curDay != taskStartDay) {
            // date goes to a new day, reset the all count
            taskStartDay = curDay;
//            taskStartTimeToday = curTime;
//            curExecCountToday = 0;
        }

        isAvailable = getLeftExecCount() > 0;
        Logger.debug("BrowseBaseTask.isTaskAvailable(), task = " + getClass().getSimpleName() + ", getLeftExecCount() = " + getLeftExecCount());

        Logger.debug("BrowseBaseTask.isTaskAvailable(), task = " + getClass().getSimpleName() + ", isAvailable = " + isAvailable);
        return isAvailable;
    }

    protected int getLeftExecCount() {
        // default return 1, means task can run without count limit
        return 1;
    }

    protected int getMaxExecCount() {
        // default return curExecCount + 1, means task can run without count limit
        return 99999;
    }

    protected int getMaxExecTime() {
        // default can run 24 hours everyday
        return 24;
    }

    @Override
    public boolean getForceStop() {
        return isForceStop;
    }

    @Override
    public void setForceStop(boolean forceStop) {
        isForceStop = forceStop;
    }

//    @Override
//    public String getTargetAppPackageName() {
//        return taskPackageName;
//    }

    ////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;

        // reset the force stop flag
        isForceStop = false;

        if (isTaskAvailable()) {
            if (!TextUtils.isEmpty(getTargetAppPackageName())) {
                // kill all other apps first
//                killAllOtherAppsAsync();
                if (SysUtil.launchApp(getTargetAppPackageName())) {
                    result = true;
                } else {
                    Logger.debug("BrowseBaseTask.initTask(), launch app failed, task = " + getClass().getSimpleName());
                }
            } else {
                // package name is null, need not launch app
                result = true;
            }
        } else {
            Logger.debug("BrowseBaseTask.initTask(), task is not available, task = " + getClass().getSimpleName());
        }

        Logger.debug("BrowseBaseTask.initTask(), result=" + result);
        return result;
    }

    @Override
    public final boolean runTask() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        boolean taskResult = false;
        InterruptedException interruptedException = null;

        try {
            // start kill other apps periodically
            startKillOtherAppsPeriodically();

            // task need to run waitTaskEndMaxTime() at least
            do {
                taskResult = autoBrowse();

                // execute success, increase the execute count
                if (taskResult) {
                    increaseExecCount();
                } else {
                    // get screenshot and save pic
                    String savePath = Constant.SD_PATH_FAILED_SCREENSHOTS
                            + DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_UNDERLINE, System.currentTimeMillis())
                            + "_runTaskFailed_" + getClass().getSimpleName()
                            + ".jpg";
                    UiDriver.saveScreenshot(new File(savePath));
                }
            } while (taskResult && isTaskAvailable() &&
                    System.currentTimeMillis() - startTime < waitTaskEndMaxTime());
        } catch (InterruptedException e) {
            interruptedException = e;
        } finally {
            // wait kill other job thread exit
            waitKillOtherAppsThreadExit();

            Logger.debug("BrowseBaseTask.runTask(), exit"
                    + ", running time = " + (System.currentTimeMillis() - startTime)
                    + ", task = " + getClass().getSimpleName());
        }

        if (null != interruptedException) {
            throw interruptedException;
        }

        return taskResult;
    }

    @Override
    public void endTask() throws InterruptedException {
        // do nothing
    }

    public long getLastDailyTaskCheckTime() {
        return 0;
    }
    public void setLastCheckTime(long lastCheckTime) {
        // do nothing
    }
    public void exeDailyTasks() throws InterruptedException {
        // do nothing
    }

    public void checkDailyTask() throws InterruptedException {
        Logger.debug("BrowseBaseTask.checkDailyTask(), entry, task=" + getClass().getSimpleName());

        if (Constant.BUILD_CONFIG.equals("release")) {
            // check daily and normal task
            if (System.currentTimeMillis() - getLastDailyTaskCheckTime() >= Constant.DAILY_TASK_CHECK_PERIOD) {
                Logger.debug("BrowseBaseTask.checkDailyTask(), execute daily task");
                exeDailyTasks();
                setLastCheckTime(System.currentTimeMillis());
            } else {
                Logger.debug("BrowseBaseTask.checkDailyTask(), not execute daily task");
            }
        }

        Logger.debug("BrowseBaseTask.checkDailyTask(), exit");
    }

    private void killAllOtherAppsAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // switch to phone home page first
                UiDriver.pressHome();

                SysUtil.killAllAppsExclude(getTargetAppPackageName());
            }
        }).start();
    }

    private void startKillOtherAppsPeriodically() {
        if (killJobRunning) {
            Logger.debug("startKillOtherAppsPeriodically(), kill job is running, stop it first");
            waitKillOtherAppsThreadExit();
        }

        // sleep task has no package name
        if (!TextUtils.isEmpty(getTargetAppPackageName())) {
            killJobRunning = true;
            killJob = killOtherAppsService.submit(new Runnable() {
                @Override
                public void run(){
                    long startTime = System.currentTimeMillis();
                    try {
                        Logger.debug("kill other apps periodically thread entry, current task = " + BrowseBaseTask.this.getClass().getSimpleName());
                        while (killJobRunning) {
                            SysUtil.killAllAppsExclude(getTargetAppPackageName());
                            Thread.sleep(1000 * 60);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Logger.debug("kill other apps periodically thread exit, running time = " + (System.currentTimeMillis() - startTime));
                    }
                }
            });
        }
    }

    private void waitKillOtherAppsThreadExit() {
        Logger.debug("waitKillOtherAppsThreadExit() entry");

        if (killJobRunning && null != killJob) {
            killJobRunning = false;

            long startTime = System.currentTimeMillis();
            boolean isKillJobDone;

            while (!killJob.isDone() && (System.currentTimeMillis() - startTime) < 1000 * 20) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isKillJobDone = killJob.isDone();

            Logger.debug("waitKillOtherAppsThreadExit() exit, isKillJobDone = " + isKillJobDone
                    + ", wait time = " + (System.currentTimeMillis() - startTime));
        }

        killJobRunning = false;
        killJob = null;
    }
}
