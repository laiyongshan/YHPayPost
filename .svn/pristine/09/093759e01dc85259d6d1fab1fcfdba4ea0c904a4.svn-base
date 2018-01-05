package com.yzf.Cpaypos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.model.TransSource;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * ClaseName：TransDialogAdapter
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/5 16:39
 * Modified By：
 * Fixtime：2017/5/5 16:39
 * FixDescription：
 */

public class TransDialogAdapter extends SimpleRecAdapter<TransSource, TransDialogAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;


    public TransDialogAdapter(Context context) {
        super(context);
    }


    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_trans;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TransSource item = data.get(position);
        holder.transItemIv.setImageDrawable(context.getResources().getDrawable(item.getImgRes()));
        holder.transItemTv.setText(item.getDesc());
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

        @BindView(R.id.trans_item_iv)
        ImageView transItemIv;
        @BindView(R.id.trans_item_tv)
        TextView transItemTv;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
