package com.toppush.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;

import com.toppush.client.core.TopPushClient;
import com.toppush.client.core.TopPushManager;
import com.toppush.client.getui.GeTuiManager;
import com.toppush.client.huawei.HuaWeiManager;
import com.toppush.client.meizu.MeizuPushManager;
import com.toppush.client.mipush.MiPushManager;

import java.util.Map;

public class DemoApplication extends Application {
    public static final String MEIZU_APP_ID = "111519";
    public static final String MEIZU_APP_KEY = "004f87a6c9694134ade8f9d5a61a2950";
    public static final String MIPUSH_APP_ID = "2882303761517630293";
    public static final String MIPUSH_APP_KEY = "5271763038293";

    private static final String mainProcessName = "com.toppush.demo";
    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getCurProcessName(this);
        if (mainProcessName.equals(processName)) {
            Log.e("DemoApplication", "processName="+processName);
            initPush();
        }
    }
    private void initPush() {
        TopPushClient.addPushManager(new MeizuPushManager(this));
        TopPushClient.addPushManager(new MiPushManager(this));
        TopPushClient.addPushManager(new GeTuiManager());
        TopPushClient.addPushManager(new HuaWeiManager());
        TopPushClient.setPushIntentService(PushIntentService.class);
        TopPushClient.setSelector(new TopPushClient.Selector() {
            @Override
            public String select(Map<String, TopPushManager> pushAdapterMap, String brand) {
                // return GeTuiManager.NAME;
                //底层已经做了小米推送、魅族推送、个推判断，也可以按照自己的需求来选择推送
                return super.select(pushAdapterMap, brand);
            }
        });
        // 配置接收推送消息的服务类
        TopPushClient.setPushIntentService(PushIntentService.class);

        // start - 下面代码在正式使用不用设置，这里仅仅用于测试
        String usePushName = getUsePushName(this);
        if (usePushName != null) {
            TopPushClient.setUsePushName(usePushName);
        }
        // - end


        // 注册推送
        TopPushClient.registerPush(this);
        // 绑定别名，一般是填写用户的ID，便于定向推送
        TopPushClient.setAlias(this, getUserId());
        // 设置标签
        TopPushClient.setTags(this,"广东");
    }
    private String getUserId(){
        return "103";
    }


    public static String getUsePushName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("usePushName", null);
    }

    public static void setUsePushName(Context context, String usePushName) {
        TopPushClient.unsetAlias(context,"103");
        TopPushClient.unRegisterPush(context);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString("usePushName", usePushName).commit();
//        throw new RuntimeException();
        Process.killProcess(Process.myPid());
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }
}
