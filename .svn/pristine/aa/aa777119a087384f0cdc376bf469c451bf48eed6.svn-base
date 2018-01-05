package com.yzf.Cpaypos.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;

import java.util.List;

/**
 * ClaseName：ListViewDialog
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/7 9:38
 * Modified By：
 * Fixtime：2017/3/7 9:38
 * FixDescription：
 */

public class ListViewDialog extends Dialog {

    private static ICardNoCallBack mCardNoCallBack;
    private final Context mContext;
    private ListView mListView;
    private TextView title;
    private LinearLayout addCardll;
    private List<GetWhiteCardListResult.DataBean> datalist;
    private Xdapter adapter;
    private String cardNo;

    public ListViewDialog(Context context, List<GetWhiteCardListResult.DataBean> datalist) {
        super(context);
        mContext = context;
        this.datalist = datalist;
        initView();
        initListView();
    }

    public interface ICardNoCallBack {
        void getCardno(String cardNo);

        void getCardBean(GetWhiteCardListResult.DataBean dataBean);

        void bondCard();
    }

    public static void setmCardNOCallBack(ICardNoCallBack mCardNoCallBack) {
        ListViewDialog.mCardNoCallBack = mCardNoCallBack;
    }

    public void setTitle(String name) {
        if (!AppTools.isEmpty(name)) {
            title.setText(name);
        }
    }

    private void initView() {
        View contentView = View.inflate(mContext, R.layout.content_dialog, null);
        mListView = (ListView) contentView.findViewById(R.id.content_dialog_lv);
        title = (TextView) contentView.findViewById(R.id.content_dialog_title);
        addCardll = (LinearLayout) contentView.findViewById(R.id.content_dialog_add_ll);
        addCardll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardNoCallBack.bondCard();
            }
        });
        setContentView(contentView);
    }

    private void initListView() {
        adapter = new ListViewDialog.Xdapter(mContext, datalist);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            return;
        }
        setHeight();
    }

    private void setHeight() {
        Window window = getWindow();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams attributes = window != null ? window.getAttributes() : null;
        if (window.getDecorView().getHeight() >= (int) (displayMetrics.heightPixels * 0.6)) {
            attributes.height = (int) (displayMetrics.heightPixels * 0.6);
        }
        window.setAttributes(attributes);
    }

    public class Xdapter extends BaseAdapter {
        private Context context;
        private List<GetWhiteCardListResult.DataBean> mData;

        public Xdapter(Context context, List<GetWhiteCardListResult.DataBean> mData) {
            this.context = context;
            this.mData = mData;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (mData != null) {
                return mData.size();
            }
            return 0;

        }

        private void setData(List<GetWhiteCardListResult.DataBean> mData) {
            this.mData = mData;
            notifyDataSetChanged();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ListViewDialog.Xdapter.ViewHolder mHolder;
            if (null == convertView) {
                convertView = LayoutInflater.from(context).inflate(R.layout.whitecardno_list_item, null);
                mHolder = new ListViewDialog.Xdapter.ViewHolder();
                mHolder.tx = (TextView) convertView.findViewById(R.id.cardNo_tx);
                mHolder.whitecardno_ll = (LinearLayout) convertView.findViewById(R.id.whitecardno_ll);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ListViewDialog.Xdapter.ViewHolder) convertView.getTag();
            }
            mHolder.tx.setText(mData.get(position).getAcctNo());
            mHolder.whitecardno_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardNo = mData.get(position).getAcctNo();
                    mCardNoCallBack.getCardno(cardNo);
                    mCardNoCallBack.getCardBean( mData.get(position));
                }
            });
            return convertView;
        }

        class ViewHolder {
            private TextView tx;
            private LinearLayout whitecardno_ll;
        }

    }
}
