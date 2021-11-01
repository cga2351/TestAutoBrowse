package com.example.test_auto_browse.task.kuaishou;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class KuaiShouRewardRepeatTask extends KuaiShouBaseTask {
    private KuaiShouRewardRepeatTask() {}
    private static KuaiShouRewardRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new KuaiShouRewardRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 60 * 10 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.KUAI_SHOU_REWARD_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getKuaiShouRewardExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseKuaiShouRewardExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            if (UiDriver.swipeUpToFindObject(new UiSelector().textContains(Constant.STR_KUAI_SHOU_1000_REWARD_TASK))) {
                result = true;
            } else {
                Logger.debug("KuaiShouRewardRepeatTask.initTask(), can't find reward task button");
            }
        } else {
            Logger.debug("KuaiShouRewardRepeatTask.initTask(), super.initTask() failed");
        }

        Logger.debug("KuaiShouRewardRepeatTask.initTask(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // enter watch ads page
        Thread.sleep(2000);
        if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_KUAI_SHOU_1000_REWARD_TASK))) {
            UiSelector uiSelector = new UiSelector().textContains(Constant.STR_KUAI_SHOU_1000_REWARD_TASK_END);
            if (null != UiDriver.find(uiSelector)) {
                Logger.debug("KuaiShouRewardRepeatTask.autoBrowse(), wait for the ads to end");

                // wait for the ads to end for max time 60s
                long startTime = System.currentTimeMillis();
                while (!result && System.currentTimeMillis() - startTime < 1000 * 60) {
                    Thread.sleep(1000 * 5);
                    if (null == UiDriver.find(uiSelector, 3000)) {
                        result = true;
                        Logger.debug("KuaiShouRewardRepeatTask.autoBrowse(), ads end after "
                                + (System.currentTimeMillis() - startTime) / 1000 + "s");
                    }
                }

                // back to home page
                UiDriver.pressBack();
                if (!result && UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_KUAI_SHOU_GIVE_UP_REWARD))) {
                    Logger.debug("KuaiShouRewardRepeatTask.autoBrowse(), task failed, the ads have not end after 60s");
                }
            } else {
                Logger.debug("KuaiShouRewardRepeatTask.autoBrowse(), open ads page failed");
            }
        } else {
            Logger.debug("KuaiShouRewardRepeatTask.autoBrowse(), click reward task failed");
        }

        Logger.debug("KuaiShouRewardRepeatTask.autoBrowse(), result=" + result);
        return result;
    }

}
