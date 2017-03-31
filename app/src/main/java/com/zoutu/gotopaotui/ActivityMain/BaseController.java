package com.zoutu.gotopaotui.ActivityMain;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by admin on 2017/3/28.
 */

public abstract class BaseController {
    public Activity activity;
    public View view;
    public Context context;
    public abstract void init();
}
