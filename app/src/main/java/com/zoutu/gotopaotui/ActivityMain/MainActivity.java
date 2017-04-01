package com.zoutu.gotopaotui.ActivityMain;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zoutu.gotolibrary.Bean.BaseBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
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
 * */
public class MainActivity extends Activity {




    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private MainActivityController mainActivityController;
    private double blat=0,blon=0;
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
        initBaidu();
/*        initViews();*/
    }
    private void initBaidu(){
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        //注册监听函数
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=3000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if(location != null) {
                //获取定位结果
                blat = location.getLatitude();
                blon = location.getLongitude();
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(MainActivity.this);
                XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
                String angelAnid = xcCacheManager.readCache(xcCacheManagerSavedName.angelAnid);
                if((angelAnid != null)&&(!angelAnid.isEmpty())){
                    System.out.println("aid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                  /*  Toast.makeText(MainActivity.this," blat:"+blat+" blon:"+blon+" addr"+location.getAddress()+location.getLocationDescribe(),Toast.LENGTH_SHORT).show();*/
                    AngleBaiduLocNetWorks angleBaiduLocNetWorks = new AngleBaiduLocNetWorks();
                    angleBaiduLocNetWorks.submitLocToNet(angelAnid,(float) blat,(float) blon, new Observer<BaseBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseBean baseBean) {
                        /*    Toast.makeText(MainActivity.this,"地图定位"+baseBean.getResult(),Toast.LENGTH_SHORT).show();*/
                        }
                    });
                }
            }
        }
    }





    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mLocationClient.unRegisterLocationListener(myListener);
    }

}
