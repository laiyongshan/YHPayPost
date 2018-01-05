package com.yzf.Cpaypos.adapter.HolderView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.model.servicesmodels.GetBannerListResult;

import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.imageloader.ILoader;

/**
 * ClaseName：AdHolderView
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/4 15:59
 * Modified By：
 * Fixtime：2017/5/4 15:59
 * FixDescription：
 */

public class AdHolderView implements Holder<GetBannerListResult.DataBean> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, GetBannerListResult.DataBean dataBean) {
        imageView.setImageResource(R.mipmap.empty_photo);
        if (dataBean.getUrl().length() > 20) {
            ILFactory.getLoader().loadNet(imageView, dataBean.getUrl(), new ILoader.Options(-1, R.mipmap.loading_fail_img).scaleType(ImageView.ScaleType.CENTER_CROP));
        } else {
            ILFactory.getLoader().loadAssets(imageView, dataBean.getUrl(), new ILoader.Options(-1, R.mipmap.loading_fail_img).scaleType(ImageView.ScaleType.CENTER_CROP));
        }
    }

}
