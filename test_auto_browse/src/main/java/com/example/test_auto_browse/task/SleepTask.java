package com.example.test_auto_browse.task;

import com.example.test_auto_browse.AutoBrowseApp;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.ShellUtil;
import com.example.test_auto_browse.utils.SysUtil;

import java.util.Calendar;

public class SleepTask extends BrowseBaseTask {
    private SleepTask() {}

    private long sleepTaskDuration = Constant.DEFAULT_SLEEP_DURATION;
    private static SleepTask instance;
    private long lastTimedTaskExecTime = 0;

    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new SleepTask();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean initTask() throws InterruptedException {
        Logger.debug("SleepTask.initTask(), entry");
//        taskPackageName = "";
        return super.initTask();
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("SleepTask.autoBrowse(), entry");

//        int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        if (curHour > 7 && curHour < 20) {
//            ShellUtil.command(false, "reboot -p");
//        }

        // stop auto browse repeatedly task
        AutoBrowseApp.getInstance().stopRepeatTaskThread();

        // if timed task is running, wait the timed task end
//        AutoBrowseApp.getInstance().waitTimedTaskThreadExit();

        // turn off screen
        UiDriver.lightScreenOff();

        // sleep for some time
        SysUtil.killAllApps();
        sleepForSomeTime();
        SysUtil.killAllApps();

        // turn on screen
        UiDriver.lightScreenOn();

        // when sleep task run all apps timed task once
        runTimedTaskForAllApps();

        Logger.debug("SleepTask.autoBrowse(), exit");

        return true;
    }

    private void sleepForSomeTime() throws InterruptedException {
        // sleep while the battery temperature is high or and battery level is low
        int sleep10MinCount = 0;
        int batteryLevel = SysUtil.getBatteryLevel();
        int batteryTemp = SysUtil.getBatteryTemperature();
        int batteryVoltage = SysUtil.getBatteryVoltage();

        Logger.debug("sleepForSomeTime(), entry"
                + ", batteryLevel=" + batteryLevel + "%"
                + ", batteryTemp=" + batteryTemp
                + ", batteryVoltage=" + batteryVoltage);

        while (batteryLevel < Constant.BATTERY_LEVEL_THRESHOLD ||
                batteryTemp > Constant.BATTERY_TEMPERATURE_THRESHOLD) {
            // sleep 10min
            Thread.sleep(1000 * 60 * 10);
            sleep10MinCount++;

            // check the battery temperature and level
            batteryLevel = SysUtil.getBatteryLevel();
            batteryTemp = SysUtil.getBatteryTemperature();
            batteryVoltage = SysUtil.getBatteryVoltage();
            Logger.debug("sleepForSomeTime(), after sleep " + sleep10MinCount * 10 + "min"
                    + ", batteryLevel=" + batteryLevel + "%"
                    + ", batteryTemp=" + batteryTemp
                    + ", batteryVoltage=" + batteryVoltage);
        }

        Logger.debug("sleepForSomeTime(), exit"
                + ", batteryLevel=" + batteryLevel + "%"
                + ", batteryTemp=" + batteryTemp
                + ", batteryVoltage=" + batteryVoltage);
    }

    @Override
    public void endTask() throws InterruptedException {
        // do nothing
        Logger.debug("SleepTask.endTask(), entry");

        // print battery temperature
        Logger.debug("SleepTask.endTask(), battery temperature = " + SysUtil.getBatteryTemperature());
    }

//    public void adjustSleepTaskDuration() {
//        int batteryLevel = SysUtil.getBatteryLevel();
//        int batteryTemp = SysUtil.getBatteryTemperature();
//        int batteryVoltage = SysUtil.getBatteryVoltage();
//        if (batteryLevel < Constant.BATTERY_LEVEL_THRESHOLD ||
//                batteryTemp >= Constant.BATTERY_TEMPERATURE_THRESHOLD) {
//            sleepTaskDuration += 1000 * 60 * 10;
//            Logger.debug("adjustSleepTaskDuration()"
//                    + ", batteryLevel=" + batteryLevel + "%"
//                    + ", batteryTemp=" + batteryTemp
//                    + ", batteryVoltage=" + batteryVoltage
//                    + ", increase sleep task duration to " + sleepTaskDuration / 1000 / 60 + "min");
//        } else {
//            sleepTaskDuration = Constant.DEFAULT_SLEEP_DURATION;
//            Logger.debug("adjustSleepTaskDuration()"
//                    + ", batteryLevel=" + batteryLevel + "%"
//                    + ", batteryTemp=" + batteryTemp
//                    + ", batteryVoltage=" + batteryVoltage
//                    + ", recover sleep task duration to default value of " + Constant.DEFAULT_SLEEP_DURATION / 1000 / 60 + "min");
//        }
//        Logger.debug("adjustSleepTaskDuration(), exit, sleepTaskDuration=" +  + (sleepTaskDuration / 1000 / 60) + "min");
//    }

    private void runTimedTaskForAllApps() throws InterruptedException {
        if (System.currentTimeMillis() - lastTimedTaskExecTime > 1000 * 60 * 20) {
            IBrowseTask autoBrowseTask = TimedTaskForAllApps.getInstance();
            if (autoBrowseTask.initTask()) {
                autoBrowseTask.runTask();
            } else {
                Logger.debug("runTimedTaskForAllApps(), TimedTaskForAllApps init failed");
            }
            autoBrowseTask.endTask();
            lastTimedTaskExecTime = System.currentTimeMillis();
        } else {
            Logger.debug("runTimedTaskForAllApps(), not the time to run timed task");
        }
    }
}
