package shop.mgzj.petshop.harubase.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by 星野悠 on 2017/2/23.
 */

public class HaruApp extends Application {

    private static Context context ;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext() ;
    }

    public static Context context(){
        return context ;
    }


}
