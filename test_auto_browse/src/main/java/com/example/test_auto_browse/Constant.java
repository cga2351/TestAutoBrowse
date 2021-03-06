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

    public static final String STR_TOU_TIAO_JUMP_ADS = "??????";
    public static final String STR_TOU_TIAO_TASK = "??????";
    public static final String STR_TOU_TIAO_COMMON_TASK = "????????????";
    public static final String STR_TOU_TIAO_HOME_PAGE = "??????";
    public static final String STR_TOU_TIAO_SURPRISE_GOLD = "??????????????????";
    public static final String STR_TOU_TIAO_TASK_PAGE_TITLE = "???????????????????????????";
    public static final String STR_TOU_TIAO_NO_NETWORK = "???????????????";
    public static final String STR_TOU_TIAO_OPEN_TREASURE_BOX = "??????????????????";
    public static final String STR_TOU_TIAO_CLOSE_ADS = "??????";
    public static final String STR_TOU_TIAO_CLOSE_ADS_CONFIRM = "????????????";
    public static final String STR_TOU_TIAO_WAIT_ADS_END_1 = "?????????";
    public static final String STR_TOU_TIAO_WAIT_ADS_END_2 = "??????????????????";
    public static final String STR_TOU_TIAO_WATCH_MORE = "????????????";
    public static final String STR_TOU_TIAO_NO_MORE_ADS = "??????????????????";
    public static final String STR_TOU_TIAO_COLON = ":";
    public static final String STR_TOU_TIAO_GET_SIGN_GOLD = "????????????";
    public static final String STR_TOU_TIAO_HAVE_A_LOOK= "?????????";
    public static final String STR_TOU_TIAO_WATCH_VIDEO_TO_GET_GOLD = "???????????????";
    public static final String STR_TOU_TIAO_CLOSE_POPUP = "??????";
    public static final String STR_TOU_TIAO_DAILY_TASK = "????????????";
    public static final String STR_TOU_TIAO_GET_GOLD = "??????";
