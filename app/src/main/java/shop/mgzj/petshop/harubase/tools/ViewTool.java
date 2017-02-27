package shop.mgzj.petshop.harubase.tools;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;

import shop.mgzj.petshop.harubase.base.HaruApp;


/**
 * Created by 星野悠 on 2017/1/4.
 */

public class ViewTool {
    public static float[] getViewCenter(View view){
        float[] loc = new float[]{view.getX()+view.getWidth()/2, view.getY()+view.getHeight()/2};
        return loc ;
    }

    public static float[] fitViewByCenter(View view, float[] center){
        view.setX(center[0]-view.getWidth()/2);
        view.setY(center[1]-view.getHeight()/2);
        float[] newLoc = new float[]{view.getX(), view.getY()};
        return newLoc ;
    }

    public static void fitViewByCenterInY(View view, float[] center){
        view.setY(center[1]-view.getHeight()/2);
    }

    public static boolean checkInput(TextView textView){
        if(textView != null){
            return checkInput(textView.getText()) ;
        }
        return false;
    }

    public static boolean checkInput(CharSequence input){
        if(input != null && ! "".equals(input)){
            return true ;
        }
        return false ;
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int dip2px(float dpValue) {
        final float scale = HaruApp.context().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static <T>T getViewByName(Class<T> cls, Object object,String name){
        try {
            Field field = object.getClass().getDeclaredField(name) ;
            boolean isAc = field.isAccessible() ;
            field.setAccessible(true);
            Object obj = field.get(object);
            field.setAccessible(isAc);
            return (T) obj;
        } catch (Exception e) {
           HLog.ex("TAG", e);
        }
        return null ;
    }

    public static boolean isEventInView(View view, MotionEvent event){
        float ex = event.getRawX() ;
        float ey = event.getRawY() ;
        float x = view.getX() ;
        float y = view.getY() ;
        if(ex >= x && ex <= x + view.getWidth()){
            if(ey >= y && ey <= y + view.getHeight()){
                return true ;
            }
        }
        return false ;
    }

    public static void removeView(View view){
        ((ViewGroup)view.getParent()).removeView(view);
    }

    public static void addView(ViewGroup newPar, View child){
        if(child == null || newPar == null){
            return;
        }
        ViewGroup par = (ViewGroup) child.getParent();
        if(par != null){
            par.removeView(child);
        }
        newPar.addView(child);
    }
}
