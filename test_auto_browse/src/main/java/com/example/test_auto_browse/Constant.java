package com.example.test_auto_browse;

import android.graphics.Color;
import android.os.Build;

import com.sun.org.apache.bcel.internal.generic.FADD;

import java.nio.charset.Charset;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2017/6/20
 * description :
 */

public class Constant {

    public static final String LD_LIBRARY_PATH =
            (Build.VERSION.SDK_INT < 20) ? "export LD_LIBRARY_PATH=/vendor/lib:/system/lib \n" :
                    (Build.BRAND.equals("samsung") && Build.VERSION.SDK_INT == 21) ? "sh \n" : "\n";

    // ------------------------------- Path -------------------------------
    public static final String AUTO_AGENT_LOG_FILE_SUFFIX = "_auto_browse_log.txt";
    public static final String RES_SUCCESS = "sucess";
    public static final String RES_FAILED = "failed";

    // ------------------------------- Other -------------------------------
    public static final String SHELL_PS_INFO                    = "/mnt/sdcard/mqms/psInfo";

    // ------------------------------- System -------------------------------
    public static final String SEPARATOR = "/";     //instead of the File.separator

    ///////////////////////////////////////////////////////////////////////
    public static final String SD_PATH_APP_ROOT = "/sdcard/testAutoBrowse/";
    public static final String SD_PATH_LOG_DIR = SD_PATH_APP_ROOT + "log/";
    public static final String SD_PATH_FAILED_SCREENSHOTS = SD_PATH_APP_ROOT + "/screenshots/failedScreenshots/";
    public static final String SD_PATH_DEBUG_SCREENSHOTS = SD_PATH_APP_ROOT + "/screenshots/debugScreenshots/";
    public static final String SD_PATH_TASK_EXEC_COUNT_FILE = SD_PATH_APP_ROOT + "taskExecCount.json";

    public static final String PKG_NAME_TOU_TIAO_LITE = "com.ss.android.article.lite";
    public static final String PKG_NAME_DIAN_TAO = "com.taobao.live";
    public static final String PKG_NAME_WEI_SHI = "com.tencent.weishi";
    public static final String PKG_NAME_KUAI_SHOU = "com.kuaishou.nebula";
    public static final String PKG_NAME_JING_DONG = "com.jd.jdlite";
    public static final String PKG_NAME_QU_KAN = "com.jifen.qukan";
    public static final String PKG_NAME_QI_YI = "com.qiyi.video.lite";
    public static final String PKG_NAME_DOU_YIN = "com.ss.android.ugc.aweme.lite";

    public static final String PKG_NAME_ALI_PAY = "com.eg.android.AlipayGphone";
//    public static final String PKG_NAME_K_GE = "com.tencent.kg.android.lite";
    public static final String PKG_NAME_WAN_DOU_JIA = "com.wandoujia.phoenix2";
//    public static final String PKG_NAME_XIMALAYA = "com.ximalaya.ting.lite";
    public static final String PKG_NAME_BAIDU = "com.baidu.searchbox.lite";
    public static final String PKG_NAME_WEI_XIN = "com.tencent.mm";

    public static final String PKG_LAUNCHER_TOU_TIAO_LITE = "com.ss.android.article.lite/.activity.SplashActivity";
    public static final String PKG_LAUNCHER_DIAN_TAO = "com.taobao.live/.SplashActivity";
    public static final String PKG_LAUNCHER_WEI_SHI = "com.tencent.weishi/com.tencent.oscar.module.splash.SplashActivity";
    public static final String PKG_LAUNCHER_KUAI_SHOU = "com.kuaishou.nebula/com.yxcorp.gifshow.HomeActivity";
    public static final String PKG_LAUNCHER_JING_DONG = "com.jd.jdlite/.MainActivity";
    public static final String PKG_LAUNCHER_QU_KAN = "com.jifen.qukan/com.jifen.qkbase.main.MainActivity";
    public static final String PKG_LAUNCHER_QI_YI = "com.qiyi.video.lite/.homepage.HomeActivity";
    public static final String PKG_LAUNCHER_DOU_YIN = "com.ss.android.ugc.aweme.lite/com.ss.android.ugc.aweme.splash.SplashActivity";

