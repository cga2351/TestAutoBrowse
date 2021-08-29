package com.example.test_auto_browse.task.douyin;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.Logger;

public class DouYinTreasureBoxTimedTask extends DouYinBaseTask {
    private DouYinTreasureBoxTimedTask() {}
    private static DouYinTreasureBoxTimedTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DouYinTreasureBoxTimedTask();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // open treasure box
        if (UiDriver.findAndClick(new UiSelector().description(Constant.STR_DOU_YIN_OPEN_TREASURE_BOX), Constant.WAIT_TIME_15_SEC)) {
//            Thread.sleep(3000);
//            if (UiDriver.findAndClick(new UiSelector().descriptionStartsWith(Constant.STR_DOU_YIN_WATCH_ADS_TO_GET_MORE))) {
//                // watch ads video to get more
//                result = douYinWatchAds();
//                Logger.debug("DouYinTreasureBoxTimedTask.autoBrowse(), watch ads result=" + result);
//            }
            result = true;
        } else {
            Logger.debug("DouYinTreasureBoxTimedTask.autoBrowse(), open treasure box failed");
        }

        Logger.debug("DouYinTreasureBoxTimedTask.autoBrowse(), result=" + result);
        return result;
    }
}