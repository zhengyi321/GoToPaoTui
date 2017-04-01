package com.zoutu.gotopaotui.ActivityMain.FragmentGetOrder;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.R;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by admin on 2017/3/27.
 */

public class MainGetOrderFragment extends Fragment {
/*    Context context;
    public MainGetOrderFragment(Context context1){

    }*/

    public MainGetOrderFragmentController mainGetOrderFragmentController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main_getorder_lly, container, false);
        init(view);
        return view;
    }
    private void init(final View view){
        mainGetOrderFragmentController = new MainGetOrderFragmentController(view);
        ButterKnife.bind(this,view);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
        String angelAnid =xcCacheManager.readCache(xcCacheManagerSavedName.angelAnid);
        if(angelAnid != null) {
            JPushInterface.setAlias(view.getContext(), angelAnid, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    if (i == 0) {
                        /*Toast.makeText(view.getContext(), "successful:"+s, Toast.LENGTH_SHORT).show();*/
                        System.out.print("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"+set);
                    }
                }
            });
        }


    }
}
