package com.zoutu.gotopaotui.NetWork;

import com.zoutu.gotolibrary.Bean.AngleOrderBean;
import com.zoutu.gotolibrary.Bean.AngleOrderDetailBean;
import com.zoutu.gotolibrary.Bean.BaseBean;
import com.zoutu.gotopaotui.NetWork.BaseFile.BaseNetWork;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;

/**
 * Created by admin on 2017/2/21.
 */

public class AngleBaiduLocNetWorks extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";
        /*跑腿员提交经纬度接口*/
        //GET请求
        @GET("staffll/savestaffll.do")
        Observable<BaseBean> submitLocToNet(@Query("angelAnid") String angelAnid,@Query("staffllDlat") Float staffllDlat,@Query("staffllDlong") Float staffllDlong);


    }

    public  void submitLocToNet(String angelAnid,Float staffllDlat,Float staffllDlong ,Observer<BaseBean> observer){
        setSubscribe(service.submitLocToNet(angelAnid,staffllDlat,staffllDlong),observer);
    }


/*    public  void angleOrderDetail(String angelAnid, String orderNo, Observer<AngleOrderDetailBean> observer){
        setSubscribe(service.angleOrderDetail(angelAnid,orderNo),observer);
    }*/
 /*   public void angleExit(Observer<BaseBean> observer){
        setSubscribe(service.angleExit(),observer);
    }*/

}
