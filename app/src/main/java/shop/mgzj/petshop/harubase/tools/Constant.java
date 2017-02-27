package shop.mgzj.petshop.harubase.tools;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Created by 星野悠 on 2017/1/11.
 */

public class Constant {
    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static final Handler MainHandler = new Handler(Looper.getMainLooper()) ;
}
