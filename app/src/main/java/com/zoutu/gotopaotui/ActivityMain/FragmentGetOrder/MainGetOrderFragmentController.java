package com.zoutu.gotopaotui.ActivityMain.FragmentGetOrder;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.zoutu.gotolibrary.Bean.AngleGetOrderBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.RecycleView.XRecycleView.XRecyclerView;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.ActivityMain.BaseController;
import com.zoutu.gotopaotui.NetWork.AngleOrderNetWorks;
import com.zoutu.gotopaotui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * Created by admin on 2017/3/28.
 */

public class MainGetOrderFragmentController extends BaseController{


    @BindView(R.id.xrv_main_getorder)
    XRecyclerView xrvMainGetOrder;
    List<AngleGetOrderBean> list ;
    public MainGetOrderFragmentRecycleViewAdapter mainGetOrderFragmentRecycleViewAdapter;
    public MainGetOrderFragmentController(View view1){
        view = view1;
        init();
    }


    @Override
    public void init() {
        ButterKnife.bind(this,view);
        initRecycleView();
        systemGiverOrder();
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


    private void systemGiverOrder(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
/*        Toast.makeText(activity,"systemGiverOrder",Toast.LENGTH_SHORT).show();*/
        String orderNo = xcCacheManager.readCache(xcCacheManagerSavedName.systemGivenOrder);
        String isGetOrder = xcCacheManager.readCache(xcCacheManagerSavedName.isGetOrder);
        if((isGetOrder == null)||(isGetOrder.equals("false"))){
            mainGetOrderFragmentRecycleViewAdapter.clean();
            return;
        }
        if(orderNo != null) {
            AngleOrderNetWorks angleOrderNetWorks = new AngleOrderNetWorks();
            /*Toast.makeText(view.getContext(),"orderNo"+orderNo,Toast.LENGTH_SHORT).show();*/
            System.out.print("\norderNo:"+orderNo);
            System.out.print("\norderNo:"+orderNo);
            System.out.print("\norderNo:"+orderNo);
            System.out.print("\norderNo:"+orderNo);
            System.out.print("\norderNo:"+orderNo);
            System.out.print("\norderNo:"+orderNo);
            System.out.print("\norderNo:"+orderNo);
            System.out.print("\norderNo:"+orderNo);
            System.out.print("\norderNo:"+orderNo);
            System.out.print("\norderNo:"+orderNo);
           /* if(mainGetOrderFragment.mainGetOrderFragmentController ==null){
                Toast.makeText(activity,"null",Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(activity,"not null",Toast.LENGTH_SHORT).show();*/
            angleOrderNetWorks.systemGivenOrderFromNet(orderNo, new Observer<AngleGetOrderBean>() {
                @Override
                public void onCompleted() {
                    /*Toast.makeText(view.getContext(),"onCompleted",Toast.LENGTH_SHORT).show();*/
                }

                @Override
                public void onError(Throwable e) {
                    /*Toast.makeText(view.getContext(),"onError"+e,Toast.LENGTH_SHORT).show();*/
                }

                @Override
                public void onNext(AngleGetOrderBean bean) {
                    mainGetOrderFragmentRecycleViewAdapter.addBean(bean);
                    XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
                    XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
                    /*Toast.makeText(view.getContext(),"onNext"+bean.getClientaddr1Name(),Toast.LENGTH_SHORT).show();*/
                    xcCacheManager.writeCache(xcCacheManagerSavedName.systemGivenOrder,"");
                    xcCacheManager.writeCache(xcCacheManagerSavedName.isGetOrder,"false");
                }
            });
            xcCacheManager.writeCache(xcCacheManagerSavedName.systemGivenOrder,"");
            xcCacheManager.writeCache(xcCacheManagerSavedName.isGetOrder,"false");
        }
    }
}
