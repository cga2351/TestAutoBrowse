package com.example.test_auto_browse.task.diantao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.utils.Logger;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/5/24
 * description :
 */

public abstract class DianTaoWalkRepeatTask extends DianTaoBaseTask {

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
}