//    public static final String STR_TOU_TIAO_WATCH_ADS_VIDEO_TO_GET_MORE = "???????????????";
//    public static final String STR_TOU_TIAO_WATCHED_ADS_VIDEO_TO_GET_MORE = "??????????????????";
    public static final String STR_TOU_TIAO_WATCH_ADS_TO_GET_MORE = "????????????";
    public static final String STR_TOU_TIAO_GET_MEAL_GOLD = "????????????";
    public static final String STR_TOU_TIAO_GET_SLEEP_GOLD = "????????????";
    public static final String STR_TOU_TIAO_GET_WALKING_GOLD = "????????????";
    public static final String STR_TOU_TIAO_MEAL_GOLD_HAS_GOT = "??????";
    public static final String STR_TOU_TIAO_GO_TO_SLEEP = "????????????";
    public static final String STR_TOU_TIAO_WAKE_UP = "????????????";
    public static final String STR_TOU_TIAO_WATCH_ADS_TO_GET_GOLD = "??????????????????";
    public static final String STR_TOU_TIAO_BROWSE_GOODS_TO_GET_GOLD = "??????????????????";
    public static final String STR_TOU_TIAO_MORE_TASK = "????????????";
    public static final String STR_TOU_TIAO_BROWSE_GOODS_30S = "??????????????????30???, ?????????200??????";
    public static final String STR_TOU_TIAO_DOUBLE_GOLD = "????????????";
    public static final String STR_TOU_TIAO_I_KNOW = "????????????";

    public static final String ID_DIAN_TAO_HOME_TAB_ITEM = "com.taobao.live:id/tab_container";
    public static final String STR_DIAN_TAO_GOLD_CENTER = "????????????";
    public static final String STR_DIAN_TAO_GO_TO_SIGN = "?????????";
    public static final String STR_DIAN_TAO_WATCH_LIVE_GET_MORE = "???????????????";
    public static final String STR_DIAN_TAO_CLICK_TO_SIGN = "????????????";
    public static final String STR_DIAN_TAO_WATCH_VIDEO_GET_GOLD = "?????????????????????";
    public static final String STR_DIAN_TAO_WATCH_LIVE_GET_GOLD = "?????????????????????";
    public static final String STR_DIAN_TAO_GET_GOLD = "????????????";
    public static final String STR_DIAN_TAO_WALK_GET_GOLD = "???????????????";
    public static final String STR_DIAN_TAO_WALK_GET_ENERGY_DRINK_STEPS = "??????";
    public static final String STR_DIAN_TAO_GET_WALK_STEPS = "?????????";
    public static final String STR_DIAN_TAO_AFTER_S_COMPLETE = "?????????";
    public static final String STR_DIAN_TAO_WATCH_VIDEO_60Sec = "?????????60???";
    public static final String STR_DIAN_TAO_WATCH_VIDEO_30Sec = "?????????30???";
    public static final String STR_DIAN_TAO_WATCH_VIDEO_3Min = "?????????3??????";
    public static final String STR_DIAN_TAO_WATCH_VIDEO_5Min = "?????????5??????";
    public static final String STR_DIAN_TAO_WATCH_DISCOVER_GOODS_5Min = "???????????????5??????";
    public static final String STR_DIAN_TAO_WATCH_GOLDEN_LIVE_30Sec = "?????????8????????????30???";
    public static final String STR_DIAN_TAO_WATCH_GOLDEN_LIVE_60Sec = "?????????8????????????60???";
    public static final String STR_DIAN_TAO_WATCH_GOLDEN_LIVE_3Min = "?????????8????????????3??????";
    public static final String STR_DIAN_TAO_WATCH_LIVE_10Sec = "?????????10???";
    public static final String STR_DIAN_TAO_WATCH_LIVE_30Sec = "?????????30???";
    public static final String STR_DIAN_TAO_WATCH_LIVE_60Sec = "?????????60???";
    public static final String STR_DIAN_TAO_WATCH_LIVE_3Min = "?????????3??????";
    public static final String STR_DIAN_TAO_WATCH_LIVE_5Min = "?????????5??????";
    public static final String STR_DIAN_TAO_WATCH_LIVE_8Min = "?????????8??????";
    public static final String STR_DIAN_TAO_WATCH_LIVE_10Min = "?????????10??????";
    public static final String STR_DIAN_TAO_GET_GOLD_ON_RIGHT_TOP = "??????";
    public static final String STR_DIAN_TAO_SLEEP_TO_GET_GOLD = "???????????????";
    public static final String STR_DIAN_TAO_START_SLEEP = "????????????";
    public static final String STR_DIAN_TAO_WAKE_UP = "????????????";
    public static final String STR_DIAN_TAO_DEPART = "??????";
    public static final String STR_DIAN_TAO_FINISH_TASK_TO_GET_STEPS = "??????????????????";
    public static final String STR_DIAN_TAO_CHECK_MORE_TASKS = "??????????????????";
    public static final String STR_DIAN_TAO_GET_STEPS_GOLD = "??????";
    public static final String STR_DIAN_TAO_GET_ENERGY_DRINK_FAILED = "??????, ????????????????????????, ???????????????";
    public static final String STR_DIAN_TAO_GET_STEPS_GOLD_FAILED = "????????????";
    public static final String STR_DIAN_TAO_WATCH_LIVE_TO_GET_MORE = "??????";
