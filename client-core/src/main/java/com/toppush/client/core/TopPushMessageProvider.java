package com.toppush.client.core;

import android.content.Context;

/**
 * Created by Wiki on 2017/6/1.
 */

public interface TopPushMessageProvider {
    /**
     * 透传
     */
    public void onReceivePassThroughMessage(Context context, TopPushMessage message);

    /**
     * 通知栏消息点击
     */
    public void onNotificationMessageClicked(Context context, TopPushMessage message);

    /**
     * 通知栏消息到达
     */
    public void onNotificationMessageArrived(Context context, TopPushMessage message);

//    public void onError(Context context, TopPushMessage message);
}
