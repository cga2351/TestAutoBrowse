package com.example.test_auto_browse.task.toutiao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/5/19
 * description :
 */

public class TouTiaoBrowseVideoRepeatTask extends TouTiaoBaseTask {
    private TouTiaoBrowseVideoRepeatTask() {}
    private static TouTiaoBrowseVideoRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new TouTiaoBrowseVideoRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.TOU_TIAO_BROWSE_VIDEO_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() - LocalStorageUtil.getCachedTaskExecCount()
                .getTouTiaoBrowseVideoExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseTouTiaoBrowseVideoExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
//            Thread.sleep(15000);
            // find watch video to get gold
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_WATCH_VIDEO_TO_GET_GOLD))) {
                Thread.sleep(10000);
                UiDriver.click(200, 500);
                result = true;
            } else {
                Logger.debug("TouTiaoBrowseVideoTask.init(), open watch video windows failed");
            }
        } else {
            Logger.debug("TouTiaoBrowseVideoTask.init(), super.initTask() failed");
        }

        Logger.debug("TouTiaoBrowseVideoTask.init(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("TouTiaoBrowseVideoTask.autoBrowse(), entry");

        while (!getForceStop()) {
            // auto watch video for 15s, and swipe to next video
            Thread.sleep(8000);
            UiDriver.swipeLeft1000pxFast();
        }

        Logger.debug("TouTiaoBrowseVideoTask.autoBrowse(), exit");
        return true;
    }

}
