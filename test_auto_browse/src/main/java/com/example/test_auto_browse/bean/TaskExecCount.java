package com.example.test_auto_browse.bean;

import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.utils.DateUtil;

public class TaskExecCount {
    int nextTaskIndex;
    long saveDate;
    int touTiaoTreasureBoxExecCount;        //TOU_TIAO_TREASURE_BOX_MAX_EXEC_COUNT = 50
    int touTiaoBrowseVideoExecCount;        //TOU_TIAO_BROWSE_VIDEO_MAX_EXEC_COUNT = 2
    int touTiaoBrowseGoodsExecCount;            //TOU_TIAO_BROWSE_GOODS_MAX_EXEC_COUNT = 3;
    int touTiaoWatchAdsExecCount;           //TOU_TIAO_WATCH_ADS_MAX_EXEC_COUNT = 10;
    int kuaiShouTreasureBoxExecCount;           //KUAI_SHOU_TREASURE_BOX_MAX_EXEC_COUNT = 10;
    int kuaiShouWatchLiveExecCount;             //KUAI_SHOU_WATCH_LIVE_MAX_EXEC_COUNT = 10;
    int kuaiShouRewardExecCount;                    //KUAI_SHOU_REWARD_MAX_EXEC_COUNT = 10;
    int kuaiShouBrowseVideoExecCount;                    //KUAI_SHOU_BROWSE_VIDEO_MAX_EXEC_COUNT = 20;
    int dianTaoBrowseLiveExecCount;             //DIAN_TAO_BROWSE_LIVE_MAX_EXEC_COUNT = 1;
    int dianTaoBrowseVideoExecCount;             //DIAN_TAO_BROWSE_VIDEO_MAX_EXEC_COUNT = 1;
    int dianTaoWalkWatchVideo60SecExecCount;        //DIAN_TAO_WALK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 5;
    int dianTaoWalkWatchVideo30SecExecCount;         //DIAN_TAO_WALK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT = 3;
    int dianTaoWalkWatchLive30SecExecCount;          //DIAN_TAO_WALK_WATCH_LIVE_30SEC_MAX_EXEC_COUNT = 10;
    int dianTaoWalkWatchLive3MinExecCount;          //DIAN_TAO_WALK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 3;
    int dianTaoWalkWatchLive5MinExecCount;          //DIAN_TAO_WALK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT = 3;
    int dianTaoWalkWatchLive8MinExecCount;          //DIAN_TAO_WALK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT = 3;
    int dianTaoWalkWatchLive10MinExecCount;         //DIAN_TAO_WALK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT = 3;
    int dianTaoWorkWatchVideo60SecExecCount;         //DIAN_TAO_WORK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 5;
    int dianTaoWorkWatchVideo30SecExecCount;         //DIAN_TAO_WORK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT = 3;
    int dianTaoWorkWatchVideo5MinExecCount;         //DIAN_TAO_WORK_WATCH_VIDEO_5MIN_MAX_EXEC_COUNT = 3;
    int dianTaoWorkWatchLive60SecExecCount;         //DIAN_TAO_WORK_WATCH_LIVE_60SEC_MAX_EXEC_COUNT = 5;
    int dianTaoWorkWatchLive3MinExecCount;          //DIAN_TAO_WORK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 3;
    int dianTaoWorkWatchLive5MinExecCount;          //DIAN_TAO_WORK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT = 1;
    int dianTaoWorkWatchLive8MinExecCount;          //DIAN_TAO_WORK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT = 1;
    int dianTaoWorkWatchLive10MinExecCount;         //DIAN_TAO_WORK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT = 1;
    int jingDongBrowseActivityExecCount;         //JING_DONG_BROWSE_ACTIVITY_MAX_EXEC_COUNT = 5;
    int jingDongBrowseGoodsExecCount;         //JING_DONG_BROWSE_GOODS_MAX_EXEC_COUNT = 5;
    int jingDongBrowseVideoExecCount;         //JING_DONG_BROWSE_VIDEO_MAX_EXEC_COUNT = 10;
    int quKanBrowseShortVideoExecCount;         //QU_KAN_BROWSE_SHORT_VIDEO_MAX_EXEC_COUNT = 50;
    int quKanLuckyMoneyGoldExecCount;         //QU_KAN_LUCKY_MONEY_GOLD_MAX_EXEC_COUNT = 100;
    int quKanBrowseArticleExecCount;         //QU_KAN_BROWSE_ARTICLE_MAX_EXEC_COUNT = 50;
    int quKanBrowseVideoExecCount;         //QU_KAN_BROWSE_VIDEO_MAX_EXEC_COUNT = 50;
    int qiYiBrowseAdsExecCount;         //
    int qiYiTreasureBoxExecCount;         //
    int douYinBrowseAdsExecCount;         //

