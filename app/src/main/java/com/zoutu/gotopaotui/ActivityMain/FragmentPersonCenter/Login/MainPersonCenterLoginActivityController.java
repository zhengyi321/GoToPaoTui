package com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter.Login;

import android.app.Activity;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoutu.gotolibrary.Bean.AngleBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.ActivityMain.BaseController;
import com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter.Login.Register.MainPersonCenterLoginRegisterActivity;
import com.zoutu.gotopaotui.NetWork.AngleUserSettingNetWorks;
import com.zoutu.gotopaotui.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * Created by admin on 2017/3/29.
 */

public class MainPersonCenterLoginActivityController extends BaseController{


    @BindView(R.id.rly_main_personcenter_login_topbar_back)
    RelativeLayout rlyMainPersonCenterLoginTopBarBack;
    @OnClick(R.id.rly_main_personcenter_login_topbar_back)
    public void rlyMainPersonCenterLoginTopBarBackOnclick(){
        activity.finish();
    }

    @BindView(R.id.tv_main_personcenter_login_register)
    TextView tvMainPersonCenterLoginRegister;
    @OnClick(R.id.tv_main_personcenter_login_register)
    public void tvMainPersonCenterLoginRegisterOnclick(){
        Intent intent = new Intent(activity, MainPersonCenterLoginRegisterActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.et_main_personcenter_login_username)
    EditText etMainPersonCenterLoginUserName;
    @BindView(R.id.et_main_personcenter_login_pass)
    EditText etMainPersonCenterLoginPass;
    @BindView(R.id.cb_main_personcenter_login)
    CheckBox cbMainPersonCenterLogin;
    @BindView(R.id.ib_main_personcenter_login_submit)
    ImageButton ibMainPersonCenterLoginSubmit;
    @OnClick(R.id.ib_main_personcenter_login_submit)
    public void ibMainPersonCenterLoginSubmitOnclick(){
        loginSubmit();
    }

    private XCCacheManager mCacheManager;
    private XCCacheManagerSavedName xcCacheManagerSavedName;
    public MainPersonCenterLoginActivityController(Activity activity1){
        activity = activity1;
        init();
    }
    @Override
    public void init() {
        ButterKnife.bind(this,activity);
        mCacheManager = XCCacheManager.getInstance(activity);
        xcCacheManagerSavedName = new XCCacheManagerSavedName();
        initLoginData();
    }

    private void initLoginData(){
        String isRememberMyLogin = mCacheManager.readCache(xcCacheManagerSavedName.isRememberMyLogin);
        if(isRememberMyLogin != null){
            if(isRememberMyLogin.equals("yes")){
                cbMainPersonCenterLogin.setChecked(true);
                String userName = mCacheManager.readCache(xcCacheManagerSavedName.userName);
                if(userName != null){
                    etMainPersonCenterLoginUserName.setText(userName);
                }
            }else{
                cbMainPersonCenterLogin.setChecked(false);
            }
        }
    }

       /*登录提交*/

    private void loginSubmit(){

        String angleName = etMainPersonCenterLoginUserName.getText().toString();
        String anglePass = etMainPersonCenterLoginPass.getText().toString();
        AngleUserSettingNetWorks angleUserSettingNetWorks = new AngleUserSettingNetWorks();
        angleUserSettingNetWorks.angleLoginToNet(angleName, anglePass, new Observer<AngleBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AngleBean angleBean) {
                if(angleBean != null){
                    XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
                    mCacheManager.writeCache(xcCacheManagerSavedName.userName,etMainPersonCenterLoginUserName.getText().toString());
                    mCacheManager.writeCache(xcCacheManagerSavedName.angelAnid,angleBean.getAngelAnid());
                    mCacheManager.writeCache(xcCacheManagerSavedName.loginStatue,"yes");
                    if(cbMainPersonCenterLogin.isChecked()) {
                        mCacheManager.writeCache(xcCacheManagerSavedName.isRememberMyLogin,"yes" );
                    }else{
                        mCacheManager.writeCache(xcCacheManagerSavedName.isRememberMyLogin,"no" );
                    }
                    Toast.makeText(activity,angleBean.getResult(),Toast.LENGTH_SHORT).show();
                    activity.finish();
                }

            }
        });

    }


    /*正常登录*/
}
