package shop.mgzj.petshop.service;


import android.os.Handler;
import android.os.Looper;

/**
 * Created by 星野悠 on 2017/2/23.
 */

public class Service {
    protected static Handler handler = new Handler(Looper.getMainLooper()) ;
    public interface CallBack<T>{
        void onResponse(T t);
        void onFail(Exception e) ;
    }
    protected static void post(Runnable runnable){
        handler.post(runnable) ;
    }
}
