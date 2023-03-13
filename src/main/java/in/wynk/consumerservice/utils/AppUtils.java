package in.wynk.consumerservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/
public class AppUtils {


    public static Gson gson = new Gson();

    public static ObjectMapper mapper = new ObjectMapper();


    public static <T> T fromJson(String json, Class<T> className) {
        return gson.fromJson(json, className);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static String writeValueAsString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            //
            return object.toString();
        }
    }
}
