package com.example.test_auto_browse.task.diantao.work;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWorkRepeatTaskWatchLive3Min extends DianTaoWorkRepeatTask{
    private DianTaoWorkRepeatTaskWatchLive3Min() {}
    private static DianTaoWorkRepeatTaskWatchLive3Min instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWorkRepeatTaskWatchLive3Min();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
//        return 1000 * 60 * 3 + 1000 * 30;
        return 1000 * 60 * 3 * 3 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WORK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWorkWatchLive3MinExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWorkWatchLive3MinExecCount());
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // click watch live 3min
        if (UiDriver.swipeUpToFindAndClickObject(new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_3Min))) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE), Constant.WAIT_TIME_10_SEC)) {
                // enter live window, and wait 3min
                int watchDuration = 1000 * 60 * 3 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, false, true);
                Logger.debug("DianTaoWorkRepeatTaskWatchLive3Min.autoBrowse(), watchVideoOrLive result = " + result);
            } else {
                // entry live window failed, exit task
                Logger.debug("DianTaoWorkRepeatTaskWatchLive3Min.autoBrowse(), enter live window failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchLive3Min.autoBrowse(), click watch live 3min failed");
        }

//        UiDriver.saveDebugScreenshot("checkWorkWatchLiveFailure_" + getClass().getSimpleName());
//        if (null != UiDriver.find(new UiSelector().textContains("????????????"))) {
//            UiDriver.dumpXml2File("/sdcard/testAutoBrowse/screenshots/debugScreenshots/checkWorkWatchLiveFailure_3Min.xml");
//        }

        // return to work page
        backToWorkPage();

        Logger.debug("DianTaoWorkRepeatTaskWatchLive3Min.autoBrowse(), result=" + result);
        return result;
    }
}
