package shop.mgzj.petshop.service;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import shop.mgzj.petshop.R;
import shop.mgzj.petshop.dto.AjaxResult;
import shop.mgzj.petshop.harubase.base.HaruApp;
import shop.mgzj.petshop.harubase.tools.Constant;
import shop.mgzj.petshop.harubase.tools.HLog;
import shop.mgzj.petshop.harubase.tools.OKHttpTool;
import shop.mgzj.petshop.vo.User;

/**
 * Created by 星野悠 on 2017/2/23.
 */

public class UserService extends Service{

    public static void login(User user, final CallBack<AjaxResult> callBack){
        if(callBack == null){
            return;
        }
        OKHttpTool.sendOkHttpRequest(HaruApp.context().getString(R.string.host) + HaruApp.context().getString(R.string.action_login)+"&myname="+user.userName+"&mypsd="+user.password , new Callback() {
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
                final AjaxResult ajaxResult = Constant.GSON.fromJson(json,  AjaxResult.class) ;
                post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onResponse(ajaxResult);
                    }
                });
            }
        });
    }

    public static void register(User user, final CallBack<AjaxResult> callBack){
        OKHttpTool.sendOkHttpRequest(HaruApp.context().getString(R.string.host) + HaruApp.context().getString(R.string.action_register)+"&username="+user.userName+"&userpass="+user.password+"&useremail="+user.email, new Callback() {
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
                HLog.e("TAG", json);
                final AjaxResult ajaxResult = Constant.GSON.fromJson(json,  AjaxResult.class) ;
                post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onResponse(ajaxResult);
                    }
                });
            }
        });
    }
}
