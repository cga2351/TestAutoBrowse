package com.example.test_auto_browse.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2017/6/21
 * description :
 */

public class GsonUtil {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        }  catch (Exception e) {
            Logger.debug("-mqmsdebug", "input json format error:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
