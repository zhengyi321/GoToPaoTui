package com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.OrderDetail.CheckOrderDetailMap;

import android.app.Activity;
import android.os.Bundle;

import com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.OrderDetail.MainOrderCenterOrderDetailActivityController;
import com.zoutu.gotopaotui.R;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/10.
 */

public class MainOrderCenterOrderDetailCheckOrderDetailMapActivity extends Activity {

    MainOrderCenterOrderDetailCheckOrderDetailMapActivityController mainOrderCenterOrderDetailCheckOrderDetailMapActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ordercenter_orderdetail_checkdetail_baidumap_lly);
        init();
        // Example of a call to a native method

    }

    private void init() {
        ButterKnife.bind(this);
        mainOrderCenterOrderDetailCheckOrderDetailMapActivityController = new MainOrderCenterOrderDetailCheckOrderDetailMapActivityController(this);

    }
}