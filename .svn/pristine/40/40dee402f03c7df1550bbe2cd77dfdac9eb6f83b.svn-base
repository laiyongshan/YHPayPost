package com.yzf.Cpaypos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.GetProfitListResult;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class ProfitAdapter extends SimpleRecAdapter<GetProfitListResult.DataBean, ProfitAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;

    public ProfitAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_profit;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GetProfitListResult.DataBean item = data.get(position);
        holder.profitAdapterAmtTv.setText("交易金额：￥" + item.getAmt());
        holder.profitAdapterProfitamtTv.setText("贡献金额：￥" + item.getIncomeAmt());
        String merchId = item.getYMerchId();
        holder.profitAdapterMerchidTv.setText(AppTools.isEmpty(merchId) ? "" : merchId.substring(0, 4) + "****" + merchId.substring(merchId.length() - 4, merchId.length()));
        holder.profitAdapterTimeTv.setText(item.getTransTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
                }
            }
        });
    }

    private String setTransWeek(String time) {
        if (!AppTools.isEmpty(time)) {
            time = AppTools.getWeek(time, "yyyy-MM-dd HH:mm:ss");
        }
        return time;
    }

    private String setTransDay(String time) {
        if (!AppTools.isEmpty(time)) {
            time = time.substring(5, 10);
        }
        return time;
    }

    private String setTransResult(String result) {
        if (!AppTools.isEmpty(result)) {
            if (result.equals("01")) {
                result = "交易成功";
            } else {
                result = "交易失败";
            }
        }
        return result;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profit_adapter_amt_tv)
        TextView profitAdapterAmtTv;
        @BindView(R.id.profit_adapter_merchid_tv)
        TextView profitAdapterMerchidTv;
        @BindView(R.id.profit_adapter_profitamt_tv)
        TextView profitAdapterProfitamtTv;
        @BindView(R.id.profit_adapter_time_tv)
        TextView profitAdapterTimeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
