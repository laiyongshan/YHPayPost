package com.yzf.Cpaypos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class MerchTransAdapter extends SimpleRecAdapter<GetOrderListResult.DataBean, MerchTransAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;



    public MerchTransAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_merchtrans;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GetOrderListResult.DataBean item = data.get(position);
        holder.transAdapterAmtTv.setText("￥" + item.getAmt() + "元");
        holder.transAdapterResultTv.setText(item.getResult());
        if (!item.getResultCode().equals("01")) {
            holder.transAdapterResultTv.setTextColor(context.getResources().getColor(R.color.text_red));
        } else {
            holder.transAdapterResultTv.setTextColor(context.getResources().getColor(R.color.text_6));
        }
        holder.transAdapterTypeTv.setText(item.getServiceName());
        holder.transAdapterTimeTv.setText(item.getTransTime());
        int id = getImgId("t" + item.getServiceId());
        if (id <=0) {
            id = getImgId("t00" );
        }
        RoundedBitmapDrawable circleDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), id2Bitmap(id));
        //设置为圆形
        circleDrawable.setCircular(true);
        holder.transAdapterImgIv.setImageDrawable(circleDrawable);

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

        @BindView(R.id.trans_adapter_amt_tv)
        TextView transAdapterAmtTv;
        @BindView(R.id.trans_adapter_result_tv)
        TextView transAdapterResultTv;
        @BindView(R.id.trans_adapter_type_tv)
        TextView transAdapterTypeTv;
        @BindView(R.id.trans_adapter_time_tv)
        TextView transAdapterTimeTv;
        @BindView(R.id.trans_adapter_img_iv)
        ImageView transAdapterImgIv;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    private Bitmap id2Bitmap(int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }

    private int getImgId(String imgName) {
        int id = -1;
        try {
            id = context.getResources().getIdentifier(imgName, "mipmap", context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

}