    public static final String STR_TOU_TIAO_JUMP_ADS = "跳过";
    public static final String STR_TOU_TIAO_TASK = "任务";
    public static final String STR_TOU_TIAO_COMMON_TASK = "日常任务";
    public static final String STR_TOU_TIAO_NO_NETWORK = "网络不给力";
    public static final String STR_TOU_TIAO_OPEN_TREASURE_BOX = "开宝箱得金币";
    public static final String STR_TOU_TIAO_WATCHED_ADS_VIDEO_TO_GET_MORE = "看完视频再领";
    public static final String STR_TOU_TIAO_CLOSE_ADS = "关闭";
    public static final String STR_TOU_TIAO_CLOSE_ADS_CONFIRM = "坚持退出";
    public static final String STR_TOU_TIAO_WAIT_ADS_END_1 = "领金币";
    public static final String STR_TOU_TIAO_WAIT_ADS_END_2 = "后可领取奖励";
    public static final String STR_TOU_TIAO_WATCH_MORE = "再看一个";
    public static final String STR_TOU_TIAO_NO_MORE_ADS = "当前无新视频";
    public static final String STR_TOU_TIAO_COLON = ":";
    public static final String STR_TOU_TIAO_HAVE_A_LOOK= "去看看";
    public static final String STR_TOU_TIAO_WATCH_VIDEO_TO_GET_GOLD = "看视频赚钱";
    public static final String STR_TOU_TIAO_CLOSE_POPUP = "图片";
    public static final String STR_TOU_TIAO_DAILY_TASK = "每日任务";
    public static final String STR_TOU_TIAO_GET_GOLD = "领取";
    public static final String STR_TOU_TIAO_WATCH_ADS_VIDEO_TO_GET_MORE = "看视频再领";
    public static final String STR_TOU_TIAO_GET_MEAL_GOLD = "吃饭补贴";
    public static final String STR_TOU_TIAO_GET_SLEEP_GOLD = "睡觉赚钱";
    public static final String STR_TOU_TIAO_GET_WALKING_GOLD = "走路赚钱";
    public static final String STR_TOU_TIAO_MEAL_GOLD_HAS_GOT = "再过";
    public static final String STR_TOU_TIAO_GO_TO_SLEEP = "我要睡了";
    public static final String STR_TOU_TIAO_WAKE_UP = "我睡醒了";
    public static final String STR_TOU_TIAO_WATCH_ADS_TO_GET_GOLD = "看广告赚金币";
    public static final String STR_TOU_TIAO_BROWSE_GOODS_TO_GET_GOLD = "逛商品赚金币";
    public static final String STR_TOU_TIAO_MORE_TASK = "更多任务";
    public static final String STR_TOU_TIAO_BROWSE_GOODS_30S = "浏览以下商品30秒, 可领取200金币";
    public static final String STR_TOU_TIAO_DOUBLE_GOLD = "点击翻倍";
    public static final String STR_TOU_TIAO_I_KNOW = "我知道了";

