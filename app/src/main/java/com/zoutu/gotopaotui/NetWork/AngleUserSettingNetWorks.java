package com.zoutu.gotopaotui.NetWork;

import com.zoutu.gotolibrary.Bean.AngleBean;
import com.zoutu.gotolibrary.Bean.BaseBean;
import com.zoutu.gotopaotui.NetWork.BaseFile.BaseNetWork;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
/**
 * Created by admin on 2017/2/21.
 */

public class AngleUserSettingNetWorks extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";
        /*跑腿员用户注册*/
        //GET请求
        @GET("angel/appreg.do")
        Observable<BaseBean> angleRegToNet(@Query("angelName") String angleName, @Query("angelPassword") String anglePassword);

        //跑腿员用户登录
        @GET("angel/applogin.do")
        Observable<AngleBean> angleLoginToNet(@Query("angelName") String angleName, @Query("angelPassword") String anglePassword);

        /*用户退出*/
        @GET("angel/appexit.do")
        Observable<BaseBean> angleExit();
        /*用户退出*/
    }

    public  void angleRegToNet(String angleName, String anglePassword, Observer<BaseBean> observer){
        setSubscribe(service.angleRegToNet(angleName, anglePassword),observer);
    }
    public  void angleLoginToNet(String angleName, String anglePassword, Observer<AngleBean> observer){
        setSubscribe(service.angleLoginToNet(angleName, anglePassword),observer);
    }
    public void angleExit(Observer<BaseBean> observer){
        setSubscribe(service.angleExit(),observer);
    }

}
