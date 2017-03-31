package com.zoutu.gotopaotui.NetWork.BaseFile;

import com.zoutu.gotolibrary.Utils.ContentUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**https://github.com/r17171709/Retrofit2Demo
 * Created by zhyan on 2017/2/7.
 */

public abstract class RetrofitUtils {
    //服务器路径
    /*private static final String API_SERVER = "http://192.168.16.147:8080";*/
    /*private  final String API_SERVER = "http://www.weather.com.cn";*/
    ContentUtils contentUtils = new ContentUtils();
    private  Retrofit mRetrofit = null;
    private  OkHttpClient mOkHttpClient;

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    protected  Retrofit getRetrofit() {

        if (null == mRetrofit) {

            if (null == mOkHttpClient) {
                OkHttp3Utils okHttp3Utils = new OkHttp3Utils();
                mOkHttpClient = okHttp3Utils.getOkHttpClient();
            }
            /*mRetrofit = new Retrofit.Builder()
                    .baseUrl(API_SERVER + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();*/

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(contentUtils.ServiceUrl + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();

        }

        return mRetrofit;
    }
}
