package com.example.test_auto_browse.task.douyin;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.BrowseBaseTask;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

public abstract class DouYinBaseTask extends BrowseBaseTask {
    private static long lastDouYinDailyTaskCheckTime = 0;

    @Override
    public long getLastDailyTaskCheckTime() {
        return lastDouYinDailyTaskCheckTime;
    }

    @Override
    public void setLastCheckTime(long time) {
        lastDouYinDailyTaskCheckTime = time;
    }

    @Override
    public String getTargetAppPackageName() { return Constant.PKG_NAME_DOU_YIN; }

    @Override
    public void exeDailyTasks() throws InterruptedException {
        // do nothing
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
//        taskPackageName = Constant.PKG_NAME_DOU_YIN;

        if (super.initTask()) {
            // close teen protection popup
            if(UiDriver.findAndClick(new UiSelector().text(Constant.STR_DOU_YIN_I_KNOW), Constant.WAIT_TIME_20_SEC)) {
                Logger.debug("DouYinBaseTask.initTask(), close teen protection popup success");
            }

            // close the recommend friend popup
            if (null != UiDriver.find(new UiSelector().text(Constant.STR_DOU_YIN_RECOMMEND_FRIENDS)) ||
                    null != UiDriver.find(new UiSelector().description(Constant.STR_DOU_YIN_RECOMMEND_FRIENDS), 2000)) {
                Logger.debug("DouYinBaseTask.initTask(), close recommend friends popup");
                UiDriver.pressBack();
            }

            // click gold center tab
            UiDriver.click(CoordsAdapter.getDouYinGoldCenterCoords());

            // dismiss sign popup
            if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_DOU_YIN_SIGN_NOW), Constant.WAIT_TIME_15_SEC)) {
                Logger.debug("DouYinBaseTask.initTask(), sign success");
            }

            // check page load end
            if (null != UiDriver.find(new UiSelector().description(Constant.STR_DOU_YIN_TOTAL_GOLD))) {
                result = true;
            } else {
                Logger.debug("DouYinBaseTask.initTask(), open gold page failed");
            }

        } else {
            Logger.debug("DouYinBaseTask.initTask(), super.initTask() failed");
        }

        Logger.debug("DouYinBaseTask.initTask(), result=" + result);

        return result;
    }

    @Override
    public void endTask() throws InterruptedException {
        Logger.debug("DouYinBaseTask.endTask(), entry");
        SysUtil.killApp(Constant.PKG_NAME_DOU_YIN);
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
                        Thread.sleep(5000);
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

}