package com.example.test_auto_browse.task.diantao.walk;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

import java.util.Calendar;

public class DianTaoWalkRepeatTaskScratch extends DianTaoWalkRepeatTask {
    private DianTaoWalkRepeatTaskScratch() {}
    private static DianTaoWalkRepeatTaskScratch instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWalkRepeatTaskScratch();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
//        return 1000 * 60 * 3;
        return 1000 * 60 * 5;
    }

    @Override
    protected int getLeftExecCount() {
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 10) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        Logger.debug("DianTaoWalkRepeatTaskScratch.autoBrowse() entry");
        boolean result = true;

        // wait page load end
        UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_REGULATION));
        Thread.sleep(8000);

        // click scratch by coords
        UiDriver.click(CoordsAdapter.getDianTaoScratchCoords());
        Thread.sleep(5000);

        //
        while (result) {
            UiDriver.click(CoordsAdapter.getDianTaoScratchMeGetGoldCoords());
            Thread.sleep(1000);

            // scratch
            UiDriver.swipe(350, 770, 700, 770, 20);
            Thread.sleep(200);

            UiDriver.swipe(350, 870, 700, 870, 20);
            Thread.sleep(200);

            UiDriver.swipe(350, 970, 700, 970, 20);
            Thread.sleep(200);

            // watch live or ads to get more
            if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_DIAN_TAO_WATCH_LIVE_TO_GET_MORE))) {
                result = watchVideoOrLive(1000 * 60, false, true);
                Logger.debug("DianTaoWalkRepeatTaskScratch.autoBrowse(), watch live or ads result = " + result);
                UiDriver.pressBack();
                Thread.sleep(3000);
            } else if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_DIAN_TAO_GO_ON_SCRATCH))) {
                Thread.sleep(1000);
                Logger.debug("DianTaoWalkRepeatTaskScratch.autoBrowse(), no live or ads, and can go on scratch");
            } else {
                Logger.debug("DianTaoWalkRepeatTaskScratch.autoBrowse(), no live or ads, and can't go on scratch, maybe no available steps or no scratch count");
                result = false;
            }
        }

        return result;
    }
}
