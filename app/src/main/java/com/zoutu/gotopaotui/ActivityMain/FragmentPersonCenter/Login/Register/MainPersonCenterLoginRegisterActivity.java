package com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter.Login.Register;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zoutu.gotolibrary.Utils.PhoneFormatCheckUtils;
import com.zoutu.gotopaotui.R;
import com.zoutu.gotopaotui.Utils.MOBSMSSDKUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/3/29.
 */

public class MainPersonCenterLoginRegisterActivity extends Activity {
    /*验证码秒数*/
    int second = 0;
    /*验证码秒数*/

    @BindView(R.id.et_main_personcenter_login_reg_username)
    EditText etMainPersonCenterLoginRegUserName;
    @BindView(R.id.et_main_personcenter_login_reg_pass)
    EditText etMainPersonCenterLoginRegPass;
    @BindView(R.id.et_main_personcenter_login_reg_identify)
    EditText etMainPersonCenterLoginRegIdentify;









    /*获取验证码*/
    @BindView(R.id.bt_main_personcenter_login_reg_getidentify)
    Button btMainPersonCenterLoginRegGetIdentify;
    @OnClick(R.id.bt_main_personcenter_login_reg_getidentify)
    public void btMainPersonCenterLoginRegGetIdentifyOnclick(){
        getIdentifyOnclick();
    }
    /*获取验证码*/
    /*注册信息提交*/
    @BindView(R.id.bt_main_personcenter_login_reg_submit)
    Button btMainPersonCenterLoginRegSubmit;
    @OnClick(R.id.bt_main_personcenter_login_reg_submit)
    public void btMainPersonCenterLoginRegSubmitOnclick(){
        regSubmitOnclick();
    }
    /*注册信息提交*/
    private MainPersonCenterLoginRegisterActivityController mainPersonCenterLoginRegisterActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_personcenter_login_register_lly);
        init();
        // Example of a call to a native method

    }

    private void init(){
        ButterKnife.bind(this);

        mainPersonCenterLoginRegisterActivityController= new MainPersonCenterLoginRegisterActivityController(this);

    }

    private void regSubmitOnclick(){
        if(isPhoneNum()) {
            String tel = etMainPersonCenterLoginRegUserName.getText().toString();
            String identity = etMainPersonCenterLoginRegIdentify.getText().toString();
            if(identity.isEmpty()){
                Toast.makeText(this,"请输入验证码",Toast.LENGTH_LONG).show();
            }
            try {
              /* mobsmssdkUtil.confirmVerifSubmit(tel, identity);*/
                mainPersonCenterLoginRegisterActivityController.mobsmssdkUtil.confirmVerifSubmit(tel, identity);
            }catch (Exception e){

            }
        }
    }
    private void getIdentifyOnclick(){

        if(isPhoneNum()){
             /*第一次点击倒计时*/
            if(second == 0){
                second = 60;
                beginTimeing();
            }else{
                Toast.makeText(this,"亲，验证码已发送，请耐心等待。。",Toast.LENGTH_LONG).show();
            }
        /*第一次点击倒计时*/
        }else{
            Toast.makeText(this,"亲，请输入正确的手机号码。。",Toast.LENGTH_LONG).show();
        }
    }
    /*判断是否正确输入手机号码*/
    private boolean isPhoneNum(){
        String tel = etMainPersonCenterLoginRegUserName.getText().toString();
        if(tel != null) {
            PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
            return phoneFormatCheckUtils.isPhoneLegal(tel);
        }else {
            return false;
        }
    }
    /*判断是否正确输入手机号码*/
    public void beginTimeing(){
        String tel = etMainPersonCenterLoginRegUserName.getText().toString();
        /*mobsmssdkUtil.getVerificationCode(tel);*/
        mainPersonCenterLoginRegisterActivityController.mobsmssdkUtil.getVerificationCode(tel);
        ThreadShow threadShow = new ThreadShow();
        Thread thread = new Thread(threadShow);
       /* thread.run();*/
        thread.start();
    }


    /*多线程开始倒计时*/
    // 线程类
    class ThreadShow implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (second > 0) {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    System.out.println("send...");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("thread error...");
                }
            }
        }
    }
    // handler类接收数据
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if(second > 0) {
                    btMainPersonCenterLoginRegGetIdentify.setText(Integer.toString(second));
                    second--;
                }else{
                    btMainPersonCenterLoginRegGetIdentify.setText("");
                    btMainPersonCenterLoginRegGetIdentify.setHint("获取验证码");
                }
                System.out.println("receive...."+second);
            }
        };
    };
    /*多线程开始倒计时*/


    protected void onDestroy(){
        super.onDestroy();

        mainPersonCenterLoginRegisterActivityController.onDestroy();
    }
}
