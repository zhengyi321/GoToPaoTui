package com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.zoutu.gotolibrary.Bean.AngleOrderBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.RecycleView.XRecycleView.XRecyclerView;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.ActivityMain.BaseController;
import com.zoutu.gotopaotui.NetWork.AngleOrderNetWorks;
import com.zoutu.gotopaotui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * Created by admin on 2017/3/30.
 */

public class MainOrderCenterFragmentController extends BaseController{


    @BindView(R.id.xrv_main_ordercenter)
    XRecyclerView xrvMainOrderCenter;

    @BindView(R.id.rb_main_ordercenter_ordertype)
    RadioButton rbMainOrderCenterOrderType;
    @OnClick(R.id.rb_main_ordercenter_ordertype)
    public void rbMainOrderCenterOrderTypeOnclick(){
        getOrderDataFromNet();
    }

    @BindView(R.id.rb_main_ordercenter_data)
    RadioButton rbMainOrderCenterData;
    @OnClick(R.id.rb_main_ordercenter_data)
    public void rbMainOrderCenterDataOnclick(){

    }


    public MainOrderCenterFragmentAllOrderRecycleViewAdapter mainOrderCenterFragmentAllOrderRecycleViewAdapter;
    List<AngleOrderBean> angleOrderBeanList;


    public MainOrderCenterFragmentController(View view1){
        view = view1;
        /*Toast.makeText(view.getContext(),"this is resume",Toast.LENGTH_SHORT).show();*/
        init();
    }
    @Override
    public void init() {
        ButterKnife.bind(this,view);
        initAllOrderXRV();

    }
    private void initAllOrderXRV(){
        angleOrderBeanList = new ArrayList<>();
        mainOrderCenterFragmentAllOrderRecycleViewAdapter =new MainOrderCenterFragmentAllOrderRecycleViewAdapter(view.getContext(), angleOrderBeanList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvMainOrderCenter.setAdapter(mainOrderCenterFragmentAllOrderRecycleViewAdapter);
        xrvMainOrderCenter.setLayoutManager(layoutManager);
        xrvMainOrderCenter.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getOrderDataFromNet();
                xrvMainOrderCenter.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                xrvMainOrderCenter.loadMoreComplete();
            }
        });
        getOrderDataFromNet();
    }

    private void getOrderDataFromNet(){
        XCCacheManager xcCacheManager= XCCacheManager.getInstance(view.getContext());
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
        String angelId = xcCacheManager.readCache(xcCacheManagerSavedName.angelAnid);
        if((angelId != null)&&(!angelId.isEmpty())){
            AngleOrderNetWorks angleOrderNetWorks = new AngleOrderNetWorks();
            angleOrderNetWorks.getAllAngleOrderFromNet(angelId, new Observer<List<AngleOrderBean>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<AngleOrderBean> angleAllOrderBeen) {
                    if(angleAllOrderBeen != null){
                        mainOrderCenterFragmentAllOrderRecycleViewAdapter.setAngleOrderBeanList(angleAllOrderBeen);
                    }
                }
            });
        }else{
            List<AngleOrderBean> angleOrderBeanList = new ArrayList<>();
            mainOrderCenterFragmentAllOrderRecycleViewAdapter.setAngleOrderBeanList(angleOrderBeanList);
        }
    }
    public void onResume(){
        /*Toast.makeText(view.getContext(),"this is resume",Toast.LENGTH_SHORT).show();*/
        getOrderDataFromNet();
    }

}
