package com.zoutu.gotopaotui.NetWork;

import com.zoutu.gotolibrary.Bean.AngleOrderBean;
import com.zoutu.gotolibrary.Bean.AngleOrderDetailBean;
import com.zoutu.gotopaotui.NetWork.BaseFile.BaseNetWork;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;

/**
 * Created by admin on 2017/2/21.
 */

public class AngleOrderNetWorks extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";
        /*跑腿员查询所有订单*/
        //GET请求
        @GET("staffs/staffind.do")
        Observable<List<AngleOrderBean>> getAllAngleOrderFromNet(@Query("angelAnid") String angelAnid);

        //跑腿员查询单个订单详情
        @GET("staffs/staffindone.do")
        Observable<List<AngleOrderDetailBean>> getAngleOrderDetailFromNet(@Query("angelAnid") String angelAnid,@Query("orderNo") String orderNo);

/*        @GET("staffs/staffindone.do")
        Observable<AngleOrderDetailBean> angleOrderDetail(@Query("angelAnid") String angelAnid, @Query("orderNo") String orderNo);*/
        /*用户退出*/
       /* @GET("angel/appexit.do")
        Observable<BaseBean> angleExit();*/
        /*用户退出*/
    }

    public  void getAllAngleOrderFromNet(String angelAnid, Observer<List<AngleOrderBean>> observer){
        setSubscribe(service.getAllAngleOrderFromNet(angelAnid),observer);
    }

    public void getAngleOrderDetailFromNet(String angelAnid,String orderNo,Observer<List<AngleOrderDetailBean>> observer){
        setSubscribe(service.getAngleOrderDetailFromNet(angelAnid,orderNo),observer);
    }
/*    public  void angleOrderDetail(String angelAnid, String orderNo, Observer<AngleOrderDetailBean> observer){
        setSubscribe(service.angleOrderDetail(angelAnid,orderNo),observer);
    }*/
 /*   public void angleExit(Observer<BaseBean> observer){
        setSubscribe(service.angleExit(),observer);
    }*/

}
