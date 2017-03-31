package com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter.Login;

import android.app.Activity;
import android.os.Bundle;

import com.zoutu.gotopaotui.R;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/29.
 */

public class MainPersonCenterLoginActivity extends Activity{


    MainPersonCenterLoginActivityController mainPersonCenterLoginActivityController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_personcenter_login_lly);
        init();
        // Example of a call to a native method

    }

    private void init(){
        ButterKnife.bind(this);
        mainPersonCenterLoginActivityController = new MainPersonCenterLoginActivityController(this);

    }
}
