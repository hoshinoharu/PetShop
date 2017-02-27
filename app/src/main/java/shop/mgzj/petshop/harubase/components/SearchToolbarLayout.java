package shop.mgzj.petshop.harubase.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import shop.mgzj.petshop.R;
import shop.mgzj.petshop.harubase.tools.AnimeTool;
import shop.mgzj.petshop.harubase.tools.HLog;
import shop.mgzj.petshop.harubase.tools.ScreenTool;
import shop.mgzj.petshop.harubase.tools.ViewTool;

/**
 * Created by 星野悠 on 2017/2/25.
 */

public class SearchToolbarLayout extends CoordinatorLayout {

    private boolean containStatusBar;
    private long animeDuration = 500;
    private boolean canFold = true ;
    private boolean canShow = true ;

    /*尺寸*/
    private int minHeight ;
    private int height ;

    /*视图控件*/
    private LinearLayout container;
    private TabLayout tabLayout ;
    private LinearLayout searchLayout ;
    private MaterialEditText searchInput ;
    private View rightTab ;
    private ImageButton searchBtn ;
    private Toolbar toolbar ;

    public SearchToolbarLayout(Context context) {
        super(context);
        init();
    }
    public SearchToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchToolbarLayout);
//        containStatusBar = array.getBoolean(R.styleable.SearchToolbarLayout_containStatusBar, false);
//        init();
    }

    private void init() {
        minHeight = ScreenTool.getActionBarSize() ;
        if(containStatusBar){
            minHeight += ScreenTool.getStatusBarHeight() ;
        }
        this.setMinimumHeight(minHeight);
        height = minHeight + ScreenTool.getActionBarSize() ;
        rightTab = new View(getContext()) ;
        initContainer();
        initTablayout();
        initSearchLayout();
        initAnime();
        initRightTab();
    }


    private void initContainer() {
        this.container = new LinearLayout(getContext());
        this.container.setOrientation(LinearLayout.VERTICAL);
        if(containStatusBar){
            this.container.setY(ScreenTool.getStatusBarHeight());
        }
        this.addView(this.container, new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }

    private void initTablayout() {
        this.tabLayout = new TabLayout(getContext()) ;
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        this.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for(int i = 0 ;i <= 20; i++){
            TextView view = new TextView(getContext()) ;
            view.setText("标签"+i);
            view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            view.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            view.setGravity(Gravity.CENTER);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            this.tabLayout.addTab(tabLayout.newTab().setCustomView(view));
        }
        this.container.addView(tabLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenTool.getActionBarSize()));
    }

    private void initSearchLayout() {
        this.searchLayout = new LinearLayout(getContext()) ;
        searchLayout.setBackgroundColor(Color.RED);
        //右边显示当前选择的标签
        searchLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.searchLayout.addView(rightTab);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenTool.getActionBarSize()) ;
        this.container.addView(searchLayout, layoutParams);
        initSearchInput();
        initSearchBtn();
        initToolbar();
    }

    private void initToolbar() {
        this.toolbar = new Toolbar(getContext()) ;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ScreenTool.getActionBarSize(), ScreenTool.getActionBarSize()) ;
        toolbar.setBackgroundColor(Color.CYAN);
        this.searchLayout.addView(toolbar, layoutParams);
    }

    private void initSearchBtn() {
        this.searchBtn = new ImageButton(getContext()) ;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ScreenTool.getActionBarSize(), ScreenTool.getActionBarSize());
        searchBtn.setImageResource(R.drawable.logo_x);
        searchBtn.setScaleType(ImageView.ScaleType.FIT_XY);
        this.searchLayout.addView(searchBtn, layoutParams);
    }

    //初始化输入框
    private void initSearchInput() {
        this.searchInput = new MaterialEditText(getContext()) ;
        searchInput.setHideUnderline(true);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT) ;
        layoutParams.weight = 1 ;
        searchInput.setBackgroundColor(Color.WHITE);
        int padding = ViewTool.dip2px(16) ;
        searchInput.setPaddings(padding, 0, padding, 0);
        searchInput.setMaxLines(1);
        this.searchLayout.addView(searchInput, layoutParams);
    }
    public void attach2HaruScrollView(HaruScrollView scrollView){
        scrollView.addOnScrollChangeListener(new HaruScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(HaruScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY > oldScrollY){
                    fold();
                }else if(scrollY < oldScrollY){
                    show();
                }
            }
        });
    }

    private ObjectAnimator showAnime ;
    private ObjectAnimator foldAnime ;
    private float curY ;
    private void initAnime() {
        this.showAnime = ObjectAnimator.ofFloat(this, "haru", this.getY()+minHeight-height, this.getY());
        this.showAnime.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                canShow = false ;
                canFold = true ;
            }
        });
        this.showAnime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (float) valueAnimator.getAnimatedValue();
                float fraction = valueAnimator.getAnimatedFraction() ;
                tabLayout.setAlpha(fraction);
                SearchToolbarLayout.this.setY(val);
                tabLayout.setY(-val);
                rightTab.setScaleX(1-fraction);
                float width = rightTab.getWidth()*(1-fraction) ;
                searchInput.setX(width);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) searchInput.getLayoutParams();
                layoutParams.width = (int) (searchLayout.getWidth()-ScreenTool.getActionBarSize()*2-width) ;
                searchInput.setLayoutParams(layoutParams);
                HLog.e("TAG",searchInput.getWidth(), layoutParams.width);
            }
        });
        this.showAnime.setDuration(animeDuration) ;
        this.foldAnime = ObjectAnimator.ofFloat(this, "haru", this.getY(), this.getY()+minHeight-height);
        this.foldAnime.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                canFold = false ;
                canShow = true ;
                initRightTab();
                rightTab.setPivotX(0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        this.foldAnime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (float) valueAnimator.getAnimatedValue();
                float fraction = 1-valueAnimator.getAnimatedFraction() ;
                tabLayout.setAlpha(fraction);
                SearchToolbarLayout.this.setY(val);
                tabLayout.setY(-val);
                rightTab.setScaleX(1-fraction);
                float width = rightTab.getWidth()*(1-fraction) ;
                searchInput.setX(width);
                searchInput.getLayoutParams().width = (int) (searchLayout.getWidth()-ScreenTool.getActionBarSize()*2-width) ;
            }
        });
        this.foldAnime.setDuration(animeDuration) ;
    }

    private void initRightTab() {
        TabLayout.Tab tab = getSelectedTab() ;
        ViewGroup view = (ViewGroup) tab.getCustomView().getParent();
        rightTab.setLayoutParams(new LinearLayout.LayoutParams(view.getLayoutParams()));
        rightTab.getLayoutParams().width = view.getWidth() ;
        rightTab.getLayoutParams().height = view.getHeight() ;
        rightTab.setBackgroundColor(Color.BLACK);
    }

    public void show() {
        if(!canShow || AnimeTool.isRunning(foldAnime)){
            return;
        }
        AnimeTool.startAnimeWhenNotIsRunning(this.showAnime);
    }

    public void fold() {
        if(!canFold || AnimeTool.isRunning(showAnime)){
            return;
        }
        AnimeTool.startAnimeWhenNotIsRunning(this.foldAnime);
    }

    public TabLayout.Tab getSelectedTab(){
        return this.tabLayout.getTabAt(this.tabLayout.getSelectedTabPosition());
    }
}
