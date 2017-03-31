package com.zoutu.gotopaotui.ActivityMain.FragmentIndex;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zoutu.gotolibrary.ScrollView.SpringScrollView;
import com.zoutu.gotopaotui.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by admin on 2017/3/27.
 */

public class MainIndexFragment extends Fragment {

    @BindView(R.id.rly_main_index_topbar)
    RelativeLayout rlyMainIndexTopBar;
    @BindView(R.id.lly_main_index_topbar)
    LinearLayout llyMainIndexTopBar;
    @BindView(R.id.ssv_main_index)
    SpringScrollView ssvMainIndex;
    float by = 0;;
    @OnTouch(R.id.ssv_main_index)
    public boolean ssvMainIndexOnTouch(View view, MotionEvent event){

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
                    float alph = llyMainIndexTopBar.getAlpha();
                    alph = alph + alphTemp;
                    Log.i("main_index_alph",alph+"");
                    llyMainIndexTopBar.setAlpha(alphTemp);
                    System.out.println("alph1:"+alph);
                }else{
                    float alphTemp = dis/270;
                    float alph = llyMainIndexTopBar.getAlpha();
                    alph = alph - alphTemp;
                    Log.i("main_index_alph",alph+"");
                    llyMainIndexTopBar.setAlpha(alphTemp);
                    System.out.println("alph2:"+alph);
                }
                System.out.println("dis:"+dis);
                break;
   /*         case MotionEvent.ACTION_UP:
                llyMainIndexTopBar.setAlpha(0);

                break;*/
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main_index_fly, container, false);
        init(view);
        return view;
    }



    private void init(View view){
        ButterKnife.bind(this,view);

    }



}
