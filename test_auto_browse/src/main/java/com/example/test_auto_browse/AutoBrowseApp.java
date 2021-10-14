package com.example.test_auto_browse;

import android.app.UiAutomation;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.android.uiautomator.core.UiAutomatorBridge;
import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.bean.TaskExecCount;
import com.example.test_auto_browse.task.diantao.DianTaoBrowseLiveRepeatTask;
import com.example.test_auto_browse.task.diantao.DianTaoBrowseVideoRepeatTask;
import com.example.test_auto_browse.task.diantao.DianTaoWalkRepeatTaskScratch;
import com.example.test_auto_browse.task.diantao.DianTaoWalkRepeatTaskStepsToGold;
import com.example.test_auto_browse.task.diantao.DianTaoWalkRepeatTaskWatchLive10Min;
import com.example.test_auto_browse.task.diantao.DianTaoWalkRepeatTaskWatchLive30Sec;
import com.example.test_auto_browse.task.diantao.DianTaoWalkRepeatTaskWatchLive3Min;
import com.example.test_auto_browse.task.diantao.DianTaoWalkRepeatTaskWatchLive5Min;
import com.example.test_auto_browse.task.diantao.DianTaoWalkRepeatTaskWatchLive8Min;
import com.example.test_auto_browse.task.diantao.DianTaoWalkRepeatTaskWatchVideo30Sec;
import com.example.test_auto_browse.task.diantao.DianTaoWalkRepeatTaskWatchVideo60Sec;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchGoldenLive30Sec;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchGoldenLive3Min;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchGoldenLive60Sec;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchLive10Min;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchLive30Sec;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchLive3Min;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchLive5Min;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchLive60Sec;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchLive8Min;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchVideo30Sec;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchVideo5Min;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.task.diantao.DianTaoWorkRepeatTaskWatchVideo60Sec;
import com.example.test_auto_browse.task.douyin.DouYinTreasureBoxTimedTask;
import com.example.test_auto_browse.task.douyin.DouYinWatchAdsTimedTask;
import com.example.test_auto_browse.task.jingdong.JingDongBrowseActivityRepeatTask;
import com.example.test_auto_browse.task.jingdong.JingDongBrowseGoodsRepeatTask;
import com.example.test_auto_browse.task.jingdong.JingDongBrowseVideoRepeatTask;
import com.example.test_auto_browse.task.kuaishou.KuaiShouBrowseVideoRepeatTask;
import com.example.test_auto_browse.task.SleepTask;
import com.example.test_auto_browse.task.kuaishou.KuaiShouRewardRepeatTask;
import com.example.test_auto_browse.task.kuaishou.KuaiShouWatchLiveRepeatTask;
import com.example.test_auto_browse.task.qiyi.QiYiBrowseAdsRepeatTask;
import com.example.test_auto_browse.task.qiyi.QiYiTreasureBoxTimedTask;
import com.example.test_auto_browse.task.qukan.QuKanBrowseArticleRepeatTask;
import com.example.test_auto_browse.task.qukan.QuKanBrowseShortVideoRepeatTask;
import com.example.test_auto_browse.task.qukan.QuKanBrowseVideoRepeatTask;
import com.example.test_auto_browse.task.toutiao.TouTiaoBrowseVideoRepeatTask;
import com.example.test_auto_browse.utils.DateUtil;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/5/19
 * description :
 */

public class AutoBrowseApp {
    private static AutoBrowseApp instance = null;

    // task scheduling thread
    private HandlerThread taskScheduleThread = null;
    private Handler taskScheduleThreadHandler = null;

    // auto browse repeatedly task thread
    private Thread repeatTaskThread = null;
    private boolean repeatTaskNeedExit = true;
    private boolean repeatTaskRunning = false;
    private final Object repeatTaskSyncObj = new Object();
    private IBrowseTask curRunningRepeatTask = null;

    // sleep task thread
    private Thread sleepTaskThread = null;
    private boolean sleepTaskRunning = false;
    private final Object sleepTaskSyncObj = new Object();
    private ScheduledExecutorService sleepTaskExecutor = Executors.newSingleThreadScheduledExecutor();

