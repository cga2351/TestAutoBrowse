package com.example.test_auto_browse.task.diantao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

import java.util.Calendar;

public class DianTaoWorkRepeatTaskWatchLive60Sec extends DianTaoWorkRepeatTask{
    private DianTaoWorkRepeatTaskWatchLive60Sec() {}
    private static DianTaoWorkRepeatTaskWatchLive60Sec instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWorkRepeatTaskWatchLive60Sec();
                }
            }
        }
        return instance;
    }

    private boolean singleTask1Success;
    private boolean singleTask2Success;
    private boolean singleTask3Success;
    private boolean singleTask4Success;
    private int singleTaskLastSuccessDay;

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 60 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WORK_WATCH_LIVE_60SEC_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWorkWatchLive60SecExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWorkWatchLive60SecExecCount());
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // open get energy task list
        UiDriver.click(CoordsAdapter.getDianTaoGetEnergyCoords());

        // 先完成一天只有一次的单次任务
        checkOneTimeTask();

        // click watch live 60sec
        UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_60Sec);
        if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                // enter live window, and wait 60sec
                int watchDuration = 1000 * 60 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, false);
                Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.autoBrowse(), watchVideoOrLive result = " + result);
            } else {
                // entry live window failed, exit task
                Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.autoBrowse(), enter live window failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.autoBrowse(), click watch live 60sec failed");
        }

        // return to work page
        backToWorkPage();

        Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.autoBrowse(), result=" + result);
        return result;
    }

    private void checkOneTimeTask() throws InterruptedException {
        if (!singleTask1Success) {
            // 点赞20次 任务
            singleTask1Success = thumbUp20Times();
            Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.checkOneTimeTask(), thumbUp20Times result = " + singleTask1Success);

            // dismiss task list
            dismissTaskListAndReOpen();
        }

//        if (!singleTask2Success) {
//            // 浏览特价卖场30秒 任务
//            singleTask2Success = browseSpecialPriceMarkets30Sec();
//            Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.checkOneTimeTask(), browseSpecialPriceMarkets20Sec result = " + singleTask2Success);
//
//            // dismiss task list
//            dismissTaskListAndReOpen();
//        }
//
//        if (!singleTask3Success) {
//            // 去玩消消乐 任务
//            singleTask3Success = playGame();
//            Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.checkOneTimeTask(), playGame result = " + singleTask3Success);
//
//            // dismiss task list
//            dismissTaskListAndReOpen();
//        }

        if (!singleTask4Success) {
            // 去芭芭农场施肥 任务
            singleTask4Success = goToFarmFertilize();
            Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.checkOneTimeTask(), playGame result = " + singleTask4Success);

            // dismiss task list
            dismissTaskListAndReOpen();
        }

        //
        if (singleTask1Success && singleTask2Success && singleTask3Success && singleTask4Success) {
            singleTaskLastSuccessDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }

        Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.checkOneTimeTask()"
                + ", singleTask1Success = " + singleTask1Success
                + ", singleTask2Success = " + singleTask2Success
                + ", singleTask3Success = " + singleTask3Success
                + ", singleTask4Success = " + singleTask4Success
        );

        // check if need reset singleTaskAllSuccess
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) != singleTaskLastSuccessDay) {
            singleTask1Success = false;
            singleTask2Success = false;
            singleTask3Success = false;
            singleTask4Success = false;
        }
    }

    private void dismissTaskListAndReOpen() throws InterruptedException {
        Thread.sleep(2000);
        UiDriver.click_Top300px_Center();
        Thread.sleep(2000);
        UiDriver.click(CoordsAdapter.getDianTaoGetEnergyCoords());
    }

    boolean thumbUp20Times() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_THUMP_UP_20_TIMES);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_TASK_FOLLOW), 20000)) {
                    int count = 0;
                    while (count < 40) {
                        UiDriver.click_ScreenCenter();
                        count++;
                    }

                    if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_TASK_CONGRATULATE_TASK_COMPLETED))) {
                        result = true;
                    } else {
                        Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.thumbUp20Times(), failed");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.thumbUp20Times(), open live window failed");
                }
            } else {
                Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.thumbUp20Times(), click thumb up 20 times failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.thumbUp20Times(), find thumb up 20 times failed");
        }

        return result;
    }

    boolean browseSpecialPriceMarkets30Sec() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_BROWSE_SPECIAL_PRICE_MARKET_30SEC);
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
                        Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.browseSpecialPriceMarkets30Sec(), failed");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.browseSpecialPriceMarkets30Sec(), open special price markets window failed");
                }
            } else {
                Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.browseSpecialPriceMarkets30Sec(), click browse special price markets failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.browseSpecialPriceMarkets30Sec(), find browse special price markets failed");
        }

        return result;
    }


    boolean playGame() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_PLAY_GAME);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                Thread.sleep(3000);
                if (null != UiDriver.find(uiSelector)) {
                    Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.playGame(), enter game page failed");
                } else {
                    UiDriver.pressBack();
                    result = true;
                }

            } else {
                Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.playGame(), click play game failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.playGame(), find play game failed");
        }

        return result;
    }

    boolean goToFarmFertilize() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_GO_TO_FERTILIZE);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                Thread.sleep(3000);
                if (null != UiDriver.find(uiSelector)) {
                    Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.goToFarmFertilize(), enter fertilize page failed");
                } else {
                    UiDriver.pressBack();
                    result = true;
                }

            } else {
                Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.goToFarmFertilize(), click fertilize failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchLive60Sec.goToFarmFertilize(), find go to fertilize failed");
        }

        return result;
    }
}
