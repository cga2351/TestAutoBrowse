package com.example.test_auto_browse.task.toutiao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.BrowseBaseTask;
import com.example.test_auto_browse.utils.DateUtil;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

import java.util.Calendar;

public abstract class TouTiaoBaseTask extends BrowseBaseTask {

    private static long lastTouTiaoDailyTaskCheckTime = 0;
//    private static long lastTouTiaoDailyTaskCheckTime = System.currentTimeMillis();

    @Override
    public long getLastDailyTaskCheckTime() {
        return lastTouTiaoDailyTaskCheckTime;
    }

    @Override
    public void setLastCheckTime(long time) {
        lastTouTiaoDailyTaskCheckTime = time;
    }

    @Override
    public String getTargetAppPackageName() { return Constant.PKG_NAME_TOU_TIAO_LITE; }

    @Override
    public void exeDailyTasks() throws InterruptedException {
        // check double gold
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_DOUBLE_GOLD))) {
            UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_I_KNOW));
        }

        // check daily task
        getDailyTaskGold();

        // get meal gold
        getMealGold();

        // get sleep gold
        getSleepGold();

        // get walking gold
        getWalkingGold();
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
//        taskPackageName = Constant.PKG_NAME_TOU_TIAO_LITE;

        if (super.initTask()) {
            // jump the launch ads if first run
            UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_JUMP_ADS));

            // close home page popup
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_HAVE_A_LOOK))) {
                Thread.sleep(10000);
                UiDriver.pressBack();
            }

            // switch center tab
            boolean switchCenterTab = switchCenterTab();

            // get sign gold
            getSignGold();

            if (switchCenterTab) {
                // check daily task
                checkDailyTask();

                result = true;
            } else {
                Logger.debug("TouTiaoBaseTask.initTask(), open task tab failed");
            }
        } else {
            Logger.debug("TouTiaoBaseTask.initTask(), super.initTask() failed");
        }

        Logger.debug("TouTiaoBaseTask.initTask(), result=" + result);

        return result;
    }

    @Override
    public void endTask() throws InterruptedException {
        Logger.debug("TouTiaoBaseTask.endTask(), entry, task = " + getClass().getSimpleName());

        // kill app
        SysUtil.killApp(Constant.PKG_NAME_TOU_TIAO_LITE);
    }

    public boolean switchCenterTab() throws InterruptedException {
        boolean switchCenterTab = UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_TASK));
        if (!switchCenterTab) {
            switchCenterTab = UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_TOU_TIAO_COLON));
            if (switchCenterTab) {
                // if has popup window, close it.
                boolean closePopup = UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_CLOSE_POPUP).className("android.widget.Image").instance(2));
                Logger.debug("TouTiaoBaseTask.switchCenterTab(), closePopup=" + closePopup);
            }
        }

        if (null == UiDriver.find(new UiSelector().textContains(Constant.STR_TOU_TIAO_TASK_PAGE_TITLE), Constant.WAIT_TIME_25_SEC)) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_TOU_TIAO_NO_NETWORK))) {
                Logger.debug("TouTiaoBaseTask.switchCenterTab(), no network, click to retry");
                UiDriver.click_ScreenCenter();
            }
        }

        return switchCenterTab;
    }

    public void getSignGold() throws InterruptedException {
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_GET_SIGN_GOLD))) {
            Logger.debug("TouTiaoBaseTask.getSignGold(), get sign gold success");
        } else {
            Logger.debug("TouTiaoBaseTask.getSignGold(), no sign gold");
        }
    }

    public void getDailyTaskGold() throws InterruptedException {
        Logger.debug("TouTiaoBaseTask.getDailyTaskGold(), entry");
        if (null != UiDriver.find(new UiSelector().textStartsWith(Constant.STR_TOU_TIAO_DAILY_TASK))) {
            boolean getDailyTaskGold = UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_GET_GOLD));
            Logger.debug("TouTiaoBaseTask.getDailyTaskGold(), getDailyTaskGold = " + getDailyTaskGold);

            if (getDailyTaskGold) {
                boolean getMoreResult = UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_TOU_TIAO_WATCH_ADS_VIDEO_TO_GET_MORE));
                Logger.debug("TouTiaoBaseTask.getDailyTaskGold(), getMoreResult = " + getMoreResult);
                if (getMoreResult) {
                    if (!touTiaoWatchAds()) {
                        // no ads, need to re-entry the tou tiao app
                        reEntryTouTiaoTaskTab();
                    }
                }
                Logger.debug("TouTiaoBaseTask.getDailyTaskGold(), getMoreResult = " + getMoreResult);
            }
        } else {
            Logger.debug("TouTiaoBaseTask.getDailyTaskGold(), no daily task gold");
        }
    }

    public void reEntryTouTiaoTaskTab() throws InterruptedException {
        SysUtil.killApp(Constant.PKG_NAME_TOU_TIAO_LITE);
        Thread.sleep(1000);
        SysUtil.launchApp(Constant.PKG_NAME_TOU_TIAO_LITE);

        // jump the launch ads if first run
        UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_JUMP_ADS), Constant.WAIT_TIME_15_SEC);

        // switch center tab
        boolean switchCenterTab = switchCenterTab();
        Logger.debug("TouTiaoBaseTask.reEntryTouTiaoTaskTab(), result = " + switchCenterTab);
    }

    public void getMealGold() throws InterruptedException {
        boolean result = false;
        Logger.debug("TouTiaoBaseTask.getMealGold(), entry");

        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_GET_MEAL_GOLD))) {
            if (null == UiDriver.find(new UiSelector().textStartsWith(Constant.STR_TOU_TIAO_MEAL_GOLD_HAS_GOT)) &&
                    UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_TOU_TIAO_GET_GOLD))) {
                Logger.debug("TouTiaoBaseTask.getMealGold(), get meal gold success");
                result = true;
            } else {
                Logger.debug("TouTiaoBaseTask.getMealGold(), get meal gold failed");
            }
            Thread.sleep(2000);
            UiDriver.pressBack();
        } else {
            Logger.debug("TouTiaoBaseTask.getMealGold(), open get meal gold window failed");
        }

        Logger.debug("TouTiaoBaseTask.getMealGold(), result = " + result);
    }

    public void getSleepGold() throws InterruptedException {
        boolean result = false;
        String curTime = DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_HYPHEN, System.currentTimeMillis());
        Logger.debug("TouTiaoBaseTask.getSleepGold(), entry, current time = " + curTime);

        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_GET_SLEEP_GOLD))) {
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_GO_TO_SLEEP))) {
                Logger.debug("TouTiaoBaseTask.getSleepGold(), go to sleep success");
                result = true;
            } else if (null != UiDriver.find(new UiSelector().text(Constant.STR_TOU_TIAO_WAKE_UP))) {
                // can wake up, but need to sleep to 12:00am to wake up
                int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                if (curHour < 20 && curHour >= 9) {
                    if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_WAKE_UP))) {
                        Logger.debug("TouTiaoBaseTask.getSleepGold(), wake up success");

                        // click get gold
                        if (UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_TOU_TIAO_GET_GOLD))) {
                            Logger.debug("TouTiaoBaseTask.getSleepGold(), get sleep gold success");
                            result = true;
                        } else {
                            Logger.debug("TouTiaoBaseTask.getSleepGold(), click get gold failed");
                        }
                    } else {
                        Logger.debug("TouTiaoBaseTask.getSleepGold(), click wake up failed");
                    }
                } else {
                    Logger.debug("TouTiaoBaseTask.getSleepGold(), go on sleeping, current time=" + curTime
                            + ", hour= " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                }
            } else {
                Logger.debug("TouTiaoBaseTask.getSleepGold(), not sleep task time, current time=" + curTime);
            }

            Thread.sleep(2000);
            // return to home page
            UiDriver.pressBack();
        } else {
            Logger.debug("TouTiaoBaseTask.getSleepGold(), open get sleep gold window failed");
        }

        Logger.debug("TouTiaoBaseTask.getSleepGold(), result = " + result);
    }

    public void getWalkingGold() throws InterruptedException {
        boolean result = false;
        Logger.debug("TouTiaoBaseTask.getWalkingGold(), entry");

        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_GET_WALKING_GOLD))) {
            if (UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_TOU_TIAO_GET_GOLD))) {
                Logger.debug("TouTiaoBaseTask.getWalkingGold(), get walking gold success");
                result = true;
            } else {
                Logger.debug("TouTiaoBaseTask.getWalkingGold(), get walking gold failed");
            }

            Thread.sleep(1000);
            UiDriver.pressBack();
        } else {
            Logger.debug("TouTiaoBaseTask.getWalkingGold(), open get walking gold window failed");
        }

        Logger.debug("TouTiaoBaseTask.getWalkingGold(), result = " + result);
    }

    public boolean touTiaoWatchAds() throws InterruptedException {
        boolean hasAds = false;

        // check if has ads
        UiSelector closeAds = new UiSelector().description(Constant.STR_TOU_TIAO_CLOSE_ADS);
        UiSelector adsEndPrompt1 = new UiSelector().descriptionContains(Constant.STR_TOU_TIAO_WAIT_ADS_END_1);
        UiSelector adsEndPrompt2 = new UiSelector().descriptionContains(Constant.STR_TOU_TIAO_WAIT_ADS_END_2);
        Thread.sleep(5000);
        if (null != UiDriver.find(closeAds, 3000) ||
                null != UiDriver.find(adsEndPrompt1, 3000) ||
                null != UiDriver.find(adsEndPrompt2, 3000)) {
            // watch the ads video and close ads
            Thread.sleep(25000);
            if (null == UiDriver.find(adsEndPrompt1, 3000)) {
                Logger.debug("TouTiaoBaseTask.touTiaoWatchAds(), watch first ads end");
                if (!UiDriver.findAndClick(closeAds, 3000)) {
                    // 没有关闭，直接返回
                    UiDriver.pressBack();
                }

                // check if has more ads
                UiSelector watchMoreSelector = new UiSelector().descriptionStartsWith(Constant.STR_TOU_TIAO_WATCH_MORE);
                if (null != UiDriver.find(watchMoreSelector)) {
                    while (null != UiDriver.find(watchMoreSelector)) {
                        Logger.debug("TouTiaoBaseTask.touTiaoWatchAds(), watch more ads");
                        UiDriver.findAndClick(watchMoreSelector);

                        // no more video, return
                        if (null != UiDriver.find(new UiSelector().text(Constant.STR_TOU_TIAO_NO_MORE_ADS), 10000)) {
                            Logger.debug("TouTiaoBaseTask.touTiaoWatchAds(), no more new ads");
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
                    UiDriver.findAndClick(new UiSelector().description(Constant.STR_TOU_TIAO_CLOSE_ADS_CONFIRM));
                    Thread.sleep(2000);
                    Logger.debug("TouTiaoBaseTask.touTiaoWatchAds(), all ads end");
                } else {
                    UiDriver.findAndClick(new UiSelector().description(Constant.STR_TOU_TIAO_CLOSE_ADS_CONFIRM));
                }

                // back to home page
                Thread.sleep(2000);
                UiDriver.pressBack();

            } else {
                Logger.debug("TouTiaoBaseTask.touTiaoWatchAds(), ads have not end");
            }

            hasAds = true;
        } else {
            Logger.debug("TouTiaoBaseTask.touTiaoWatchAds(), open ads page failed");
        }

        return hasAds;
    }
}

