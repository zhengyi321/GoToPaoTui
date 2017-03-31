package com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.OrderDetail;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter.Login.MainPersonCenterLoginActivityController;
import com.zoutu.gotopaotui.R;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/30.
 */

public class MainOrderCenterOrderDetailActivity extends Activity{

    MainOrderCenterOrderDetailActivityController mainPersonCenterLoginActivityController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ordercenter_orderdetail_lly);
        init();
        // Example of a call to a native method

    }

    private void init(){
        ButterKnife.bind(this);
        mainPersonCenterLoginActivityController = new MainOrderCenterOrderDetailActivityController(this);

    }

}
