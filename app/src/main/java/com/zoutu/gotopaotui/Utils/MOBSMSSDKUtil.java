package com.zoutu.gotopaotui.Utils;

import android.app.Activity;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.zoutu.gotolibrary.Utils.AppkeyAppIdContentUtils;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 *短信验证码工具类
 * 用的是mob
 * 官方地址http://www.mob.com/
 * http://www.jb51.net/article/84946.htm
 * Created by admin on 2017/2/27.
 */

public class MOBSMSSDKUtil {
/*
    private String AppKey = "1c90b28a30081";//1ba1c7a305068   1c90b28a30081
    private String APPSecret = "89e8480a2f7285290e9f3053064d7d77";//096f0187d03334152678d08a767e8ea7*/
    AppkeyAppIdContentUtils appkeyAppIdContentUtils = new AppkeyAppIdContentUtils();
    private String AppKey = appkeyAppIdContentUtils.MobSMSAppKey;
    private String APPSecret = appkeyAppIdContentUtils.MobSMSAPPSecret;
    private  final int CODE_ING = 1;   //已发送，倒计时
    private  final int CODE_REPEAT = 2;  //重新发送
    private  final int SMSDDK_HANDLER = 3;  //短信回调

    public boolean isSMSCompleteSuccess = false;
    private Activity activity;
    public MOBSMSSDKUtil(Activity activity){
        initSDK(activity);
        this.activity = activity;
    }

    //初始化SMSSDK
    private void initSDK(Activity activity) {
        SMSSDK.initSDK(activity, AppKey, APPSecret);
       /* EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                smsSDKResultComplete(event,result,data);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);*/
    }
    public void getVerificationCode(String tel){
        SMSSDK.getVerificationCode("86", tel);
    };
   /* public void smsSDKResultComplete(int event, int result, Object data){
        //回调完成
        if (result == SMSSDK.RESULT_COMPLETE)
        {
            //验证码验证成功
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
            {
               *//* Toast.makeText(activity, "验证成功", Toast.LENGTH_LONG).show();*//*
              *//*  if (check())//其他合法性的检测
                {

                    //user.setUserIdCard(userIdCard);
                    //user.setUserImage("");    //暂时为空
                    //注册->服务器

                }*//*
                isSMSCompleteSuccess = true;

            }
            //已发送验证码
            else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
            {
                *//*Toast.makeText(activity, "验证码已经发送", Toast.LENGTH_SHORT).show();*//*
                isSMSCompleteSuccess = false;
            } else
            {
                ((Throwable) data).printStackTrace();
                isSMSCompleteSuccess = false;
            }
        }if(result==SMSSDK.RESULT_ERROR)
        {
            try {
                Throwable throwable = (Throwable) data;
                throwable.printStackTrace();
                JSONObject object = new JSONObject(throwable.getMessage());
                String des = object.optString("detail");//错误描述
                int status = object.optInt("status");//错误代码
                isSMSCompleteSuccess = false;
                if (status > 0 && !TextUtils.isEmpty(des)) {
                    *//*Toast.makeText(activity, des, Toast.LENGTH_SHORT).show();*//*
                    return;
                }
            } catch (Exception e) {
                //do something
            }
        }
    }*/

    /*对验证码进行验证*/
    public void confirmVerifSubmit(String tel,String identify){
        SMSSDK.submitVerificationCode("86", tel, identify);//对验证码进行验证->回调函数
    }

}
