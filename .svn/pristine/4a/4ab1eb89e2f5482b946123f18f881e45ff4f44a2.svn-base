package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.UploadPhotosActivity;

import java.io.File;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * ClaseName：PUploadPhotos
 * Description：上传图片逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/21 17:00
 * Modified By：
 * Fixtime：2017/3/21 17:00
 * FixDescription：
 */

public class PUploadPhotos extends XPresent<UploadPhotosActivity> {
    /**
     * 上传图片
     *
     * @param path
     * @param type
     * @param merchId
     */
    public void uploadPhoto(String path, final String type, String merchId) {
        // 创建 RequestBody，用于封装 请求RequestBody
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part pfile =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Api.getAPPService().uploadPhoto(pfile, type, merchId)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        if (baseResults.getRespCode().equals("00")) {
                            getV().showphotos(type, "success");
                        } else {
                            getV().showphotos(type, "fail");
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showphotos(type, "error");
                    }

                });
    }
}
