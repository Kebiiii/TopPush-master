package com.toppush.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.toppush.client.core.TopPushClient;
import com.toppush.client.core.TopPushMessage;
import com.toppush.client.core.TopPushMessageReceiver;
import com.toppush.client.getui.GeTuiManager;
import com.toppush.client.meizu.MeizuPushManager;
import com.toppush.client.mipush.MiPushManager;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        TextView text_use_push = (TextView) findViewById(R.id.text_use_push);
        text_use_push.setText("当前推送平台："+ TopPushClient.getUsePushName());
        IntentFilter filter = new IntentFilter();
        filter.addAction(TopPushMessageReceiver.RECEIVE_THROUGH_MESSAGE);
        filter.addAction(TopPushMessageReceiver.NOTIFICATION_ARRIVED);
        filter.addAction(TopPushMessageReceiver.NOTIFICATION_CLICKED);
        filter.addAction(TopPushMessageReceiver.ERROR);
        this.registerReceiver(mBroadcastReceiver, filter);

        findViewById(R.id.btn_switch_mipush).setOnClickListener(this);
        findViewById(R.id.btn_switch_meizu).setOnClickListener(this);
        findViewById(R.id.btn_switch_getui).setOnClickListener(this);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_close).setOnClickListener(this);
        findViewById(R.id.btn_set_alias).setOnClickListener(this);
        findViewById(R.id.btn_set_tags).setOnClickListener(this);


        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }
            @Override
            public void log(String content, Throwable t) {
                Log.d("mipush", content, t);
            }
            @Override
            public void log(String content) {
                Log.d("mipush", content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    private BroadcastReceiver mBroadcastReceiver = new TopPushMessageReceiver() {
        @Override
        public void onReceivePassThroughMessage(Context context, TopPushMessage message) {
            text.setText(text.getText().toString() + "\n收到透传消息:Platform:" + message.getPlatform());
            text.setText(text.getText().toString() + "\n收到透传消息:Content:" + message.getContent()+",title:"+message.getTitle());
        }

        @Override
        public void onNotificationMessageClicked(Context context, TopPushMessage message) {
            text.setText(text.getText().toString() + "\n通知栏消息点击:" + message.getPlatform());
            text.setText(text.getText().toString() + "\n通知栏消息点击:" + message.getContent());
        }

        @Override
        public void onNotificationMessageArrived(Context context, TopPushMessage message) {
            text.setText(text.getText().toString() + "\n通知栏消息到达:" + message.getPlatform());
            text.setText(text.getText().toString() + "\n通知栏消息到达:" + message.getContent());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch_mipush:
                DemoApplication.setUsePushName(this,MiPushManager.NAME);
                break;
            case R.id.btn_switch_meizu:
                DemoApplication.setUsePushName(this,MeizuPushManager.NAME);
                break;
            case R.id.btn_switch_getui:
                DemoApplication.setUsePushName(this,GeTuiManager.NAME);
                break;
            case R.id.btn_start:
                TopPushClient.registerPush(getApplicationContext());
                break;
            case R.id.btn_close:
                TopPushClient.unRegisterPush(getApplicationContext());
                break;
            case R.id.btn_set_alias:
                TopPushClient.setAlias(getApplicationContext(), "103");
                break;
            case R.id.btn_set_tags:
                TopPushClient.setTags(getApplicationContext(), "广东");
                break;
        }
    }
}
