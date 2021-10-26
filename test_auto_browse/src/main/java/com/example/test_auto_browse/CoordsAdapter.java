package com.example.test_auto_browse;

import android.graphics.Point;

import com.example.test_auto_browse.utils.SysUtil;

import java.util.HashMap;

public class CoordsAdapter {

    // model name
    private static String modelName;
    private static final String MODEL_NAME_HUAWEI_P8MAX = "HUAWEI P8max";
    private static final String MODEL_NAME_SM_G950N = "SM-G950N";
    private static final String MODEL_NAME_HISENSE_F23 = "Hisense F23";

    // ui object name
    private static final String WeiShiMineTab = "WeiShiMineTab";    // 微视主页tab, "我"
    private static final String DianTaoScratch = "DianTaoScratch";  // 点淘走路赚元宝界面右上角, "刮刮乐"
    private static final String DianTaoScratchMeGetGold = "DianTaoScratchMeGetGold";  // 点淘刮刮乐界面，"点我刮奖"
    private static final String DianTaoWorkToGodGold = "DianTaoWorkToGodGold";  // 点淘打工赚钱界面，"去打工赚钱"
    private static final String DianTaoGetEnergy = "DianTaoGetEnergy";  // 点淘打工赚钱界面，"赚体力"
    private static final String DianTaoLuckyDraw = "DianTaoLuckyDraw";  // 点淘签到抽奖界面，"开奖"
    private static final String JingDongGetGold = "JingDongGetGold";  // 京东极速版主界面，"赚金币"
    private static final String QuKanLuckyMoney = "QuKanLuckyMoney";  // 趣头条主界面右下角红包，"最高+500金币"
    private static final String DouYinGoldCenter = "DouYinGoldCenter";  // 抖音极速版中间tab，"来赚钱"
    private static final String DianTaoWorkSign = "DianTaoWorkSign";  // 点淘打工界面，签到领体力，"签到"


    // <modelName, <UIObjectName, Point>>
    private static HashMap<String, HashMap<String, Point>> coordsMap = new HashMap<>();

    public static void init() {
        modelName = SysUtil.getModelName().trim();

        // init model name
        coordsMap.put(MODEL_NAME_HUAWEI_P8MAX, new HashMap<String, Point>());
        coordsMap.put(MODEL_NAME_HISENSE_F23, new HashMap<String, Point>());
        coordsMap.put(MODEL_NAME_SM_G950N, new HashMap<String, Point>());

        // init coords for all models
        // for "HUAWEI P8max"
        HashMap<String, Point> HuaWeiP8MaxMap = coordsMap.get(MODEL_NAME_HUAWEI_P8MAX);
        HuaWeiP8MaxMap.put(WeiShiMineTab, new Point(975, 1765));
        HuaWeiP8MaxMap.put(DianTaoScratch, new Point(960, 380));
        HuaWeiP8MaxMap.put(DianTaoScratchMeGetGold, new Point(550, 850));
        HuaWeiP8MaxMap.put(DianTaoWorkToGodGold, new Point(850,1350));
        HuaWeiP8MaxMap.put(DianTaoGetEnergy, new Point(380, 1400));
        HuaWeiP8MaxMap.put(DianTaoLuckyDraw, new Point( 560, 1770));
        HuaWeiP8MaxMap.put(JingDongGetGold, new Point( 560, 860));
        HuaWeiP8MaxMap.put(QuKanLuckyMoney, new Point( 960, 1600));
        HuaWeiP8MaxMap.put(DouYinGoldCenter, new Point( 550, 1760));
        HuaWeiP8MaxMap.put(DianTaoWorkSign, new Point( 640, 1560));

        // for "Hisense F23"
        HashMap<String, Point> HISENSE_F23Map = coordsMap.get(MODEL_NAME_HISENSE_F23);
        HISENSE_F23Map.put(WeiShiMineTab, new Point(0, 0));
        HISENSE_F23Map.put(DianTaoScratch, new Point(650, 270));
        HISENSE_F23Map.put(DianTaoScratchMeGetGold, new Point(360, 580));
        HISENSE_F23Map.put(DianTaoWorkToGodGold, new Point(570, 950));
        HISENSE_F23Map.put(DianTaoGetEnergy, new Point(250, 1000));
        HISENSE_F23Map.put(DianTaoLuckyDraw, new Point(350, 1230));
        HISENSE_F23Map.put(JingDongGetGold, new Point( 0, 0));
        HISENSE_F23Map.put(QuKanLuckyMoney, new Point( 620, 1100));
        HISENSE_F23Map.put(DouYinGoldCenter, new Point( 0, 0));

//        // for "SM-G950N"
//        HashMap<String, Point> SM_950NMap = coordsMap.get(MODEL_NAME_SM_G950N);
//        SM_950NMap.put(WeiShiMineTab, new Point(972, 2001));
//        SM_950NMap.put(DianTaoScratch, new Point(0, 0));
//        SM_950NMap.put(DianTaoScratchMeGetGold, new Point(0, 0));
//        SM_950NMap.put(DianTaoWorkToGodGold, new Point(0, 0));

    }

    public static Point getWeiShiMineTabCoords() {
        return coordsMap.get(modelName).get(WeiShiMineTab);
    }

    public static Point getDianTaoScratchCoords() {
        return coordsMap.get(modelName).get(DianTaoScratch);
    }

    public static Point getDianTaoScratchMeGetGoldCoords() {
        return coordsMap.get(modelName).get(DianTaoScratchMeGetGold);
    }

    public static Point getDianTaoWorkToGetGoldCoords() {
        return coordsMap.get(modelName).get(DianTaoWorkToGodGold);
    }

    public static Point getDianTaoGetEnergyCoords() {
        return coordsMap.get(modelName).get(DianTaoGetEnergy);
    }

    public static Point getDianTaoLuckyDrawCoords() {
        return coordsMap.get(modelName).get(DianTaoLuckyDraw);
    }

    public static Point getJingDongGetGoldCoords() {
        return coordsMap.get(modelName).get(JingDongGetGold);
    }

    public static Point getQuKanLuckyMoneyCoords() {
        return coordsMap.get(modelName).get(QuKanLuckyMoney);
    }

    public static Point getDouYinGoldCenterCoords() {
        return coordsMap.get(modelName).get(DouYinGoldCenter);
    }

    public static Point getDianTaoWorkSign() {
        return coordsMap.get(modelName).get(DianTaoWorkSign);
    }
}
