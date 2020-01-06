package com.bw.movie.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.bw.movie.utils.DataCleanManager;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.liys.doubleclicklibrary.ViewDoubleHelper;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;

public class App extends Application {
    //全局上下文
    private static App sContext;
    public static final String APP_ID = "wxb3852e6a6b7d9516";

    // IWXAPI 是第三方app和微信通信的openApi接口
    public static IWXAPI api;
    public static SharedPreferences sharedPreferences;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initXGPush();
        sContext = this;
        Fresco.initialize(this, ImagePipelineConfig.newBuilder(this)
                //图片缓存路径
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(this)
                        .setBaseDirectoryPath(new File(Environment.getExternalStorageDirectory().getAbsolutePath()))
                        .setMaxCacheSize(10*1024*1024)
                        .build())
                .build());


            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            api = WXAPIFactory.createWXAPI(this, APP_ID, true);

            // 将应用的appId注册到微信
            api.registerApp(APP_ID);

            //建议动态监听微信启动广播进行注册到微信
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    // 将该app注册到微信
                    api.registerApp(sContext.APP_ID);
                }
            }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
        ViewDoubleHelper.init(this); //默认时间：1秒
        try {
            DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File externalCacheDir = sContext.getExternalCacheDir();
        try {
            DataCleanManager.getFolderSize(externalCacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sharedPreferences = sContext.getSharedPreferences("yonghu", Context.MODE_PRIVATE);


    }

    public static App getAppContext() {
        return sContext;
    }
    private void initXGPush(){
        XGPushConfig.enableDebug(this,true);
        XGPushManager.registerPush(this, new XGIOperateCallback(){
            @Override
            public void onSuccess(Object data, int flag){
                //token在设备卸载重装的时候有可能会变
                Log.e("TPush","注册成功，设备token为："+ data);
                setToken(data.toString());
            }
            @Override
            public void onFail(Object data, int errCode,String msg) {
                Log.e("TPush","注册失败，错误码："+ errCode +",错误信息："+ msg);
            }
        });

//设置账号
        XGPushManager.registerPush(getApplicationContext(),"XINGE");
// 设置标签
        XGPushManager.setTag(this,"XINGE");
    }
}
