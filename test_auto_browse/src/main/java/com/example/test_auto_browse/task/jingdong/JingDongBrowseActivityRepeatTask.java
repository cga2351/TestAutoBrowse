package com.example.test_auto_browse.task.jingdong;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class JingDongBrowseActivityRepeatTask extends JingDongBaseTask {
    private JingDongBrowseActivityRepeatTask() { }
    private static JingDongBrowseActivityRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new JingDongBrowseActivityRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 15 * getMaxExecCount() + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.JING_DONG_BROWSE_ACTIVITY_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getJingDongBrowseActivityExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseJingDongBrowseActivityExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_JING_DONG_BROWSE_ACTIVITY_GET_GOLD))) {
                if (null != UiDriver.find(new UiSelector().resourceId(Constant.ID_JING_DONG_GOLD_PROGRESS))) {
                    result = true;
                } else {
                    Logger.debug("JingDongBrowseActivityRepeatTask.initTask(), open browse activity page failed");
                }
            } else {
                Logger.debug("JingDongBrowseActivityRepeatTask.initTask(), click browse activity failed");
            }
        } else {
            Logger.debug("JingDongBrowseActivityRepeatTask.initTask(), super.initTask() failed");
        }

        Logger.debug("JingDongBrowseActivityRepeatTask.initTask(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;
        Logger.debug("JingDongBrowseActivityRepeatTask.autoBrowse(), entry");

        while (!getForceStop()) {
            if (null != UiDriver.find(new UiSelector().text(Constant.STR_JING_DONG_TASK_COMPLETED_TODAY), 2000)) {
                Logger.debug("JingDongBrowseActivityRepeatTask.autoBrowse(), browse activity count reach to max count, "
                        + LocalStorageUtil.getCachedTaskExecCount().getJingDongBrowseActivityExecCount());
                break;
            } else {
                UiSelector uiSelector = new UiSelector().text(Constant.STR_JING_DONG_BROWSE_NEXT);
                while (null == UiDriver.find(uiSelector, 1000)) {
                    UiDriver.swipeUp600pxFast();
                    Thread.sleep(1000);
                }
                UiDriver.findAndClick(uiSelector);
                result = true;
            }
        }

        Logger.debug("JingDongBrowseActivityRepeatTask.autoBrowse(), result=" + result);
        return result;
    }

}
