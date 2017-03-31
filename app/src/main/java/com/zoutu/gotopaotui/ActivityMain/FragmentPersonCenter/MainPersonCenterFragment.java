package com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zoutu.gotolibrary.ScrollView.SpringScrollView;
import com.zoutu.gotopaotui.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by admin on 2017/3/27.
 */

public class MainPersonCenterFragment extends Fragment {

    @BindView(R.id.lly_main_personcenter_topbar)
    LinearLayout llyMainPersonCenterTopbar;
    @BindView(R.id.ssv_main_personcenter)
    SpringScrollView ssvMainPersonCenter;
    float by = 0;;
    @OnTouch(R.id.ssv_main_personcenter)
    public boolean ssvMainPersonCenterOnclick(View view, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                by = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float ey = event.getY();
                float dis = ey - by;
                dis = Math.abs(dis);
                if(ey < by){
                    float alphTemp = dis/270;
                    llyMainPersonCenterTopbar.setAlpha(alphTemp);
                }else{
                    float alphTemp = dis/270;
                /*    Log.i("main_index_alph",alph+"");*/
                    llyMainPersonCenterTopbar.setAlpha(alphTemp);
                    /*System.out.println("alph2:"+alph);*/
                }
                System.out.println("dis:"+dis);
                break;

        }
        return false;
    }

    MainPersonCenterFragmentController mainPersonCenterFragmentController;
    private View view1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main_personcenter_fly, container, false);
        view1 = view;
        init(view);
        return view;
    }


    private void init(View view){
        ButterKnife.bind(this,view);
        mainPersonCenterFragmentController = new MainPersonCenterFragmentController(view);
    }

    @Override
    public void onResume(){
        super.onResume();
        mainPersonCenterFragmentController.onResume();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

/*    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(view1.getContext(),"this is receive",Toast.LENGTH_LONG).show();
    }*/

    /*http://blog.csdn.net/xiazdong/article/details/7768807/*/
/*    public class loginOutBroadReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
           *//* String action = intent.getExtras().getString("action");*//*

*//*            if(action != null){
                onResume();
            }
            Log.i("Recevier1", "接收到:"+action);*//*
        }
    }*/
}