    public static final String ID_DIAN_TAO_HOME_TAB_ITEM = "com.taobao.live:id/tab_container";
    public static final String STR_DIAN_TAO_GOLD_CENTER = "元宝中心";
    public static final String STR_DIAN_TAO_GO_TO_SIGN = "去签到";
    public static final String STR_DIAN_TAO_WATCH_LIVE_GET_MORE = "去看直播赚";
    public static final String STR_DIAN_TAO_CLICK_TO_SIGN = "点击签到";
    public static final String STR_DIAN_TAO_WATCH_VIDEO_GET_GOLD = "看视频，赚元宝";
    public static final String STR_DIAN_TAO_WATCH_LIVE_GET_GOLD = "看直播，赚元宝";
    public static final String STR_DIAN_TAO_GET_GOLD = "领取奖励";
    public static final String STR_DIAN_TAO_WALK_GET_GOLD = "走路赚元宝";
    public static final String STR_DIAN_TAO_WALK_GET_ENERGY_DRINK_STEPS = "领取";
    public static final String STR_DIAN_TAO_GET_WALK_STEPS = "赚步数";
    public static final String STR_DIAN_TAO_AFTER_S_COMPLETE = "后完成";
    public static final String STR_DIAN_TAO_WATCH_VIDEO_60Sec = "看视频60秒";
    public static final String STR_DIAN_TAO_WATCH_VIDEO_30Sec = "看视频30秒";
    public static final String STR_DIAN_TAO_WATCH_VIDEO_3Min = "看视频3分钟";
    public static final String STR_DIAN_TAO_WATCH_VIDEO_5Min = "看视频5分钟";
    public static final String STR_DIAN_TAO_WATCH_GOLDEN_LIVE_30Sec = "看黄金8点档直播30秒";
    public static final String STR_DIAN_TAO_WATCH_GOLDEN_LIVE_60Sec = "看黄金8点档直播60秒";
    public static final String STR_DIAN_TAO_WATCH_GOLDEN_LIVE_3Min = "看黄金8点档直播3分钟";
    public static final String STR_DIAN_TAO_WATCH_LIVE_30Sec = "看直播30秒";
    public static final String STR_DIAN_TAO_WATCH_LIVE_60Sec = "看直播60秒";
    public static final String STR_DIAN_TAO_WATCH_LIVE_3Min = "看直播3分钟";
    public static final String STR_DIAN_TAO_WATCH_LIVE_5Min = "看直播5分钟";
    public static final String STR_DIAN_TAO_WATCH_LIVE_8Min = "看直播8分钟";
    public static final String STR_DIAN_TAO_WATCH_LIVE_10Min = "看直播10分钟";
    public static final String STR_DIAN_TAO_GET_GOLD_ON_RIGHT_TOP = "领取";
    public static final String STR_DIAN_TAO_SLEEP_TO_GET_GOLD = "睡觉赚元宝";
    public static final String STR_DIAN_TAO_START_SLEEP = "开始睡觉";
    public static final String STR_DIAN_TAO_WAKE_UP = "我醒来啦";
    public static final String STR_DIAN_TAO_DEPART = "出发";
    public static final String STR_DIAN_TAO_FINISH_TASK_TO_GET_STEPS = "做任务赚步数";
    public static final String STR_DIAN_TAO_CHECK_MORE_TASKS = "查看更多任务";
    public static final String STR_DIAN_TAO_GET_STEPS_GOLD = "领取";
    public static final String STR_DIAN_TAO_GET_ENERGY_DRINK_FAILED = "哇哦, 被您的激情打败啦, 稍后再试哦";
    public static final String STR_DIAN_TAO_WATCH_LIVE_TO_GET_MORE = "再得";
//    public static final String STR_DIAN_TAO_I_KNOW = "我知道了";
    public static final String STR_DIAN_TAO_REGULATION = "规则";
    public static final String STR_DIAN_TAO_NO_AVAILABLE_STEPS = "可用步数不足";
    public static final String STR_DIAN_TAO_GO_ON_SCRATCH = "继续刮奖";
    public static final String STR_DIAN_TAO_BROWSE_SPECIAL_PRICE_MARKET_20SEC = "浏览特价卖场20秒";
    public static final String STR_DIAN_TAO_BROWSE_SPECIAL_PRICE_MARKET_30SEC = "浏览特价卖场30秒";
    public static final String STR_DIAN_TAO_PLAY_GAME = "去玩消消乐";
    public static final String STR_DIAN_TAO_GO_TO_FERTILIZE = "去芭芭农场施肥";
    public static final String STR_DIAN_TAO_SPECIAL_PRICE_MARKET_SWIPE = "滑动浏览";
    public static final String STR_DIAN_TAO_THUMP_UP_20_TIMES = "去直播间残暴点赞20次";
    public static final String STR_DIAN_TAO_TASK_TASK_COMPLETED = "任务完成";
    public static final String STR_DIAN_TAO_TASK_CONGRATULATE_TASK_COMPLETED = "恭喜完成任务";
    public static final String STR_DIAN_TAO_TASK_FOLLOW = "关注";
    public static final String STR_DIAN_TAO_WORK_TO_GET_GOLD = "打工赚元宝";
    public static final String STR_DIAN_TAO_WORK_SIGN = "连续签到";
    public static final String STR_DIAN_TAO_SIGN_TODAY = "今日签到";
    public static final String STR_DIAN_TAO_LUCK_DRAW_HIS = "开奖记录";
    public static final String STR_DIAN_TAO_GET_WORK_GOLD = "领取";
    public static final String STR_DIAN_TAO_WATCH_LIVE = "看直播";
    public static final String STR_DIAN_TAO_I_KNOW = "我知道了";
    public static final String STR_DIAN_TAO_I_AM_WORKING = "我正在拼命打工中";
    public static final String STR_DIAN_TAO_COOK_WORK = "厨师";
    public static final String STR_DIAN_TAO_START_WORK = "开始打工";
    public static final String STR_DIAN_TAO_START_NO_AVAILABLE_ENERGY = "体力不足";
    public static final String STR_DIAN_TAO_START_GET_ENERGY = "体力+";
    public static final String STR_DIAN_TAO_SWIPE_RIGHT_TO_VERIFY = "向右滑动验证";
    public static final String STR_DIAN_TAO_LOTTERY = "幸运大抽奖";