    // all repeat task id define
    private ArrayList<IBrowseTask> allRepeatTasks = new ArrayList<>();

    public void run() {

        SimpleUncaughtCrashHandler.getInstance().init();

        Logger.init();
        SysUtil.init();
        UiDriver.init();
        CoordsAdapter.init();
        LocalStorageUtil.init();

        // light screen on first
        UiDriver.lightScreenOn();

        // init task <-> message map
        initAllRepeatTask();

        //
        startCheckBatteryInfoTimer();

        // start auto browse scheduling thread
        initAutoBrowseSchedulingThread();

        if (Constant.BUILD_CONFIG.equals("release")) {
            // kill all apps first
            SysUtil.killAllApps();

            // start sleep task check timer
            startSleepTaskTimer();

            // start repeat task
            sendRepeatTaskMsg(LocalStorageUtil.getCachedTaskExecCount().getNextTaskIndex());
        }

        /////////////////////////////////////////////////////////////////////////////////
        if (Constant.BUILD_CONFIG.equals("debug")) {
            try {
                // test code
                testFunction();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /////////////////////////////////////////////////////////////////////////////////

        // wait autoBrowseSchedulingThread exit
        try {
            taskScheduleThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initAutoBrowseSchedulingThread() {
        taskScheduleThread = new HandlerThread("AutoBrowseScheduling");
        taskScheduleThread.start();
        taskScheduleThreadHandler = new Handler(taskScheduleThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                final IBrowseTask autoBrowseTask = (IBrowseTask)message.obj;
                Logger.debug("handleMessage(), autoBrowseTask=" + autoBrowseTask.getClass().getSimpleName()
                        + ", sleepTaskRunning=" + sleepTaskRunning);
                if (!sleepTaskRunning) {
                    // some tasks can only run several times per day, need check it
//                    int taskLeftExecCount = autoBrowseTask.getLeftExecCount();
//                    Logger.debug("handleMessage(), taskLeftExecCount=" + taskLeftExecCount);
//                    if (taskLeftExecCount > 0) {
                    switch (message.what) {
                        case Constant.TASK_MSG_SLEEP_TASK:
                            Logger.debug("handleMessage(), start sleep task");
                            startSleepTask(autoBrowseTask);
                            break;
                        case Constant.TASK_MSG_ALL_APPS_TIMED_TASK:
                            Logger.debug("handleMessage(), run all apps timed task");
//                            startTimedTaskThread(autoBrowseTask);
                            break;
                        case Constant.TASK_MSG_REPEAT_TASK:
                            Logger.debug("handleMessage(), start repeat task, autoBrowseTask=" + autoBrowseTask.getClass().getSimpleName());
                            startRepeatTaskThread(autoBrowseTask);
                            break;
                    }
//                    } else {
//                        // current task has no left execute count, start next repeat task
//                        sendNextRepeatTaskMsg();
//                        Logger.debug("handleMessage(), taskLeftExecCount <= 0, start next repeat task" );
//                    }
                } else {
                    Logger.debug("handleMessage(), sleep task is running, ignore current task, task=" + autoBrowseTask.getClass().getSimpleName());
                }
                return true;
            }
        });
    }

    private void startCheckBatteryInfoTimer() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // print battery temperature
                Logger.debug("record battery info periodically"
                                + ", temp = " + SysUtil.getBatteryTemperature()
                                + ", level = " + SysUtil.getBatteryLevel() + "%"
                                + ", voltage = " + SysUtil.getBatteryVoltage());
            }
        }, 0, 1000 * 60 * 10);
    }

    private void initAllRepeatTask() {
        if (Constant.BUILD_CONFIG.equals("debug")) {
            Logger.debug("initAllRepeatTask(), set next task index");
//            LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount().setNextTaskIndex(0));
            LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount().setNextTaskIndex(11));
        }

        allRepeatTasks.add(TouTiaoBrowseVideoRepeatTask.getInstance());     // 0
        allRepeatTasks.add(DianTaoBrowseVideoRepeatTask.getInstance());     // 1
        allRepeatTasks.add(DianTaoBrowseLiveRepeatTask.getInstance());      // 2
        allRepeatTasks.add(DianTaoWalkRepeatTaskWatchLive30Sec.getInstance());      // 3
        allRepeatTasks.add(DianTaoWalkRepeatTaskWatchVideo60Sec.getInstance());     // 4
        allRepeatTasks.add(DianTaoWalkRepeatTaskWatchVideo30Sec.getInstance());      // 5
        allRepeatTasks.add(DianTaoWalkRepeatTaskWatchLive3Min.getInstance());       // 6
        allRepeatTasks.add(DianTaoWalkRepeatTaskWatchLive5Min.getInstance());       // 7
        allRepeatTasks.add(DianTaoWalkRepeatTaskWatchLive8Min.getInstance());       // 8
        allRepeatTasks.add(DianTaoWalkRepeatTaskWatchLive10Min.getInstance());      // 9
        allRepeatTasks.add(DianTaoWalkRepeatTaskScratch.getInstance());     // 10
        allRepeatTasks.add(DianTaoWalkRepeatTaskStepsToGold.getInstance());     // 11
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchGoldenLive30Sec.getInstance());      // 12
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchGoldenLive60Sec.getInstance());      // 13
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchGoldenLive3Min.getInstance());      // 14
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchLive30Sec.getInstance());      // 15
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchLive60Sec.getInstance());      // 16
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchLive3Min.getInstance());       // 17
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchLive5Min.getInstance());       // 18
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchLive8Min.getInstance());       // 19
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchLive10Min.getInstance());      // 20
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchVideo60Sec.getInstance());     // 21
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchVideo30Sec.getInstance());      // 22
        allRepeatTasks.add(DianTaoWorkRepeatTaskWatchVideo5Min.getInstance());      // 23
        allRepeatTasks.add(KuaiShouBrowseVideoRepeatTask.getInstance());        // 24
        allRepeatTasks.add(KuaiShouWatchLiveRepeatTask.getInstance());      // 25
        allRepeatTasks.add(KuaiShouRewardRepeatTask.getInstance());     // 26
        allRepeatTasks.add(JingDongBrowseActivityRepeatTask.getInstance());     // 27
        allRepeatTasks.add(JingDongBrowseVideoRepeatTask.getInstance());        // 28
        allRepeatTasks.add(JingDongBrowseGoodsRepeatTask.getInstance());        // 29
        allRepeatTasks.add(QuKanBrowseShortVideoRepeatTask.getInstance());      // 30
        allRepeatTasks.add(QuKanBrowseArticleRepeatTask.getInstance());     // 31
        allRepeatTasks.add(QuKanBrowseVideoRepeatTask.getInstance());       // 32
        allRepeatTasks.add(QiYiBrowseAdsRepeatTask.getInstance());      // 33
    }

    public static final AutoBrowseApp getInstance() {
        if (null == instance) {
            synchronized (AutoBrowseApp.class) {
                if (null == instance) {
                    instance = new AutoBrowseApp();
                }
            }
        }
        return instance;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
//    private static AppiumFacadeImpl appiumFacade;

    private static UiAutomatorBridge originUiAutomatorBridge;
    private static UiAutomation originUiAutomation;

//    public static void initAppiumFacade() {
////        if (null != appiumFacade) {
////            Logger.debug("-mqmsdebug", "initAppiumFacade(), appiumFacade not null, interrupt appiumFacade thread");
////            appiumFacade.interrupt();
////            appiumFacade = null;
////        }
//
//        if (null == appiumFacade) {
//            Logger.debug("-mqmsdebug", "initAppiumFacade(), create AppiumFacadeImpl");
//            Bundle params = new Bundle();
//
//            //disable watchers, crash watchers may consume too much time sometimes,
//            params.putString("disableAndroidWatchers", "true");
//
//            appiumFacade = new AppiumFacadeImpl(params);
//            appiumFacade.start();
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        appiumFacade.connectAppiumFacade();
//    }
//
//    public static boolean resetAppiumConnection() {
//        if (null != appiumFacade) {
//            appiumFacade.resetAppiumConnection();
//        }
//        return true;
//    }
//
//    public static AppiumFacadeImpl getAppiumFacade() {
//        return appiumFacade;
//    }

    public static UiAutomatorBridge getOriginUiAutomatorBridge() {
        return originUiAutomatorBridge;
    }

    public static UiAutomation getOriginUiAutomation() {
        return originUiAutomation;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    public void sendSleepTaskMsg() {
        Message msg = Message.obtain();
        msg.what = Constant.TASK_MSG_SLEEP_TASK;
        msg.obj = SleepTask.getInstance();
        taskScheduleThreadHandler.sendMessage(msg);
    }


    public void sendNextRepeatTaskMsg() {
        int nextTaskIndex = LocalStorageUtil.getCachedTaskExecCount().getNextTaskIndex();
        Logger.debug("sendNextRepeatTaskMsg(), nextTaskIndex=" + nextTaskIndex);
        sendRepeatTaskMsg(nextTaskIndex);
    }
    public void sendRepeatTaskMsg(int repeatTask) {
        Logger.debug("sendRepeatTaskMsg(), repeatTask=" + repeatTask);

        if (0 == repeatTask) {
            Logger.debug("sendRepeatTaskMsg(), all tasks have run once");
        }

        Message msg = Message.obtain();
        msg.what = Constant.TASK_MSG_REPEAT_TASK;
        msg.obj = allRepeatTasks.get(repeatTask);

        Logger.debug("sendBrowseRepeatedTaskMsg(), browse repeatedly task=" + msg.obj.getClass().getSimpleName());
        taskScheduleThreadHandler.sendMessage(msg);

        // update next task index
        repeatTask++;
        repeatTask %= allRepeatTasks.size();
        LocalStorageUtil.updateCachedTaskExecCount(
                LocalStorageUtil.getCachedTaskExecCount().setNextTaskIndex(repeatTask));
    }

//    public void sendAllAppsTimedTaskMsg() {
//        Message msg = Message.obtain();
//        msg.what = Constant.TASK_MSG_ALL_APPS_TIMED_TASK;
//        msg.obj = TimedTaskForAllApps.getInstance();
//        taskScheduleThreadHandler.sendMessage(msg);
//    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private void startSleepTask(final IBrowseTask autoBrowseTask) {
        Logger.debug("startSleepTask(), entry");
        synchronized (sleepTaskSyncObj) {
            Logger.debug("startSleepTask(), create sleepTaskThread");
            sleepTaskThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    sleepTaskRunning = true;
                    try {
                        autoBrowseTask.initTask();
                        autoBrowseTask.runTask();
                        autoBrowseTask.endTask();
                    } catch (InterruptedException e) {
                        Logger.debug("startSleepTask, autoBrowseTask exception, info:" + e.getMessage());
                        e.printStackTrace();
                    }
                    sleepTaskRunning = false;

                    // start next repeatedly browse task
                    sendNextRepeatTaskMsg();
                }
            });
            sleepTaskThread.start();
        }
    }

    private void startRepeatTaskThread(final IBrowseTask autoBrowseTask) {
        Logger.debug("startRepeatTaskThread(), entry");
        synchronized(repeatTaskSyncObj) {
            Logger.debug("startRepeatTaskThread(), create repeatTaskThread");
            repeatTaskThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Logger.debug("repeatTaskThread, entry");
                    repeatTaskRunning = true;
                    repeatTaskNeedExit = false;
                    curRunningRepeatTask = autoBrowseTask;

                    Logger.debug("repeatTaskThread, repeatTask=" + autoBrowseTask.getClass().getSimpleName());
                    boolean taskResult = false;

                    try {
                        taskResult = autoBrowseTask.initTask();
                        if (taskResult) {
                           // browse video repeatedly
                            do {
                                taskResult = autoBrowseTask.runTask();
                                Logger.debug("repeatTaskThread, auto browse once end, taskResult=" + taskResult);

                                // check if has left exec count
                                if (!autoBrowseTask.isTaskAvailable()) {
                                    Logger.debug("repeatTaskThread, task is not available");
                                    taskResult = false;
                                }
                            } while (!repeatTaskNeedExit && taskResult);
                        } else {
                            Logger.debug("repeatTaskThread, init task failed");

                            // save the task is available but init failed screen shot
                            if (autoBrowseTask.isTaskAvailable()) {
                                // get screenshot and save pic
                                String savePath = Constant.SD_PATH_FAILED_SCREENSHOTS
                                        + DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_UNDERLINE, System.currentTimeMillis())
                                        + "_initTaskFailed_" + curRunningRepeatTask.getClass().getSimpleName()
                                        + ".jpg";
                                UiDriver.saveScreenshot(new File(savePath));
                            }

                        }
                        autoBrowseTask.endTask();
                        Logger.debug("repeatTaskThread, autoBrowseTask end, taskResult=" + taskResult + ", repeatTaskNeedExit=" + repeatTaskNeedExit);
                    } catch (InterruptedException e) {
                        Logger.debug("repeatTaskThread, autoBrowseTask exception, info:" + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        try {
                            autoBrowseTask.endTask();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    repeatTaskRunning = false;

                    // if repeat task need not exit, and taskResult is false, continue next repeat task
                    if (!repeatTaskNeedExit && !taskResult) {
                        // start next repeat task
                        Logger.debug("repeatTaskThread, repeat task need not to exit, continue next repeat task");
                        sendNextRepeatTaskMsg();
                    }
                    Logger.debug("repeatTaskThread, exit, taskResult=" + taskResult);
                }
            });

            repeatTaskThread.start();
        }
    }

    public boolean stopRepeatTaskThread() {
        Logger.debug("stopRepeatTaskThread(), entry");
        boolean result = true;

        synchronized(repeatTaskSyncObj) {
            Logger.debug("stopRepeatTaskThread(), check repeat task thread running state");
            if (repeatTaskRunning && null != repeatTaskThread) {
                Logger.debug("stopRepeatTaskThread(), repeatTaskThread is alive = " + repeatTaskThread.isAlive());
                try {
                    // set the repeat thread exit flag and task force stop flag
                    repeatTaskNeedExit = true;
                    curRunningRepeatTask.setForceStop(true);

                    int maxWaitEndTime = curRunningRepeatTask.waitTaskEndMaxTime();
                    Logger.debug("stopRepeatTaskThread(), maxWaitEndTime = " + maxWaitEndTime / 1000 + "s"
                            + ", task = " + curRunningRepeatTask.getClass().getSimpleName());
                    repeatTaskThread.join(maxWaitEndTime);

                    if (repeatTaskThread.isAlive()) {
                        // force stop thread, interrupt thread, and wait exit
                        long startTime = System.currentTimeMillis();
                        while (repeatTaskThread.isAlive() && (System.currentTimeMillis() - startTime) < 1000 * 30) {
                            Logger.debug("stopRepeatTaskThread(), interrupt repeatTaskThread");
                            repeatTaskThread.interrupt();
                            repeatTaskThread.join(300);
                        }
                    }

                    if (repeatTaskThread.isAlive()) {
                        Logger.debug("stopRepeatTaskThread(), stop repeatTaskThread failed, thread is still running");
                    } else {
                        repeatTaskThread = null;
                        curRunningRepeatTask = null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result = !repeatTaskRunning;
            } else {
                Logger.debug("stopRepeatTaskThread(), repeatTaskThread is not running");
            }
            Logger.debug("stopRepeatTaskThread(), exit, result=" + result);
        }

        return result;
    }

    private void waitSleepTaskThreadExit() {
        Logger.debug("waitSleepTaskThreadExit(), entry, sleepTaskThread = " + sleepTaskThread);
        long startTime = System.currentTimeMillis();
        try {
            if (null != sleepTaskThread) {
                sleepTaskThread.join();
                sleepTaskThread = null;
            } else {
                Logger.debug("waitSleepTaskThreadExit(), sleep task is not running");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.debug("waitSleepTaskThreadExit(), exit, waitTime = " + (System.currentTimeMillis() - startTime) / 1000 + "s");
    }

//    public void waitTimedTaskThreadExit() {
//        Logger.debug("waitTimedTaskThreadExit(), entry, timedTaskThread = " + timedTaskThread);
//        long startTime = System.currentTimeMillis();
//        try {
//            if (null != timedTaskThread) {
//                Logger.debug("waitTimedTaskThreadExit(), timedTaskThread is alive = " + timedTaskThread.isAlive());
//                timedTaskThread.join();
//                timedTaskThread = null;
//            } else {
//                Logger.debug("waitTimedTaskThreadExit(), timedTaskThread is null");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Logger.debug("waitTimedTaskThreadExit() exit, wait time = " + (System.currentTimeMillis() - startTime));
//    }

    private void startSleepTaskTimer() {
        sleepTaskExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                sendSleepTaskMsg();

                // wait 2s the sleep task thread start,
                // and then wait sleep task end, to start next sleep task schedule
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                waitSleepTaskThreadExit();
            }
        }, Constant.SLEEP_TASK_PERIOD, Constant.SLEEP_TASK_PERIOD, TimeUnit.MILLISECONDS);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    private void setTaskExecCount() {
        TaskExecCount readTaskExecCount = LocalStorageUtil.getCachedTaskExecCount();

        readTaskExecCount.setDianTaoWalkWatchLive3MinExecCount(3);
        readTaskExecCount.setDianTaoWalkWatchLive5MinExecCount(3);
        readTaskExecCount.setDianTaoWalkWatchLive8MinExecCount(2);
        readTaskExecCount.setDianTaoWalkWatchLive10MinExecCount(2);
        readTaskExecCount.setDianTaoWalkWatchLive30SecExecCount(10);
        readTaskExecCount.setDianTaoWalkWatchVideo30SecExecCount(3);
        readTaskExecCount.setDianTaoWalkWatchVideo60SecExecCount(5);

        readTaskExecCount.setDianTaoWorkWatchLive3MinExecCount(1);
        readTaskExecCount.setDianTaoWorkWatchLive5MinExecCount(1);
        readTaskExecCount.setDianTaoWorkWatchLive8MinExecCount(1);
        readTaskExecCount.setDianTaoWorkWatchLive10MinExecCount(1);
        readTaskExecCount.setDianTaoWorkWatchLive60SecExecCount(2);
        readTaskExecCount.setDianTaoWorkWatchVideo30SecExecCount(3);
        readTaskExecCount.setDianTaoWorkWatchVideo5MinExecCount(2);

        readTaskExecCount.setKuaiShouRewardExecCount(10);
        readTaskExecCount.setKuaiShouTreasureBoxExecCount(10);
        readTaskExecCount.setKuaiShouWatchLiveExecCount(4);

        readTaskExecCount.setTouTiaoWatchAdsExecCount(10);
        readTaskExecCount.setTouTiaoBrowseGoodsExecCount(3);

        LocalStorageUtil.updateCachedTaskExecCount(readTaskExecCount);
    }

    public boolean douYinWatchAds() throws InterruptedException {
        boolean hasAds = false;

        // check if has ads
        UiSelector closeAds = new UiSelector().description(Constant.STR_DOU_YIN_CLOSE_ADS);
        UiSelector adsEndPrompt1 = new UiSelector().descriptionContains(Constant.STR_DOU_YIN_WAIT_ADS_END_1);
        UiSelector adsEndPrompt2 = new UiSelector().descriptionContains(Constant.STR_DOU_YIN_WAIT_ADS_END_2);
        Thread.sleep(5000);
        if (null != UiDriver.find(closeAds, 3000) ||
                null != UiDriver.find(adsEndPrompt1, 3000) ||
                null != UiDriver.find(adsEndPrompt2, 3000)) {
            // watch the ads video and close ads
            Thread.sleep(25000);
            if (null == UiDriver.find(adsEndPrompt1, 3000)) {
                Logger.debug("DouYinBaseTask.douYinWatchAds(), watch first ads end");
                if (!UiDriver.findAndClick(closeAds, 3000)) {
                    // 没有关闭，直接返回
                    UiDriver.pressBack();
                }

                // check if has more ads
                UiSelector watchMoreSelector = new UiSelector().descriptionStartsWith(Constant.STR_DOU_YIN_WATCH_MORE);
                if (null != UiDriver.find(watchMoreSelector)) {
                    while (null != UiDriver.find(watchMoreSelector)) {
                        Thread.sleep(2000);
                        Logger.debug("DouYinBaseTask.douYinWatchAds(), watch more ads");
                        UiDriver.findAndClick(watchMoreSelector);

                        // no more video, return
                        if (null != UiDriver.find(new UiSelector().text(Constant.STR_DOU_YIN_NO_MORE_ADS), 10000)) {
                            Logger.debug("DouYinBaseTask.douYinWatchAds(), no more new ads");
                            break;
                        }

                        // has new ads video, wait ads end
                        Thread.sleep(20000);
                        // wait ads end
                        long startTime = System.currentTimeMillis();
                        while (null != UiDriver.find(adsEndPrompt1, 5000) && (System.currentTimeMillis() - startTime) < 5000) {
                            Thread.sleep(2000);
                        }
                        // close ads
                        if (!UiDriver.findAndClick(closeAds, 3000)) {
                            // 没有关闭，直接返回
                            UiDriver.pressBack();
                        }
                        Thread.sleep(3000);
                    }

                    // close ads page
                    UiDriver.findAndClick(new UiSelector().description(Constant.STR_DOU_YIN_CLOSE_ADS_CONFIRM));
                    Thread.sleep(2000);
                    Logger.debug("DouYinBaseTask.douYinWatchAds(), all ads end");
                } else {
                    UiDriver.findAndClick(new UiSelector().description(Constant.STR_DOU_YIN_CLOSE_ADS_CONFIRM));
                }

                Thread.sleep(2000);
                UiDriver.pressBack();
            } else {
                Logger.debug("DouYinBaseTask.douYinWatchAds(), ads have not end");
            }

            hasAds = true;
        } else {
            Logger.debug("DouYinBaseTask.douYinWatchAds(), open ads page failed");
        }

        return hasAds;
    }

    private void testExecuteService() throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Logger.debug("test service entry");
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.debug("test service exit");
            }
        };

        Logger.debug("test service 1");
        Future<?> future = service.submit(runnable);
        Logger.debug("test service 2, future.isDone()=" + future.isDone());
        Thread.sleep(5000);
        Logger.debug("test service 3, future.isDone()=" + future.isDone());
        Thread.sleep(10000);
        Logger.debug("test service 4, future.isDone()=" + future.isDone());
        future = service.submit(runnable);
        Logger.debug("test service 5, future.isDone()=" + future.isDone());
    }

    private void testFunction() throws InterruptedException {

        if (Constant.BUILD_CONFIG.equals("debug")) {
            /////////////////////////////////////////////////////////////////////////////
//            // start sleep task check timer
//            startSleepTaskTimer();
//
//            // start repeat task
////            sendRepeatTaskMsg(LocalStorageUtil.getCachedTaskExecCount().getNextTaskIndex());
//            sendRepeatTaskMsg(12);
            ///////////////////////////////////////////////////////////////////////////
//            UiDriver.dumpXml2File("/sdcard/testAutoBrowse/errorDump.xml");
            /////////////////////////////////////////////////////////////////////////////

            IBrowseTask task = DianTaoWalkRepeatTaskScratch.getInstance();

            if (task.initTask()) {
                int count = 0;
                while (count < 30) {
                    boolean taskResult = task.runTask();
//                task.endTask();
                    Logger.debug("DianTaoWalkRepeatTaskScratch end, taskResult=" + taskResult);
                    if (taskResult) {
                        count++;
                    }
                }
            }


//            UiDriver.swipeUpToFindAndClickObject(new UiSelector().text("看广告赚金币"));
//            UiDriver.findAndClick(new UiSelector().text("开宝箱领金币"));



        }

    }
}
