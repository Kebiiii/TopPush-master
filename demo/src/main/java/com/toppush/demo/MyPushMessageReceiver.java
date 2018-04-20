package com.toppush.demo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.toppush.client.core.TopPushMessage;
import com.toppush.client.core.TopPushMessageReceiver;

/**
 * 需要定义一个receiver 或 Service 来接收透传和通知栏点击的信息，建议使用Service，更加简单
 * Created by Wiki on 2017/6/1.
 */

public class MyPushMessageReceiver extends TopPushMessageReceiver {

    private static final String TAG = "MyPushMessageReceiver";

    @Override
    public void onReceivePassThroughMessage(Context context, TopPushMessage message) {
        Log.d(TAG,"收到透传消息 -> "+message.getContent()+",title="+message.getTitle());
        Toast.makeText(context, "收到透传消息 -> "+message.getContent(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotificationMessageClicked(Context context, TopPushMessage message) {
        Log.d(TAG,"通知栏消息点击 -> "+message.getContent());
        Toast.makeText(context, "通知栏消息点击 -> "+message.getContent(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotificationMessageArrived(Context context, TopPushMessage message) {
        Log.d(TAG,"通知栏消息到达 -> "+message.getContent());
        Toast.makeText(context, "通知栏消息到达 -> "+message.getContent(), Toast.LENGTH_SHORT).show();
    }
}
