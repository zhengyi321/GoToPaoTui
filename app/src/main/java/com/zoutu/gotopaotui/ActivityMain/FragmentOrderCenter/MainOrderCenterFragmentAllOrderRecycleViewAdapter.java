package com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zoutu.gotolibrary.Bean.AngleOrderBean;
import com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache.XCCacheManager;
import com.zoutu.gotolibrary.Utils.TimeUtil;
import com.zoutu.gotolibrary.Utils.XCCacheManagerSavedName;
import com.zoutu.gotopaotui.ActivityMain.FragmentOrderCenter.OrderDetail.MainOrderCenterOrderDetailActivity;
import com.zoutu.gotopaotui.R;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/3/30.
 */

public class MainOrderCenterFragmentAllOrderRecycleViewAdapter extends RecyclerView.Adapter<MainOrderCenterFragmentAllOrderRecycleViewAdapter.ItemViewHold>{

    private Context context;
    private List<AngleOrderBean> angleOrderBeanList;
    private LayoutInflater inflater;
    public MainOrderCenterFragmentAllOrderRecycleViewAdapter(Context context1, List<AngleOrderBean> angleOrderBeanList1){
        context = context1;
        angleOrderBeanList = angleOrderBeanList1;
        inflater = LayoutInflater.from(context1);
    }


    public void setAngleOrderBeanList(Collection<AngleOrderBean> angleOrderBeanList1){
        if(angleOrderBeanList1 !=null){
            angleOrderBeanList.clear();
            angleOrderBeanList.addAll(angleOrderBeanList1);
            notifyDataSetChanged();
        }
    }
    public void setEmpty(){
        angleOrderBeanList.clear();
        notifyDataSetChanged();
    }

    public List<AngleOrderBean> getAngleOrderBeanList(){
        return angleOrderBeanList;
    }
    @Override
    public ItemViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHold(inflater.inflate(R.layout.fragment_activity_main_ordercenter_content_xrv_item_lly, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHold holder, int position) {
        if(holder != null){
          /*  TimeUtil timeUtil = new TimeUtil();
            String time = timeUtil.dateToString(angleOrderBeanList.get(position).getOrderOrdertime());*/
            holder.tvMainOrderCenterDataTime.setText(angleOrderBeanList.get(position).getOrderOrdertime());
            holder.tvMainOrderCenterDestAddr.setText(angleOrderBeanList.get(position).getClientaddrAddr());
            holder.tvMainOrderCenterBegAddr.setText(angleOrderBeanList.get(position).getClientaddrAddr1());
            holder.tvMainOrderCenterOrderNo.setText(angleOrderBeanList.get(position).getOrderNo());
            holder.tvMainOrderCenterMoney.setText(" ¥ "+ angleOrderBeanList.get(position).getOrderOrderprice());
            holder.pos = position;
        }
    }

    @Override
    public int getItemCount() {
        return angleOrderBeanList.size();
    }

    public class ItemViewHold extends RecyclerView.ViewHolder{
        int pos = 0;
        @BindView(R.id.lly_main_ordercenter_total)
        LinearLayout llyMainOrderCenterTotal;

        @OnClick(R.id.lly_main_ordercenter_total)
        public void llyMainOrderCenterTotalOnclick(){
            Intent intent = new Intent(context, MainOrderCenterOrderDetailActivity.class);
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(context);
            XCCacheManagerSavedName xcCacheManagerSavedName = new XCCacheManagerSavedName();
            xcCacheManager.writeCache(xcCacheManagerSavedName.orderNo,angleOrderBeanList.get(pos).getOrderNo());
            context.startActivity(intent);
        }


        @BindView(R.id.tv_main_ordercenter_datatime)
        TextView tvMainOrderCenterDataTime;
        @BindView(R.id.tv_main_ordercenter_destaddr)
        TextView tvMainOrderCenterDestAddr;
        @BindView(R.id.tv_main_ordercenter_begaddr)
        TextView tvMainOrderCenterBegAddr;

        @BindView(R.id.tv_main_ordercenter_orderno)
        TextView tvMainOrderCenterOrderNo;
        @BindView(R.id.tv_main_ordercenter_money)
        TextView tvMainOrderCenterMoney;


        public ItemViewHold(View viewItem){
            super(viewItem);
            ButterKnife.bind(this,viewItem);
        }
    }
}
