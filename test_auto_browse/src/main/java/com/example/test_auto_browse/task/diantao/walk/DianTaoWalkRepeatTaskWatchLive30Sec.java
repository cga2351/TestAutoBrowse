package com.example.test_auto_browse.task.diantao.walk;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

import java.util.Calendar;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/5/24
 * description :
 */

public class DianTaoWalkRepeatTaskWatchLive30Sec extends DianTaoWalkRepeatTask {
    private DianTaoWalkRepeatTaskWatchLive30Sec() {}
    private static DianTaoWalkRepeatTaskWatchLive30Sec instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWalkRepeatTaskWatchLive30Sec();
                }
            }
        }
        return instance;
    }

    private boolean singleTaskAllSuccess;
    private int singleTaskLastSuccessDay;

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 30 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WALK_WATCH_LIVE_30SEC_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWalkWatchLive30SecExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWalkWatchLive30SecExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch live 30s
        // click get walk steps
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_WALK_STEPS))) {
            // 先完成一天只有一次的单次任务
           checkOneTimeTask();

            // 观看直播30秒
            // click watch live 30s, dismiss task list first
            dismissTaskListAndReOpen();
            UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_30Sec);
            if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                    // enter live window, and wait 40s
                    int watchDuration = 1000 * 30 + 1000 * 10;
                    result = watchVideoOrLive(watchDuration, false, true);
                    Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.autoBrowse(), watch result = " + result);
                } else {
                    // entry live window failed, exit task
                    Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.autoBrowse(), enter live window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.autoBrowse(), click watch live 30s failed");
            }

            // press back
            UiDriver.pressBack();
            Thread.sleep(2000);
        } else {
            Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.autoBrowse(), click get walk steps failed");
        }

        Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.autoBrowse(), result=" + result);
        return result;
    }

    private void checkOneTimeTask() throws InterruptedException {
        if (!singleTaskAllSuccess) {
            // 点赞20次 任务
            boolean result1 = thumbUp20Times();
            Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.checkOneTimeTask(), thumbUp20Times result = " + result1);

            // dismiss task list
            dismissTaskListAndReOpen();

            // 浏览特价卖场20秒 任务
            boolean result2 = browseSpecialPriceMarkets20Sec();
            Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.checkOneTimeTask(), browseSpecialPriceMarkets20Sec result = " + result2);

            //
            if (result1 && result2) {
                singleTaskAllSuccess = true;
                singleTaskLastSuccessDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            }
        }

        Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.checkOneTimeTask(), singleTaskAllSuccess = " + singleTaskAllSuccess);

        // check if need reset singleTaskAllSuccess
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) != singleTaskLastSuccessDay) {
            singleTaskAllSuccess = false;
        }
    }

    private void dismissTaskListAndReOpen() throws InterruptedException {
        UiDriver.click_Top300px_Center();
        Thread.sleep(2000);
        UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_WALK_STEPS));
    }

    boolean thumbUp20Times() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_THUMP_UP_20_TIMES);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_TASK_FOLLOW), 10000)) {
                    int count = 0;
                    while (count < 40) {
                        UiDriver.click_ScreenCenter();
                        count++;
                    }

                    if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_TASK_CONGRATULATE_TASK_COMPLETED))) {
                        result = true;
                    } else {
                        Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.thumbUp20Times(), failed");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.thumbUp20Times(), open live window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.thumbUp20Times(), click thumb up 20 times failed");
            }
        } else {
            Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.thumbUp20Times(), find thumb up 20 times failed");
        }

        return result;
    }

    boolean browseSpecialPriceMarkets20Sec() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_BROWSE_SPECIAL_PRICE_MARKET_20SEC);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                if (null != UiDriver.find(new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_SPECIAL_PRICE_MARKET_SWIPE))) {
                    // swipe up 1 time per 3 seconds, total swipe up 15 times
                    int count = 0;
                    while (count < 15) {
                        UiDriver.swipeUp600pxSlowly();
                        Thread.sleep(3000);
                        count++;
                    }

                    if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_TASK_TASK_COMPLETED))) {
                        result = true;
                    } else {
                        Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.browseSpecialPriceMarkets20Sec(), failed");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.browseSpecialPriceMarkets20Sec(), open special price markets window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.browseSpecialPriceMarkets20Sec(), click browse special price markets failed");
            }
        } else {
            Logger.debug("DianTaoWalkRepeatTaskWatchLive30Sec.browseSpecialPriceMarkets20Sec(), find browse special price markets failed");
        }

        return result;
    }
}
