package com.toppush.client.core;

import android.app.IntentService;
import android.content.Intent;


/**
 * 透传服务类
 */
public abstract class TopPushIntentService extends IntentService {

    public static final String TAG = "TopPushIntentService";

    public TopPushIntentService() {
        super("TopPushIntentService");
    }

    @Override
    public final void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            try {
                TopPushMessage message = (TopPushMessage) intent.getSerializableExtra(TopPushMessageReceiver.MESSAGE);
                if (TopPushMessageReceiver.RECEIVE_THROUGH_MESSAGE.equals(action)) {
                    onReceivePassThroughMessage(message);
                } else if (TopPushMessageReceiver.NOTIFICATION_ARRIVED.equals(action)) {
                    onNotificationMessageArrived(message);
                } else if (TopPushMessageReceiver.NOTIFICATION_CLICKED.equals(action)) {
                    onNotificationMessageClicked(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 透传
     */
    public abstract void onReceivePassThroughMessage(TopPushMessage message);

    /**
     * 通知栏消息点击
     */
    public abstract void onNotificationMessageClicked(TopPushMessage message);

    /**
     * 通知栏消息到达,
     * flyme6基于android6.0以上不再回调，
     * MIUI基于小米推送，在APP被杀死不会回调，
     * 在个推不会回调，所以不建议使用，
     */
    @Deprecated
    public void onNotificationMessageArrived(TopPushMessage message) {

    }
}