    public static final String STR_WEI_SHI_JUMP_ADS = "跳过";
    public static final String STR_WEI_SHI_TEEN_PROTECTION = "青少年保护功能提示";
    public static final String STR_WEI_SHI_I_KNOW = "知道了";
    public static final String STR_WEI_SHI_HOME_TAB_MINE = "我";
    public static final String STR_WEI_SHI_WELFARE_CENTER = "福利中心";
    public static final String STR_WEI_SHI_SIGN_TO_GET_GOLD = "签到领现金";
    public static final String STR_WEI_SHI_ALL_KARAOKE = "全民K歌";

    public static final String ID_KUAI_SHOU_LEFT_SETTING_BTN = "com.kuaishou.nebula:id/left_btn";
    public static final String ID_KUAI_SHOU_POPUP_DIALOG = "com.kuaishou.nebula:id/img_nebula_pull_new_dialog";
    public static final String ID_KUAI_SHOU_COSE_POPUP_DIALOG = "com.kuaishou.nebula:id/close";
    public static final String ID_KUAI_SHOU_AWARD_COUNT_DOWN = "com.kuaishou.nebula:id/award_count_down_text";
    public static final String ID_KUAI_SHOU_AWARD_COUNT_DOWN_NEW_VERSION = "com.kuaishou.nebula:id/award_count_down_group";
    public static final String STR_KUAI_SHOU_GO_TO_GET_GOLD = "去赚钱";
    public static final String STR_KUAI_SHOU_TASK_CENTER = "任务中心";
    public static final String STR_KUAI_SHOU_SIGN_TO_GET_GOLD = "签到领金币";
    public static final String STR_KUAI_SHOU_SIGN_IMMEDIATELY = "立即签到";
    public static final String STR_KUAI_SHOU_WATCH_VIDEO_TO_GET_GOLD = "看视频赚金币";
    public static final String STR_KUAI_SHOU_CLICK_TO_OPEN = "点击打开";
    public static final String STR_KUAI_SHOU_OPEN_TREASURE_BOX = "开宝箱得金币";
    public static final String STR_KUAI_SHOU_WATCH_VIDEO_TO_GET_MORE = "看精彩视频赚更多";
    public static final String STR_KUAI_SHOU_WAIT_ADS_END = "s后可领取奖励";
    public static final String STR_KUAI_SHOU_1100_GOLD_WATCH_LIVE_TASK = "1100金币";
    public static final String STR_KUAI_SHOU_1000_REWARD_TASK = "金币悬赏";
    public static final String STR_KUAI_SHOU_1000_REWARD_TASK_END = "s后可领取奖励";
    public static final String STR_KUAI_SHOU_GIVE_UP_REWARD = "放弃奖励";
    public static final String STR_KUAI_SHOU_I_KNOW = "我知道了";
    public static final String STR_KUAI_SHOU_FOLLOW_AND_EXIT = "关注并退出";
    public static final String STR_KUAI_SHOU_EXIT = "退出";
    public static final String STR_KUAI_SHOU_LIVE_LIST_TITLE = "com.kuaishou.nebula:id/award_progress_num";
    public static final String STR_KUAI_SHOU_LIVE_LIST_ITEM = "com.kuaishou.nebula:id/constraint_layout_live_item_container";


