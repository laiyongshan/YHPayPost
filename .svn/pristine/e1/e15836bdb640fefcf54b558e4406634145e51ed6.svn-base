package com.yzf.Cpaypos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.GetSubmerchResult;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class SubMerchAdapter extends SimpleRecAdapter<GetSubmerchResult.DataBean, SubMerchAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;

    public SubMerchAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_submerch;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GetSubmerchResult.DataBean item = data.get(position);
        String merchId = item.getMerchId();
        merchId = AppTools.isEmpty(merchId) ? "" : merchId.substring(0, 4) + "****" + merchId.substring(merchId.length() - 4, merchId.length());
        holder.submerchAdapterMerchidTv.setText("商户号：" + merchId);
        holder.submerchAdapterLeveTv.setText("级别：" + setLevel(item.getMerchLevel()));
        holder.submerchAdapterTimeTv.setText("创建时间：" + item.getCreateTime());
        holder.submerchAdapterStatusTv.setText("状态：" + setStatus(item.getStatus()));

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

    private String setLevel(String merchLevel) {
        if (!AppTools.isEmpty(merchLevel)) {
            if (merchLevel.equals("1")) {
                merchLevel = "黄金";
            } else if (merchLevel.equals("2")) {
                merchLevel = "铂金";
            } else if (merchLevel.equals("3")) {
                merchLevel = "钻石";
            } else if (merchLevel.equals("4")) {
                merchLevel = "合伙人";
            } else if (merchLevel.equals("5")) {
                merchLevel = "五星";
            }
        }
        return merchLevel;
    }

    private String setStatus(String Status) {
        if (!AppTools.isEmpty(Status)) {
            if (Status.equals("00")) {
                Status = "已认证";
            } else if (Status.equals("01")) {
                Status = "信息不全";
            } else if (Status.equals("02")) {
                Status = "图片不全";
            } else if (Status.equals("03")) {
                Status = "未认证";
            } else if (Status.equals("10")) {
                Status = "停用";
            } else if (Status.equals("30")) {
                Status = "审核中";
            }
        }
        return Status;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.submerch_adapter_merchid_tv)
        TextView submerchAdapterMerchidTv;
        @BindView(R.id.submerch_adapter_leve_tv)
        TextView submerchAdapterLeveTv;
        @BindView(R.id.submerch_adapter_time_tv)
        TextView submerchAdapterTimeTv;
        @BindView(R.id.submerch_adapter_status_tv)
        TextView submerchAdapterStatusTv;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
