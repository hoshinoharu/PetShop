package shop.mgzj.petshop.harubase.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

import shop.mgzj.petshop.R;
import shop.mgzj.petshop.harubase.tools.HLog;
import shop.mgzj.petshop.harubase.tools.ScreenTool;

/**
 * Created by 星野悠 on 2017/2/27.
 */

public class HaruAppBarLayout extends AppBarLayout {
    private boolean containStatusBar;
    public HaruAppBarLayout(Context context) {
        super(context);
        init();
    }
    public HaruAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HaruAppBarLayout) ;
        containStatusBar = array.getBoolean(R.styleable.HaruAppBarLayout_containStatusBar, false);
        init();
    }

    private void init() {

    }
    int t = 0 ;
     @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         t ++ ;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = ScreenTool.getActionBarSize()*2 ;
        if(containStatusBar) {
            height += ScreenTool.getStatusBarHeight() ;
            this.setPadding(0, ScreenTool.getStatusBarHeight(), 0, 0);
        }
        HLog.e("TAG", "onMeasure", t);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, heightMode));
    }

}