    public static final String ID_JING_DONG_BROWSE_NEXT = "com.jd.jdlite:id/ll_task_bottom_next";
    public static final String ID_JING_DONG_GOLD_PROGRESS = "com.jd.jdlite:id/progressbar2";
    public static final String STR_JING_DONG_JUMP_ADS = "跳过";
    public static final String STR_JING_DONG_CLOSE_POPUP = "关闭页面";
    public static final String STR_JING_DONG_GET_GOLD = "赚钱";
    public static final String STR_JING_DONG_EXCHANGE_LUCKY_MONEY = "换红包";
    public static final String STR_JING_DONG_BROWSE_GOODS_GET_GOLD = "逛商品赚金币";
    public static final String STR_JING_DONG_BROWSE_ACTIVITY_GET_GOLD = "逛活动赚金币";
    public static final String STR_JING_DONG_BROWSE_NEXT = "点击逛下一个";
    public static final String STR_JING_DONG_TASK_COMPLETED_TODAY = "今日已完成";
    public static final String STR_JING_DONG_BROWSE_VIDEO_GET_GOLD = "看视频赚金币";

    public static final String ID_QU_KAN_ADS_VIDEO_PROGRESS = "com.jifen.qukan:id/tt_video_progress";
    public static final String ID_QU_KAN_ADS_VIDEO_CLOSE = "com.jifen.qukan:id/tt_video_ad_close";
    public static final String STR_QU_KAN_SIGN_SUCESS = "签到成功";
    public static final String STR_QU_KAN_WATCH_VIDEO_TO_GET_MORE = "看视频再领";
    public static final String STR_QU_KAN_WATCH_VIDEO_COUNT_DOWN = "后可领金币";
    public static final String STR_QU_KAN_GET_GOLD_SUCCESS = "金币领取成功";
    public static final String STR_QU_KAN_TASK_PAGE = "任务";
    public static final String STR_QU_KAN_SHORT_VIDEO_PAGE = "小视频";
    public static final String STR_QU_KAN_VIDEO_PAGE = "视频";
    public static final String STR_QU_KAN_SHORT_VIDEO_SHARE = "分享";
    public static final String STR_QU_KAN_MY_GOLD = "我的金币";
    public static final String STR_QU_KAN_GET_GOLD_IMMEDIATELY = "立即领取";
    public static final String STR_QU_KAN_LUCKY_MONEY_TASK_NOT_AVAILABLE = "不要急";
    public static final String STR_QU_KAN_WATCH_VIDEO_TO_GET_GOLD = "看视频领金币";
    public static final String STR_QU_KAN_ADS_END = "后可领金币";
    public static final String STR_QU_KAN_CANCEL_OPEN_APP = "点击取消";
    public static final String STR_QU_KAN_REPLAY_ADS = "点击重播";
    public static final String STR_QU_KAN_DOWNLOAD_NOW = "立即下载";
    public static final String STR_QU_KAN_ADS_END_AFTER = "秒后发放";
    public static final String STR_QU_KAN_WATCH_VIDEO_CHECK_DETAIL = "查看详情";
    public static final String STR_QU_KAN_WATCH_VIDEO_JUMP_ADS = "跳过";
    public static final String STR_QU_KAN_HAS_GOD_GOLD = "已成功领取";
    public static final String STR_QU_KAN_HEADLINE = "头条";
    public static final String STR_QU_KAN_HEADLINE_GET_GOLD = "领取";
    public static final String STR_QU_KAN_BROWSE_ARTICLE = "阅读文章";
    public static final String STR_QU_KAN_REFRESH = "刷新";
    public static final String STR_QU_KAN_RECOMMEND = "推荐";

