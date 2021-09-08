package com.example.test_auto_browse.task;

import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.task.diantao.DianTaoWorkTimedTaskCheckWork;
import com.example.test_auto_browse.task.douyin.DouYinTreasureBoxTimedTask;
import com.example.test_auto_browse.task.douyin.DouYinWatchAdsTimedTask;
import com.example.test_auto_browse.task.kuaishou.KuaiShouTreasureBoxTimedTask;
import com.example.test_auto_browse.task.qiyi.QiYiTreasureBoxTimedTask;
import com.example.test_auto_browse.task.qukan.QuKanLuckyMoneyGoldTimedTask;
import com.example.test_auto_browse.task.toutiao.TouTiaoBrowseGoodsTimedTask;
import com.example.test_auto_browse.task.toutiao.TouTiaoTreasureBoxTimedTask;
import com.example.test_auto_browse.task.toutiao.TouTiaoWatchAdsTimedTask;
import com.example.test_auto_browse.utils.Logger;

public class TimedTaskForAllApps extends BrowseBaseTask {
    private TimedTaskForAllApps() {}
    private static TimedTaskForAllApps instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new TimedTaskForAllApps();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("TimedTaskForAllApps.autoBrowse(), entry");
        long startTime = System.currentTimeMillis();

        boolean taskResult;
        IBrowseTask task;

        if (Constant.BUILD_CONFIG.equals("release")) {
            // some tasks can only run several times per day, need check it
            // tou tiao treasure box task
            task = TouTiaoTreasureBoxTimedTask.getInstance();
            if (task.initTask()) {
                taskResult = task.runTask();
                task.endTask();
                Logger.debug("TimedTaskForAllApps.autoBrowse(), TouTiaoTreasureBoxTimedTask end, taskResult=" + taskResult);
            }

            // tou tiao watch ads task
            task = TouTiaoWatchAdsTimedTask.getInstance();
            if (task.initTask()) {
                taskResult = task.runTask();
                task.endTask();
                Logger.debug("TimedTaskForAllApps.autoBrowse(), TouTiaoWatchAdsTimedTask end, taskResult=" + taskResult);
            }

            // tou tiao browse goods task
            task = TouTiaoBrowseGoodsTimedTask.getInstance();
            if (task.initTask()) {
                taskResult = task.runTask();
                task.endTask();
                Logger.debug("TimedTaskForAllApps.autoBrowse(), TouTiaoBrowseGoodsTimedTask end, taskResult=" + taskResult);
            }

            // dian tao work to get gold task
            task = DianTaoWorkTimedTaskCheckWork.getInstance();
            if (task.initTask()) {
                taskResult = task.runTask();
                task.endTask();
                Logger.debug("TimedTaskForAllApps.autoBrowse(), DianTaoWorkTimedTaskCheckWork end, taskResult=" + taskResult);
            }

            // kuai shou treasure box task
            task = KuaiShouTreasureBoxTimedTask.getInstance();
            if (task.initTask()) {
                taskResult = task.runTask();
                task.endTask();
                Logger.debug("TimedTaskForAllApps.autoBrowse(), KuaiShouTreasureBoxTimedTask end, taskResult=" + taskResult);
            }

            // qu kan lucky money gold task
            task = QuKanLuckyMoneyGoldTimedTask.getInstance();
            if (task.initTask()) {
                taskResult = task.runTask();
                task.endTask();
                Logger.debug("TimedTaskForAllApps.autoBrowse(), QuKanLuckyMoneyGoldTimedTask end, taskResult=" + taskResult);
            }

            // qi yi treasure box gold
            task = QiYiTreasureBoxTimedTask.getInstance();
            if (task.initTask()) {
                taskResult = task.runTask();
                task.endTask();
                Logger.debug("TimedTaskForAllApps.autoBrowse(), QiYiTreasureBoxTimedTask end, taskResult=" + taskResult);
            }



//            // dou yin treasure box task
//            task = DouYinTreasureBoxTimedTask.getInstance();
//            if (task.initTask()) {
//                taskResult = task.runTask();
//                task.endTask();
//                Logger.debug("TimedTaskForAllApps.autoBrowse(), DouYinTreasureBoxTimedTask end, taskResult=" + taskResult);
//            }

//            // dou yin watch ads task
//            task = DouYinWatchAdsTimedTask.getInstance();
//            if (task.initTask()) {
//                taskResult = task.runTask();
//                task.endTask();
//                Logger.debug("TimedTaskForAllApps.autoBrowse(), DouYinWatchAdsTimedTask end, taskResult=" + taskResult);
//            }
        }

        long runningTime = (System.currentTimeMillis() - startTime) / 1000;
        Logger.debug("TimedTaskForAllApps.autoBrowse(), exit, running time = " + runningTime / 60 + "min" + runningTime % 60 + "sec");
        return true;
    }
}
