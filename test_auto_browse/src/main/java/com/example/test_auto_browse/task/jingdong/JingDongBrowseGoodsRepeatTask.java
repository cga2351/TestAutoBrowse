package com.example.test_auto_browse.task.jingdong;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class JingDongBrowseGoodsRepeatTask extends JingDongBaseTask {
    private JingDongBrowseGoodsRepeatTask() { }
    private static JingDongBrowseGoodsRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new JingDongBrowseGoodsRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 60 * 3;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.JING_DONG_BROWSE_GOODS_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getJingDongBrowseGoodsExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseJingDongBrowseGoodsExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_JING_DONG_BROWSE_GOODS_GET_GOLD))) {
                if (null != UiDriver.find(new UiSelector().resourceId(Constant.ID_JING_DONG_GOLD_PROGRESS))) {
                    result = true;
                } else {
                    Logger.debug("JingDongBrowseGoodsRepeatTask.initTask(), open browse goods page failed");
                }
            } else {
                Logger.debug("JingDongBrowseGoodsRepeatTask.initTask(), click browse goods failed");
            }
        } else {
            Logger.debug("JingDongBrowseGoodsRepeatTask.initTask(), super.initTask() failed");
        }

        Logger.debug("JingDongBrowseGoodsRepeatTask.initTask(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;
        Logger.debug("JingDongBrowseGoodsRepeatTask.autoBrowse(), entry");

        while (!getForceStop()) {
            if (null != UiDriver.find(new UiSelector().text(Constant.STR_JING_DONG_TASK_COMPLETED_TODAY), 2000)) {
                Logger.debug("JingDongBrowseGoodsRepeatTask.autoBrowse(), browse goods count reach to max count, "
                        + LocalStorageUtil.getCachedTaskExecCount().getJingDongBrowseGoodsExecCount());
                break;
            } else {
                UiSelector uiSelector = new UiSelector().text(Constant.STR_JING_DONG_BROWSE_NEXT);
                while (null == UiDriver.find(uiSelector, 1000)) {
                    int count = 0;
                    while (count < 10) {
                        UiDriver.swipeUp1000pxFast();
                        count++;
                        Thread.sleep(1000);
                    }
                }
                UiDriver.findAndClick(uiSelector);
                result = true;
            }
        }

        Logger.debug("JingDongBrowseGoodsRepeatTask.autoBrowse(), result=" + result);
        return result;
    }

}