    public TaskExecCount() {}
    public TaskExecCount(TaskExecCount taskExecCount) {
        nextTaskIndex = taskExecCount.nextTaskIndex;
        saveDate = taskExecCount.saveDate;
        touTiaoTreasureBoxExecCount = taskExecCount.touTiaoTreasureBoxExecCount;
        touTiaoBrowseVideoExecCount = taskExecCount.touTiaoBrowseVideoExecCount;
        touTiaoBrowseGoodsExecCount = taskExecCount.touTiaoBrowseGoodsExecCount;
        touTiaoWatchAdsExecCount = taskExecCount.touTiaoWatchAdsExecCount;
        kuaiShouTreasureBoxExecCount = taskExecCount.kuaiShouTreasureBoxExecCount;
        kuaiShouWatchLiveExecCount = taskExecCount.kuaiShouWatchLiveExecCount;
        kuaiShouRewardExecCount = taskExecCount.kuaiShouRewardExecCount;
        kuaiShouBrowseVideoExecCount = taskExecCount.kuaiShouBrowseVideoExecCount;
        dianTaoBrowseLiveExecCount = taskExecCount.dianTaoBrowseLiveExecCount;
        dianTaoBrowseVideoExecCount = taskExecCount.dianTaoBrowseVideoExecCount;
        dianTaoWalkWatchVideo60SecExecCount = taskExecCount.dianTaoWalkWatchVideo60SecExecCount;
        dianTaoWalkWatchVideo30SecExecCount = taskExecCount.dianTaoWalkWatchVideo30SecExecCount;
        dianTaoWalkWatchLive30SecExecCount = taskExecCount.dianTaoWalkWatchLive30SecExecCount;
        dianTaoWalkWatchLive3MinExecCount = taskExecCount.dianTaoWalkWatchLive3MinExecCount;
        dianTaoWalkWatchLive5MinExecCount = taskExecCount.dianTaoWalkWatchLive5MinExecCount;
        dianTaoWalkWatchLive8MinExecCount = taskExecCount.dianTaoWalkWatchLive8MinExecCount;
        dianTaoWalkWatchLive10MinExecCount = taskExecCount.dianTaoWalkWatchLive10MinExecCount;
        dianTaoWorkWatchVideo60SecExecCount = taskExecCount.dianTaoWorkWatchVideo60SecExecCount;
        dianTaoWorkWatchVideo30SecExecCount = taskExecCount.dianTaoWorkWatchVideo30SecExecCount;
        dianTaoWorkWatchVideo5MinExecCount = taskExecCount.dianTaoWorkWatchVideo5MinExecCount;
        dianTaoWorkWatchLive60SecExecCount = taskExecCount.dianTaoWorkWatchLive60SecExecCount;
        dianTaoWorkWatchLive3MinExecCount = taskExecCount.dianTaoWorkWatchLive3MinExecCount;
        dianTaoWorkWatchLive5MinExecCount = taskExecCount.dianTaoWorkWatchLive5MinExecCount;
        dianTaoWorkWatchLive8MinExecCount = taskExecCount.dianTaoWorkWatchLive8MinExecCount;
        dianTaoWorkWatchLive10MinExecCount = taskExecCount.dianTaoWorkWatchLive10MinExecCount;
        jingDongBrowseActivityExecCount = taskExecCount.jingDongBrowseActivityExecCount;
        jingDongBrowseGoodsExecCount = taskExecCount.jingDongBrowseGoodsExecCount;
        jingDongBrowseVideoExecCount = taskExecCount.jingDongBrowseVideoExecCount;
        quKanBrowseShortVideoExecCount = taskExecCount.quKanBrowseShortVideoExecCount;
        quKanLuckyMoneyGoldExecCount = taskExecCount.quKanLuckyMoneyGoldExecCount;
        quKanBrowseArticleExecCount = taskExecCount.quKanBrowseArticleExecCount;
        quKanBrowseVideoExecCount = taskExecCount.quKanBrowseVideoExecCount;
        qiYiBrowseAdsExecCount = taskExecCount.qiYiBrowseAdsExecCount;
        qiYiTreasureBoxExecCount = taskExecCount.qiYiTreasureBoxExecCount;
        douYinBrowseAdsExecCount = taskExecCount.douYinBrowseAdsExecCount;
    }

