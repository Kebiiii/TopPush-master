package com.toppush.client.getui;

import android.content.Context;

import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.toppush.client.core.TopPushMessageProvider;
import com.toppush.client.core.TopPushManager;

/**
 * Created by Wiki on 2017/6/1.
 */

public class GeTuiManager implements TopPushManager {
    public static final String NAME = "getui";

    public static TopPushMessageProvider sTopPushMessageProvider;

    @Override
    public void registerPush(Context context) {
        PushManager.getInstance().initialize(context, null);
        PushManager.getInstance().registerPushIntentService(context, GeTuiMessageIntentService.class);
    }

    @Override
    public void unRegisterPush(Context context) {
        PushManager.getInstance().stopService(context);
    }


    @Override
    public void setAlias(Context context, String alias) {
        PushManager.getInstance().bindAlias(context, alias);
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        PushManager.getInstance().unBindAlias(context, alias, false);
    }

    @Override
    public void setTags(Context context, String... tags) {
        Tag[] temps = new Tag[tags.length];
        for (int i = 0; i < tags.length; i++) {
            Tag tag = new Tag();
            tag.setName(tags[i]);
            temps[i] = tag;
        }
        PushManager.getInstance().setTag(context, temps, null);
    }

    @Override
    public void unsetTags(Context context, String... tags) {
        PushManager.getInstance().setTag(context, new Tag[0], null);
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
}
