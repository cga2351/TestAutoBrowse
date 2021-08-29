package com.example.test_auto_browse.task.toutiao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class TouTiaoBrowseGoodsTimedTask extends TouTiaoBaseTask {
    private TouTiaoBrowseGoodsTimedTask() {}
    private static TouTiaoBrowseGoodsTimedTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new TouTiaoBrowseGoodsTimedTask();
                }
            }
        }
        return instance;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.TOU_TIAO_BROWSE_GOODS_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getTouTiaoBrowseGoodsExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseTouTiaoBrowseGoodsExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_TOU_TIAO_BROWSE_GOODS_TO_GET_GOLD);
        int swipeCount = 0;
        boolean foundBrowseGoods = false;
        while (swipeCount < 5 && !foundBrowseGoods) {
            if (null != UiDriver.findInVisibleRect(uiSelector)) {
                Logger.debug("TouTiaoBrowseGoodsTimedTask.autoBrowse(), found browse goods button");
                foundBrowseGoods = true;
            } else {
                // try to open the task list
                if(UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_MORE_TASK), 2000)) {
                    Logger.debug("TouTiaoBrowseGoodsTimedTask.autoBrowse(), open more task list");
                }

                // swipe up to find again
                UiDriver.swipeUp800pxSlowly();
                swipeCount++;
                Logger.debug("TouTiaoBrowseGoodsTimedTask.autoBrowse(), swipe up to find again, swipeCount = " + swipeCount);
            }
        }

        if (foundBrowseGoods) {
            if (UiDriver.findAndClick(uiSelector)) {
                if (null != UiDriver.find(new UiSelector().text(Constant.STR_TOU_TIAO_BROWSE_GOODS_30S))) {
                    Thread.sleep(1000 * 35);
                    UiDriver.pressBack();
                    Thread.sleep(1000 * 5);
                    result = true;
                } else {
                    Logger.debug("TouTiaoBrowseGoodsTimedTask.autoBrowse(), open browse goods window failed");
                }
            } else {
                Logger.debug("TouTiaoBrowseGoodsTimedTask.autoBrowse(), click to open browse goods window failed");
            }
        } else {
            Logger.debug("TouTiaoBrowseGoodsTimedTask.autoBrowse(), can't find browse goods button");
        }

        Logger.debug("TouTiaoBrowseGoodsTimedTask.autoBrowse(), result = " + result);

        return result;
    }

}
