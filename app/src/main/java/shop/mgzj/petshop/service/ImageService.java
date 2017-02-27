package shop.mgzj.petshop.service;

import com.github.why168.modle.BannerInfo;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import shop.mgzj.petshop.R;
import shop.mgzj.petshop.harubase.base.HaruApp;
import shop.mgzj.petshop.harubase.tools.Constant;
import shop.mgzj.petshop.harubase.tools.OKHttpTool;

/**
 * Created by 星野悠 on 2017/2/25.
 */

public class ImageService extends Service {

    public static void getBanners(final CallBack<ArrayList<BannerInfo>> callBack){
        if(callBack == null){
            return;
        }
        OKHttpTool.sendOkHttpRequest(HaruApp.context().getString(R.string.host) + HaruApp.context().getString(R.string.action_getAllBanners), new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string() ;
                String[] bannerUrls = Constant.GSON.fromJson(json, String[].class);
                final ArrayList<BannerInfo> bannerInfoArrayList = new ArrayList<BannerInfo>() ;
                for(String url:bannerUrls){
                    bannerInfoArrayList.add(new BannerInfo( url, ""));
                }
                post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onResponse(bannerInfoArrayList);
                    }
                });
            }
        });
    }
}
