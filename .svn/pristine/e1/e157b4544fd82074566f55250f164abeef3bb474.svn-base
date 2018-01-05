package com.yzf.Cpaypos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.model.servicesmodels.GetPlanCardResults;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class PlanCardAdapter extends SimpleRecAdapter<GetPlanCardResults.DataBean, PlanCardAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;

    private PlanCardAdapter.OnMyItemClickListener listener;

    public void setOnMyItemClickListener(PlanCardAdapter.OnMyItemClickListener listener) {
        this.listener = listener;

    }

    public interface OnMyItemClickListener {
        void myClick(View v, int pos, GetPlanCardResults.DataBean item);

        void mLongClick(View v, int pos, GetPlanCardResults.DataBean item);
    }

    public PlanCardAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_plancard;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GetPlanCardResults.DataBean item = data.get(position);
        holder.plancardDateAdapter.setText(item.getDate());
        holder.plancardAmtAdapter.setText("还款金额：" + item.getRepaymentAmount());
        holder.plancardStatusAdapter.setText(format(item.getStatus()));
        holder.plancardFeeAdapter.setText("手续费：" +item.getFeeAmount());

        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(v, position, item);
                }
            });
            // set LongClick
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.mLongClick(v, position, item);
                    return true;
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.plancard_amt_adapter)
        TextView plancardAmtAdapter;
        @BindView(R.id.plancard_fee_adapter)
        TextView plancardFeeAdapter;
        @BindView(R.id.plancard_status_adapter)
        TextView plancardStatusAdapter;
        @BindView(R.id.plancard_date_adapter)
        TextView plancardDateAdapter;
        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    private String format(String i) {
        if (i.equals("00")) {
            return "未确认";
        }
        if (i.equals("01")) {
            return "执行中";
        }
        if (i.equals("02")) {
            return "已完成";
        }
        if (i.equals("03")) {
            return "失效";
        }
        if (i.equals("04")) {
            return "异常处理中";
        }
        if (i.equals("05")) {
            return "异常失败";
        }
        if (i.equals("06")) {
            return "已退还保留金";
        }
        if (i.equals("07")) {
            return "确认中";
        }
        if (i.equals("08")) {
            return "退款中";
        }
        return i;
    }

}
