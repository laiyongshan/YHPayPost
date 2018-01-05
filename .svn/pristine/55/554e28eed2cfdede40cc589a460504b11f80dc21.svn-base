package com.yzf.Cpaypos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/10.
 */

public class BankCardAdapter extends SimpleRecAdapter<GetWhiteCardListResult.DataBean, BankCardAdapter.ViewHolder> {

    public static final int TAG_VIEW = 0;

    private BankCardAdapter.OnMyItemClickListener listener;

    public void setOnMyItemClickListener(BankCardAdapter.OnMyItemClickListener listener) {
        this.listener = listener;

    }

    public interface OnMyItemClickListener {
        void myClick(View v, int pos, GetWhiteCardListResult.DataBean item);

        void mLongClick(View v, int pos, GetWhiteCardListResult.DataBean item);
    }

    public BankCardAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_bankcard;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GetWhiteCardListResult.DataBean item = data.get(position);
        holder.bankcardAdapterAcctnoTv.setText(setAcctno(item.getAcctNo()));
        holder.bankcardAdapterBanknameTv.setText(item.getCardDesc());
        holder.bankcardAdapterBanktypeTv.setText(setCardType(item.getCardType()));
        holder.bankcardAdapterPaydayTv.setText("账单日："+item.getAdd3()+"日");
        holder.bankcardAdapterRepaydayTv.setText("还款日："+item.getAdd4()+"日");
        holder.bankcardAdapterLimitTv.setText("额度：" + item.getCardLimit() + "元");
        if (item.getCardDesc().contains("工商") || item.getCardDesc().contains("中国银行")
                || item.getCardDesc().contains("招商")|| item.getCardDesc().contains("中信")) {
            holder.bank_bg.setBackground(ContextCompat.getDrawable(context, R.drawable.bankcard_red));
        } else if (item.getCardDesc().contains("农业") || item.getCardDesc().contains("邮政")) {
            holder.bank_bg.setBackground(ContextCompat.getDrawable(context, R.drawable.bankcard_green));
        } else if (item.getCardDesc().contains("交通") || item.getCardDesc().contains("浦")|| item.getCardDesc().contains("民生")
                || item.getCardDesc().contains("兴业")|| item.getCardDesc().contains("建设")) {
            holder.bank_bg.setBackground(ContextCompat.getDrawable(context, R.drawable.bankcard_blue));
        } else if (item.getCardDesc().contains("平安") || item.getCardDesc().contains("光大") || item.getCardDesc().contains("农村")) {
            holder.bank_bg.setBackground(ContextCompat.getDrawable(context, R.drawable.bankcard_yellow));
        } else {
            holder.bank_bg.setBackground(ContextCompat.getDrawable(context, R.drawable.bankcard_red));
        }
        int id = getImgId("b" + item.getCardInst());
        if (id > 0) {
            RoundedBitmapDrawable circleDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), id2Bitmap(id));
            //设置为圆形
            circleDrawable.setCircular(true);
            holder.bankIv.setImageDrawable(circleDrawable);
        }
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

    private String setAcctno(String acctNo) {
        if (!AppTools.isEmpty(acctNo)) {
            acctNo = "****     ****     ****     " + acctNo.substring(acctNo.length() - 4, acctNo.length());

        }
        return acctNo;
    }

    private String setCardType(String cartType) {
        String type = "储蓄卡";
        if (!AppTools.isEmpty(cartType)) {
            if (cartType.equals("00")) {
                type = "信用卡";
            } else if (cartType.equals("01")) {
                type = "储蓄卡";
            } else if (cartType.equals("02")) {
                type = "准贷记卡";
            }
        }
        return type;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bankcard_adapter_bankname_tv)
        TextView bankcardAdapterBanknameTv;
        @BindView(R.id.bankcard_adapter_banktype_tv)
        TextView bankcardAdapterBanktypeTv;
        @BindView(R.id.bankcard_adapter_acctno_tv)
        TextView bankcardAdapterAcctnoTv;
        @BindView(R.id.bankcard_adapter_payday_tv)
        TextView bankcardAdapterPaydayTv;
        @BindView(R.id.bankcard_adapter_repayday_tv)
        TextView bankcardAdapterRepaydayTv;
        @BindView(R.id.bankcard_adapter_limit_tv)
        TextView bankcardAdapterLimitTv;
        @BindView(R.id.bank_bg)
        LinearLayout bank_bg;
        @BindView(R.id.bank_iv)
        ImageView bankIv;


        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
