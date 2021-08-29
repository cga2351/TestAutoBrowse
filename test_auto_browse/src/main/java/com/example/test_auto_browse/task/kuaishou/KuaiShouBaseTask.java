package com.example.test_auto_browse.task.kuaishou;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.BrowseBaseTask;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/6/10
 * description :
 */

public abstract class KuaiShouBaseTask extends BrowseBaseTask {
    private static long lastKuaiShouDailyTaskCheckTime = 0;
//    private static long lastKuaiShouDailyTaskCheckTime = System.currentTimeMillis();

    @Override
    public long getLastDailyTaskCheckTime() {
        return lastKuaiShouDailyTaskCheckTime;
    }

    @Override
    public void setLastCheckTime(long time) {
        lastKuaiShouDailyTaskCheckTime = time;
    }

    @Override
    public String getTargetAppPackageName() { return Constant.PKG_NAME_KUAI_SHOU; }

    @Override
    public void exeDailyTasks() throws InterruptedException {
        // get sign gold
        getSignGold();
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
//        taskPackageName = Constant.PKG_NAME_KUAI_SHOU;

        if (super.initTask()) {
            // dismiss popup page
            dismissPopupPage();

            if (enterTaskCenterPageFromHomePage()) {
                result = true;

                // check sign popup page
                UiDriver.findAndClick(new UiSelector().text(Constant.STR_KUAI_SHOU_SIGN_IMMEDIATELY));

                // check daily task
                checkDailyTask();
            } else {
                Logger.debug("KuaiShouBaseTask.initTask(), enter task center page failed");
            }
        } else {
            Logger.debug("KuaiShouBaseTask.initTask(), super.initTask() failed");
        }

        Logger.debug("KuaiShouBaseTask.initTask(), result=" + result);

        return result;
    }

    private void dismissPopupPage() throws InterruptedException {
        Logger.debug("KuaiShouBaseTask.dismissPopupPage() entry");
        if (null != UiDriver.find(new UiSelector().resourceId(Constant.ID_KUAI_SHOU_POPUP_DIALOG), Constant.WAIT_TIME_15_SEC)) {
            boolean closePopupResult = UiDriver.findAndClick(new UiSelector().resourceId(Constant.ID_KUAI_SHOU_COSE_POPUP_DIALOG));
            Logger.debug("KuaiShouBaseTask.dismissPopupPage(), closePopupResult = " + closePopupResult);
        }

        // close teen protection
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_KUAI_SHOU_I_KNOW))) {
            Logger.debug("KuaiShouBaseTask.dismissPopupPage(), close teen protection prompt");
        }
    }

    @Override
    public void endTask() throws InterruptedException {
        Logger.debug("KuaiShouBaseTask.endTask(), entry");
        SysUtil.killApp(Constant.PKG_NAME_KUAI_SHOU);
    }

    private void getSignGold() throws InterruptedException {
        UiSelector uiSelector = new UiSelector().text(Constant.STR_KUAI_SHOU_SIGN_TO_GET_GOLD);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                Logger.debug("KuaiShouBaseTask.getSignGold(), sign success");

                // close the popup page, and re-enter task center page
                Thread.sleep(2000);
                UiDriver.pressBack();
                enterTaskCenterPageFromHomePage();
            } else {
                Logger.debug("KuaiShouBaseTask.getSignGold(), open sign window failed");
            }
        } else {
            Logger.debug("KuaiShouBaseTask.getSignGold(), can't find sign button");
        }
    }

    private boolean enterTaskCenterPageFromHomePage() throws InterruptedException {
        boolean result = false;

        // find left setting btn and wait 2s
        UiSelector uiSelector = new UiSelector().resourceId(Constant.ID_KUAI_SHOU_LEFT_SETTING_BTN);
        if (null != UiDriver.find(uiSelector, Constant.WAIT_TIME_15_SEC)) {
            Thread.sleep(2000);
            if (UiDriver.findAndClick(uiSelector)) {
                Thread.sleep(3000);
                if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_KUAI_SHOU_GO_TO_GET_GOLD))) {
                    // wait page load end
                    if (null != UiDriver.find(new UiSelector().text(Constant.STR_KUAI_SHOU_TASK_CENTER), 20000) ||
                            null != UiDriver.find(new UiSelector().description(Constant.STR_KUAI_SHOU_TASK_CENTER), 20000) ) {
                        result = true;
                    } else {
                        Logger.debug("KuaiShouBaseTask.enterTaskCenterPageFromHomePage(), task center page load not ended");
                    }
                } else {
                    Logger.debug("KuaiShouBaseTask.enterTaskCenterPageFromHomePage(), open get gold window failed");
                }
            } else {
                Logger.debug("KuaiShouBaseTask.enterTaskCenterPageFromHomePage(), open setting side bar failed");
            }
        } else {
            Logger.debug("KuaiShouBaseTask.enterTaskCenterPageFromHomePage(), find left setting btn failed");
        }


        return result;
    }
}
