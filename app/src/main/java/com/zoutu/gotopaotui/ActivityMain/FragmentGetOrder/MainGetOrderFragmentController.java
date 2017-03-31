package com.zoutu.gotopaotui.ActivityMain.FragmentGetOrder;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zoutu.gotolibrary.RecycleView.XRecycleView.XRecyclerView;
import com.zoutu.gotopaotui.ActivityMain.BaseController;
import com.zoutu.gotopaotui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/28.
 */

public class MainGetOrderFragmentController extends BaseController{


    @BindView(R.id.xrv_main_getorder)
    XRecyclerView xrvMainGetOrder;
    List<String> list ;
    MainGetOrderFragmentRecycleViewAdapter mainGetOrderFragmentRecycleViewAdapter;
    public MainGetOrderFragmentController(View view1){
        view = view1;
        init();
    }


    @Override
    public void init() {
        ButterKnife.bind(this,view);
        initRecycleView();
    }

    private void initRecycleView(){
        list = new ArrayList<>();
        mainGetOrderFragmentRecycleViewAdapter = new MainGetOrderFragmentRecycleViewAdapter(view.getContext(),list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvMainGetOrder.setAdapter(mainGetOrderFragmentRecycleViewAdapter);
        xrvMainGetOrder.setLayoutManager(layoutManager);
        xrvMainGetOrder.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                xrvMainGetOrder.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                xrvMainGetOrder.loadMoreComplete();
            }
        });
    }
}
