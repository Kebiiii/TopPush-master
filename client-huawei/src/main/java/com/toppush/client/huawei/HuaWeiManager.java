package com.toppush.client.huawei;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.huawei.hms.support.api.push.PushException;
import com.huawei.hms.support.api.push.TokenResult;
import com.toppush.client.core.SPUtils;
import com.toppush.client.core.TopPushManager;
import com.toppush.client.core.TopPushMessageProvider;

/**
 * Created by liuke on 2017/10/26.
 */
public class HuaWeiManager implements TopPushManager {
    private static final String TAG = "HuaWeiManager";
    public static final String NAME = "huawei";
    public static final String HUAWEI_TOKEN = "huawei_token";
    private HuaweiApiClient client;
    public static TopPushMessageProvider sTopPushMessageProvider;
    @Override
    public void registerPush(Context context) {
        client = new HuaweiApiClient.Builder(context)
                .addApi(HuaweiPush.PUSH_API)
                .addConnectionCallbacks(new HuaweiApiClient.ConnectionCallbacks(){

                    @Override
                    public void onConnected() {
                        Log.i(TAG, "HuaweiApiClient 连接成功");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.i(TAG, "HuaweiApiClient 连接断开");
                    }
                })
                .addOnConnectionFailedListener(new HuaweiApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.i(TAG, "HuaweiApiClient连接失败，错误码：" + connectionResult.getErrorCode());
//                        if(HuaweiApiAvailability.getInstance().isUserResolvableError(arg0.getErrorCode())) {
//                            final int errorCode = arg0.getErrorCode();
//                            new Handler(getMainLooper()).post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // 此方法必须在主线程调用, xxxxxx.this 为当前界面的activity
//                                    HuaweiApiAvailability.getInstance().resolveError(xxxxxx.this, errorCode, REQUEST_HMS_RESOLVE_ERROR);
//                                }
//                            });
//                        } else {
//                            //其他错误码请参见开发指南或者API文档
//                        }
                    }
                })
                .build();
        client.connect();
    }

    @Override
    public void unRegisterPush(Context context) {
        if (client != null) {
            client.disconnect();
        }
    }

    @Override
    public void setAlias(Context context, String alias) {
        //to do nothing 华为不支持别名
        getTokenSync();
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        //to do nothing 华为不支持别名
        deleteToken(context);
    }

    @Override
    public void setTags(Context context, String... tags) {

    }

    @Override
    public void unsetTags(Context context, String... tags) {

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

    /**
     * 调用getToken接口-同步方式
     */
    private void getTokenSync() {
        if(!client.isConnected()) {
            Log.i(TAG, "获取token失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }

        //需要在子线程中调用函数
        new Thread() {
            public void run() {
                Log.i(TAG, "同步接口获取push token");
                PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(client);
                TokenResult result = tokenResult.await();
            };
        }.start();
    }

    /**
     * 调用getToken接口-异步方式
     */
    private void getTokenAsyn() {
        if(!client.isConnected()) {
            Log.i(TAG, "获取token失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }

        Log.i(TAG, "异步接口获取push token");
        PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(client);
        tokenResult.setResultCallback(new ResultCallback<TokenResult>() {
            @Override
            public void onResult(TokenResult result) {
            }
        });
    }

    private void deleteToken(Context context) {
        final String token = SPUtils.getString(HUAWEI_TOKEN, context);
        if ("".equals(token)) {
            Log.i(TAG, "注销TOKEN失败，原因：暂无token");
            return;
        }
        if(!client.isConnected()) {
            Log.i(TAG, "注销TOKEN失败，原因：HuaweiApiClient未连接");
            client.connect();
            return;
        }

        SPUtils.put(HUAWEI_TOKEN, "", context);
        //需要在子线程中执行删除TOKEN操作
        new Thread() {
            public void run() {
                //调用删除TOKEN需要传入通过getToken接口获取到TOKEN，并且需要对TOKEN进行非空判断
                Log.i(TAG, "删除TOKEN：" + token);
                if (!TextUtils.isEmpty(token)){
                    try {
                        HuaweiPush.HuaweiPushApi.deleteToken(client, token);
                    } catch (PushException e) {
                        Log.i(TAG, "删除TOKEN失败:" + e.getMessage());
                    }
                }
            }
        }.start();
    }
}