    public static final String STR_QI_YI_JUMP_ADS = "关闭";
    public static final String STR_QI_YI_DO_NOT_UPGRADE = "暂不升级";
    public static final String STR_QI_YI_GOLD_TAB = "赚钱";
    public static final String STR_QI_YI_SIGN_SUCCESS = "签到成功";
    public static final String STR_QI_YI_DAILY_TASK = "日常任务";
    public static final String STR_QI_YI_WATCH_ADS = "1000金币轻松赚";
    public static final String STR_QI_YI_GET_MORE_100_GOLD = "再赚100金币";
    public static final String STR_QI_YI_OK_I_KNOW = "好，我知道了";
    public static final String STR_QI_YI_WAIT_ADS_END = "后可领取奖励";
    public static final String STR_QI_YI_CLOSE_ADS = "关闭广告";
    public static final String STR_QI_YI_HOME_PAGE = "首页";
    public static final String STR_QI_YI_GET_GOLD = "领金币";
    public static final String STR_QI_YI_GET_MORE = "再赚";
    public static final String STR_QI_YI_OPEN_TREASURE_BOX = "开宝箱领金币";

    public static final String STR_DOU_YIN_I_KNOW = "我知道了";
    public static final String STR_DOU_YIN_RECOMMEND_FRIENDS = "朋友推荐";
    public static final String STR_DOU_YIN_SIGN_NOW = "立即签到";
    public static final String STR_DOU_YIN_CLOSE_ADS = "关闭";
    public static final String STR_DOU_YIN_WAIT_ADS_END_1 = "领金币";
    public static final String STR_DOU_YIN_WAIT_ADS_END_2 = "后可领取奖励";
    public static final String STR_DOU_YIN_WATCH_MORE = "再看一个";
    public static final String STR_DOU_YIN_NO_MORE_ADS = "当前无新视频";
    public static final String STR_DOU_YIN_CLOSE_ADS_CONFIRM = "坚持退出";
    public static final String STR_DOU_YIN_GET_GOLD = "开宝箱";
    public static final String STR_DOU_YIN_TOTAL_GOLD = "金币收益";
    public static final String STR_DOU_YIN_WATCH_ADS_TO_GET_GOLD = "看广告赚金币";
    public static final String STR_DOU_YIN_OPEN_TREASURE_BOX = "开宝箱得金币";
    public static final String STR_DOU_YIN_WATCH_ADS_TO_GET_MORE = "看广告视频再赚";

    // task thread message
    public static final int TASK_MSG_SLEEP_TASK = 0;
    public static final int TASK_MSG_ALL_APPS_TIMED_TASK = 1;
    public static final int TASK_MSG_REPEAT_TASK = 2;

    public static final long DAILY_TASK_CHECK_PERIOD = 1000 * 60 * 60 * 2;


    // debug/release data ----------------------------------------------------------------
    // ----------------------------- for real -----------------------------
    public static final long WAIT_TIME_20_SEC = 1000 * 20;
    public static final long WAIT_TIME_15_SEC = 1000 * 15;
    public static final long WAIT_TIME_10_SEC = 1000 * 10;

    public static final long SLEEP_TASK_PERIOD = 1000 * 60 * 3;
    public static final long DEFAULT_SLEEP_DURATION = 1000 * 60 * 10;
    public static final long BATTERY_LEVEL_THRESHOLD = 80;
    public static final long BATTERY_TEMPERATURE_THRESHOLD = 360;

    // all task execute count (normal) ------------------------------------------
    public static final int TOU_TIAO_TREASURE_BOX_MAX_EXEC_COUNT = 100;
    public static final int TOU_TIAO_BROWSE_VIDEO_MAX_EXEC_COUNT = 50;
    public static final int TOU_TIAO_BROWSE_GOODS_MAX_EXEC_COUNT = 10;
    public static final int TOU_TIAO_WATCH_ADS_MAX_EXEC_COUNT = 10;

    public static final int KUAI_SHOU_TREASURE_BOX_MAX_EXEC_COUNT = 10;
    public static final int KUAI_SHOU_WATCH_LIVE_MAX_EXEC_COUNT = 10;//10;
    public static final int KUAI_SHOU_REWARD_MAX_EXEC_COUNT = 10;
    public static final int KUAI_SHOU_BROWSE_VIDEO_MAX_EXEC_COUNT = 50;

    public static final int DIAN_TAO_BROWSE_LIVE_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_BROWSE_VIDEO_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WALK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_WALK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT = 20;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_30SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 3;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT = 3;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT = 3;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT = 0;

