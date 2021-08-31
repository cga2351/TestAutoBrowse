package com.example.test_auto_browse.task.diantao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.utils.Logger;

public abstract class DianTaoWorkRepeatTask extends DianTaoBaseTask {

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

                    // open get energy task list
                    UiDriver.click(CoordsAdapter.getDianTaoGetEnergyCoords());

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
        // press back and dismiss task list
        UiDriver.pressBack();
        Thread.sleep(7000);
        UiDriver.click_Top300px_Center();
        Thread.sleep(2000);
    }

}
