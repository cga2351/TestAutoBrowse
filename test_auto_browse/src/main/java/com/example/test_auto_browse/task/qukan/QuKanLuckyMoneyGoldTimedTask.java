package com.example.test_auto_browse.task.qukan;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.task.TimedTaskAvailableChecker;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class QuKanLuckyMoneyGoldTimedTask extends QuKanBaseTask {
    private QuKanLuckyMoneyGoldTimedTask() {}
    private static QuKanLuckyMoneyGoldTimedTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new QuKanLuckyMoneyGoldTimedTask();
                }
            }
        }
        return instance;
    }

    // for timed task
    TimedTaskAvailableChecker timedTaskChecker = new TimedTaskAvailableChecker() {
        @Override
        protected int getExecInterval() {
            return 1000 * 60 * 60;
        }
    };

    @Override
    protected int getMaxExecCount() {
        return Constant.QU_KAN_LUCKY_MONEY_GOLD_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        if (timedTaskChecker.isTimedTaskAvailable()) {
            return getMaxExecCount() - LocalStorageUtil.getCachedTaskExecCount()
                    .getQuKanLuckyMoneyGoldExecCount();
        } else {
            return 0;
        }
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseQuKanLuckyMoneyGoldExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // get 3 lucky money gold
        result = getLuckyMoneyGold();
        Logger.debug("QuKanLuckyMoneyGoldTimedTask.autoBrowse(), getLuckyMoneyGold result = " + result);

        // get top watch video gold
        result = getTopWatchVideoGold();
        Logger.debug("QuKanLuckyMoneyGoldTimedTask.autoBrowse(), getTopWatchVideoGold result = " + result);

        // get headline gold
        result = getHeadlineGold();
        Logger.debug("QuKanLuckyMoneyGoldTimedTask.autoBrowse(), getHeadlineGold result = " + result);

        // set the last success time, even though task failed
        timedTaskChecker.setLastSuccessTime(System.currentTimeMillis());

        return result;
    }

    private boolean getHeadlineGold() throws InterruptedException {
        boolean result = false;
        boolean headlineGoldAvailable = true;

        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QU_KAN_HEADLINE))) {
            UiSelector uiSelector = new UiSelector().text(Constant.STR_QU_KAN_HEADLINE_GET_GOLD);
            if (null != UiDriver.find(uiSelector, Constant.WAIT_TIME_15_SEC)) {
                headlineGoldAvailable = true;

                while (headlineGoldAvailable) {
                    if (UiDriver.findAndClick(uiSelector)) {
                        result = true;

                        boolean watchAdsResult = quKanWatchAds();
                        Logger.debug("QuKanLuckyMoneyGoldTimedTask.getHeadlineGold(), watchAdsResult = " + watchAdsResult);

                        // check if has more gold
                        headlineGoldAvailable = (null != UiDriver.find(uiSelector));
                    } else {
                        headlineGoldAvailable = false;
                        Logger.debug("QuKanLuckyMoneyGoldTimedTask.getHeadlineGold(), click get headline gold failed");
                    }
                }
            } else {
                Logger.debug("QuKanLuckyMoneyGoldTimedTask.getHeadlineGold(), headline gold not available");
            }
        } else {
            Logger.debug("QuKanLuckyMoneyGoldTimedTask.getHeadlineGold(), switch to headline tab failed");
        }

        Logger.debug("QuKanLuckyMoneyGoldTimedTask.getHeadlineGold(), result = " + result);
        return result;
    }

    private boolean getLuckyMoneyGold() throws InterruptedException {
        boolean result = false;
        boolean luckyMoneyAvailable = true;
        int getLuckyMoneyCount = 0;

        // get 3 lucky money gold
        UiDriver.click(CoordsAdapter.getQuKanLuckyMoneyCoords());
        while (luckyMoneyAvailable) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_QU_KAN_LUCKY_MONEY_TASK_NOT_AVAILABLE), 2000)) {
                luckyMoneyAvailable = false;
                Logger.debug("QuKanLuckyMoneyGoldTimedTask.getLuckyMoneyGold(), lucky money gold task still not available");
            } else if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QU_KAN_GET_GOLD_IMMEDIATELY))) {
                getLuckyMoneyCount++;
                if (getLuckyMoneyCount < 3) {
                    result = quKanWatchAds();
                    Logger.debug("QuKanLuckyMoneyGoldTimedTask.getLuckyMoneyGold(), watch ads result = " + result);
                } else {
                    // the 3rd lucky money gold is the final one, and has no ads
                    result = true;
                    luckyMoneyAvailable = false;
                    Logger.debug("QuKanLuckyMoneyGoldTimedTask.getLuckyMoneyGold(), the 3rd lucky money gold has no ads");
                }
            } else {
                luckyMoneyAvailable = false;
                Logger.debug("QuKanLuckyMoneyGoldTimedTask.getLuckyMoneyGold(), got all lucky money gold");
            }
        }

        Logger.debug("QuKanLuckyMoneyGoldTimedTask.getLuckyMoneyGold(), result=" + result);
        return result;
    }

    private boolean getTopWatchVideoGold() throws InterruptedException {
        boolean result = false;
        int tryTime = 0;

        // get top watch video gold
        UiSelector uiSelector = new UiSelector().text(Constant.STR_QU_KAN_WATCH_VIDEO_TO_GET_GOLD);
        while (null != UiDriver.find(uiSelector) && tryTime < 3) {
            if (UiDriver.findAndClick(uiSelector)) {
                // watch and wait ads end
                result = quKanWatchAds();
                Logger.debug("QuKanLuckyMoneyGoldTimedTask.getTopWatchVideoGold(), watch ads result = " + result);
            } else {
                Logger.debug("QuKanLuckyMoneyGoldTimedTask.getTopWatchVideoGold(), click watch video to get gold failed");
            }
            tryTime++;
        }

        return result;
    }

    private boolean quKanWatchAds() throws InterruptedException {
        boolean result = false;

        if (checkEnterAdsPage()) {
            // watch and wait ads end
            result = waitAdsEnd();
        } else {
            Logger.debug("QuKanLuckyMoneyGoldTimedTask.quKanWatchAds(), open ads page failed");
        }

        Logger.debug("QuKanLuckyMoneyGoldTimedTask.quKanWatchAds(), result = " + result);
        return result;
    }

    private boolean checkEnterAdsPage() throws InterruptedException {
        boolean inAdsPage = false;
        if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_QU_KAN_DOWNLOAD_NOW), 8000) ||
                null != UiDriver.find(new UiSelector().resourceId(Constant.ID_QU_KAN_ADS_VIDEO_PROGRESS), 2000) ||
                null != UiDriver.find(new UiSelector().textContains(Constant.STR_QU_KAN_WATCH_VIDEO_CHECK_DETAIL), 2000) ||
                null != UiDriver.find(new UiSelector().textContains(Constant.STR_QU_KAN_ADS_END_AFTER), 2000)
        ) {
            inAdsPage = true;
        } else {
            inAdsPage = false;
        }

        Logger.debug("QuKanLuckyMoneyGoldTimedTask.checkEnterAdsPage(), inAdsPage = " + inAdsPage);
        return inAdsPage;
    }

    private boolean waitAdsEnd() throws InterruptedException {
        // wait ads end
        long startTime = System.currentTimeMillis();

        boolean closeAdsResult = false;
        while (!closeAdsResult && (System.currentTimeMillis() - startTime) < 1000 * 60) {
            // check if has cancel open app button
            if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_QU_KAN_CANCEL_OPEN_APP)) ||
                    null != UiDriver.find(new UiSelector().textContains(Constant.STR_QU_KAN_REPLAY_ADS), 500) ||
                    null != UiDriver.find(new UiSelector().resourceId(Constant.ID_QU_KAN_ADS_VIDEO_CLOSE), 500) ||
                    null != UiDriver.find(new UiSelector().textContains(Constant.STR_QU_KAN_WATCH_VIDEO_JUMP_ADS), 500)) {
                // close ads page
                if (!UiDriver.findAndClick(new UiSelector().resourceId(Constant.ID_QU_KAN_ADS_VIDEO_CLOSE), 1000) &&
                        !UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_QU_KAN_WATCH_VIDEO_JUMP_ADS), 1000)) {
                    // press back to close ads page
                    Logger.debug("QuKanLuckyMoneyGoldTimedTask.waitAdsEnd(), no close button, press back to close ads page");
                    UiDriver.pressBack();
                } else {
                    Logger.debug("QuKanLuckyMoneyGoldTimedTask.waitAdsEnd(), click close or jump button to close ads page");
                }

                closeAdsResult = true;
                Logger.debug("QuKanLuckyMoneyGoldTimedTask.waitAdsEnd(), ads video end");
            } else {
                Logger.debug("QuKanLuckyMoneyGoldTimedTask.waitAdsEnd(), can't find ads end ui object, retry");
            }
        }

        // close ads failed after 60s, press back to close ads directly
        if (!closeAdsResult) {
            Logger.debug("QuKanLuckyMoneyGoldTimedTask.waitAdsEnd(), can't find ads end ui object after 60s, press back to close ads directly");
            UiDriver.pressBack();
        }

        return closeAdsResult;
    }

}