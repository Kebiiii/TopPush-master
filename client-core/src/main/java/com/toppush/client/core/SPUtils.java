package com.toppush.client.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SPUtils {
  public static boolean put(String key, Object value, Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    Editor editor = sp.edit();
    if (value instanceof String) {
      editor.putString(key, (String) value);
    } else if (value instanceof Integer) {
      editor.putInt(key, (Integer) value);
    } else if (value instanceof Boolean) {
      editor.putBoolean(key, (Boolean) value);
    } else if (value instanceof Float) {
      editor.putFloat(key, (Float) value);
    } else if (value instanceof Long) {
      editor.putLong(key, (Long) value);
    }
    return editor.commit();
  }

  /**
   * 方法名： getBoolean <br>
   * @param key
   * @param context
   * @return 默认返回false
   */
  public static boolean getBoolean(String key, Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getBoolean(key, false);
  }
  public static boolean getBoolean(String key, String state, Context context) {
    SharedPreferences preferences = context.getSharedPreferences(state, Context.MODE_PRIVATE);
    return preferences.getBoolean(key, false);
  }

  public static String getString(String key, Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getString(key, "");
  }

  public static float getFloat(String key, Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getFloat(key, 0.0f);
  }

  public static Long getLong(String key, Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getLong(key, 0L);
  }

  public static Integer getInt(String key, Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getInt(key, 0);
  }

  public static Integer getInt(String key, int def, Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getInt(key, def);
  }
}
