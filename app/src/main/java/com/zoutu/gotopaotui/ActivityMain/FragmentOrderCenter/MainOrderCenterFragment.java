package com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zoutu.gotopaotui.R;

/**
 * Created by admin on 2017/3/27.
 */

public class MainOrderCenterFragment extends Fragment {


    public MainOrderCenterFragmentController mainOrderCenterFragmentController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main_ordercenter_lly, container, false);
        init(view);
        return view;
    }
    private void init(View view){
        mainOrderCenterFragmentController = new MainOrderCenterFragmentController(view);

    }
    @Override
    public void onResume(){
        super.onResume();
        mainOrderCenterFragmentController.onResume();
    }


}
