package com.toppush.client.mipush;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.toppush.client.core.TopPushManager;
import com.toppush.client.core.TopPushMessageProvider;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

public class MiPushManager implements TopPushManager {
    public static final String NAME = "mipush";
    public static TopPushMessageProvider sTopPushMessageProvider;
    private String appId;
    private String appKey;

    public static final String MIPUSH_APP_ID = "com.thinmoo.toppush.MIPUSH_APP_ID";
    public static final String MIPUSH_APP_KEY = "com.thinmoo.toppush.MIPUSH_APP_KEY";

    public MiPushManager(Context context) {
        this.appId = getMetaDataValue(MIPUSH_APP_ID,context);
        this.appKey = getMetaDataValue(MIPUSH_APP_KEY,context);
    }
//    public MiPushManager(String appId, String appKey) {
//        this.appId = appId;
//        this.appKey = appKey;
//    }

    @Override
    public void registerPush(Context context) {
        MiPushClient.registerPush(context.getApplicationContext(), appId, appKey);
    }

    @Override
    public void unRegisterPush(Context context) {
        unsetAlias(context, null);
        MiPushClient.unregisterPush(context.getApplicationContext());
    }

    @Override
    public void setAlias(Context context, String alias) {
        if (!MiPushClient.getAllAlias(context).contains(alias)) {
            MiPushClient.setAlias(context, alias, null);
        }
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        List<String> allAlias = MiPushClient.getAllAlias(context);
        for (int i = 0; i < allAlias.size(); i++) {
            MiPushClient.unsetAlias(context, allAlias.get(i), null);
        }
    }

    @Override
    public void setTags(Context context, String... tags) {
        for (String tag : tags){
            MiPushClient.subscribe(context, tag, null);
        }

    }

    @Override
    public void unsetTags(Context context, String... tags) {
        for (String tag : tags) {
            MiPushClient.unsubscribe(context, tag, null);
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
