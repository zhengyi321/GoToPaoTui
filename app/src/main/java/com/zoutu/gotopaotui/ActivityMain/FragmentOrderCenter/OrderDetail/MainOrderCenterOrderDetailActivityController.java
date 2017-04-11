package com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.OrderDetail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoutu.gotolibrary.Bean.AngleOrderBean;
import com.zoutu.gotolibrary.Bean.AngleOrderDetailBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.Dialog.AlertView.AlertView;
import com.zoutu.gotolibrary.Dialog.AlertView.OnItemClickListener;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.ActivityMain.BaseController;
import com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.OrderDetail.CheckOrderDetailMap.MainOrderCenterOrderDetailCheckOrderDetailMapActivity;
import com.zoutu.gotopaotui.NetWork.AngleOrderNetWorks;
import com.zoutu.gotopaotui.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * Created by admin on 2017/3/30.
 */

public class MainOrderCenterOrderDetailActivityController extends BaseController {



    @BindView(R.id.rly_main_ordercenter_orderdetail_topbar_back)
    RelativeLayout rlyMainOrderCenterOrderDetailTopBarBack;

    @OnClick(R.id.rly_main_ordercenter_orderdetail_topbar_back)
    public void rlyMainOrderCenterOrderDetailTopBarBackOnclick(){
        activity.finish();
    }

