package com.example.test_auto_browse.task.diantao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.BrowseBaseTask;
import com.example.test_auto_browse.utils.DateUtil;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

import java.io.File;
import java.util.Calendar;

public abstract class DianTaoBaseTask extends BrowseBaseTask {
    private static int lastDismissPopupWindowDay = 0;
    private static int lastLuckyDrawDay = 0;
    private static long lastDianTaoDailyTaskCheckTime = 0;
//    private static long lastDianTaoDailyTaskCheckTime = System.currentTimeMillis();

    @Override
    public long getLastDailyTaskCheckTime() {
        return lastDianTaoDailyTaskCheckTime;
    }

    @Override
    public void setLastCheckTime(long time) {
        lastDianTaoDailyTaskCheckTime = time;
    }

    @Override
    public String getTargetAppPackageName() { return Constant.PKG_NAME_DIAN_TAO; }

    @Override
    public void exeDailyTasks() throws InterruptedException {

        // check if has get gold on the right top
        getGoldOnRightTop();

        // get sign gold
        getSignGold();

        // get sleep gold
        getSleepGold();

        // get sign lucky draw
        getSignLuckyDraw();
    }

    private void getSignGold() throws InterruptedException {
        // if no sign, click to sign
        // click sing may failed, retry in 1 min
        long startTime = System.currentTimeMillis();
        while (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_DIAN_TAO_CLICK_TO_SIGN)) &&
                (System.currentTimeMillis() - startTime) < 1000 * 30) {
            Thread.sleep(5000);
        }
    }

    @Override
    public boolean initTask() throws InterruptedException {
        Logger.debug("DianTaoBaseTask.initTask(), entry");
//        taskPackageName = Constant.PKG_NAME_DIAN_TAO;

        boolean result = false;
        if (super.initTask()) {
            // switch to the my tab page
            if (UiDriver.findAndClick(new UiSelector().resourceId(Constant.ID_DIAN_TAO_HOME_TAB_ITEM).instance(3), Constant.WAIT_TIME_20_SEC)) {
                // open gold center
                if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GOLD_CENTER), Constant.WAIT_TIME_15_SEC)) {
                    // dismiss popup window
                    if (Constant.BUILD_CONFIG.equals("release")) {
                        dismissPopupWindow();
                    }

                    // check daily task
                    checkDailyTask();

                    Thread.sleep(3000);
                    result = true;
                } else {
                    Logger.debug("DianTaoBaseTask.initTask(), click gold center failed");
                }
            } else {
                Logger.debug("DianTaoBaseTask.initTask(), switch to home mine tab page failed");
            }
        } else {
            Logger.debug("DianTaoBaseTask.initTask(), super.initTask() failed");
        }

        return result;
    }

    private void dismissPopupWindow() throws InterruptedException {
        int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        if (lastDismissPopupWindowDay != curDay) {
            // if has popup, press back, and re-enter gold center, total 3 times
            int i = 0;
            while (i < 3) {
                Thread.sleep(5000);
                UiDriver.pressBack();
                Thread.sleep(1000);
                UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GOLD_CENTER));
                i++;
            }

            lastDismissPopupWindowDay = curDay;
        }
    }

    @Override
    public void endTask() throws InterruptedException {
        Logger.debug("DianTaoBaseTask.endTask(), entry, task = " + getClass().getSimpleName());

        // kill app
        SysUtil.killApp(Constant.PKG_NAME_DIAN_TAO);
    }

    public void getGoldOnRightTop() throws InterruptedException {
        // if has get gold on right top, click directly
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_GOLD_ON_RIGHT_TOP))) {
            Thread.sleep(2000);
            UiDriver.pressBack();
            Logger.debug("DianTaoBaseTask.getGoldOnRightTop(), success");
        } else {
            Logger.debug("DianTaoBaseTask.getGoldOnRightTop(), no right top gold");
        }
    }

    public void getSleepGold() throws InterruptedException {
        boolean result = false;
        String curTime = DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_HYPHEN, System.currentTimeMillis());
        Logger.debug("DianTaoBaseTask.getSleepGold(), entry, current time = " + curTime);

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_SLEEP_TO_GET_GOLD);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                Thread.sleep(5000);
                if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_START_SLEEP))) {
                    Logger.debug("DianTaoBaseTask.getSleepGold(), go to sleep success");
                    Thread.sleep(2000);
                    result = true;
                } else if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_WAKE_UP))) {
                    // can wake up, but need to sleep to 12:00am to wake up
                    int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    if (curHour < 21 && curHour >= 8) {
                        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_WAKE_UP))) {
                            Logger.debug("DianTaoBaseTask.getSleepGold(), wake up success");
                            if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_DIAN_TAO_WATCH_LIVE_TO_GET_MORE))) {
                                Thread.sleep(35000);
                                UiDriver.pressBack();
                            }
                            result = true;
                        } else {
                            Logger.debug("DianTaoBaseTask.getSleepGold(), click wake up failed");
                        }
                    } else {
                        Logger.debug("DianTaoBaseTask.getSleepGold(), go on sleeping, current time=" + curTime
                                + ", hour= " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    }
                } else {
                    Logger.debug("DianTaoBaseTask.getSleepGold(), not sleep task time, current time=" + curTime);
                }

                // return to home page
                UiDriver.pressBack();
            } else {
                Logger.debug("DianTaoBaseTask.getSleepGold(), open get sleep gold window failed");
            }
        } else {
            Logger.debug("DianTaoBaseTask.getSleepGold(),click get sleep gold failed");
        }

        // swipe down to top
        Thread.sleep(2000);
        UiDriver.swipeDown800pxFast();

        Logger.debug("DianTaoBaseTask.getSleepGold(), result = " + result);
    }

    public void getSignLuckyDraw() throws InterruptedException{
        Logger.debug("DianTaoBaseTask.getSignLuckyDraw(), entry");
        int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int curDayHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (curDay != lastLuckyDrawDay && curDayHour >= 20) {
            Logger.debug("DianTaoBaseTask.getSignLuckyDraw(), get sign lucky draw");
            // get sign lucky draw
            UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_SIGN_TODAY);
            if (UiDriver.swipeUpToFindObject(uiSelector)) {
                if (UiDriver.findAndClick(uiSelector)) {
                    if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_LUCK_DRAW_HIS))) {
                        Thread.sleep(5000);
                        UiDriver.click(CoordsAdapter.getDianTaoLuckyDrawCoords());
                        Logger.debug("DianTaoBaseTask.getSignLuckyDraw(), get lucky draw success");

                        // back to gold center page
                        Thread.sleep(2000);
                        UiDriver.pressBack();
                        Thread.sleep(2000);
                    } else {
                        Logger.debug("DianTaoBaseTask.getSignLuckyDraw(), lucky draw page load failed");
                    }
                } else {
                    Logger.debug("DianTaoBaseTask.getSignLuckyDraw(), click lucky draw failed");
                }
            } else {
                Logger.debug("DianTaoBaseTask.getSignLuckyDraw(), find lucky draw failed");
            }

            // record lucky draw day
            lastLuckyDrawDay = curDay;

            // swipe down to top
            Thread.sleep(2000);
            UiDriver.swipeDown800pxFast();
        }
    }

    protected boolean watchVideoOrLive(int watchDuration, boolean autoSwipe) throws InterruptedException {
        boolean result;
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < watchDuration) {
            Thread.sleep(8000);
            if (autoSwipe) {
                UiDriver.swipeUp800pxFast();
            }
        }

        // check count down end
        result = waitWatchVideoOrLiveCountDownEnd();
        if (!result) {
            Logger.debug("DianTaoBaseTask.watchVideoOrLive(), watch video or live for " + (watchDuration / (60.0f * 1000)) +  "min failed");
        }

        return result;
    }

    protected boolean waitWatchVideoOrLiveCountDownEnd() throws InterruptedException {
        boolean result = false;

        // wait count down end, max wait 2 min
        long startWaitTime = System.currentTimeMillis();
        while (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE), 3000) &&
                (System.currentTimeMillis() - startWaitTime) < 1000 * 60 * 2) {
            UiDriver.swipeUp600pxFast();
            Thread.sleep(1000 * 10);
        }

        if (null == UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE), 3000)) {
            result = true;
        }

        return result;
    }
}
