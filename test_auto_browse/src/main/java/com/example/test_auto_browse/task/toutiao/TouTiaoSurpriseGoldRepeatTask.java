package com.example.test_auto_browse.task.toutiao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class TouTiaoSurpriseGoldRepeatTask extends TouTiaoBaseTask {
    private TouTiaoSurpriseGoldRepeatTask() {}
    private static TouTiaoSurpriseGoldRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new TouTiaoSurpriseGoldRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 60 * 5 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.TOU_TIAO_SURPRISE_GOLD_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() - LocalStorageUtil.getCachedTaskExecCount()
                .getTouTiaoSurpriseGoldExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseTouTiaoSurpriseGoldExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            // switch to home tab
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_HOME_PAGE))) {
                return true;
            } else {
                Logger.debug("TouTiaoSurpriseGoldRepeatTask.init(), switch home tab failed");
                return false;
            }
        } else {
            Logger.debug("TouTiaoSurpriseGoldRepeatTask.init(), super.initTask() failed");
        }

        Logger.debug("TouTiaoSurpriseGoldRepeatTask.init(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("TouTiaoSurpriseGoldRepeatTask.autoBrowse(), entry");

        while (!getForceStop()) {
            // swipe up to try to find surprise gold
            Thread.sleep(2000);
            UiDriver.swipeUp1000pxSlowly();

            if (tryToGetSurpriseGold()) {
                Logger.debug("TouTiaoSurpriseGoldRepeatTask.autoBrowse(), found surprise gold outside");
            } else {
                Logger.debug("TouTiaoSurpriseGoldRepeatTask.autoBrowse(), no surprise gold, open an article");
                if (UiDriver.findAndClick(new UiSelector().descriptionMatches(".*\\d{1,5}评论$"), 3000)) {    //匹配 任意字符+数字+评论"xxxx1234评论"
                    Logger.debug("TouTiaoSurpriseGoldRepeatTask.autoBrowse(), click the article or video comment to open page");

                    int swipeUpCount = 0;
                    while (swipeUpCount < 3) {
                        Thread.sleep(2000);
                        UiDriver.swipeUp800pxSlowly();
                        swipeUpCount++;

                        // try to find surprise gold
                        if (tryToGetSurpriseGold()) {
                            Logger.debug("TouTiaoSurpriseGoldRepeatTask.autoBrowse(), found surprise gold inside");
                            break;
                        }
                    }

                    // press back to outside page, wait 2s to back to home page
                    UiDriver.pressBack();
                }
            }
        }

        Logger.debug("TouTiaoSurpriseGoldRepeatTask.autoBrowse(), exit");
        return true;
    }

    private boolean tryToGetSurpriseGold() throws InterruptedException {
        boolean result = false;
        if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_TOU_TIAO_SURPRISE_GOLD), 3000)) {
            Logger.debug("TouTiaoSurpriseGoldRepeatTask.tryToGetSurpriseGold(), found surprise gold");
            UiSelector uiSelector = new UiSelector().textContains(Constant.STR_TOU_TIAO_WATCH_ADS_TO_GET_MORE);
            if (null != UiDriver.find(uiSelector)) {
                Logger.debug("TouTiaoSurpriseGoldRepeatTask.tryToGetSurpriseGold(), got surprise gold, watch ads to get more");

                Thread.sleep(1000);
                UiDriver.findAndClick(uiSelector);

                // watch ads video to get more
                touTiaoWatchAds();
            }
            result = true;
        }
        return result;
    }

}