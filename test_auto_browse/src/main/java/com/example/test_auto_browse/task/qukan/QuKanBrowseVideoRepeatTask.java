package com.example.test_auto_browse.task.qukan;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class QuKanBrowseVideoRepeatTask extends QuKanBaseTask {
    private QuKanBrowseVideoRepeatTask() {}
    private static QuKanBrowseVideoRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new QuKanBrowseVideoRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.QU_KAN_BROWSE_VIDEO_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getQuKanBrowseVideoExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseQuKanBrowseVideoExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            // switch to short video tab
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QU_KAN_VIDEO_PAGE))) {
                if (null != UiDriver.find(new UiSelector().text(Constant.STR_QU_KAN_RECOMMEND))) {
                    result = true;
                } else {
                    Logger.debug("QuKanBrowseVideoRepeatTask.init(), switch to video tab failed");
                }
            } else {
                Logger.debug("QuKanBrowseVideoRepeatTask.init(), click video tab failed");
            }
        } else {
            Logger.debug("QuKanBrowseVideoRepeatTask.init(), super.initTask() failed");
        }

        Logger.debug("QuKanBrowseVideoRepeatTask.init(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("QuKanBrowseVideoRepeatTask.autoBrowse(), entry");

        while (!getForceStop()) {
            // click play button and wait
            UiSelector playButton = new UiSelector().textMatches("\\d{2}:\\d{2}");
            if (UiDriver.findAndClick(playButton)) {
                // click play button, and wait 30s
                Logger.debug("QuKanBrowseVideoRepeatTask.autoBrowse(), watch video for 30s");
                Thread.sleep(1000 * 30);
            }

            UiDriver.swipeUp800pxSlowly();

            if (!haveClickedBonus) {
                Logger.debug("QuKanBrowseVideoRepeatTask.autoBrowse(), click bonus button");
                clickBonusIcon();
                haveClickedBonus = true;
            }
        }

        Logger.debug("QuKanBrowseVideoRepeatTask.autoBrowse(), exit");
        return true;
    }
}
