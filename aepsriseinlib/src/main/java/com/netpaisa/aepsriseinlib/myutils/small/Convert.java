/*
package com.netpaisa.aepsriseinlib.myutils.small;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Convert {

    public Convert() {
    }

    public static float amountToFloat(String var0) {
        if (var0 != null && !var0.trim().isEmpty()) {
            try {
                return Float.parseFloat(var0);
            } catch (Exception var1) {
                return 0.0F;
            }
        } else {
            return 0.0F;
        }
    }

    public static JsonObject toJsonObject(String var0) {
        return var0.startsWith("{") && var0.endsWith("}") ? ((JsonElement)(new Gson()).fromJson(var0, JsonElement.class)).getAsJsonObject() : new JsonObject();
    }

    public static JsonArray toJsonArray(String var0) {
        return var0.startsWith("[") && var0.endsWith("]") ? ((JsonElement)(new Gson()).fromJson(var0, JsonElement.class)).getAsJsonArray() : new JsonArray();
    }

    public static String mapToJsonObject(Map<String, String> var0) {
        Map var10000 = var0;
        JSONObject var3;
        var3 = new JSONObject.<init>();
        Iterator var1 = var10000.entrySet().iterator();

        while(var1.hasNext()) {
            Map.Entry var2;
            var3.put((String)(var2 = (Map.Entry)var1.next()).getKey(), var2.getValue());
        }

        return var3.toString();
    }

    public static String toCamelCase(String var0) {
        String var10000 = var0.toLowerCase();
        StringBuilder var6;
        var6 = new StringBuilder.<init>();
        boolean var1 = true;
        char[] var2;
        int var3 = (var2 = var10000.toCharArray()).length;

        for(int var4 = 0; var4 < var3; ++var4) {
            char var5;
            if (Character.isSpaceChar(var5 = var2[var4])) {
                var1 = true;
            } else if (var1) {
                var5 = Character.toTitleCase(var5);
                var1 = false;
            }

            var6.append(var5);
        }

        return var6.toString();
    }

    public static File getFile(String var0) {
        return var0 != null ? new File(var0) : null;
    }

    public static String[] toArray(List<String> var0) {
        if (var0 == null) {
            return null;
        } else {
            String[] var1 = new String[var0.size()];

            for(int var2 = 0; var2 < var0.size(); ++var2) {
                var1[var2] = (String)var0.get(var2);
            }

            return var1;
        }
    }

    public static List<String> toList(String[] var0) {
        return new ArrayList(Arrays.asList(var0));
    }

}
*/
