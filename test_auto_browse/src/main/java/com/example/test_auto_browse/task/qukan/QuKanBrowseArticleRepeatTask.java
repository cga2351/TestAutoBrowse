package com.example.test_auto_browse.task.qukan;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class QuKanBrowseArticleRepeatTask extends QuKanBaseTask {
    private QuKanBrowseArticleRepeatTask() {}
    private static QuKanBrowseArticleRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new QuKanBrowseArticleRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.QU_KAN_BROWSE_ARTICLE_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getQuKanBrowseArticleExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseQuKanBrowseArticleExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            int swipeCount = 0;
            while (swipeCount < 4) {
                if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QU_KAN_BROWSE_ARTICLE), 3000)) {
                    if (null != UiDriver.find(new UiSelector().text(Constant.STR_QU_KAN_REFRESH))) {
                        result = true;
                    } else {
                        Logger.debug("QuKanBrowseArticleRepeatTask.init(), open article list page failed");
                    }
                    break;
                }

                // click the collapse task list button if it exists
                if (UiDriver.findAndClick(new UiSelector().text("点击展开更多"), 3000)) {
                    Logger.debug("QuKanBrowseArticleRepeatTask.init(), collapse the task list");
                }

                UiDriver.swipeUp600pxSlowly();
                swipeCount++;
                Logger.debug("QuKanBrowseArticleRepeatTask.init(), can't find brows article, swipe up once");
            }

            if (swipeCount >= 3) {
                Logger.debug("QuKanBrowseArticleRepeatTask.init(), can't find brows article after swipe up 3 times");
            }
        } else {
            Logger.debug("QuKanBrowseArticleRepeatTask.init(), super.initTask() failed");
        }

        Logger.debug("QuKanBrowseArticleRepeatTask.init(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("QuKanBrowseArticleRepeatTask.autoBrowse(), entry");
        Thread.sleep(2000);

        // try to open a article page
        boolean openArticlePage = false;
        long startTime = System.currentTimeMillis();
        while (!openArticlePage && (System.currentTimeMillis() - startTime) < 1000 * 60) {
            Thread.sleep(3000);
            UiDriver.swipeUp600pxFast();
            Thread.sleep(3000);

            // click a headline view
            UiDriver.click_ScreenCenter();
            // short video page, class = com.qukan.media.player.renderview.TextureRenderView
            // video page, class = android.widget.ProgressBar;
            // ads page, no "关注"
            if (null == UiDriver.find(new UiSelector().className("com.qukan.media.player.renderview.TextureRenderView"), 3000) &&
                    null == UiDriver.find(new UiSelector().className("android.widget.ProgressBar"), 3000) &&
                    null != UiDriver.find(new UiSelector().textStartsWith("赏"), 3000)) {
                openArticlePage = true;
            } else {
                // return to the headline list page, and re-try to open a article page
                UiDriver.pressBack();
                Logger.debug("QuKanBrowseArticleRepeatTask.autoBrowse(), not open an article page, return and retry");
            }
        }

        if (openArticlePage) {
            // swipe up and down repeatedly for 40s
            long startSwipeTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startSwipeTime < 1000 * 40) {
                Thread.sleep(3000);
                UiDriver.swipeUp600pxSlowly();

                if (!haveClickedBonus) {
                    Logger.debug("QuKanBrowseArticleRepeatTask.autoBrowse(), click bonus button");
                    clickBonusIcon();
                    haveClickedBonus = true;
                }
            }
            // return to the headline list page
            UiDriver.pressBack();
            Logger.debug("QuKanBrowseArticleRepeatTask.autoBrowse(), browse article success");
        } else {
            Logger.debug("QuKanBrowseArticleRepeatTask.autoBrowse(), can't open a article page after trying 60s");
        }

        Logger.debug("QuKanBrowseArticleRepeatTask.autoBrowse(), exit");
        return true;
    }


}