    public void setAllTaskToMaxExecCount() {
        touTiaoTreasureBoxExecCount = Constant.TOU_TIAO_TREASURE_BOX_MAX_EXEC_COUNT;
        touTiaoBrowseVideoExecCount = Constant.TOU_TIAO_BROWSE_VIDEO_MAX_EXEC_COUNT;
        touTiaoBrowseGoodsExecCount = Constant.TOU_TIAO_BROWSE_GOODS_MAX_EXEC_COUNT;
        touTiaoWatchAdsExecCount = Constant.TOU_TIAO_WATCH_ADS_MAX_EXEC_COUNT;
        kuaiShouTreasureBoxExecCount = Constant.KUAI_SHOU_TREASURE_BOX_MAX_EXEC_COUNT;
        kuaiShouWatchLiveExecCount = Constant.KUAI_SHOU_WATCH_LIVE_MAX_EXEC_COUNT;
        kuaiShouRewardExecCount = Constant.KUAI_SHOU_REWARD_MAX_EXEC_COUNT;
        kuaiShouBrowseVideoExecCount = Constant.KUAI_SHOU_BROWSE_VIDEO_MAX_EXEC_COUNT;
        dianTaoBrowseLiveExecCount = Constant.DIAN_TAO_BROWSE_LIVE_MAX_EXEC_COUNT;
        dianTaoBrowseVideoExecCount = Constant.DIAN_TAO_BROWSE_VIDEO_MAX_EXEC_COUNT;
        dianTaoWalkWatchVideo60SecExecCount = Constant.DIAN_TAO_WALK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT;
        dianTaoWalkWatchVideo30SecExecCount = Constant.DIAN_TAO_WALK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT;
        dianTaoWalkWatchLive30SecExecCount = Constant.DIAN_TAO_WALK_WATCH_LIVE_30SEC_MAX_EXEC_COUNT;
        dianTaoWalkWatchLive3MinExecCount = Constant.DIAN_TAO_WALK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT;
        dianTaoWalkWatchLive5MinExecCount = Constant.DIAN_TAO_WALK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT;
        dianTaoWalkWatchLive8MinExecCount = Constant.DIAN_TAO_WALK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT;
        dianTaoWalkWatchLive10MinExecCount = Constant.DIAN_TAO_WALK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT;
        dianTaoWorkWatchVideo60SecExecCount = Constant.DIAN_TAO_WORK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT;
        dianTaoWorkWatchVideo30SecExecCount = Constant.DIAN_TAO_WORK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT;
        dianTaoWorkWatchVideo5MinExecCount = Constant.DIAN_TAO_WORK_WATCH_VIDEO_5MIN_MAX_EXEC_COUNT;
        dianTaoWorkWatchLive60SecExecCount = Constant.DIAN_TAO_WORK_WATCH_LIVE_60SEC_MAX_EXEC_COUNT;
        dianTaoWorkWatchLive3MinExecCount = Constant.DIAN_TAO_WORK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT;
        dianTaoWorkWatchLive5MinExecCount = Constant.DIAN_TAO_WORK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT;
        dianTaoWorkWatchLive8MinExecCount = Constant.DIAN_TAO_WORK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT;
        dianTaoWorkWatchLive10MinExecCount = Constant.DIAN_TAO_WORK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT;
        jingDongBrowseActivityExecCount = Constant.JING_DONG_BROWSE_ACTIVITY_MAX_EXEC_COUNT;
        jingDongBrowseGoodsExecCount = Constant.JING_DONG_BROWSE_GOODS_MAX_EXEC_COUNT;
        jingDongBrowseVideoExecCount = Constant.JING_DONG_BROWSE_VIDEO_MAX_EXEC_COUNT;
        quKanBrowseShortVideoExecCount = Constant.QU_KAN_BROWSE_SHORT_VIDEO_MAX_EXEC_COUNT;
        quKanLuckyMoneyGoldExecCount = Constant.QU_KAN_LUCKY_MONEY_GOLD_MAX_EXEC_COUNT;
        quKanBrowseArticleExecCount = Constant.QU_KAN_BROWSE_ARTICLE_MAX_EXEC_COUNT;
        quKanBrowseVideoExecCount = Constant.QU_KAN_BROWSE_VIDEO_MAX_EXEC_COUNT;
        qiYiBrowseAdsExecCount = Constant.QI_YI_BROWSE_ADS_MAX_EXEC_COUNT;
        qiYiTreasureBoxExecCount = Constant.QI_YI_TREASURE_BOX_MAX_EXEC_COUNT;
        douYinBrowseAdsExecCount = Constant.DOU_YIN_BROWSE_ADS_MAX_EXEC_COUNT;
    }


