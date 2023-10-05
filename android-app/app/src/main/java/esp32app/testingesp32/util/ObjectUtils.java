package esp32app.testingesp32.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class ObjectUtils {

    public static String objectToString(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static Gson Converter(){
        return new Gson();
    }

    public static <T> Object stringToObject(String jsonProduct, Type classType) {
        Gson gson = new Gson();
        return gson.<T>fromJson(jsonProduct, classType);
    }

    public static String convertToValidJSON(String input) {
        String[] keyValuePairs = input.split(", ");
        JsonObject jsonObject = new JsonObject();

        for (String keyValuePair : keyValuePairs) {
            String[] parts = keyValuePair.split("=");
            String key = parts[0].trim();
            String value = parts[1].trim();

            if (key.contains(":")) {
                key = "" + key + "";
            }

            jsonObject.addProperty(key, value);
        }

        Gson gson = new Gson();
        return gson.toJson(jsonObject);
    }

}
