package com.zoutu.gotopaotui.ActivityMain;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.zoutu.gotolibrary.Bean.AngleGetOrderBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.ActivityMain.FragmentGetOrder.MainGetOrderFragment;
import com.zoutu.gotopaotui.ActivityMain.FragmentIndex.MainIndexFragment;
import com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.MainOrderCenterFragment;
import com.zoutu.gotopaotui.ActivityMain.FragmentPersonCenter.MainPersonCenterFragment;
import com.zoutu.gotopaotui.NetWork.AngleOrderNetWorks;
import com.zoutu.gotopaotui.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * Created by admin on 2017/3/29.
 */

public class MainActivityController extends BaseController {


    /*首页*/
    @BindView(R.id.rb_main_common_bottom_index)
    RadioButton rbMainCommonBottomIndex;
    @OnClick(R.id.rb_main_common_bottom_index)
    public void rbMainCommonBottomIndexOnclick(){
        initFragment("index");
        /*Toast.makeText(this,"this is 0",Toast.LENGTH_SHORT).show();*/
        /*nvpMainContent.setCurrentItem(1);*/
    }
    /*首页*/
    /*接单*/
    @BindView(R.id.rb_main_common_bottom_getorder)
    RadioButton rbMainCommonBottomGetOrder;
    @OnClick(R.id.rb_main_common_bottom_getorder)
    public void rbMainCommonBottomGetOrderOnclick(){
        initFragment("getorder");
        /*Toast.makeText(this,"this is 1",Toast.LENGTH_SHORT).show();*/
        /*nvpMainContent.setCurrentItem(2);*/
    }
    /*接单*/

    /*订单中心*/
    @BindView(R.id.rb_main_common_bottom_ordercenter)
    RadioButton rbMainCommonBottomOrderCenter;
    @OnClick(R.id.rb_main_common_bottom_ordercenter)
    public void rbMainCommonBottomOrderCenterOnclick(){
        /*Toast.makeText(this,"this is 2",Toast.LENGTH_SHORT).show();*/
        initFragment("ordercenter");
        /*nvpMainContent.setCurrentItem(3);*/
    }
    /*订单中心*/

    /*个人中心*/
    @BindView(R.id.rb_main_common_bottom_personconter)
    RadioButton rbMainCommonBottomPersonConter;
    @OnClick(R.id.rb_main_common_bottom_personconter)
    public void rbMainCommonBottomPersonConterOnclick(){
        initFragment("personconter");
        /*Toast.makeText(this,"this is 3",Toast.LENGTH_SHORT).show();*/
        /*nvpMainContent.setCurrentItem(4);*/
    }
    /*个人中心*/
    @BindView(R.id.fly_main_content)
    FrameLayout nvpMainContent;


    private MainIndexFragment mainIndexFragment;
    private MainGetOrderFragment mainGetOrderFragment;
    private MainOrderCenterFragment mainOrderCenterFragment;
    private  MainPersonCenterFragment mainPersonCenterFragment;



    public MainActivityController(Activity activity1){
        activity = activity1;
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this,activity);
        initMainFragment();
        /*initFragment("index");*/
    }

    private void initMainFragment(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
        String type = xcCacheManager.readCache(xcCacheManagerSavedName.showMainFragment);
        if(type != null){

            if(type.equals("GetOrder")){
                initFragment("getorder");
                rbMainCommonBottomIndex.setChecked(false);
                rbMainCommonBottomGetOrder.setChecked(true);
                rbMainCommonBottomOrderCenter.setChecked(false);
                rbMainCommonBottomPersonConter.setChecked(false);
                xcCacheManager.writeCache(xcCacheManagerSavedName.showMainFragment,"index");
                return;
            }
        }
        initFragment("index");
    }




    private void hideFragment(FragmentTransaction transaction){
        if(mainIndexFragment !=null){
            transaction.hide(mainIndexFragment);
        }
        if(mainGetOrderFragment != null){
            transaction.hide(mainGetOrderFragment);
        }
        if(mainOrderCenterFragment != null){
            transaction.hide(mainOrderCenterFragment);
        }
        if(mainPersonCenterFragment != null){
            transaction.hide(mainPersonCenterFragment);
        }
    }
    /* 初始化fragment*/
    private void initFragment(String type){
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);
        // 动态增加Fragment
/*         mainIndexFragment = new MainIndexFragment();
         mainGetOrderFragment = new MainGetOrderFragment();
         mainOrderCenterFragment = new MainOrderCenterFragment();
         mainPersonCenterFragment = new MainPersonCenterFragment();*/

        switch (type){
            case "index":
                if(mainIndexFragment !=null){
                    transaction.show(mainIndexFragment);
                }else {
                    mainIndexFragment = new MainIndexFragment();
                    transaction.add(R.id.fly_main_content, mainIndexFragment, "index");
                }
                break;
            case "getorder":
                if(mainGetOrderFragment != null){

                    transaction.show(mainGetOrderFragment);
                }else {
                    mainGetOrderFragment = new MainGetOrderFragment();

                    transaction.add(R.id.fly_main_content, mainGetOrderFragment, "getorder");
                }
                break;
            case "ordercenter":
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
                XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
                String angelId = xcCacheManager.readCache(xcCacheManagerSavedName.angelAnid);
                if(mainOrderCenterFragment != null){
                    /*Toast.makeText(activity,"this is ordercenter angelid:"+angelId,Toast.LENGTH_SHORT).show();*/
                    /*判断为退出状态清空订单中心*/
                    if((angelId == null)||(angelId.isEmpty())){
                        /*Toast.makeText(activity,"this is ordercenter angelid is empty",Toast.LENGTH_SHORT).show();*/
                        mainOrderCenterFragment.mainOrderCenterFragmentController.mainOrderCenterFragmentAllOrderRecycleViewAdapter.setEmpty();
                    }
                    /*判断为退出状态清空订单中心*/
                    transaction.show(mainOrderCenterFragment);
                }else {
                    mainOrderCenterFragment = new MainOrderCenterFragment();
                    transaction.add(R.id.fly_main_content, mainOrderCenterFragment, "ordercenter");
                }
                break;
            case "personconter":
                if(mainPersonCenterFragment != null){
                    transaction.show(mainPersonCenterFragment);
                }else {
                    mainPersonCenterFragment = new MainPersonCenterFragment();
                    transaction.add(R.id.fly_main_content, mainPersonCenterFragment, "personcenter");
                }
                break;
        }

        transaction.commit();
    }


}