//    public static final String STR_DIAN_TAO_I_KNOW = "????????????";
    public static final String STR_DIAN_TAO_REGULATION = "??????";
    public static final String STR_DIAN_TAO_NO_AVAILABLE_STEPS = "??????????????????";
    public static final String STR_DIAN_TAO_GO_ON_SCRATCH = "????????????";
    public static final String STR_DIAN_TAO_BROWSE_SPECIAL_PRICE_MARKET_20SEC = "??????????????????20???";
    public static final String STR_DIAN_TAO_BROWSE_SPECIAL_PRICE_MARKET_30SEC = "??????????????????30???";
    public static final String STR_DIAN_TAO_PLAY_GAME = "???????????????";
    public static final String STR_DIAN_TAO_GO_TO_FERTILIZE = "?????????????????????";
    public static final String STR_DIAN_TAO_BROWSE_GOODS_MARKET_30s = "??????????????????30???";
    public static final String STR_DIAN_TAO_BROWSE_RECOMMEND_30s = "??????????????????30???";
    public static final String STR_DIAN_TAO_BROWSE_NEW_ARRIVAL_30s = "??????????????????";
    public static final String STR_DIAN_TAO_SPECIAL_PRICE_MARKET_SWIPE = "????????????";
    public static final String STR_DIAN_TAO_THUMP_UP_20_TIMES = "????????????????????????20???";
    public static final String STR_DIAN_TAO_BROWSE_RECOMMEND_20s = "??????????????????20???";
    public static final String STR_DIAN_TAO_SWIPE_TO_BROWSE = "????????????";
    public static final String STR_DIAN_TAO_TASK_TASK_COMPLETED = "????????????";
    public static final String STR_DIAN_TAO_TASK_CONGRATULATE_TASK_COMPLETED = "??????????????????";
    public static final String STR_DIAN_TAO_TASK_FOLLOW = "??????";
    public static final String STR_DIAN_TAO_WORK_TO_GET_GOLD = "???????????????";
    public static final String STR_DIAN_TAO_WORK_SIGN = "????????????";
    public static final String STR_DIAN_TAO_SIGN_TODAY = "????????????";
    public static final String STR_DIAN_TAO_GET_SIGN_GOLD_FAILED = "?????????????????????";
    public static final String STR_DIAN_TAO_LUCK_DRAW_HIS = "????????????";
    public static final String STR_DIAN_TAO_GET_WORK_GOLD = "??????";
    public static final String STR_DIAN_TAO_WATCH_LIVE = "?????????";
    public static final String STR_DIAN_TAO_I_KNOW = "????????????";
    public static final String STR_DIAN_TAO_I_AM_WORKING = "????????????????????????";
    public static final String STR_DIAN_TAO_COOK_WORK = "??????";
    public static final String STR_DIAN_TAO_START_WORK = "????????????";
    public static final String STR_DIAN_TAO_START_NO_AVAILABLE_ENERGY = "????????????";
    public static final String STR_DIAN_TAO_START_GET_ENERGY = "??????+";
    public static final String STR_DIAN_TAO_SWIPE_RIGHT_TO_VERIFY = "??????????????????";
    public static final String STR_DIAN_TAO_LOTTERY = "???????????????";
    public static final String STR_DIAN_TAO_WORK_MORE_TASKS = "??????????????????";

    public static final String STR_WEI_SHI_JUMP_ADS = "??????";
    public static final String STR_WEI_SHI_TEEN_PROTECTION = "???????????????????????????";
    public static final String STR_WEI_SHI_I_KNOW = "?????????";
    public static final String STR_WEI_SHI_HOME_TAB_MINE = "???";
    public static final String STR_WEI_SHI_WELFARE_CENTER = "????????????";
    public static final String STR_WEI_SHI_SIGN_TO_GET_GOLD = "???????????????";
    public static final String STR_WEI_SHI_ALL_KARAOKE = "??????K???";

    public static final String ID_KUAI_SHOU_LEFT_SETTING_BTN = "com.kuaishou.nebula:id/left_btn";
    public static final String ID_KUAI_SHOU_POPUP_DIALOG = "com.kuaishou.nebula:id/img_nebula_pull_new_dialog";
    public static final String ID_KUAI_SHOU_COSE_POPUP_DIALOG = "com.kuaishou.nebula:id/close";
    public static final String ID_KUAI_SHOU_AWARD_COUNT_DOWN = "com.kuaishou.nebula:id/award_count_down_text";
    public static final String ID_KUAI_SHOU_AWARD_COUNT_DOWN_NEW_VERSION = "com.kuaishou.nebula:id/award_count_down_group";
    public static final String STR_KUAI_SHOU_GO_TO_GET_GOLD = "?????????";
    public static final String STR_KUAI_SHOU_TASK_CENTER = "????????????";
    public static final String STR_KUAI_SHOU_SIGN_TO_GET_GOLD = "???????????????";
    public static final String STR_KUAI_SHOU_SIGN_IMMEDIATELY = "????????????";
    public static final String STR_KUAI_SHOU_WATCH_VIDEO_TO_GET_GOLD = "??????????????????";
    public static final String STR_KUAI_SHOU_CLICK_TO_OPEN = "????????????";
    public static final String STR_KUAI_SHOU_OPEN_TREASURE_BOX = "??????????????????";
    public static final String STR_KUAI_SHOU_WATCH_VIDEO_TO_GET_MORE = "????????????????????????";
    public static final String STR_KUAI_SHOU_WAIT_ADS_END = "s??????????????????";
    public static final String STR_KUAI_SHOU_1100_GOLD_WATCH_LIVE_TASK = "1100??????";
    public static final String STR_KUAI_SHOU_1000_REWARD_TASK = "????????????";
    public static final String STR_KUAI_SHOU_1000_REWARD_TASK_END = "s??????????????????";
    public static final String STR_KUAI_SHOU_GIVE_UP_REWARD = "????????????";
    public static final String STR_KUAI_SHOU_I_KNOW = "????????????";
    public static final String STR_KUAI_SHOU_FOLLOW_AND_EXIT = "???????????????";
    public static final String STR_KUAI_SHOU_EXIT = "??????";
    public static final String STR_KUAI_SHOU_LIVE_LIST_TITLE = "com.kuaishou.nebula:id/award_progress_num";
    public static final String STR_KUAI_SHOU_LIVE_LIST_ITEM = "com.kuaishou.nebula:id/constraint_layout_live_item_container";


    public static final String ID_JING_DONG_BROWSE_NEXT = "com.jd.jdlite:id/ll_task_bottom_next";
    public static final String ID_JING_DONG_GOLD_PROGRESS = "com.jd.jdlite:id/progressbar2";
    public static final String STR_JING_DONG_JUMP_ADS = "??????";
    public static final String STR_JING_DONG_CLOSE_POPUP = "????????????";
    public static final String STR_JING_DONG_GET_GOLD = "??????";
    public static final String STR_JING_DONG_EXCHANGE_LUCKY_MONEY = "?????????";
    public static final String STR_JING_DONG_BROWSE_GOODS_GET_GOLD = "??????????????????";
    public static final String STR_JING_DONG_BROWSE_ACTIVITY_GET_GOLD = "??????????????????";
    public static final String STR_JING_DONG_BROWSE_NEXT = "??????????????????";
    public static final String STR_JING_DONG_TASK_COMPLETED_TODAY = "???????????????";
    public static final String STR_JING_DONG_BROWSE_VIDEO_GET_GOLD = "??????????????????";

    public static final String ID_QU_KAN_ADS_VIDEO_PROGRESS = "com.jifen.qukan:id/tt_video_progress";
    public static final String ID_QU_KAN_ADS_VIDEO_CLOSE = "com.jifen.qukan:id/tt_video_ad_close";
    public static final String STR_QU_KAN_SIGN_SUCESS = "????????????";
    public static final String STR_QU_KAN_WATCH_VIDEO_TO_GET_MORE = "???????????????";
    public static final String STR_QU_KAN_WATCH_VIDEO_COUNT_DOWN = "???????????????";
    public static final String STR_QU_KAN_GET_GOLD_SUCCESS = "??????????????????";
    public static final String STR_QU_KAN_TASK_PAGE = "??????";
    public static final String STR_QU_KAN_SHORT_VIDEO_PAGE = "?????????";
    public static final String STR_QU_KAN_VIDEO_PAGE = "??????";
    public static final String STR_QU_KAN_SHORT_VIDEO_SHARE = "??????";
    public static final String STR_QU_KAN_MY_GOLD = "????????????";
    public static final String STR_QU_KAN_GET_GOLD_IMMEDIATELY = "????????????";
    public static final String STR_QU_KAN_LUCKY_MONEY_TASK_NOT_AVAILABLE = "?????????";
    public static final String STR_QU_KAN_WATCH_VIDEO_TO_GET_GOLD = "??????????????????";
    public static final String STR_QU_KAN_ADS_END = "???????????????";
    public static final String STR_QU_KAN_CANCEL_OPEN_APP = "????????????";
    public static final String STR_QU_KAN_REPLAY_ADS = "????????????";
    public static final String STR_QU_KAN_DOWNLOAD_NOW = "????????????";
    public static final String STR_QU_KAN_ADS_END_AFTER = "????????????";
    public static final String STR_QU_KAN_WATCH_VIDEO_CHECK_DETAIL = "????????????";
    public static final String STR_QU_KAN_WATCH_VIDEO_JUMP_ADS = "??????";
    public static final String STR_QU_KAN_HAS_GOD_GOLD = "???????????????";
    public static final String STR_QU_KAN_HEADLINE = "??????";
    public static final String STR_QU_KAN_HEADLINE_GET_GOLD = "??????";
    public static final String STR_QU_KAN_BROWSE_ARTICLE = "????????????";
    public static final String STR_QU_KAN_REFRESH = "??????";
    public static final String STR_QU_KAN_RECOMMEND = "??????";

    public static final String STR_QI_YI_JUMP_ADS = "??????";
    public static final String STR_QI_YI_DO_NOT_UPGRADE = "????????????";
    public static final String STR_QI_YI_GOLD_TAB = "??????";
    public static final String STR_QI_YI_SIGN_SUCCESS = "????????????";
    public static final String STR_QI_YI_DAILY_TASK = "????????????";
    public static final String STR_QI_YI_WATCH_ADS = "1000???????????????";
    public static final String STR_QI_YI_GET_MORE_100_GOLD = "??????100??????";
    public static final String STR_QI_YI_OK_I_KNOW = "??????????????????";
    public static final String STR_QI_YI_WAIT_ADS_END = "??????????????????";
    public static final String STR_QI_YI_CLOSE_ADS = "????????????";
    public static final String STR_QI_YI_HOME_PAGE = "??????";
    public static final String STR_QI_YI_GET_GOLD = "?????????";
    public static final String STR_QI_YI_GET_MORE = "??????";
    public static final String STR_QI_YI_OPEN_TREASURE_BOX = "??????????????????";
    public static final String STR_QI_YI_AFTER_S_COMPLETE = "?????????";

    public static final String STR_DOU_YIN_I_KNOW = "????????????";
    public static final String STR_DOU_YIN_RECOMMEND_FRIENDS = "????????????";
    public static final String STR_DOU_YIN_SIGN_NOW = "????????????";
    public static final String STR_DOU_YIN_CLOSE_ADS = "??????";
    public static final String STR_DOU_YIN_WAIT_ADS_END_1 = "?????????";
    public static final String STR_DOU_YIN_WAIT_ADS_END_2 = "??????????????????";
    public static final String STR_DOU_YIN_WATCH_MORE = "????????????";
    public static final String STR_DOU_YIN_NO_MORE_ADS = "??????????????????";
    public static final String STR_DOU_YIN_CLOSE_ADS_CONFIRM = "????????????";
    public static final String STR_DOU_YIN_GET_GOLD = "?????????";
    public static final String STR_DOU_YIN_TOTAL_GOLD = "????????????";
    public static final String STR_DOU_YIN_WATCH_ADS_TO_GET_GOLD = "??????????????????";
    public static final String STR_DOU_YIN_OPEN_TREASURE_BOX = "??????????????????";
    public static final String STR_DOU_YIN_WATCH_ADS_TO_GET_MORE = "?????????????????????";

    // task thread message
    public static final int TASK_MSG_SLEEP_TASK = 0;
    public static final int TASK_MSG_ALL_APPS_TIMED_TASK = 1;
    public static final int TASK_MSG_REPEAT_TASK = 2;

    public static final long DAILY_TASK_CHECK_PERIOD = 1000 * 60 * 60 * 2;


    // debug/release data ----------------------------------------------------------------
    // ----------------------------- for real -----------------------------
    public static final long WAIT_TIME_25_SEC = 1000 * 25;
    public static final long WAIT_TIME_20_SEC = 1000 * 20;
    public static final long WAIT_TIME_15_SEC = 1000 * 15;
    public static final long WAIT_TIME_10_SEC = 1000 * 10;

    public static final long SLEEP_TASK_PERIOD = 1000 * 60 * 3;
    public static final long DEFAULT_SLEEP_DURATION = 1000 * 60 * 10;
    public static final long BATTERY_LEVEL_THRESHOLD = 80;
    public static final long BATTERY_TEMPERATURE_THRESHOLD = 360;

    // all task execute count (normal) ------------------------------------------
    public static final int TOU_TIAO_TREASURE_BOX_MAX_EXEC_COUNT = 100;
    public static final int TOU_TIAO_BROWSE_VIDEO_MAX_EXEC_COUNT = 2;
    public static final int TOU_TIAO_SURPRISE_GOLD_MAX_EXEC_COUNT = 0;
    public static final int TOU_TIAO_BROWSE_GOODS_MAX_EXEC_COUNT = 3;
    public static final int TOU_TIAO_WATCH_ADS_MAX_EXEC_COUNT = 10;

    public static final int KUAI_SHOU_TREASURE_BOX_MAX_EXEC_COUNT = 10;
    public static final int KUAI_SHOU_WATCH_LIVE_MAX_EXEC_COUNT = 10;//10;
    public static final int KUAI_SHOU_REWARD_MAX_EXEC_COUNT = 10;
    public static final int KUAI_SHOU_BROWSE_VIDEO_MAX_EXEC_COUNT = 50;

    public static final int DIAN_TAO_BROWSE_LIVE_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_BROWSE_VIDEO_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WALK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WALK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_WALK_WATCH_VIDEO_3MIN_MAX_EXEC_COUNT = 20;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_10SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_30SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 1;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT = 1;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WALK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WALK_WATCH_GOLDEN_LIVE_30SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_WALK_WATCH_GOLDEN_LIVE_60SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_WALK_WATCH_DISCOVER_GOODS_5MIN_MAX_EXEC_COUNT = 0;

    public static final int DIAN_TAO_WORK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 20;
    public static final int DIAN_TAO_WORK_WATCH_VIDEO_3MIN_MAX_EXEC_COUNT = 20;
    public static final int DIAN_TAO_WORK_WATCH_VIDEO_5MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_GOLDEN_LIVE_30SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_WORK_WATCH_GOLDEN_LIVE_60SEC_MAX_EXEC_COUNT = 10;
    public static final int DIAN_TAO_WORK_WATCH_GOLDEN_LIVE_3MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_30SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_60SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_5MIN_MAX_EXEC_COUNT = 4;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_LIVE_10MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_WORK_WATCH_DISCOVER_GOODS_5MIN_MAX_EXEC_COUNT = 3;

    public static final int DIAN_TAO_LOTTERY_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_LOTTERY_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_LOTTERY_WATCH_VIDEO_3MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_LOTTERY_WATCH_LIVE_60SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_LOTTERY_WATCH_LIVE_3MIN_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_LOTTERY_WATCH_GOLDEN_LIVE_30SEC_MAX_EXEC_COUNT = 0;
    public static final int DIAN_TAO_LOTTERY_WATCH_GOLDEN_LIVE_60SEC_MAX_EXEC_COUNT = 0;

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
//    public static String BUILD_CONFIG = "debug";
    public static String BUILD_CONFIG = "release";
}
