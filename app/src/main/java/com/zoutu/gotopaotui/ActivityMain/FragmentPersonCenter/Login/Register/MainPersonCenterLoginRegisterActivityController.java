package com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter.Login.Register;

import android.app.Activity;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zoutu.gotolibrary.Bean.BaseBean;
import com.zoutu.gotopaotui.ActivityMain.BaseController;
import com.zoutu.gotopaotui.NetWork.AngleUserSettingNetWorks;
import com.zoutu.gotopaotui.R;
import com.zoutu.gotopaotui.Utils.MOBSMSSDKUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Observer;

import static android.os.Looper.getMainLooper;

/**
 * Created by admin on 2017/3/29.
 */

public class MainPersonCenterLoginRegisterActivityController extends BaseController {

    @BindView(R.id.rly_main_personcenter_login_register_topbar_back)
    RelativeLayout rlyMainPersonCenterLoginRegisterTopBarBack;
    @OnClick(R.id.rly_main_personcenter_login_register_topbar_back)
    public void rlyMainPersonCenterLoginRegisterTopBarBackOnclick(){
        activity.finish();
    }



    @BindView(R.id.et_main_personcenter_login_reg_username)
    EditText etMainPersonCenterLoginRegUserName;
    @BindView(R.id.et_main_personcenter_login_reg_pass)
    EditText etMainPersonCenterLoginRegPass;
    @BindView(R.id.et_main_personcenter_login_reg_identify)
    EditText etMainPersonCenterLoginRegIdentify;



    /*验证码秒数*/
    int second = 0;
    /*验证码秒数*/



    MOBSMSSDKUtil mobsmssdkUtil ;

    public MainPersonCenterLoginRegisterActivityController(Activity activity1){
        activity = activity1;
        init();
    }
    @Override
    public void init() {
        ButterKnife.bind(this,activity);
        mobsmssdkUtil = new MOBSMSSDKUtil(activity);
        mobSMSRegister();
    }


    private void mobSMSRegister(){

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                smsSDKResultComplete(event,result,data);
            }
        };

        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }
    public void smsSDKResultComplete(int event, int result, Object data){
        //回调完成
        if (result == SMSSDK.RESULT_COMPLETE)
        {
            //验证码验证成功
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
            {
                regSubmit();

            }
            //已发送验证码
            else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
            {
                /*Toast.makeText(activity, "验证码已经发送", Toast.LENGTH_SHORT).show();*/

            } else
            {
                ((Throwable) data).printStackTrace();

            }
        }if(result==SMSSDK.RESULT_ERROR)
        {
            /*Toast.makeText(activity, "验证码输入错误", Toast.LENGTH_LONG).show();*/
            try {
                Throwable throwable = (Throwable) data;
                throwable.printStackTrace();
                JSONObject object = new JSONObject(throwable.getMessage());
                String des = object.optString("detail");//错误描述
                int status = object.optInt("status");//错误代码
                if (status > 0 && !TextUtils.isEmpty(des)) {
                    /*Toast.makeText(activity, des, Toast.LENGTH_LONG).show();*/
                    return;
                }

            } catch (Exception e) {
                //do something
            }
        }
    }
    /*注册信息*/
    private void regSubmit(){
        String tel = etMainPersonCenterLoginRegUserName.getText().toString();
        String pass = etMainPersonCenterLoginRegPass.getText().toString();
        AngleUserSettingNetWorks angleUserSettingNetWorks = new AngleUserSettingNetWorks();
        angleUserSettingNetWorks.angleRegToNet(tel, pass, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                Toast.makeText(activity,""+baseBean.getResult(), Toast.LENGTH_LONG).show();
                activity.finish();
            }
        });
    }
    /*注册信息*/



    public void onDestroy(){

        SMSSDK.unregisterAllEventHandler();


    }
}
