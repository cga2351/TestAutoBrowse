package com.example.test_auto_browse.task.toutiao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.task.TimedTaskAvailableChecker;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/5/19
 * description :
 */

public class TouTiaoTreasureBoxTimedTask extends TouTiaoBaseTask {
    private TouTiaoTreasureBoxTimedTask() {}
    private static TouTiaoTreasureBoxTimedTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new TouTiaoTreasureBoxTimedTask();
                }
            }
        }
        return instance;
    }

    // for timed task
    TimedTaskAvailableChecker timedTaskChecker = new TimedTaskAvailableChecker() {
        @Override
        protected int getExecInterval() {
            return 1000 * 60 * 10;
        }
    };

    @Override
    protected int getMaxExecCount() {
        return Constant.TOU_TIAO_TREASURE_BOX_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        if (timedTaskChecker.isTimedTaskAvailable()) {
            return getMaxExecCount() - LocalStorageUtil.getCachedTaskExecCount()
                    .getTouTiaoTreasureBoxExecCount();
        } else {
            return 0;
        }
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseTouTiaoTreasureBoxExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // open treasure box
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_OPEN_TREASURE_BOX), Constant.WAIT_TIME_15_SEC)) {
            if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_TOU_TIAO_WATCH_ADS_TO_GET_MORE))) {
                // watch ads video to get more
                touTiaoWatchAds();
            }
            result = true;
        } else {
            Logger.debug("TouTiaoTreasureBoxTimedTask.autoBrowse(), open treasure box failed");
        }

        if (result) {
            timedTaskChecker.setLastSuccessTime(System.currentTimeMillis());
        }

        Logger.debug("TouTiaoTreasureBoxTimedTask.autoBrowse(), result=" + result);
        return result;
    }
}
