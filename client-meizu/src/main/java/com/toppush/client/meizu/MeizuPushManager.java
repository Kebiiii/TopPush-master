package com.toppush.client.meizu;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.meizu.cloud.pushsdk.PushManager;
import com.toppush.client.core.TopPushManager;
import com.toppush.client.core.TopPushMessageProvider;

/**
 * 魅族推送只支持Flyme系统，务必需要注意
 */
public class MeizuPushManager implements TopPushManager {
    public static final String NAME = "meizuPush";
    public static TopPushMessageProvider sTopPushMessageProvider;

    private static final String MEIZU_APP_ID = "com.thinmoo.toppush.MEIZU_APP_ID";
    private static final String MEIZU_APP_KEY = "com.thinmoo.toppush.MEIZU_APP_KEY";

    private String appId;
    private String appKey;

    public MeizuPushManager(Context context) {
        this.appId = getMetaDataValue(MEIZU_APP_ID,context);
        this.appKey = getMetaDataValue(MEIZU_APP_KEY,context);
    }
//    public MeizuPushManager(String appId, String appKey) {
//        this.appId = appId;
//        this.appKey = appKey;
//    }

    @Override
    public void registerPush(Context context) {
        PushManager.register(context, appId, appKey);
    }

    @Override
    public void unRegisterPush(Context context) {
        PushManager.unRegister(context, appId, appKey);
    }

    @Override
    public void setAlias(Context context, String alias) {
        PushManager.subScribeAlias(context, appId, appKey, PushManager.getPushId(context), alias);
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        PushManager.unSubScribeAlias(context, appId, appKey, PushManager.getPushId(context), alias);
    }

    @Override
    public void setTags(Context context, String... tags) {
        for (String tag : tags) {
            PushManager.subScribeTags(context, appId, appKey, PushManager.getPushId(context), tag);
        }
    }

    @Override
    public void unsetTags(Context context, String... tags) {
        for (String tag : tags) {
            PushManager.unSubScribeTags(context, appId, appKey, PushManager.getPushId(context), tag);
        }

    }


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void setMessageProvider(TopPushMessageProvider provider) {
        sTopPushMessageProvider = provider;
    }

    @Override
    public void disable(Context context) {
        unRegisterPush(context);
    }

    private String getMetaDataValue(String key, Context context) {
        String value = "";
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String callName = appInfo.metaData.getString(key);
            if (callName != null && !callName.equals("")) {
                value = callName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        value = value.replaceFirst("toppush", "");
//        Log.i("TopPush", key + ":" + value);
        return value;
    }
}