    public static final int DIAN_TAO_WORK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT = 20;
    public static final int DIAN_TAO_WORK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 20;
    public static final int DIAN_TAO_WORK_WATCH_VIDEO_3MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_VIDEO_5MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_GOLDEN_LIVE_30SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_WORK_WATCH_GOLDEN_LIVE_60SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_WORK_WATCH_GOLDEN_LIVE_3MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_30SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_60SEC_MAX_EXEC_COUNT = 3;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 4;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT = 4;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT = 1;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT = 0;

    public static final int DIAN_TAO_LOTTERY_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT = 20;
    public static final int DIAN_TAO_LOTTERY_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 20;
    public static final int DIAN_TAO_LOTTERY_WATCH_VIDEO_3MIN_MAX_EXEC_COUNT = 20;
    public static final int DIAN_TAO_LOTTERY_WATCH_LIVE_60SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_LOTTERY_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 8;
    public static final int DIAN_TAO_LOTTERY_WATCH_GOLDEN_LIVE_30SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_LOTTERY_WATCH_GOLDEN_LIVE_60SEC_MAX_EXEC_COUNT = 10;

    public static final int JING_DONG_BROWSE_ACTIVITY_MAX_EXEC_COUNT = 0;
    public static final int JING_DONG_BROWSE_GOODS_MAX_EXEC_COUNT = 0;
    public static final int JING_DONG_BROWSE_VIDEO_MAX_EXEC_COUNT = 0;

    public static final int QU_KAN_LUCKY_MONEY_GOLD_MAX_EXEC_COUNT = 100;
    public static final int QU_KAN_BROWSE_ARTICLE_MAX_EXEC_COUNT = 50;
    public static final int QU_KAN_BROWSE_SHORT_VIDEO_MAX_EXEC_COUNT = 50;
    public static final int QU_KAN_BROWSE_VIDEO_MAX_EXEC_COUNT = 50;

    public static final int QI_YI_BROWSE_ADS_MAX_EXEC_COUNT = 10;
    public static final int QI_YI_TREASURE_BOX_MAX_EXEC_COUNT = 10;

    public static final int DOU_YIN_BROWSE_ADS_MAX_EXEC_COUNT = 50;
    // all task execute count (normal) ------------------------------------------

//    // all task execute count (summer) ------------------------------------------
//    public static final int TOU_TIAO_TREASURE_BOX_MAX_EXEC_COUNT = 10;
//    public static final int TOU_TIAO_BROWSE_VIDEO_MAX_EXEC_COUNT = 0;
//    public static final int TOU_TIAO_BROWSE_GOODS_MAX_EXEC_COUNT = 0;
//    public static final int TOU_TIAO_WATCH_ADS_MAX_EXEC_COUNT = 0;
//
//    public static final int KUAI_SHOU_TREASURE_BOX_MAX_EXEC_COUNT = 10;
//    public static final int KUAI_SHOU_WATCH_LIVE_MAX_EXEC_COUNT = 0;
//    public static final int KUAI_SHOU_REWARD_MAX_EXEC_COUNT = 0;
//    public static final int KUAI_SHOU_BROWSE_VIDEO_MAX_EXEC_COUNT = 0;
//
//    public static final int DIAN_TAO_BROWSE_LIVE_MAX_EXEC_COUNT = 0;
//    public static final int DIAN_TAO_BROWSE_VIDEO_MAX_EXEC_COUNT = 0;
//    public static final int DIAN_TAO_WALK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 5;
//    public static final int DIAN_TAO_WALK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT = 5;
//    public static final int DIAN_TAO_WALK_WATCH_LIVE_30SEC_MAX_EXEC_COUNT = 10;
//    public static final int DIAN_TAO_WALK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WALK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WALK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WALK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT = 3;
//
//    public static final int DIAN_TAO_WORK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT = 5;
//    public static final int DIAN_TAO_WORK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 5;
//    public static final int DIAN_TAO_WORK_WATCH_VIDEO_3MIN_MAX_EXEC_COUNT = 0;
//    public static final int DIAN_TAO_WORK_WATCH_VIDEO_5MIN_MAX_EXEC_COUNT = 0;
//    public static final int DIAN_TAO_WORK_WATCH_LIVE_60SEC_MAX_EXEC_COUNT = 5;
//    public static final int DIAN_TAO_WORK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WORK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WORK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT = 2;
//    public static final int DIAN_TAO_WORK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT = 1;
//
//    public static final int JING_DONG_BROWSE_ACTIVITY_MAX_EXEC_COUNT = 0;
//    public static final int JING_DONG_BROWSE_GOODS_MAX_EXEC_COUNT = 0;
//    public static final int JING_DONG_BROWSE_VIDEO_MAX_EXEC_COUNT = 0;
//
//    public static final int QU_KAN_LUCKY_MONEY_GOLD_MAX_EXEC_COUNT = 0;
//    public static final int QU_KAN_BROWSE_ARTICLE_MAX_EXEC_COUNT = 0;
//    public static final int QU_KAN_BROWSE_SHORT_VIDEO_MAX_EXEC_COUNT = 10;
//    public static final int QU_KAN_BROWSE_VIDEO_MAX_EXEC_COUNT = 0;
//
//    public static final int QI_YI_BROWSE_ADS_MAX_EXEC_COUNT = 10;
//
//    public static final int DOU_YIN_BROWSE_ADS_MAX_EXEC_COUNT = 0;
//    // all task execute count (summer) ------------------------------------------

