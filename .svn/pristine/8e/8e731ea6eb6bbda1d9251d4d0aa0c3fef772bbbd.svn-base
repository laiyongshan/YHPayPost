package com.yzf.Cpaypos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.HelpSource;
import com.yzf.Cpaypos.model.servicesmodels.GetAccountResults;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class AccountAdapter extends SimpleRecAdapter<GetAccountResults, AccountAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;


    public AccountAdapter(Context context) {
        super(context);
    }


    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_account;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GetAccountResults item = data.get(position);
        holder.accountAdapterAmtTv.setText(item.getAmt());
        holder.accountAdapterAvlamtTv.setText(item.getAvlAmt());
        holder.accountAdapterFznamtTv.setText(item.getFznAmt());
        holder.accountAdapterTypeTv.setText(formateType(item.getType()));
        holder.accountAdapterWithdrawTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
                }
            }
        });
    }

    private String formateType(String type) {
         String s = "账户余额";
        if (!AppTools.isEmpty(type)) {
            if (type.equals("0")) {
                s = "账户余额";
            } else if (type.equals("1")) {
                s = "分润余额";
            } else if (type.equals("2"))
            {
                s = "积分余额";
            }
        }
        return s;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.account_adapter_amt_tv)
        TextView accountAdapterAmtTv;
        @BindView(R.id.account_adapter_avlamt_tv)
        TextView accountAdapterAvlamtTv;
        @BindView(R.id.account_adapter_fznamt_tv)
        TextView accountAdapterFznamtTv;
        @BindView(R.id.account_adapter_withdraw_tv)
        TextView accountAdapterWithdrawTv;
        @BindView(R.id.account_adapter_type_tv)
        TextView accountAdapterTypeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
