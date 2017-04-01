package com.zoutu.gotopaotui.ActivityMain.FragmentGetOrder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zoutu.gotolibrary.Bean.AngleGetOrderBean;
import com.zoutu.gotopaotui.NetWork.AngleOrderNetWorks;
import com.zoutu.gotopaotui.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/3/28.
 */

public class MainGetOrderFragmentRecycleViewAdapter extends RecyclerView.Adapter<MainGetOrderFragmentRecycleViewAdapter.ItemViewHolder> {

    private List<AngleGetOrderBean> list;
    private Context context;
    private LayoutInflater inflater;
    public MainGetOrderFragmentRecycleViewAdapter(Context context1, List<AngleGetOrderBean> list1){
        this.context = context1;
        this.list = list1;
        inflater = LayoutInflater.from(context1);
    }

    public void addBean(AngleGetOrderBean bean){
        list.clear();
        list.add(bean);
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.fragment_activity_main_getorder_xrv_item_lly,parent,false));
    }



    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.pos = position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{
        int pos = 0;
        @BindView(R.id.rly_main_getorder_getordersubmit)
        RelativeLayout rlyMainGetOrderGetOrderSubmit;
        @OnClick(R.id.rly_main_getorder_getordersubmit)
        public void rlyMainGetOrderGetOrderSubmitOnclick(){
            AngleOrderNetWorks angleOrderNetWorks = new AngleOrderNetWorks();
           /* angleOrderNetWorks.getOrderFromNet(list.get(pos));*/
        }

        public ItemViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
