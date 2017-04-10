package com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.OrderDetail.CheckOrderDetailMap;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.ActivityMain.BaseController;
import com.zoutu.gotopaotui.BaiDuMap.OverlayUtil.BikingRouteOverlay;
import com.zoutu.gotopaotui.BaiDuMap.OverlayUtil.OverlayManager;
import com.zoutu.gotopaotui.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/4/10.
 */

public class MainOrderCenterOrderDetailCheckOrderDetailMapActivityController extends BaseController implements OnGetRoutePlanResultListener,OnGetGeoCoderResultListener {

    // 浏览路线节点相关
    private Double selfLat=0.0,selfLon=0.0;
    private String selfAddr="";
    /*百度地图*/
    private  final int accuracyCircleFillColor = 0xAAFFFF88;
    private  final int accuracyCircleStrokeColor = 0xAA00FF00;
    private String beginAddr,endAddr;
    private GeoCoder search=null;
    private boolean isBegin = false;
    private LatLng beginLLg,endLLg;
    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    RouteLine route = null;
    MassTransitRouteLine massroute = null;
    MassTransitRouteResult nowResultmass = null;
    private TextView popupText = null; // 泡泡view
    private MyLocationConfiguration.LocationMode mCurrentMode;
    @BindView(R.id.rly_main_ordercenter_orderdetail_checkdetail_baidummap_topbar_back)
    RelativeLayout rlyMainOrderCenterOrderDetailCheckDetailBaiDuMapTopBarBack;
    @OnClick(R.id.rly_main_ordercenter_orderdetail_checkdetail_baidummap_topbar_back)
    public void rlyMainOrderCenterOrderDetailCheckDetailBaiDuMapTopBarBackOnclick(){
        activity.finish();

    }
    @BindView(R.id.mv_main_ordercenter_orderdetail_checkdetail_baidumap)
    MapView mvMainOrderCenterOrderDetailCheckDetailBaiDuMap;
    @BindView(R.id.ib_main_ordercenter_orderdetail_checkdetail_baidumap_locself)
    ImageButton ibMainOrderCenterOrderDetailCheckDetailBaiDuMapLocSelf;
    @OnClick(R.id.ib_main_ordercenter_orderdetail_checkdetail_baidumap_locself)
    public void ibMainOrderCenterOrderDetailCheckDetailBaiDuMapLocSelfOnclick(){
        if((selfLat != null)&&(selfLon != null)) {
            location(new LatLng(selfLat, selfLon));
        }
    }
    boolean useDefaultIcon = false;
    private BaiduMap mBaidumap;
    OverlayManager routeOverlay = null;
    // 搜索相关
    RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
    int nowSearchType = -1 ; // 当前进行的检索，供判断浏览节点时结果使用。

    public MainOrderCenterOrderDetailCheckOrderDetailMapActivityController(Activity activity1){
        activity = activity1;
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this,activity);
        mBaidumap = mvMainOrderCenterOrderDetailCheckDetailBaiDuMap.getMap();
        mvMainOrderCenterOrderDetailCheckDetailBaiDuMap.showZoomControls(false);
        initRouteOverLay();
        initGeoSearch();
        initSelfLocMark();
        beginGetAddressToSearchLLg();
        showSelfCurrentPos();

