package com.zoutu.gotopaotui.ActivityMain.FragmentGetOrder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zoutu.gotopaotui.R;

import java.util.List;

/**
 * Created by admin on 2017/3/28.
 */

public class MainGetOrderFragmentRecycleViewAdapter extends RecyclerView.Adapter<MainGetOrderFragmentRecycleViewAdapter.ItemViewHolder> {

    private List<String> list;
    private Context context;
    private LayoutInflater inflater;
    public MainGetOrderFragmentRecycleViewAdapter(Context context1, List<String> list1){
        this.context = context1;
        this.list = list1;
        inflater = LayoutInflater.from(context1);
    }



    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.fragment_activity_main_ordercenter_content_xrv_item_lly,parent,false));
    }



    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public ItemViewHolder(View view){
            super(view);
        }
    }
}
