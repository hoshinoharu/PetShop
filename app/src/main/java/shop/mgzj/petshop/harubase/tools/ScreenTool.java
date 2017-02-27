package shop.mgzj.petshop.harubase.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import shop.mgzj.petshop.harubase.base.HaruApp;


/**
 * Created by 星野悠 on 2017/1/4.
 */

public class ScreenTool {
    private static DisplayMetrics metrics = null ;
    private static DisplayMetrics realMetrics = null ;
    public static DisplayMetrics getScreenSize(){
        if(metrics == null) {
            metrics = new DisplayMetrics() ;
            WindowManager wm = (WindowManager) HaruApp.context().getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
        }
        return metrics ;
    }

    public static DisplayMetrics getRealScreenSize(){
        DisplayMetrics displayMetrics ;
        if(realMetrics == null){
            WindowManager windowManager = (WindowManager) HaruApp.context().getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            displayMetrics = new DisplayMetrics();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
                method.invoke(display, displayMetrics);
                realMetrics = displayMetrics ;
            }catch(Exception e){
                e.printStackTrace();
            }
            if(displayMetrics == null){
                realMetrics = ScreenTool.getScreenSize() ;
            }
        }
        return realMetrics ;
    }
    private static int statusBarHeight = -1 ;
    public static int getStatusBarHeight(){
        if(statusBarHeight == -1){
            int result = 0;
            int resourceId = HaruApp.context().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = HaruApp.context().getResources().getDimensionPixelSize(resourceId);
            }
            statusBarHeight = result ;
        }
        return statusBarHeight;
    }

    public static void noStatusBar(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        }
    }

    public static Bitmap takeViewPhoto(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static Bitmap noStatusBarScreen(Activity activity) {
        return curScreen(activity, true) ;
    }

    private static Map<String, Drawable> photoMapper = new HashMap<>() ;
    public static void saveActivityPhoto(Activity activity){
        photoMapper.put(activity.getClass().getName(),
                new BitmapDrawable(activity.getResources(), ScreenTool.noStatusBarScreen(activity))
        ) ;
    }

    private static Bitmap curScreen(Activity activity, boolean withoutStatusBar) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        int statusBarHeights = 0 ;
        if(! withoutStatusBar){
            statusBarHeights = ScreenTool.getStatusBarHeight() ;
        }
        // 获取屏幕宽和高
        int widths = ScreenTool.getScreenSize().widthPixels ;
        int heights = ScreenTool.getScreenSize().heightPixels ;

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }

    public static Bitmap curWindow(Activity activity){
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取屏幕宽和高
        int widths = ScreenTool.getRealScreenSize().widthPixels ;
        int heights = ScreenTool.getRealScreenSize().heightPixels ;

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                0, widths, heights);
        // 销毁缓存信息
        view.destroyDrawingCache();
        return bmp;
    }
    public static void saveWindow(Activity activity){
        photoMapper.put(activity.getClass().getName(),
                new BitmapDrawable(activity.getResources(), ScreenTool.curWindow(activity))
        ) ;
    }

    private static Map<String, PointF> touchMapper ;

    private static float touchX;
    private static float touchY;
    private static final GestureDetector detector = new GestureDetector(HaruApp.context(), new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            touchX =  e.getRawX();
            touchY =  e.getRawY();
            return super.onSingleTapUp(e);
        }
    });

    public static boolean onTouch(MotionEvent event) {
        return detector.onTouchEvent(event) ;
    }

    public static void saveTouch(Activity activity){
        if(touchMapper == null){
            touchMapper = new HashMap<>() ;
        }
        touchMapper.put(activity.getClass().getName(), new PointF(touchX, touchY));
    }

    public static PointF finalTouchInActivity(String activityName){
        if(touchMapper == null || touchMapper.get(activityName) == null){
            return new PointF() ;
        }
        return touchMapper.get(activityName) ;
    }


    public static Drawable getNoStatusBarPhotoInActivity(String preActivityName) {
        return photoMapper.get(preActivityName) ;
    }

    public static int getActionBarSize(){
        TypedValue typedValue = new TypedValue();
        HaruApp.context().getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        return (int) typedValue.getDimension(ScreenTool.getRealScreenSize());
    }

}