       /* searchProcess();*/
    }
    private void initGeoSearch(){
        search= GeoCoder.newInstance();
        /**根据经纬度得到屏幕中心点地址**/
        search.setOnGetGeoCodeResultListener(this);
    }
    private void initRouteOverLay(){
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
    }
    /*开始搜索骑行路径*/
    private void searchProcess(){
        /*Toast.makeText(activity,"searchProcess: 1",Toast.LENGTH_LONG).show();*/
        PlanNode stNode,enNode;
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
        String beginAddr = xcCacheManager.readCache(xcCacheManagerSavedName.orderDetailBeginAddr).trim();
        String endAddr =xcCacheManager.readCache(xcCacheManagerSavedName.orderDetailEndAddr).trim();
        int beginBlankIndex = beginAddr.indexOf(" ");
        if(beginBlankIndex >= 0){
            beginAddr = beginAddr.substring(0,beginBlankIndex+1);
        }
        int endBlankIndex = endAddr.trim().indexOf(" ");
        if(endBlankIndex >= 0){
            endAddr = endAddr.substring(0,endBlankIndex+1);
        }
        int beginCityIndex = beginAddr.indexOf("市");
        if(beginCityIndex > 0){

            String city = beginAddr.substring(0,beginCityIndex+1).trim();
            String addr = beginAddr.substring(beginCityIndex+1,beginAddr.length());
            stNode = PlanNode.withCityNameAndPlaceName(city, addr);
        }else{
             stNode = PlanNode.withCityNameAndPlaceName("中国浙江省温州市", beginAddr);
        }
        int endCityIndex = endAddr.indexOf("市");
        if(endCityIndex > 0){

            String city = endAddr.substring(0,endCityIndex+1).trim();
            String addr = endAddr.substring(endCityIndex+1,endAddr.length()).trim();
            enNode = PlanNode.withCityNameAndPlaceName(city, addr);
        }else{
             enNode = PlanNode.withCityNameAndPlaceName("中国浙江省温州市", endAddr);
        }
        // 设置起终点信息，对于tranist search 来说，城市名无意义
     /*   PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", startNodeStr);*/
        /*PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", endNodeStr);*/
        if((stNode != null)&&(enNode != null)) {
            /*Toast.makeText(activity,"searchProcess: 11",Toast.LENGTH_LONG).show();*/
          /* Toast.makeText(activity,"stNode:"+stNode+" addr:"+enNode,Toast.LENGTH_LONG).show();*/
            mSearch.bikingSearch((new BikingRoutePlanOption())
                    .from(stNode).to(enNode));
            nowSearchType = 4;
        }
        /*Toast.makeText(activity,"searchProcess: 12",Toast.LENGTH_LONG).show();*/
    }
    private void initSelfLocMark(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
        String selfLat1 = xcCacheManager.readCache(xcCacheManagerSavedName.selfLocLat);
        String selfLon1 = xcCacheManager.readCache(xcCacheManagerSavedName.selfLocLon);
        String selfAddr1 = xcCacheManager.readCache(xcCacheManagerSavedName.selfLocAddr);
        if ((selfLat1 != null) && (selfLon != null) && (selfAddr1 != null)) {
            selfLat = Double.parseDouble(selfLat1);
            selfLon = Double.parseDouble(selfLon1);
            selfAddr = selfAddr1;

        }

        TextView textView = new TextView(activity);
        Drawable drawable1 = activity.getResources().getDrawable(R.drawable.courier_logo);
        drawable1.setBounds(0, 0,40, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        textView.setCompoundDrawables(drawable1,null,null,null);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromView(textView);
        /*定位蓝色点*/
        MyLocationData locData = new MyLocationData.Builder()
               /* .accuracy(location.getRadius())*/
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(selfLat)
                .longitude(selfLon).build();
        mBaidumap.setMyLocationData(locData);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaidumap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
    }

    private void showSelfCurrentPos(){
        MyLocThread myLocThread = new MyLocThread();
        myLocThread.start();
    }
    class MyLocThread extends Thread{
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
        @Override
        public void run(){
            try {
                while(true) {
                    sleep(3000);
                    String selfLat1 = xcCacheManager.readCache(xcCacheManagerSavedName.selfLocLat);
                    String selfLon1 = xcCacheManager.readCache(xcCacheManagerSavedName.selfLocLon);
                    String selfAddr1 = xcCacheManager.readCache(xcCacheManagerSavedName.selfLocAddr);
                    if ((selfLat1 != null) && (selfLon != null) && (selfAddr1 != null)) {
                        selfLat = Double.parseDouble(selfLat1);
                        selfLon = Double.parseDouble(selfLon1);
                        selfAddr = selfAddr1;
                    }
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                    System.out.print("\nthis is thread:"+selfAddr1+" "+selfLat1+" "+selfLon1);
                }
            }catch (Exception e){

            }
        }
    }

    public void location(LatLng latLng){
        /*只要调用画面 就能赋值*/
        Toast.makeText(activity,"this is update1:"+latLng.latitude+" rlon:"+latLng.longitude,Toast.LENGTH_SHORT).show();
        /*无论哪个调用此动画 都将经纬度赋值*/
       /* mBaiduMap.clear();*/
        //定义地图状态
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(18.0f);
        mBaidumap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        /*Toast.makeText(activity,"this is update2",Toast.LENGTH_SHORT).show();*/
    }

    private void beginGetAddressToSearchLLg(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
         beginAddr = xcCacheManager.readCache(xcCacheManagerSavedName.orderDetailBeginAddr).trim();
         endAddr =xcCacheManager.readCache(xcCacheManagerSavedName.orderDetailEndAddr).trim();
        beginSearchLalByAddress(beginAddr);
        isBegin = true;


    }

    private void searchProcessByLLG(LatLng begLlg ,LatLng endLlg){
        PlanNode stNode,enNode;
        stNode = PlanNode.withLocation(begLlg);
        enNode = PlanNode.withLocation(endLlg);
        mSearch.bikingSearch((new BikingRoutePlanOption())
                .from(stNode).to(enNode));
    }

    /*根据地名开始查找经纬度*/
    public void beginSearchLalByAddress(String address){
       /* String address = etHelpMeBuyAddSellerAddressContentAddress.getText().toString();*/

        address = address.trim();
        int indexBlank = address.indexOf(" ");
        if(indexBlank > 0) {
            address = address.substring(0, indexBlank+1);
        }
        int index = address.indexOf("市");


        try {
            if (index > 0) {
                String city = address.substring(0, index+1);
                address = address.substring(index+1,address.length());
                search.geocode(new GeoCodeOption().city(city).address(address));
            } else {
                search.geocode(new GeoCodeOption().city("浙江省温州市").address(address));
            }
        }catch (Exception e){

        }
    }
    /*根据地名开始查找经纬度*/


    /**
     * 节点浏览示例
     *
     * @param v
     */
    public void nodeClick(View v) {
        LatLng nodeLocation = null;
        String nodeTitle = null;
        Object step = null;

        if ( nowSearchType != 0  && nowSearchType != -1) {
            // 非跨城综合交通
            if (route == null || route.getAllStep() == null) {
                return;
            }
            // 获取节结果信息
            step = route.getAllStep().get(nodeIndex);
            if (step instanceof DrivingRouteLine.DrivingStep) {
                nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
                nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
            } else if (step instanceof WalkingRouteLine.WalkingStep) {
                nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrance().getLocation();
                nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
            } else if (step instanceof TransitRouteLine.TransitStep) {
                nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
                nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
            } else if (step instanceof BikingRouteLine.BikingStep) {
                nodeLocation = ((BikingRouteLine.BikingStep) step).getEntrance().getLocation();
                nodeTitle = ((BikingRouteLine.BikingStep) step).getInstructions();
            }
        } else if ( nowSearchType == 0) {
            // 跨城综合交通  综合跨城公交的结果判断方式不一样


            if (massroute == null || massroute.getNewSteps() == null) {
                return;
            }

            boolean isSamecity = nowResultmass.getOrigin().getCityId() == nowResultmass.getDestination().getCityId();
            int size = 0;
            if ( isSamecity ) {
                size = massroute.getNewSteps().size();
            } else {
                for ( int i = 0; i < massroute.getNewSteps().size(); i++ ) {
                    size += massroute.getNewSteps().get(i).size();
                }
            }


            if ( isSamecity ) {
                // 同城
                step = massroute.getNewSteps().get(nodeIndex).get(0);
            } else {
                // 跨城
                int num = 0;
                for (int j = 0; j < massroute.getNewSteps().size(); j++ ) {
                    num += massroute.getNewSteps().get(j).size();
                    if ( nodeIndex - num < 0) {
                        int k = massroute.getNewSteps().get(j).size() + nodeIndex - num;
                        step = massroute.getNewSteps().get(j).get(k);
                        break;
                    }
                }
            }

            nodeLocation = ((MassTransitRouteLine.TransitStep) step).getStartLocation();
            nodeTitle = ((MassTransitRouteLine.TransitStep) step).getInstructions();
        }

        if (nodeLocation == null || nodeTitle == null) {
            return;
        }

        // 移动节点至中心
        mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
        // show popup
        popupText = new TextView(activity);
        popupText.setBackgroundResource(R.drawable.popup);
        popupText.setTextColor(0xFF000000);
        popupText.setText(nodeTitle);
        mBaidumap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));
    }
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult result) {
        Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show();
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(activity, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            /*Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show();*/
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            Toast.makeText(activity, "3", Toast.LENGTH_SHORT).show();
            return;
        }
        /*Toast.makeText(activity, "4", Toast.LENGTH_SHORT).show();*/
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            /*Toast.makeText(activity, "5", Toast.LENGTH_SHORT).show();*/
            if (result.getRouteLines().size() == 1){
                BikingRouteOverlay overlay = new MyBikingRouteOverlay(mBaidumap);
                routeOverlay = overlay;
                mBaidumap.setOnMarkerClickListener(overlay);
    /*        Toast.makeText(activity,"result.getRouteLines():"+result.getRouteLines().size(),Toast.LENGTH_LONG).show();*/
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
                /*Toast.makeText(activity, "6", Toast.LENGTH_SHORT).show();*/
            }else {
                /*Toast.makeText(activity, "7", Toast.LENGTH_SHORT).show();*/
                Log.d("route result", "结果数<0" );
                return;
            }
            /*Toast.makeText(activity, "8", Toast.LENGTH_SHORT).show();*/
        }
        /*Toast.makeText(activity, "9", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if(geoCodeResult == null){
            return;
        }
        if(isBegin){
            beginLLg = geoCodeResult.getLocation();
            isBegin = false;
            beginSearchLalByAddress(endAddr);
        }else{
            endLLg = geoCodeResult.getLocation();
            isBegin = true;
            if((beginLLg != null)&&(endLLg != null)){
                searchProcessByLLG(beginLLg,endLLg);
            }
        }

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }


    private class MyBikingRouteOverlay extends BikingRouteOverlay {
        public  MyBikingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }


    }
}
