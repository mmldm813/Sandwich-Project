package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();
    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_MAIN_NAME = "mainName";
    private static final String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String SANDWICH_DESCRIPTION = "description";
    private static final String SANDWICH_IMAGE = "image";
    private static final String SANDWICH_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        Sandwich sandwichObject = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameObject = jsonObject.getJSONObject(SANDWICH_NAME);
            String mainName = nameObject.getString(SANDWICH_MAIN_NAME);
            List<String> alsoKnownAs = populateJsonArrayList(nameObject.getJSONArray(SANDWICH_ALSO_KNOWN_AS));
            String placeOfOrigin = jsonObject.optString(SANDWICH_PLACE_OF_ORIGIN);
            String description = jsonObject.getString(SANDWICH_DESCRIPTION);
            String image = jsonObject.optString(SANDWICH_IMAGE);
            List<String> ingredients = populateJsonArrayList(jsonObject.optJSONArray(SANDWICH_INGREDIENTS));

            sandwichObject = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "PROBLEM WITH PARSING!!", e);
        }
        return sandwichObject;

    }

    private static List<String> populateJsonArrayList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>(0);
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Problems with array list!", e);
                }
            }
        }
        return list;
    }
}
