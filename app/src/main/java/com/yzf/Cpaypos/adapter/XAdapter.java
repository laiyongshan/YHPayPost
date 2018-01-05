package com.yzf.Cpaypos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.model.servicesmodels.LoginResult;

import java.util.List;

public class XAdapter extends BaseAdapter {
    private Context context;
    private List<LoginResult.DataBean.ServiceListBean> mData;

    public XAdapter(Context context, List<LoginResult.DataBean.ServiceListBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    private void setData(List<LoginResult.DataBean.ServiceListBean> mData) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        XAdapter.ViewHolder mHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spiner_item, null);
            mHolder = new XAdapter.ViewHolder();
            mHolder.tx = (TextView) convertView.findViewById(R.id.dialoglist_item_tv);
            convertView.setTag(mHolder);
        } else {
            mHolder = (XAdapter.ViewHolder) convertView.getTag();
        }
        mHolder.tx.setText(mData.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        private TextView tx;
    }

}
