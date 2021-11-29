package com.example.test_auto_browse.task.diantao.work;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.diantao.DianTaoBaseTask;
import com.example.test_auto_browse.utils.Logger;

import java.util.Calendar;

public abstract class DianTaoWorkRepeatTask extends DianTaoBaseTask {

    private boolean singleTask1Success;
    private boolean singleTask2Success;
    private boolean singleTask3Success;
    private boolean singleTask4Success;
    private boolean singleTask5Success;
    private boolean singleTask6Success;
    private boolean singleTask7Success;
    private int singleTaskLastSuccessDay;
    
    @Override
    public boolean initTask() throws InterruptedException {
        Logger.debug("DianTaoWorkRepeatTask.initTask(), entry");
        boolean result = false;

        if (super.initTask()) {
            Thread.sleep(2000);

            // swipe up to click work to get gold
            UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_WORK_TO_GET_GOLD);
            UiDriver.swipeUpToFindObject(uiSelector);

            // click work to get gold
            if (UiDriver.findAndClick(uiSelector)) {
                // wait page load end
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_WORK_SIGN), Constant.WAIT_TIME_10_SEC)) {
                    Thread.sleep(2000);

                    // DianTaoWorkTimedTaskCheckWork task need not to open the task list
                    if (!(this instanceof DianTaoWorkTimedTaskCheckWork)) {
                        // open get energy task list
                        UiDriver.click(CoordsAdapter.getDianTaoGetEnergyCoords());
                    }

                    result = true;
                } else {
                    Logger.debug("DianTaoWorkRepeatTask.initTask(), work page load not ended");
                }
            } else {
                Logger.debug("DianTaoWorkRepeatTask.initTask(), click work to get gold failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTask.initTask(), super.initTask() failed");
        }

        Logger.debug("DianTaoWorkRepeatTask.initTask(), result=" + result + ", task = " + getClass().getSimpleName());
        return result;
    }

    protected void backToWorkPage() throws InterruptedException {
        // press back
        UiDriver.pressBack();
        Thread.sleep(7000);

        // dismiss task list
//        UiDriver.click_Top300px_Center();
//        Thread.sleep(2000);
    }

    // one time task
    protected void checkOneTimeTask() throws InterruptedException {
        //////////////////////////////////////////////////////////////////////////////////////////
//        // 点赞20次 任务
//        if (!singleTask1Success) {
//            singleTask1Success = thumbUp20Times();
//            Logger.debug("DianTaoWorkRepeatTask.checkOneTimeTask(), thumbUp20Times result = " + singleTask1Success);
//
//            // dismiss task list
//            dismissTaskListAndReOpen();
//        }

        //////////////////////////////////////////////////////////////////////////////////////////
//        // 浏览特价卖场30秒 任务
//        if (!singleTask2Success) {
//            singleTask2Success = browseSpecialPriceMarket30s();
//            Logger.debug("DianTaoWorkRepeatTask.checkOneTimeTask(), browseSpecialPriceMarket30s result = " + singleTask2Success);
//
//            // dismiss task list
//            dismissTaskListAndReOpen();
//        }

        //////////////////////////////////////////////////////////////////////////////////////////
//        // 去玩消消乐 任务
//        if (!singleTask3Success) {
//            singleTask3Success = playGame();
//            Logger.debug("DianTaoWorkRepeatTask.checkOneTimeTask(), playGame result = " + singleTask3Success);
//
//            // dismiss task list
//            dismissTaskListAndReOpen();
//        }

        //////////////////////////////////////////////////////////////////////////////////////////
//        // 去芭芭农场施肥 任务
//        if (!singleTask4Success) {
//            singleTask4Success = goToFarmFertilize();
//            Logger.debug("DianTaoWorkRepeatTask.checkOneTimeTask(), goToFarmFertilize result = " + singleTask4Success);
//
//            // dismiss task list
//            dismissTaskListAndReOpen();
//        }

        //////////////////////////////////////////////////////////////////////////////////////////
        // 浏览好货卖场30s 任务
        if (!singleTask5Success) {
            singleTask5Success = browseGoodsMarket30s();
            Logger.debug("DianTaoWorkRepeatTask.checkOneTimeTask(), browseGoodsMarket30s result = " + singleTask5Success);

            // dismiss task list
            dismissTaskListAndReOpen();
        }

        //////////////////////////////////////////////////////////////////////////////////////////
        // 浏览精选推荐30s 任务
        if (!singleTask6Success) {
            singleTask6Success = browseRecommend30s();
            Logger.debug("DianTaoWorkRepeatTask.checkOneTimeTask(), browseRecommend30s result = " + singleTask6Success);

            // dismiss task list
            dismissTaskListAndReOpen();
        }

        //////////////////////////////////////////////////////////////////////////////////////////
        // 浏览上新日历 任务
        if (!singleTask7Success) {
            singleTask7Success = browseNewArrival30s();
            Logger.debug("DianTaoWorkRepeatTask.checkOneTimeTask(), browseNewArrival30s result = " + singleTask7Success);

            // dismiss task list
            dismissTaskListAndReOpen();
        }


        //
        if (singleTask5Success && singleTask6Success && singleTask7Success) {
            singleTaskLastSuccessDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }

        Logger.debug("DianTaoWorkRepeatTask.checkOneTimeTask()"
                + ", singleTask1Success = " + singleTask1Success
                + ", singleTask2Success = " + singleTask2Success
                + ", singleTask3Success = " + singleTask3Success
                + ", singleTask4Success = " + singleTask4Success
                + ", singleTask5Success = " + singleTask5Success
                + ", singleTask6Success = " + singleTask6Success
                + ", singleTask7Success = " + singleTask7Success
        );

        // check if need reset singleTaskAllSuccess
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) != singleTaskLastSuccessDay) {
            singleTask1Success = false;
            singleTask2Success = false;
            singleTask3Success = false;
            singleTask4Success = false;
            singleTask5Success = false;
            singleTask6Success = false;
            singleTask7Success = false;
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
                        Logger.debug("DianTaoWorkRepeatTask.thumbUp20Times(), failed");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWorkRepeatTask.thumbUp20Times(), open live window failed");
                }
            } else {
                Logger.debug("DianTaoWorkRepeatTask.thumbUp20Times(), click thumb up 20 times failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTask.thumbUp20Times(), find thumb up 20 times failed");
        }

        return result;
    }

    boolean browseSpecialPriceMarket30s() throws InterruptedException {
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
                        Logger.debug("DianTaoWorkRepeatTask.browseSpecialPriceMarket30s(), failed");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWorkRepeatTask.browseSpecialPriceMarket30s(), open special price markets window failed");
                }
            } else {
                Logger.debug("DianTaoWorkRepeatTask.browseSpecialPriceMarket30s(), click browse special price markets failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTask.browseSpecialPriceMarket30s(), find browse special price markets failed");
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
                    Logger.debug("DianTaoWorkRepeatTask.playGame(), enter game page failed");
                } else {
                    UiDriver.pressBack();
                    result = true;
                }

            } else {
                Logger.debug("DianTaoWorkRepeatTask.playGame(), click play game failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTask.playGame(), find play game failed");
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
                    Logger.debug("DianTaoWorkRepeatTask.goToFarmFertilize(), enter fertilize page failed");
                } else {
                    UiDriver.pressBack();
                    result = true;
                }

            } else {
                Logger.debug("DianTaoWorkRepeatTask.goToFarmFertilize(), click fertilize failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTask.goToFarmFertilize(), find go to fertilize failed");
        }

        return result;
    }

    boolean browseGoodsMarket30s() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_BROWSE_GOODS_MARKET_30s);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                Thread.sleep(3000);
                if (null != UiDriver.find(new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_SWIPE_TO_BROWSE))) {
                    long startTime = System.currentTimeMillis();
                    while (System.currentTimeMillis() - startTime < 1000 * 30 + 1000 * 10) {
                        UiDriver.swipeUp600pxSlowly();
                        Thread.sleep(1000 * 5);
                    }

                    if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_TASK_TASK_COMPLETED))) {
                        Logger.debug("DianTaoWalkRepeatTask.browseGoodsMarket30s(), task success");
                        result = true;
                    } else {
                        Logger.debug("DianTaoWalkRepeatTask.browseGoodsMarket30s(), task not finished");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWorkRepeatTask.browseGoodsMarket30s(), enter page failed");
                }

            } else {
                Logger.debug("DianTaoWorkRepeatTask.browseGoodsMarket30s(), click button failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTask.browseGoodsMarket30s(), find button failed");
        }

        return result;
    }

    boolean browseRecommend30s() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_BROWSE_RECOMMEND_30s);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                Thread.sleep(3000);
                if (null != UiDriver.find(new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_SWIPE_TO_BROWSE))) {
                    long startTime = System.currentTimeMillis();
                    while (System.currentTimeMillis() - startTime < 1000 * 30 + 1000 * 10) {
                        UiDriver.swipeUp600pxSlowly();
                        Thread.sleep(1000 * 5);
                    }

                    if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_TASK_TASK_COMPLETED))) {
                        Logger.debug("DianTaoWalkRepeatTask.browseRecommend30s(), task success");
                        result = true;
                    } else {
                        Logger.debug("DianTaoWalkRepeatTask.browseRecommend30s(), task not finished");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWorkRepeatTask.browseRecommend30s(), enter page failed");
                }

            } else {
                Logger.debug("DianTaoWorkRepeatTask.browseRecommend30s(), click button failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTask.browseRecommend30s(), find button failed");
        }

        return result;
    }

    boolean browseNewArrival30s() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_BROWSE_NEW_ARRIVAL_30s);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                Thread.sleep(3000);
                if (null != UiDriver.find(new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_SWIPE_TO_BROWSE))) {
                    long startTime = System.currentTimeMillis();
                    while (System.currentTimeMillis() - startTime < 1000 * 30 + 1000 * 10) {
                        UiDriver.swipeUp600pxSlowly();
                        Thread.sleep(1000 * 5);
                    }

                    if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_TASK_TASK_COMPLETED))) {
                        Logger.debug("DianTaoWalkRepeatTask.browseNewArrival30s(), task success");
                        result = true;
                    } else {
                        Logger.debug("DianTaoWalkRepeatTask.browseNewArrival30s(), task not finished");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWorkRepeatTask.browseNewArrival30s(), enter page failed");
                }

            } else {
                Logger.debug("DianTaoWorkRepeatTask.browseNewArrival30s(), click button failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTask.browseNewArrival30s(), find button failed");
        }

        return result;
    }
}
