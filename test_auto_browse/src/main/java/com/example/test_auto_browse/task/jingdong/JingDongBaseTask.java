package com.example.test_auto_browse.task.jingdong;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.BrowseBaseTask;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

public abstract class JingDongBaseTask extends BrowseBaseTask {
    private static long lastJingDongDailyTaskCheckTime = 0;

    @Override
    public long getLastDailyTaskCheckTime() {
        return lastJingDongDailyTaskCheckTime;
    }

    @Override
    public void setLastCheckTime(long time) {
        lastJingDongDailyTaskCheckTime = time;
    }

    @Override
    public String getTargetAppPackageName() { return Constant.PKG_NAME_JING_DONG; }

    @Override
    public void exeDailyTasks() throws InterruptedException {
        // check all daily tasks
        checkAllDailyTasks();
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
//        taskPackageName = Constant.PKG_NAME_JING_DONG;

        if (super.initTask()) {
            // dismiss popup page
            dismissPopupPage();

            // enter get gold page
            result = openGoldCenterPage();
            Thread.sleep(2000);

            if (result) {
                // check daily task
                checkDailyTask();
            }
        } else {
            Logger.debug("JingDongBaseTask.initTask(), super.initTask() failed");
        }

        Logger.debug("JingDongBaseTask.initTask(), result=" + result);

        return result;
    }

    private void checkAllDailyTasks() throws InterruptedException {
        // click all daily task
        finishDailyTask("邀好友赚金币");      // invite friends
        finishDailyTask("签到提现");        // sign
        finishDailyTask("东东爱消除");        // tiny game dismiss
        finishDailyTask("购券抽大奖");        // lucky draw
        finishDailyTask("幸运转盘");        // lucky dial
        finishDailyTask("现金签到");        // cash sign
        finishDailyTask("推推赚大钱");        // push get gold
        finishDailyTask("天天抽奖");        // lucky draw everyday
        finishDailyTask("水果免费领");        // free fruit
        finishDailyTask("砍价免费拿");        // bargain

        // back to click all gold icon
        Thread.sleep(2000);
        UiDriver.pressBack();
        Logger.debug("JingDongBaseTask.initTask(), click all gold");

        //
        UiDriver.findAndClick(new UiSelector().text("邀好友赚金币"), 3000);
        UiDriver.findAndClick(new UiSelector().text("签到提现"), 3000);
        UiDriver.findAndClick(new UiSelector().text("东东爱消除"), 3000);
        UiDriver.findAndClick(new UiSelector().text("购券抽大奖"), 3000);
        UiDriver.findAndClick(new UiSelector().text("幸运转盘"), 3000);
        UiDriver.findAndClick(new UiSelector().text("现金签到"), 3000);
        UiDriver.findAndClick(new UiSelector().text("推推赚大钱"), 3000);
        UiDriver.findAndClick(new UiSelector().text("天天抽奖"), 3000);
        UiDriver.findAndClick(new UiSelector().text("水果免费领"), 3000);
        UiDriver.findAndClick(new UiSelector().text("砍价免费拿"), 3000);

        // re-open task list
        Thread.sleep(2000);
        UiDriver.click(CoordsAdapter.getJingDongGetGoldCoords());
    }

    private void finishDailyTask(String taskName) throws InterruptedException {
        boolean result = false;
        UiSelector uiSelector = new UiSelector().text(taskName);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            Thread.sleep(2000);
            UiDriver.findAndClick(uiSelector);
            Thread.sleep(1000);
            UiDriver.pressBack();
            result = true;
        }
        Logger.debug("JingDongBaseTask.finishDailyTask() result = " + result + ", taskName = " + taskName);
    }

    private boolean openGoldCenterPage() throws InterruptedException {
        boolean result = false;
        // click get gold page btn
        if (UiDriver.findAndClick(new UiSelector().description(Constant.STR_JING_DONG_GET_GOLD))) {
            // wait gold center page load end
            if (null != UiDriver.find(new UiSelector().text(Constant.STR_JING_DONG_EXCHANGE_LUCKY_MONEY))) {
                Thread.sleep(2000);
                UiDriver.click(CoordsAdapter.getJingDongGetGoldCoords());

                if (null != UiDriver.find(new UiSelector().text(Constant.STR_JING_DONG_BROWSE_GOODS_GET_GOLD))) {
                    result = true;
                }
            } else {
                Logger.debug("JingDongBaseTask.openGoldCenterPage(), gold center page not load completed");
            }
        } else {
            Logger.debug("JingDongBaseTask.openGoldCenterPage(), click gold center page failed");
        }

        return result;
    }

    private void dismissPopupPage() throws InterruptedException {
        Logger.debug("JingDongBaseTask.dismissPopupPage() entry");

        Thread.sleep(5000);

        // close launch ads
        UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_JING_DONG_JUMP_ADS));

        // close popup page
        UiDriver.findAndClick(new UiSelector().description(Constant.STR_JING_DONG_CLOSE_POPUP));

    }

    @Override
    public void endTask() throws InterruptedException {
        Logger.debug("JingDongBaseTask.endTask(), entry");
        SysUtil.killApp(Constant.PKG_NAME_JING_DONG);
    }

    private void getSignGold() throws InterruptedException {
//        UiSelector uiSelector = new UiSelector().text(Constant.STR_KUAI_SHOU_SIGN_TO_GET_GOLD);
//        if (UiDriver.swipeUpToFindObject(uiSelector)) {
//            if (UiDriver.findAndClick(uiSelector)) {
//                Logger.debug("JingDongBaseTask.getSignGold(), sign success");
//
//                // close the popup page, and re-enter task center page
//                Thread.sleep(2000);
//                UiDriver.pressBack();
//                enterTaskCenterPageFromHomePage();
//            } else {
//                Logger.debug("JingDongBaseTask.getSignGold(), open sign window failed");
//            }
//        } else {
//            Logger.debug("JingDongBaseTask.getSignGold(), can't find sign button");
//        }
    }

}
