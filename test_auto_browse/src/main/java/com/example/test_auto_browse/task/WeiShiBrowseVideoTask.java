package com.example.test_auto_browse.task;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;

import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/6/9
 * description :
 */

public class WeiShiBrowseVideoTask extends BrowseBaseTask {
    private WeiShiBrowseVideoTask() {}

    private static WeiShiBrowseVideoTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new WeiShiBrowseVideoTask();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;

        // launch weishi
        if (SysUtil.launchApp(Constant.PKG_NAME_WEI_SHI)) {
            // jump ads
            if (!UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_WEI_SHI_JUMP_ADS))) {
                Logger.debug("WeiShiBrowseVideoTask.initTask(), no startup ads");
            }

            // close teen protection
            if (null != UiDriver.find(new UiSelector().text(Constant.STR_WEI_SHI_TEEN_PROTECTION))) {
                Logger.debug("WeiShiBrowseVideoTask.initTask(), close teen protection popup");
                UiDriver.find(new UiSelector().textStartsWith(Constant.STR_WEI_SHI_I_KNOW));
            }

            try {
                // check sign, click coords [864,1926][1080,2076] of (108, 75) to switch mine tab
                Logger.debug("WeiShiBrowseVideoTask.initTask(), click mine tab coords");
                UiDriver.click(CoordsAdapter.getWeiShiMineTabCoords());
                if (null != UiDriver.find(new UiSelector().text(Constant.STR_WEI_SHI_WELFARE_CENTER))) {
                    if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_WEI_SHI_WELFARE_CENTER))) {
                        // swipe up a little bit when page load end
                        UiDriver.find(new UiSelector().text("任务中心"), 15000);
                        UiDriver.swipeUp600pxSlowly();

                        // click sign to get gold
                        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_WEI_SHI_SIGN_TO_GET_GOLD))) {
                            Logger.debug("WeiShiBrowseVideoTask.initTask(), sign success");
                        } else {
                            Logger.debug("WeiShiBrowseVideoTask.initTask(), click sign failed");
                        }
                    } else {
                        Logger.debug("WeiShiBrowseVideoTask.initTask(), open welfare center failed");
                    }
                } else {
                    Logger.debug("WeiShiBrowseVideoTask.initTask(), switch to mine tab failed");
                }

                // swipe up, open browse video window
                UiDriver.swipeUp1000pxSlowly();
                Thread.sleep(2000);
                UiDriver.swipeUp1000pxSlowly();

                if (UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_WEI_SHI_ALL_KARAOKE))) {
                    result = true;
                } else {
                    Logger.debug("WeiShiBrowseVideoTask.initTask(), open browse video window failed");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Logger.debug("WeiShiBrowseVideoTask.initTask(), launch app failed");
        }


        Logger.debug("WeiShiBrowseVideoTask.init(), result=" + result);

        return result;
    }

    @Override
    public boolean autoBrowse() {
        return false;
    }

    @Override
    public void endTask() {

    }
}
