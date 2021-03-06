package com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoutu.gotolibrary.Bean.BaseBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.Dialog.AlertView.AlertView;
import com.zoutu.gotolibrary.Dialog.AlertView.OnItemClickListener;
import com.zoutu.gotolibrary.ImageView.RoundImageView;
import com.zoutu.gotolibrary.RecycleView.XRecycleView.XRecyclerView;
import com.zoutu.gotolibrary.Utils.PhoneFormatCheckUtils;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.ActivityMain.BaseController;
import com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.MainOrderCenterFragmentAllOrderRecycleViewAdapter;
import com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter.Login.MainPersonCenterLoginActivity;
import com.zoutu.gotopaotui.NetWork.AngleUserSettingNetWorks;
import com.zoutu.gotopaotui.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import android.graphics.Bitmap.Config;
/**
 * Created by admin on 2017/3/29.
 */

public class MainPersonCenterFragmentController extends BaseController {



    @BindView(R.id.rly_main_personcenter_topbar_exit)
    RelativeLayout rlyMainPersonCenterTopbarExit;
    @OnClick(R.id.rly_main_personcenter_topbar_exit)
    public void rlyMainPersonCenterExitOnclick(){
        loginOut();
    }
    @BindView(R.id.tv_main_personcenter_username)
    TextView tvMainPersonCenterUserName;




    public MainPersonCenterFragmentController(View view1){
        view = view1;
        init();

    }
    @Override
    public void init() {
        ButterKnife.bind(this,view);

    }

    private void loginOut(){
        XCCacheManager  xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
        String isRememberMyLogin = xcCacheManager.readCache(xcCacheManagerSavedName.isRememberMyLogin);
        if(isRememberMyLogin != null){
            if(isRememberMyLogin.equals("yes")){
                xcCacheManager.writeCache(xcCacheManagerSavedName.loginStatue,"no");
                xcCacheManager.writeCache(xcCacheManagerSavedName.angelAnid,"");
            }else{
                xcCacheManager.writeCache(xcCacheManagerSavedName.loginStatue,"no");
                xcCacheManager.writeCache(xcCacheManagerSavedName.angelAnid,"");
                xcCacheManager.writeCache(xcCacheManagerSavedName.userName,"");

            }
        }else{
            xcCacheManager.writeCache(xcCacheManagerSavedName.loginStatue,"no");
            xcCacheManager.writeCache(xcCacheManagerSavedName.isRememberMyLogin,"no");
            xcCacheManager.writeCache(xcCacheManagerSavedName.angelAnid,"");
            xcCacheManager.writeCache(xcCacheManagerSavedName.userName,"");
        }

        AngleUserSettingNetWorks angleUserSettingNetWorks = new AngleUserSettingNetWorks();
        angleUserSettingNetWorks.angleExit(new Observer<BaseBean>() {
            @Override
            public void onCompleted() {
                /*Toast.makeText(view.getContext(),"onCompleted",Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(view.getContext(),"onError:"+e,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(BaseBean baseBean) {
                Toast.makeText(view.getContext(),baseBean.getResult(),Toast.LENGTH_SHORT).show();
                tvMainPersonCenterUserName.setText("登录");

            }
        });

    }









    public void onResume(){
        XCCacheManager  xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
         /*判断是否为登录状态 若不是 前往登录界面 or选择头像*/
        String loginStatus = xcCacheManager.readCache(xcCacheManagerSavedName.loginStatue);
        if(loginStatus != null){
            if(loginStatus.equals("yes")){
                String userName = xcCacheManager.readCache(xcCacheManagerSavedName.userName);
                if(userName != null){
                    PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
                    userName = phoneFormatCheckUtils.telReplaceMiddleByStar(userName);
                    tvMainPersonCenterUserName.setText(userName);
                }
            }
        }
    }

}