    @Override
    public String toString() {
        return "TaskExecCount{" +
                "nextTaskIndex=" + nextTaskIndex +
                ", saveDate=" + DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_HYPHEN, saveDate) +
                ", touTiaoTreasureBoxExecCount=" + touTiaoTreasureBoxExecCount +
                ", touTiaoBrowseVideoExecCount=" + touTiaoBrowseVideoExecCount +
                ", touTiaoBrowseGoodsExecCount=" + touTiaoBrowseGoodsExecCount +
                ", touTiaoWatchAdsExecCount=" + touTiaoWatchAdsExecCount +
                ", kuaiShouTreasureBoxExecCount=" + kuaiShouTreasureBoxExecCount +
                ", kuaiShouWatchLiveExecCount=" + kuaiShouWatchLiveExecCount +
                ", kuaiShouRewardExecCount=" + kuaiShouRewardExecCount +
                ", kuaiShouBrowseVideoExecCount=" + kuaiShouBrowseVideoExecCount +
                ", dianTaoBrowseLiveExecCount=" + dianTaoBrowseLiveExecCount +
                ", dianTaoBrowseVideoExecCount=" + dianTaoBrowseVideoExecCount +
                ", dianTaoWalkWatchVideo60SecExecCount=" + dianTaoWalkWatchVideo60SecExecCount +
                ", dianTaoWalkWatchVideo30SecExecCount=" + dianTaoWalkWatchVideo30SecExecCount +
                ", dianTaoWalkWatchLive30SecExecCount=" + dianTaoWalkWatchLive30SecExecCount +
                ", dianTaoWalkWatchLive3MinExecCount=" + dianTaoWalkWatchLive3MinExecCount +
                ", dianTaoWalkWatchLive5MinExecCount=" + dianTaoWalkWatchLive5MinExecCount +
                ", dianTaoWalkWatchLive8MinExecCount=" + dianTaoWalkWatchLive8MinExecCount +
                ", dianTaoWalkWatchLive10MinExecCount=" + dianTaoWalkWatchLive10MinExecCount +
                ", dianTaoWorkWatchVideo60SecExecCount=" + dianTaoWorkWatchVideo60SecExecCount +
                ", dianTaoWorkWatchVideo30SecExecCount=" + dianTaoWorkWatchVideo30SecExecCount +
                ", dianTaoWorkWatchVideo5MinExecCount=" + dianTaoWorkWatchVideo5MinExecCount +
                ", dianTaoWorkWatchLive60SecExecCount=" + dianTaoWorkWatchLive60SecExecCount +
                ", dianTaoWorkWatchLive3MinExecCount=" + dianTaoWorkWatchLive3MinExecCount +
                ", dianTaoWorkWatchLive5MinExecCount=" + dianTaoWorkWatchLive5MinExecCount +
                ", dianTaoWorkWatchLive8MinExecCount=" + dianTaoWorkWatchLive8MinExecCount +
                ", dianTaoWorkWatchLive10MinExecCount=" + dianTaoWorkWatchLive10MinExecCount +
                ", jingDongBrowseActivityExecCount=" + jingDongBrowseActivityExecCount +
                ", jingDongBrowseGoodsExecCount=" + jingDongBrowseGoodsExecCount +
                ", jingDongBrowseVideoExecCount=" + jingDongBrowseVideoExecCount +
                ", quKanBrowseShortVideoExecCount=" + quKanBrowseShortVideoExecCount +
                ", quKanLuckyMoneyGoldExecCount=" + quKanLuckyMoneyGoldExecCount +
                ", quKanBrowseArticleExecCount=" + quKanBrowseArticleExecCount +
                ", quKanBrowseVideoExecCount=" + quKanBrowseVideoExecCount +
                ", qiYiBrowseAdsExecCount=" + qiYiBrowseAdsExecCount +
                ", qiYiTreasureBoxExecCount=" + qiYiTreasureBoxExecCount +
                ", douYinBrowseAdsExecCount=" + douYinBrowseAdsExecCount +
                '}';
    }

    public int getNextTaskIndex() {
        return nextTaskIndex;
    }

    public TaskExecCount setNextTaskIndex(int nextTaskIndex) {
        this.nextTaskIndex = nextTaskIndex;
        return this;
    }

    public long getSaveDate() {
        return saveDate;
    }

    public TaskExecCount setSaveDate(long saveDate) {
        this.saveDate = saveDate;
        return this;
    }


    public int getTouTiaoTreasureBoxExecCount() {
        return touTiaoTreasureBoxExecCount;
    }

    public TaskExecCount increaseTouTiaoTreasureBoxExecCount() {
        this.touTiaoTreasureBoxExecCount++;
        return this;
    }

    public void setTouTiaoTreasureBoxExecCount(int touTiaoTreasureBoxExecCount) {
        this.touTiaoTreasureBoxExecCount = touTiaoTreasureBoxExecCount;
    }

    public int getTouTiaoBrowseVideoExecCount() {
        return touTiaoBrowseVideoExecCount;
    }

    public TaskExecCount increaseTouTiaoBrowseVideoExecCount() {
        this.touTiaoBrowseVideoExecCount++;
        return this;
    }

    public void setTouTiaoBrowseVideoExecCount(int touTiaoBrowseVideoExecCount) {
        this.touTiaoBrowseVideoExecCount = touTiaoBrowseVideoExecCount;
    }

    public int getTouTiaoBrowseGoodsExecCount() {
        return touTiaoBrowseGoodsExecCount;
    }

    public TaskExecCount increaseTouTiaoBrowseGoodsExecCount() {
        this.touTiaoBrowseGoodsExecCount++;
        return this;
    }

    public TaskExecCount setTouTiaoBrowseGoodsExecCount(int touTiaoBrowseGoodsExecCount) {
        this.touTiaoBrowseGoodsExecCount = touTiaoBrowseGoodsExecCount;
        return this;
    }

    public int getTouTiaoWatchAdsExecCount() {
        return touTiaoWatchAdsExecCount;
    }

    public TaskExecCount increaseTouTiaoWatchAdsExecCount() {
        this.touTiaoWatchAdsExecCount++;
        return this;
    }

    public TaskExecCount setTouTiaoWatchAdsExecCount(int touTiaoWatchAdsExecCount) {
        this.touTiaoWatchAdsExecCount = touTiaoWatchAdsExecCount;
        return this;
    }

    public int getKuaiShouTreasureBoxExecCount() {
        return kuaiShouTreasureBoxExecCount;
    }

    public TaskExecCount increaseKuaiShouTreasureBoxExecCount() {
        this.kuaiShouTreasureBoxExecCount++;
        return this;
    }

    public TaskExecCount setKuaiShouTreasureBoxExecCount(int kuaiShouTreasureBoxExecCount) {
        this.kuaiShouTreasureBoxExecCount = kuaiShouTreasureBoxExecCount;
        return this;
    }

    public int getKuaiShouWatchLiveExecCount() {
        return kuaiShouWatchLiveExecCount;
    }

    public TaskExecCount increaseKuaiShouWatchLiveExecCount()  {
        this.kuaiShouWatchLiveExecCount++;
        return this;
    }

    public TaskExecCount setKuaiShouWatchLiveExecCount(int kuaiShouWatchLiveExecCount) {
        this.kuaiShouWatchLiveExecCount = kuaiShouWatchLiveExecCount;
        return this;
    }

    public int getKuaiShouRewardExecCount() {
        return kuaiShouRewardExecCount;
    }

    public TaskExecCount increaseKuaiShouRewardExecCount() {
        this.kuaiShouRewardExecCount++;
        return this;
    }

    public TaskExecCount setKuaiShouRewardExecCount(int kuaiShouRewardExecCount) {
        this.kuaiShouRewardExecCount = kuaiShouRewardExecCount;
        return this;
    }

    public int getKuaiShouBrowseVideoExecCount() {
        return kuaiShouBrowseVideoExecCount;
    }

    public TaskExecCount increaseKuaiShouBrowseVideoExecCount() {
        this.kuaiShouBrowseVideoExecCount++;
        return this;
    }

    public void setKuaiShouBrowseVideoExecCount(int kuaiShouBrowseVideoExecCount) {
        this.kuaiShouBrowseVideoExecCount = kuaiShouBrowseVideoExecCount;
    }

    public int getDianTaoBrowseLiveExecCount() {
        return dianTaoBrowseLiveExecCount;
    }

    public TaskExecCount increaseDianTaoBrowseLiveExecCount() {
        this.dianTaoBrowseLiveExecCount++;
        return this;
    }

    public void setDianTaoBrowseLiveExecCount(int dianTaoBrowseLiveExecCount) {
        this.dianTaoBrowseLiveExecCount = dianTaoBrowseLiveExecCount;
    }

    public int getDianTaoBrowseVideoExecCount() {
        return dianTaoBrowseVideoExecCount;
    }

    public TaskExecCount increaseDianTaoBrowseVideoExecCount() {
        this.dianTaoBrowseVideoExecCount++;
        return this;
    }

    public void setDianTaoBrowseVideoExecCount(int dianTaoBrowseVideoExecCount) {
        this.dianTaoBrowseVideoExecCount = dianTaoBrowseVideoExecCount;
    }

    public int getDianTaoWalkWatchVideo60SecExecCount() {
        return dianTaoWalkWatchVideo60SecExecCount;
    }

    public TaskExecCount increaseDianTaoWalkWatchVideo60SecExecCount() {
        this.dianTaoWalkWatchVideo60SecExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWalkWatchVideo60SecExecCount(int dianTaoWalkWatchVideo60SecExecCount) {
        this.dianTaoWalkWatchVideo60SecExecCount = dianTaoWalkWatchVideo60SecExecCount;
        return this;
    }

    public int getDianTaoWalkWatchVideo30SecExecCount() {
        return dianTaoWalkWatchVideo30SecExecCount;
    }

    public TaskExecCount increaseDianTaoWalkWatchVideo30SecExecCount() {
        this.dianTaoWalkWatchVideo30SecExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWalkWatchVideo30SecExecCount(int dianTaoWalkWatchVideo30SecExecCount) {
        this.dianTaoWalkWatchVideo30SecExecCount = dianTaoWalkWatchVideo30SecExecCount;
        return this;
    }

    public int getDianTaoWalkWatchLive30SecExecCount() {
        return dianTaoWalkWatchLive30SecExecCount;
    }

    public TaskExecCount increaseDianTaoWalkWatchLive30SecExecCount() {
        this.dianTaoWalkWatchLive30SecExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWalkWatchLive30SecExecCount(int dianTaoWalkWatchLive30SecExecCount) {
        this.dianTaoWalkWatchLive30SecExecCount = dianTaoWalkWatchLive30SecExecCount;
        return this;
    }

    public int getDianTaoWalkWatchLive3MinExecCount() {
        return dianTaoWalkWatchLive3MinExecCount;
    }

    public TaskExecCount increaseDianTaoWalkWatchLive3MinExecCount() {
        this.dianTaoWalkWatchLive3MinExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWalkWatchLive3MinExecCount(int dianTaoWalkWatchLive3MinExecCount) {
        this.dianTaoWalkWatchLive3MinExecCount = dianTaoWalkWatchLive3MinExecCount;
        return this;
    }

    public int getDianTaoWalkWatchLive5MinExecCount() {
        return dianTaoWalkWatchLive5MinExecCount;
    }

    public TaskExecCount increaseDianTaoWalkWatchLive5MinExecCount() {
        this.dianTaoWalkWatchLive5MinExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWalkWatchLive5MinExecCount(int dianTaoWalkWatchLive5MinExecCount) {
        this.dianTaoWalkWatchLive5MinExecCount = dianTaoWalkWatchLive5MinExecCount;
        return this;
    }

    public int getDianTaoWalkWatchLive8MinExecCount() {
        return dianTaoWalkWatchLive8MinExecCount;
    }

    public TaskExecCount increaseDianTaoWalkWatchLive8MinExecCount() {
        this.dianTaoWalkWatchLive8MinExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWalkWatchLive8MinExecCount(int dianTaoWalkWatchLive8MinExecCount) {
        this.dianTaoWalkWatchLive8MinExecCount = dianTaoWalkWatchLive8MinExecCount;
        return this;
    }

    public int getDianTaoWalkWatchLive10MinExecCount() {
        return dianTaoWalkWatchLive10MinExecCount;
    }

    public TaskExecCount increaseDianTaoWalkWatchLive10MinExecCount() {
        this.dianTaoWalkWatchLive10MinExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWalkWatchLive10MinExecCount(int dianTaoWalkWatchLive10MinExecCount) {
        this.dianTaoWalkWatchLive10MinExecCount = dianTaoWalkWatchLive10MinExecCount;
        return this;
    }

    public int getDianTaoWorkWatchVideo60SecExecCount() {
        return dianTaoWorkWatchVideo60SecExecCount;
    }

    public TaskExecCount increaseDianTaoWorkWatchVideo60SecExecCount() {
        this.dianTaoWorkWatchVideo60SecExecCount++;
        return this;
    }

    public void setDianTaoWorkWatchVideo60SecExecCount(int dianTaoWorkWatchVideo60SecExecCount) {
        this.dianTaoWorkWatchVideo60SecExecCount = dianTaoWorkWatchVideo60SecExecCount;
    }

    public int getDianTaoWorkWatchVideo30SecExecCount() {
        return dianTaoWorkWatchVideo30SecExecCount;
    }

    public TaskExecCount increaseDianTaoWorkWatchVideo30SecExecCount() {
        this.dianTaoWorkWatchVideo30SecExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWorkWatchVideo30SecExecCount(int dianTaoWorkWatchVideo30SecExecCount) {
        this.dianTaoWorkWatchVideo30SecExecCount = dianTaoWorkWatchVideo30SecExecCount;
        return this;
    }

    public int getDianTaoWorkWatchVideo5MinExecCount() {
        return dianTaoWorkWatchVideo5MinExecCount;
    }

    public TaskExecCount increaseDianTaoWorkWatchVideo5MinExecCount() {
        this.dianTaoWorkWatchVideo5MinExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWorkWatchVideo5MinExecCount(int dianTaoWorkWatchVideo5MinExecCount) {
        this.dianTaoWorkWatchVideo5MinExecCount = dianTaoWorkWatchVideo5MinExecCount;
        return this;
    }

    public int getDianTaoWorkWatchLive60SecExecCount() {
        return dianTaoWorkWatchLive60SecExecCount;
    }

    public TaskExecCount increaseDianTaoWorkWatchLive60SecExecCount() {
        this.dianTaoWorkWatchLive60SecExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWorkWatchLive60SecExecCount(int dianTaoWorkWatchLive60SecExecCount) {
        this.dianTaoWorkWatchLive60SecExecCount = dianTaoWorkWatchLive60SecExecCount;
        return this;
    }

    public int getDianTaoWorkWatchLive3MinExecCount() {
        return dianTaoWorkWatchLive3MinExecCount;
    }

    public TaskExecCount increaseDianTaoWorkWatchLive3MinExecCount() {
        this.dianTaoWorkWatchLive3MinExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWorkWatchLive3MinExecCount(int dianTaoWorkWatchLive3MinExecCount) {
        this.dianTaoWorkWatchLive3MinExecCount = dianTaoWorkWatchLive3MinExecCount;
        return this;
    }

    public int getDianTaoWorkWatchLive5MinExecCount() {
        return dianTaoWorkWatchLive5MinExecCount;
    }

    public TaskExecCount increaseDianTaoWorkWatchLive5MinExecCount() {
        this.dianTaoWorkWatchLive5MinExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWorkWatchLive5MinExecCount(int dianTaoWorkWatchLive5MinExecCount) {
        this.dianTaoWorkWatchLive5MinExecCount = dianTaoWorkWatchLive5MinExecCount;
        return this;
    }

    public int getDianTaoWorkWatchLive8MinExecCount() {
        return dianTaoWorkWatchLive8MinExecCount;
    }

    public TaskExecCount increaseDianTaoWorkWatchLive8MinExecCount() {
        this.dianTaoWorkWatchLive8MinExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWorkWatchLive8MinExecCount(int dianTaoWorkWatchLive8MinExecCount) {
        this.dianTaoWorkWatchLive8MinExecCount = dianTaoWorkWatchLive8MinExecCount;
        return this;
    }

    public int getDianTaoWorkWatchLive10MinExecCount() {
        return dianTaoWorkWatchLive10MinExecCount;
    }

    public TaskExecCount increaseDianTaoWorkWatchLive10MinExecCount() {
        this.dianTaoWorkWatchLive10MinExecCount++;
        return this;
    }

    public TaskExecCount setDianTaoWorkWatchLive10MinExecCount(int dianTaoWorkWatchLive10MinExecCount) {
        this.dianTaoWorkWatchLive10MinExecCount = dianTaoWorkWatchLive10MinExecCount;
        return this;
    }

    public int getJingDongBrowseActivityExecCount() {
        return jingDongBrowseActivityExecCount;
    }

    public TaskExecCount increaseJingDongBrowseActivityExecCount() {
        this.jingDongBrowseActivityExecCount++;
        return this;
    }

    public void setJingDongBrowseActivityExecCount(int jingDongBrowseActivityExecCount) {
        this.jingDongBrowseActivityExecCount = jingDongBrowseActivityExecCount;
    }

    public int getJingDongBrowseGoodsExecCount() {
        return jingDongBrowseGoodsExecCount;
    }

    public TaskExecCount increaseJingDongBrowseGoodsExecCount() {
        this.jingDongBrowseGoodsExecCount++;
        return this;
    }

    public void setJingDongBrowseGoodsExecCount(int jingDongBrowseGoodsExecCount) {
        this.jingDongBrowseGoodsExecCount = jingDongBrowseGoodsExecCount;
    }

    public int getJingDongBrowseVideoExecCount() {
        return jingDongBrowseVideoExecCount;
    }

    public TaskExecCount increaseJingDongBrowseVideoExecCount() {
        this.jingDongBrowseVideoExecCount++;
        return this;
    }

    public void setJingDongBrowseVideoExecCount(int jingDongBrowseVideoExecCount) {
        this.jingDongBrowseVideoExecCount = jingDongBrowseVideoExecCount;
    }

    public int getQuKanBrowseShortVideoExecCount() {
        return quKanBrowseShortVideoExecCount;
    }

    public TaskExecCount increaseQuKanBrowseShortVideoExecCount() {
        this.quKanBrowseShortVideoExecCount++;
        return this;
    }

    public void setQuKanBrowseShortVideoExecCount(int quKanBrowseShortVideoExecCount) {
        this.quKanBrowseShortVideoExecCount = quKanBrowseShortVideoExecCount;
    }

    public int getQuKanLuckyMoneyGoldExecCount() {
        return quKanLuckyMoneyGoldExecCount;
    }

    public TaskExecCount increaseQuKanLuckyMoneyGoldExecCount() {
        this.quKanLuckyMoneyGoldExecCount++;
        return this;
    }

    public void setQuKanLuckyMoneyGoldExecCount(int quKanLuckyMoneyGoldExecCount) {
        this.quKanLuckyMoneyGoldExecCount = quKanLuckyMoneyGoldExecCount;
    }

    public int getQuKanBrowseArticleExecCount() {
        return quKanBrowseArticleExecCount;
    }

    public TaskExecCount increaseQuKanBrowseArticleExecCount() {
        this.quKanBrowseArticleExecCount++;
        return this;
    }

    public void setQuKanBrowseArticleExecCount(int quKanBrowseArticleExecCount) {
        this.quKanBrowseArticleExecCount = quKanBrowseArticleExecCount;
    }

    public int getQuKanBrowseVideoExecCount() {
        return quKanBrowseVideoExecCount;
    }

    public TaskExecCount increaseQuKanBrowseVideoExecCount() {
        this.quKanBrowseVideoExecCount++;
        return this;
    }

    public void setQuKanBrowseVideoExecCount(int quKanBrowseVideoExecCount) {
        this.quKanBrowseVideoExecCount = quKanBrowseVideoExecCount;
    }

    public int getQiYiBrowseAdsExecCount() {
        return qiYiBrowseAdsExecCount;
    }

    public TaskExecCount increaseQiYiBrowseAdsExecCount() {
        this.qiYiBrowseAdsExecCount++;
        return this;
    }

    public void setQiYiBrowseAdsExecCount(int qiYiBrowseAdsExecCount) {
        this.qiYiBrowseAdsExecCount = qiYiBrowseAdsExecCount;
    }

    public int getQiYiTreasureBoxExecCount() {
        return qiYiTreasureBoxExecCount;
    }

    public TaskExecCount increaseQiYiTreasureBoxExecCount() {
        this.qiYiTreasureBoxExecCount++;
        return this;
    }

    public void setQiYiTreasureBoxExecCount(int qiYiTreasureBoxExecCount) {
        this.qiYiTreasureBoxExecCount = qiYiTreasureBoxExecCount;
    }

    public int getDouYinBrowseAdsExecCount() {
        return douYinBrowseAdsExecCount;
    }

    public TaskExecCount increaseDouYinBrowseAdsExecCount() {
        this.douYinBrowseAdsExecCount++;
        return this;
    }

    public void setDouYinBrowseAdsExecCount(int douYinBrowseAdsExecCount) {
        this.douYinBrowseAdsExecCount = douYinBrowseAdsExecCount;
    }
}
