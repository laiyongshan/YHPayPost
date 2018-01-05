package com.yzf.Cpaypos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.model.HelpSource;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class HelpAdapter extends SimpleRecAdapter<HelpSource, HelpAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;


    public HelpAdapter(Context context) {
        super(context);
    }


    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_help;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HelpSource item = data.get(position);
        holder.helpQusAdapter.setText(item.getQuestion());
        holder.helpAnsAdapter.setText(item.getAnswer());
        holder.helpQusLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.helpAnsLl.getVisibility() == View.GONE) {
                    holder.helpIvAdapter.setImageResource(R.mipmap.icon_up);
                    holder.helpAnsLl.setVisibility(View.VISIBLE);
                } else {
                    holder.helpIvAdapter.setImageResource(R.mipmap.icon_next);
                    holder.helpAnsLl.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick(R.id.help_ans_ll)
    public void onViewClicked() {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.help_qus_adapter)
        TextView helpQusAdapter;
        @BindView(R.id.help_iv_adapter)
        ImageView helpIvAdapter;
        @BindView(R.id.help_ans_adapter)
        TextView helpAnsAdapter;
        @BindView(R.id.help_ans_ll)
        LinearLayout helpAnsLl;
        @BindView(R.id.help_qus_ll)
        LinearLayout helpQusLl;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
