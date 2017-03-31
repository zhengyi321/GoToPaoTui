package com.zoutu.gotopaotui.ActivityMain.FragmentGetOrder;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zoutu.gotopaotui.R;

import butterknife.BindView;

/**
 * Created by admin on 2017/3/27.
 */

public class MainGetOrderFragment extends Fragment {
/*    Context context;
    public MainGetOrderFragment(Context context1){

    }*/

    private MainGetOrderFragmentController mainGetOrderFragmentController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main_getorder_lly, container, false);
        init(view);
        return view;
    }
    private void init(View view){
        mainGetOrderFragmentController = new MainGetOrderFragmentController(view);
    }
}
