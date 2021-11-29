package com.example.test_auto_browse.task.diantao.walk;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.diantao.DianTaoBaseTask;
import com.example.test_auto_browse.utils.Logger;

import java.util.Calendar;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/5/24
 * description :
 */

public abstract class DianTaoWalkRepeatTask extends DianTaoBaseTask {

    private boolean singleTaskAllSuccess;
    private boolean singleTask1Success;
    private boolean singleTask2Success;
    private boolean singleTask3Success;
    private boolean singleTask4Success;
    private int singleTaskLastSuccessDay;

    @Override
    public boolean initTask() throws InterruptedException {
        Logger.debug("DianTaoWalkRepeatTask.initTask(), entry");
        boolean result = false;

        if (super.initTask()) {
            Thread.sleep(2000);

            // swipe up to click walk to get gold
//            UiDriver.swipeUp800pxSlowly();
            UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_WALK_GET_GOLD);
            UiDriver.swipeUpToFindObject(uiSelector);

            // click walk to get gold
            if (UiDriver.findAndClick(uiSelector)) {
                // try to get energy drink steps
                if (UiDriver.findAndClick(new UiSelector().textMatches("^[+]\\d{1,4}[步]$"))) {  //匹配 "+" 开头，中间包含1到4位数字，以"步"结尾的字符串
                    // wait the popup dismiss
                    Thread.sleep(3000);
                    Logger.debug("DianTaoWalkRepeatTask.initTask(), get energy drink steps success");
                } else {
                    Logger.debug("DianTaoWalkRepeatTask.initTask(), no energy drink steps");
                }

                result = true;
            } else {
                Logger.debug("DianTaoWalkRepeatTask.initTask(), click walk to get gold");
            }
        } else {
            Logger.debug("DianTaoWalkRepeatTask.initTask(), super.initTask() failed");
        }

        Logger.debug("DianTaoWalkRepeatTask.initTask(), result=" + result + ", task = " + getClass().getSimpleName());
        return result;
    }

    // one time task
    protected void checkOneTimeTask() throws InterruptedException {
        //////////////////////////////////////////////////////////////////////////////////////////
//        // 点赞20次 任务
//        if (!singleTask1Success) {
//            singleTask1Success = thumbUp20Times();
//            Logger.debug("DianTaoWalkRepeatTask.checkOneTimeTask(), thumbUp20Times result = " + singleTask1Success);
//
//            // dismiss task list
//            dismissTaskListAndReOpen();
//        }

        //////////////////////////////////////////////////////////////////////////////////////////
//        // 浏览特价卖场20秒 任务
//        if (!singleTask2Success) {
//            singleTask2Success = browseSpecialPriceMarkets20Sec();
//            Logger.debug("DianTaoWalkRepeatTask.checkOneTimeTask(), browseSpecialPriceMarkets20Sec result = " + singleTask2Success);
//
//            // dismiss task list
//            dismissTaskListAndReOpen();
//        }

        //////////////////////////////////////////////////////////////////////////////////////////
        // 浏览精选推荐20s 任务
        if (!singleTask3Success) {
            singleTask3Success = browseRecommend20s();
            Logger.debug("DianTaoWalkRepeatTask.checkOneTimeTask(), browseRecommend20s result = " + singleTask3Success);

            // dismiss task list
            dismissTaskListAndReOpen();
        }

        //
        if (singleTask3Success) {
            singleTaskLastSuccessDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }

        // check if need reset singleTaskAllSuccess
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) != singleTaskLastSuccessDay) {
            singleTask1Success = false;
            singleTask2Success = false;
            singleTask3Success = false;
            singleTask4Success = false;
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
                        Logger.debug("DianTaoWalkRepeatTask.thumbUp20Times(), failed");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWalkRepeatTask.thumbUp20Times(), open live window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTask.thumbUp20Times(), click thumb up 20 times failed");
            }
        } else {
            Logger.debug("DianTaoWalkRepeatTask.thumbUp20Times(), find thumb up 20 times failed");
        }

        return result;
    }

    boolean browseRecommend20s() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_BROWSE_RECOMMEND_20s);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {

                if (null != UiDriver.find(new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_SWIPE_TO_BROWSE))) {
                    long startTime = System.currentTimeMillis();
                    while (System.currentTimeMillis() - startTime < 1000 * 20 + 1000 * 10) {
                        UiDriver.swipeUp600pxSlowly();
                        Thread.sleep(1000 * 5);
                    }

                    if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_TASK_TASK_COMPLETED))) {
                        Logger.debug("DianTaoWalkRepeatTask.browseRecommend20s(), task success");
                        result = true;
                    } else {
                        Logger.debug("DianTaoWalkRepeatTask.browseRecommend20s(), task not finished");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWalkRepeatTask.browseRecommend20s(), open live window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTask.browseRecommend20s(), click thumb up 20 times failed");
            }
        } else {
            Logger.debug("DianTaoWalkRepeatTask.browseRecommend20s(), find thumb up 20 times failed");
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
                        Logger.debug("DianTaoWalkRepeatTask.browseSpecialPriceMarkets20Sec(), failed");
                    }

                    // back to task list
                    UiDriver.pressBack();
                } else {
                    Logger.debug("DianTaoWalkRepeatTask.browseSpecialPriceMarkets20Sec(), open special price markets window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTask.browseSpecialPriceMarkets20Sec(), click browse special price markets failed");
            }
        } else {
            Logger.debug("DianTaoWalkRepeatTask.browseSpecialPriceMarkets20Sec(), find browse special price markets failed");
        }

        return result;
    }
}
