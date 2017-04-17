package com.zoutu.gotopaotui.ActivityMain;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zoutu.gotolibrary.Bean.BaseBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.NetWork.AngleBaiduLocNetWorks;

import rx.Observer;

/**
 * Created by admin on 2017/4/5.
 */

public class LocMapService extends Service{
    public  final String TAG = "MyService";
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private double blat=0,blon=0;
    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        initBaidu();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 开始执行后台任务

            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
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
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);// 设置定位模式
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
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
                String angelAnid = xcCacheManager.readCache(xcCacheManagerSavedName.angelAnid);
                xcCacheManager.writeCache(xcCacheManagerSavedName.selfLocLat,""+blat);
                xcCacheManager.writeCache(xcCacheManagerSavedName.selfLocLon,""+blon);
                xcCacheManager.writeCache(xcCacheManagerSavedName.selfLocAddr,""+location.getAddrStr()+" "+location.getLocationDescribe());
                if((angelAnid != null)&&(!angelAnid.isEmpty())){
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
                    System.out.println("\naid:"+angelAnid+" blat:"+blat+" blon:"+blon);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
        mLocationClient.unRegisterLocationListener(myListener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder {

        public void startDownload() {
            Log.d("TAG", "startDownload() executed");
            // 执行具体的下载任务
        }

    }

}
