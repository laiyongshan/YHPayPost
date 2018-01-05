package com.yzf.Cpaypos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.model.HomeSource;

import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class HomeAdapter extends SimpleRecAdapter<HomeSource, HomeAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;


    public HomeAdapter(Context context) {
        super(context);
    }


    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_home;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HomeSource item = data.get(position);
        holder.homeItemIv.setImageDrawable(context.getResources().getDrawable(item.getImgRes()));
        holder.homeItemTv.setText(item.getStrRes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.home_item_iv)
        ImageView homeItemIv;
        @BindView(R.id.home_item_tv)
        TextView homeItemTv;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
