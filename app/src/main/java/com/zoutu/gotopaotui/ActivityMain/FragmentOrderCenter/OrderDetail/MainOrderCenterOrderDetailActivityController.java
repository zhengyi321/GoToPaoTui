package com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.OrderDetail;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoutu.gotolibrary.Bean.AngleOrderBean;
import com.zoutu.gotolibrary.Bean.AngleOrderDetailBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.ActivityMain.BaseController;
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
    @BindView(R.id.tv_main_ordercenter_orderdetail_toaddr)
    TextView tvMainOrderCenterOrderDetailToAddr;
    @BindView(R.id.tv_main_ordercenter_orderdetail_totime)
    TextView tvMainOrderCenterOrderDetailToTime;
    @BindView(R.id.tv_main_ordercenter_orderdetail_getname)
    TextView tvMainOrderCenterOrderDetailGetName;
    @BindView(R.id.tv_main_ordercenter_orderdetail_gettel)
    TextView tvMainOrderCenterOrderDetailGetTel;
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
        System.out.println("orderNo:"+orderNo+" angelAnid:"+angelAnid);
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
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
                    System.out.println("onError:"+e);
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
        tvMainOrderCenterOrderDetailToName.setText(angleOrderDetailBean.getClientaddrName());
        tvMainOrderCenterOrderDetailToAddr.setText(angleOrderDetailBean.getClientaddrAddr());
        tvMainOrderCenterOrderDetailGetTime.setText(angleOrderDetailBean.getOrderOrdertime());
        tvMainOrderCenterOrderDetailGetName.setText(angleOrderDetailBean.getClientaddr1Name());
        tvMainOrderCenterOrderDetailGetTel.setText(angleOrderDetailBean.getClientaddrTel());
        tvMainOrderCenterOrderDetailGetAddr.setText(angleOrderDetailBean.getClientaddrAddr1());
        /*tvMainOrderCenterOrderDetailGetTel.setText(angleOrderDetailBean.g);*/

        tvMainOrderCenterOrderDetailMile.setText((angleOrderDetailBean.getOrderMileage()/1000)+"km");
        tvMainOrderCenterOrderDetailPrice.setText(angleOrderDetailBean.getOrderOrderprice()+"");
    }

}
