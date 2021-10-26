package com.example.test_auto_browse.task.diantao.lottery;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.diantao.DianTaoBaseTask;
import com.example.test_auto_browse.utils.Logger;

public abstract class DianTaoLotteryRepeatTask extends DianTaoBaseTask {

    @Override
    public boolean initTask() throws InterruptedException {
        Logger.debug("DianTaoLotteryRepeatTask.initTask(), entry");
        boolean result = false;

        if (super.initTask()) {
            Thread.sleep(2000);

            // swipe up to click walk to get gold
            UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_LOTTERY);
            UiDriver.swipeUpToFindObject(uiSelector, 8);

            // click walk to get gold
            if (UiDriver.findAndClick(uiSelector)) {
                result = true;
                Logger.debug("DianTaoLotteryRepeatTask.initTask(), click lottery success");
            } else {
                Logger.debug("DianTaoLotteryRepeatTask.initTask(), click lottery failed");
            }
        } else {
            Logger.debug("DianTaoLotteryRepeatTask.initTask(), super.initTask() failed");
        }

        Logger.debug("DianTaoLotteryRepeatTask.initTask(), result=" + result + ", task = " + getClass().getSimpleName());
        return result;
    }
}