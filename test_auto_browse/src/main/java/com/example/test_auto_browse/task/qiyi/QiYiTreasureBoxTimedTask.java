package com.example.test_auto_browse.task.qiyi;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.task.TimedTaskAvailableChecker;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class QiYiTreasureBoxTimedTask extends QiYiBaseTask {
    private QiYiTreasureBoxTimedTask() {}
    private static QiYiTreasureBoxTimedTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new QiYiTreasureBoxTimedTask();
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
        return Constant.QI_YI_TREASURE_BOX_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        if (timedTaskChecker.isTimedTaskAvailable()) {
            return getMaxExecCount() - LocalStorageUtil.getCachedTaskExecCount()
                    .getQiYiTreasureBoxExecCount();
        } else {
            return 0;
        }
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseQiYiTreasureBoxExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;
        Logger.debug("QiYiTreasureBoxTimedTask.autoBrowse(), entry");

        result = openTreasureBox();

        // get home page right top gold
        getRightTopGold();

        if (result) {
            timedTaskChecker.setLastSuccessTime(System.currentTimeMillis());
        }

        Logger.debug("QiYiTreasureBoxTimedTask.autoBrowse(), result=" + result);
        return result;
    }

    private boolean openTreasureBox() throws InterruptedException {
        boolean result = false;
        Logger.debug("QiYiTreasureBoxTimedTask.openTreasureBox(), entry");

        if (UiDriver.findAndClick(new UiSelector().description(Constant.STR_QI_YI_OPEN_TREASURE_BOX))) {
            //get more gold
            Logger.debug("QiYiTreasureBoxTimedTask.openTreasureBox(), open treasure box success");
            result = true;

            // get more
            if (UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_QI_YI_GET_MORE))) {
                Logger.debug("QiYiTreasureBoxTimedTask.openTreasureBox(), watch ads to get more");
                boolean watchAdsResult = watchAds(1000 * 60);
                Logger.debug("QiYiTreasureBoxTimedTask.openTreasureBox(), watchAdsResult = " + watchAdsResult);
            }

        } else {
            Logger.debug("QiYiTreasureBoxTimedTask.openTreasureBox(), click open treasure box failed");
        }

        return result;
    }

    private void getRightTopGold() throws InterruptedException {
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QI_YI_HOME_PAGE))) {
//            if (UiDriver.findAndClick(new UiSelector().textMatches(Constant.STR_QI_YI_GET_GOLD))) {
            if (UiDriver.findAndClick(new UiSelector().textMatches("^[赚领]金币"))) {   //"赚金币"或"领金币"
                // get more
                if (UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_QI_YI_GET_MORE))) {
                    Logger.debug("QiYiTreasureBoxTimedTask.getRightTopGold(), watch ads to get more");
                    boolean result = watchAds(1000 * 60);
                    Logger.debug("QiYiTreasureBoxTimedTask.getRightTopGold(), watch ads end, result = " + result);
                }
                Logger.debug("QiYiTreasureBoxTimedTask.getRightTopGold(), get right top gold success");
            } else {
                Logger.debug("QiYiTreasureBoxTimedTask.getRightTopGold(), click get right top gold failed");
            }
        } else {
            Logger.debug("QiYiTreasureBoxTimedTask.getRightTopGold(), switch to home tab failed");
        }
    }

    private boolean watchAds(long maxWatchDuration) throws InterruptedException {
        boolean result = false;

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < maxWatchDuration) {
            if (null == UiDriver.find(new UiSelector().textContains(Constant.STR_QI_YI_WAIT_ADS_END))) {
                Logger.debug("QiYiTreasureBoxTimedTask.watchAds(), watch ads end");
                result = true;
                break;
            }
            Thread.sleep(5000);
        }

        // return to gold page
        UiDriver.pressBack();

        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QI_YI_CLOSE_ADS))) {
            result = false;
            Logger.debug("QiYiTreasureBoxTimedTask.watchAds(), watch ads not success, close forcibly");
        }

        Logger.debug("QiYiTreasureBoxTimedTask.watchAds(), result=" + result);
        return result;
    }
}