    // ----------------------------- for debug -----------------------------
//    public static final long SLEEP_TASK_PERIOD = 1000 * 60 * 2;
//    public static final long DEFAULT_SLEEP_DURATION = 1000 * 60 * 1;
//    public static final long BATTERY_LEVEL_THRESHOLD = 30;
//    public static final long BATTERY_TEMPERATURE_THRESHOLD = 370;
//
//    public static final int TOU_TIAO_BROWSE_VIDEO_MAX_EXEC_COUNT = 200000;
//    public static final int TOU_TIAO_BROWSE_GOODS_MAX_EXEC_COUNT = 3;
//    public static final int TOU_TIAO_WATCH_ADS_MAX_EXEC_COUNT = 10;
//
//    public static final int KUAI_SHOU_TREASURE_BOX_MAX_EXEC_COUNT = 10;
//    public static final int KUAI_SHOU_WATCH_LIVE_MAX_EXEC_COUNT = 10;
//    public static final int KUAI_SHOU_REWARD_MAX_EXEC_COUNT = 10;
//    public static final int KUAI_SHOU_BROWSE_VIDEO_MAX_EXEC_COUNT = 20;
//
//    public static final int DIAN_TAO_BROWSE_LIVE_MAX_EXEC_COUNT = 0;
//    public static final int DIAN_TAO_BROWSE_VIDEO_MAX_EXEC_COUNT = 0;
//    public static final int DIAN_TAO_WALK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 5;
//    public static final int DIAN_TAO_WALK_WATCH_VIDEO_5MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WALK_WATCH_LIVE_30SEC_MAX_EXEC_COUNT = 10;
//    public static final int DIAN_TAO_WALK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WALK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WALK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WALK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT = 3;
//
//    public static final int DIAN_TAO_WORK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 5;
//    public static final int DIAN_TAO_WORK_WATCH_VIDEO_3MIN_MAX_EXEC_COUNT = 0;
//    public static final int DIAN_TAO_WORK_WATCH_VIDEO_5MIN_MAX_EXEC_COUNT = 0;
//    public static final int DIAN_TAO_WORK_WATCH_LIVE_60SEC_MAX_EXEC_COUNT = 5;
//    public static final int DIAN_TAO_WORK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WORK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT = 3;
//    public static final int DIAN_TAO_WORK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT = 2;
//    public static final int DIAN_TAO_WORK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT = 1;
//
//    public static final int JING_DONG_BROWSE_ACTIVITY_MAX_EXEC_COUNT = 5;
//    public static final int JING_DONG_BROWSE_GOODS_MAX_EXEC_COUNT = 5;
//    public static final int JING_DONG_BROWSE_VIDEO_MAX_EXEC_COUNT = 20;
//
//    public static final int QU_KAN_BROWSE_SHORT_VIDEO_MAX_EXEC_COUNT = 20;
    // debug/release data ----------------------------------------------------------------


    // build config
    public static String BUILD_CONFIG = "debug";
//    public static String BUILD_CONFIG = "release";
}
