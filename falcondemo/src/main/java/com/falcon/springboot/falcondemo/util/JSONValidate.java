package com.falcon.springboot.falcondemo.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONValidate {
	public static boolean isValid(String jsonString) {
		try {
			new JSONObject(jsonString);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
}
