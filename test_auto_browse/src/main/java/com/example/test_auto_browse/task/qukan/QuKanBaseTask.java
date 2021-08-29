package com.example.test_auto_browse.task.qukan;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.BrowseBaseTask;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

public abstract class QuKanBaseTask extends BrowseBaseTask {
    private static long lastQuKanDailyTaskCheckTime = 0;
    protected boolean haveClickedBonus = false;

    @Override
    public long getLastDailyTaskCheckTime() {
        return lastQuKanDailyTaskCheckTime;
    }

    @Override
    public void setLastCheckTime(long time) {
        lastQuKanDailyTaskCheckTime = time;
    }

    @Override
    public String getTargetAppPackageName() { return Constant.PKG_NAME_QU_KAN; }

    @Override
    public void exeDailyTasks() throws InterruptedException {
        // do nothing
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
//        taskPackageName = Constant.PKG_NAME_QU_KAN;

        haveClickedBonus = false;

        if (super.initTask()) {
            // switch to task page
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QU_KAN_TASK_PAGE), Constant.WAIT_TIME_20_SEC)) {
                // dismiss sign success popup
                dismissGetSignGoldPage();

                // dismiss other popup page
                dismissPopupPage();

                // wait task page load end
                UiDriver.find(new UiSelector().text(Constant.STR_QU_KAN_MY_GOLD), Constant.WAIT_TIME_20_SEC);
                Thread.sleep(5000);

                // check daily task
                checkDailyTask();

                result = true;
            } else {
                Logger.debug("QuKanBaseTask.initTask(), switch to task page failed");
            }
        } else {
            Logger.debug("QuKanBaseTask.initTask(), super.initTask() failed");
        }

        Logger.debug("QuKanBaseTask.initTask(), result=" + result);

        return result;
    }

    private void dismissPopupPage() throws InterruptedException {
        Logger.debug("QuKanBaseTask.dismissPopupPage() entry");
        // press back to close the popup page and re-click the task tab
        UiDriver.pressBack();
        Thread.sleep(1000);
        UiDriver.findAndClick(new UiSelector().text(Constant.STR_QU_KAN_TASK_PAGE));
    }

    @Override
    public void endTask() throws InterruptedException {
        Logger.debug("QuKanBaseTask.endTask(), entry");
        haveClickedBonus = false;
        SysUtil.killApp(Constant.PKG_NAME_QU_KAN);
    }

    private void dismissGetSignGoldPage() throws InterruptedException {
        if (null != UiDriver.find(new UiSelector().text(Constant.STR_QU_KAN_SIGN_SUCESS), Constant.WAIT_TIME_15_SEC)) {
            if (UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_QU_KAN_WATCH_VIDEO_TO_GET_MORE))) {
                Logger.debug("QuKanBaseTask.dismissGetSignGoldPage()，sign success, watch video to get more");
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_QU_KAN_WATCH_VIDEO_COUNT_DOWN), Constant.WAIT_TIME_10_SEC)) {
                    Logger.debug("QuKanBaseTask.dismissGetSignGoldPage()，enter watch video page, wait ads end");
                    long startTime = System.currentTimeMillis();
                    boolean adsEnd = false;
                    while (System.currentTimeMillis() - startTime < 1000 * 50) {
                        if (null != UiDriver.find(new UiSelector().text(Constant.STR_QU_KAN_GET_GOLD_SUCCESS))) {
                            adsEnd = true;
                            break;
                        }
                        Thread.sleep(1000);
                    }

                    if (adsEnd) {
                        if (null == UiDriver.find(new UiSelector().text(Constant.STR_QU_KAN_GET_GOLD_SUCCESS))) {
                            // open the ads' app, double click back to close it
                            UiDriver.pressBack();
                            Thread.sleep(500);
                            UiDriver.pressBack();
                        }

                        // back to home page
                        UiDriver.pressBack();
                    } else {
                        Logger.debug("QuKanBaseTask.dismissGetSignGoldPage()，after 50s, the ads is still not end");
                    }
                } else {
                    Logger.debug("QuKanBaseTask.dismissGetSignGoldPage()，enter watch video page failed");
                }
            } else {
                Logger.debug("QuKanBaseTask.dismissGetSignGoldPage()，sign success, click watch video to get more");
            }
        } else {
            Logger.debug("QuKanBaseTask.dismissGetSignGoldPage()，no sign success popup");
        }
    }

    protected void clickBonusIcon() throws InterruptedException {
        UiDriver.findAndClick(new UiSelector().resourceIdMatches("^com.jifen.qukan:id.*").className("android.widget.ImageView").clickable(false), 3000);
        Thread.sleep(5000);
        UiDriver.pressBack();
    }
}