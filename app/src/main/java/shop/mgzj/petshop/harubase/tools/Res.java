package shop.mgzj.petshop.harubase.tools;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import shop.mgzj.petshop.harubase.base.HaruApp;


/**
 * Created by 星野悠 on 2017/1/6.
 */

public class Res {

    public static int dimen(@NonNull Context context, @DimenRes int resId) {
        return (int) context.getResources().getDimension(resId);
    }
    public static float dimen(@DimenRes int resId) {
        return HaruApp.context().getResources().getDimension(resId);
    }

    private static DisplayMetrics metrics ;

    public static float dimen(int unit, float size){
        if(metrics == null){
            metrics = Resources.getSystem().getDisplayMetrics() ;
        }
        return TypedValue.applyDimension(unit, size, metrics);
    }

    public static int color(@NonNull Context context,@ColorRes int resId) {
        return context.getResources().getColor(resId);
    }

    public static int color(@ColorRes int resId){
        return HaruApp.context().getResources().getColor(resId) ;
    }

    public static int integer(@NonNull Context context,@IntegerRes int resId) {
        return context.getResources().getInteger(resId);
    }

    public static String string(@StringRes int resId){
        return HaruApp.context().getString(resId) ;
    }

}
