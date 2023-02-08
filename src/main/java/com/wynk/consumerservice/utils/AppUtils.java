package com.wynk.consumerservice.utils;

import com.google.gson.Gson;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/
public class AppUtils {


    private static Gson gson = new Gson();


    public static <T> T fromJson(String json, Class<T> className) {
        return gson.fromJson(json, className);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
