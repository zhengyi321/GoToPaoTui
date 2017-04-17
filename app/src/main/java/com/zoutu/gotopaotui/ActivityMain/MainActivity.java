package com.zoutu.gotopaotui.ActivityMain;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zoutu.gotolibrary.Bean.BaseBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.ImmersionBar.SystemBarTintManager;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotolibrary.ViewPage.NoScrollViewPager;
import com.zoutu.gotopaotui.ActivityMain.FragmentGetOrder.MainGetOrderFragment;
import com.zoutu.gotopaotui.ActivityMain.FragmentIndex.MainIndexFragment;
import com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.MainOrderCenterFragment;
import com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter.MainPersonCenterFragment;
import com.zoutu.gotopaotui.NetWork.AngleBaiduLocNetWorks;
import com.zoutu.gotopaotui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * http://blog.csdn.net/jxq1994/article/details/52573506
 *
 * https://github.com/lguipeng/AndroidPractice  沉浸式状态栏
 * */
public class MainActivity extends Activity {





    private MainActivityController mainActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lly);
        init();
    // Example of a call to a native method  activity_main_lly  test_lly

    }
    private void init(){
        ButterKnife.bind(this);
        mainActivityController = new MainActivityController(this);
        startLocMapService();

/*        initViews();*/
    }
    private void startLocMapService(){
        Intent intent = new Intent(this,LocMapService.class);
        startService(intent);
    }





    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
/*    static {
        System.loadLibrary("native-lib");
    }*/




    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

}