    @BindView(R.id.tv_main_ordercenter_orderdetail_toname)
    TextView tvMainOrderCenterOrderDetailToName;
    @BindView(R.id.tv_main_ordercenter_orderdetail_totel)
    TextView tvMainOrderCenterOrderDetailToTel;
    @OnClick(R.id.tv_main_ordercenter_orderdetail_totel)
    public void tvMainOrderCenterOrderDetailToTelOnclick(){
        new AlertView.Builder().setContext(activity)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("联系人电话")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive(tvMainOrderCenterOrderDetailToName.getText().toString())
                /*.setDestructive1("10公斤以内")*/
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        /*Toast.makeText(getBaseContext(),"pos"+position,Toast.LENGTH_SHORT).show();*/
                        switch (position){
                            case 0:
                                /*tvMainHelpMeSendContentGoodsWeight.setText("超出10公斤");*/
                                startCallTel(tvMainOrderCenterOrderDetailToTel.getText().toString());
                                break;
                          /*  case 1:
                                tvMainHelpMeSendContentGoodsWeight.setText("10公斤以内");
                                break;*/
                            case -1:
                                break;
                        }
                    }
                })
                .build()
                .show();
    }
    @BindView(R.id.tv_main_ordercenter_orderdetail_toaddr)
    TextView tvMainOrderCenterOrderDetailToAddr;
    @BindView(R.id.tv_main_ordercenter_orderdetail_totime)
    TextView tvMainOrderCenterOrderDetailToTime;
    @BindView(R.id.tv_main_ordercenter_orderdetail_getname)
    TextView tvMainOrderCenterOrderDetailGetName;
    @BindView(R.id.tv_main_ordercenter_orderdetail_gettel)
    TextView tvMainOrderCenterOrderDetailGetTel;
    @OnClick(R.id.tv_main_ordercenter_orderdetail_gettel)
    public void tvMainOrderCenterOrderDetailGetTelOnclick(){
        new AlertView.Builder().setContext(activity)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("联系人电话")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive(tvMainOrderCenterOrderDetailGetTel.getText().toString())
                /*.setDestructive1("10公斤以内")*/
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        /*Toast.makeText(getBaseContext(),"pos"+position,Toast.LENGTH_SHORT).show();*/
                        switch (position){
                            case 0:
                                /*tvMainHelpMeSendContentGoodsWeight.setText("超出10公斤");*/
                                startCallTel(tvMainOrderCenterOrderDetailGetTel.getText().toString());
                                break;
                          /*  case 1:
                                tvMainHelpMeSendContentGoodsWeight.setText("10公斤以内");
                                break;*/
                            case -1:
                                break;
                        }
                    }
                })
                .build()
                .show();
    }
    @BindView(R.id.tv_main_ordercenter_orderdetail_getaddr)
    TextView tvMainOrderCenterOrderDetailGetAddr;
    @BindView(R.id.tv_main_ordercenter_orderdetail_gettime)
    TextView tvMainOrderCenterOrderDetailGetTime;
    @BindView(R.id.tv_main_ordercenter_orderdetail_remark)
    TextView tvMainOrderCenterOrderDetailRemark;
    @BindView(R.id.tv_main_ordercenter_orderdetail_mile)
    TextView tvMainOrderCenterOrderDetailMile;
    @BindView(R.id.tv_main_ordercenter_orderdetail_usertime)
    TextView tvMainOrderCenterOrderDetailUserTime;
    @BindView(R.id.tv_main_ordercenter_orderdetail_price)
    TextView tvMainOrderCenterOrderDetailPrice;
    @BindView(R.id.lly_main_ordercenter_orderdetail_checkroad)
    LinearLayout llyMainOrderCenterOrderDetailCheckRoad;
    @OnClick(R.id.lly_main_ordercenter_orderdetail_checkroad)
    public void llyMainOrderCenterOrderDetailCheckRoadOnclick(){


        Intent intent = new Intent(activity, MainOrderCenterOrderDetailCheckOrderDetailMapActivity.class);
        activity.startActivity(intent);

    }

    public MainOrderCenterOrderDetailActivityController(Activity activity1){
        activity = activity1;
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this,activity);
        initOrderDetail();
    }
    private void initOrderDetail(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
        String orderNo = xcCacheManager.readCache(xcCacheManagerSavedName.orderNo);
        String angelAnid = xcCacheManager.readCache(xcCacheManagerSavedName.angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
        System.out.println("\norderNo:"+orderNo+" angelAnid:"+angelAnid);
       /* Toast.makeText(activity,"orderNo:"+orderNo+" angelAnid:"+angelAnid,Toast.LENGTH_SHORT).show();*/
        if((orderNo != null)&&(angelAnid != null)) {
            AngleOrderNetWorks angleOrderNetWorks = new AngleOrderNetWorks();
            angleOrderNetWorks.getAngleOrderDetailFromNet(angelAnid, orderNo, new Observer<List<AngleOrderDetailBean>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                }

                @Override
                public void onNext(List<AngleOrderDetailBean> angleOrderDetailBeen) {
                    /*Toast.makeText(activity,"successful",Toast.LENGTH_SHORT).show();*/
                    if(angleOrderDetailBeen.get(0) != null) {
                        initDetailFromNet(angleOrderDetailBeen.get(0));
                    }
                }
            });
        }
    }

    private void initDetailFromNet(AngleOrderDetailBean angleOrderDetailBean){
/*        tvMainOrderCenterOrderDetailToName.setText(angleOrderDetailBean.getClientaddrName());
        tvMainOrderCenterOrderDetailToAddr.setText(angleOrderDetailBean.getClientaddrAddr());*/




        tvMainOrderCenterOrderDetailToName.setText(angleOrderDetailBean.getClientaddr1Name());//发件人姓名
        tvMainOrderCenterOrderDetailToAddr.setText(angleOrderDetailBean.getClientaddrAddr1());//发件人地址
        tvMainOrderCenterOrderDetailToTel.setText(angleOrderDetailBean.getLientaddr1Tel());//发件人手机
        tvMainOrderCenterOrderDetailToTime.setText(angleOrderDetailBean.getOrderOrdertime());//下单时间

/*        tvMainOrderCenterOrderDetailGetTime.setText(angleOrderDetailBean.getOrderOrdertime());*/
        tvMainOrderCenterOrderDetailGetName.setText(angleOrderDetailBean.getClientaddrName());//收件人姓名
        tvMainOrderCenterOrderDetailGetTel.setText(angleOrderDetailBean.getClientaddrTel());//收件人手机
        tvMainOrderCenterOrderDetailGetAddr.setText(angleOrderDetailBean.getClientaddrAddr());//收件人地址
        tvMainOrderCenterOrderDetailGetTime.setText(angleOrderDetailBean.getTransportationtime());
        /*tvMainOrderCenterOrderDetailGetTel.setText(angleOrderDetailBean.g);*/
        if(!angleOrderDetailBean.getDetailsGoodsname().isEmpty()){
            tvMainOrderCenterOrderDetailRemark.setText(angleOrderDetailBean.getDetailsGoodsname());
        }
        tvMainOrderCenterOrderDetailRemark.setText(angleOrderDetailBean.getOrderRemark());
        tvMainOrderCenterOrderDetailMile.setText((angleOrderDetailBean.getOrderMileage())+"km");
        tvMainOrderCenterOrderDetailPrice.setText(angleOrderDetailBean.getOrderOrderprice()+"");
        initOrderDetailToCache(angleOrderDetailBean);


    }
    private void initOrderDetailToCache(AngleOrderDetailBean angleOrderDetailBean){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
        xcCacheManager.writeCache(xcCacheManagerSavedName.orderDetailBeginAddr,angleOrderDetailBean.getClientaddrAddr());
        xcCacheManager.writeCache(xcCacheManagerSavedName.orderDetailEndAddr,angleOrderDetailBean.getClientaddrAddr1());
    }


    private void startCallTel(String number){
        /*PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
        if((number != null)&&(phoneFormatCheckUtils.IsNumber(number))) {*/
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        activity.startActivity(intent);
       /* }*/
    }
}
