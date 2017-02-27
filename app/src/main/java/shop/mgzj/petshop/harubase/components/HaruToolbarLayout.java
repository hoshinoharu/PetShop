package shop.mgzj.petshop.harubase.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import shop.mgzj.petshop.R;
import shop.mgzj.petshop.harubase.tools.ScreenTool;
import shop.mgzj.petshop.harubase.tools.ViewTool;

/**
 * Created by 星野悠 on 2017/2/23.
 */

public class HaruToolbarLayout extends CoordinatorLayout {

/*
    属性设置
    app:includeStatusBar="true"
    app:title="登录页面"
    app:titleColor="#fff"
    app:leftHint="返回"
    app:leftHintColor="#fff"
    app:leftDrawable="@drawable/ic_password"
*/

    private boolean hasInited = false ;
    private boolean includeStatusBar = false ;
    private boolean firstMeasure = true ;
    private int oriHeight ;

    //工具条
    private Toolbar toolbar ;

    //最外面的容器
    private FrameLayout container ;

    //标题
    private TextView titleView;

    //工具条容器 左边是按钮 右边是toolbar
    private LinearLayout toolbarLayout;

    //左边按钮和图片的容器用于响应点击
    private LinearLayout leftLayout;

    //左边的按钮
    private ImageView leftImg ;

    //左边图片按钮说明
    private TextView leftImgHint;

    private String title ;
    private String leftHint;
    private int titleColor ;
    private int leftHintColor ;
    private int leftImgResId ;
    public HaruToolbarLayout(Context context) {
        super(context);
        init();
    }

    public HaruToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //判断statusBar是否透明
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HaruToolbarLayout);
        includeStatusBar = array.getBoolean(R.styleable.HaruToolbarLayout_includeStatusBar, false);
        title = array.getString(R.styleable.HaruToolbarLayout_title) ;
        titleColor = array.getColor(R.styleable.HaruToolbarLayout_titleColor, Color.WHITE) ;
        leftHint = array.getString(R.styleable.HaruToolbarLayout_leftHint) ;
        leftHintColor = array.getColor(R.styleable.HaruToolbarLayout_leftHintColor, Color.WHITE) ;
        leftImgResId = array.getResourceId(R.styleable.HaruToolbarLayout_leftDrawable, -1) ;
        array.recycle();
        init();
    }

    public void init(){
        if(hasInited){
            return;
        }
        hasInited = true ;
        //设置默认大小为actionBarSize
        this.oriHeight = ScreenTool.getActionBarSize() ;
        initContainer();
        initToolbarLayout();
        initLeftLayout();
        initLeftImg();
        initLeftImgInfo();
        initToolbar();
        initTitle();
    }

    private void initLeftLayout() {
        leftLayout = new LinearLayout(getContext()) ;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) ;
        leftLayout.setLayoutParams(layoutParams);
        toolbarLayout.addView(leftLayout);
    }

    //初始化左边文字提示
    private void initLeftImgInfo() {
        this.leftImgHint = new TextView(getContext()) ;
        if(leftHint != null){
            leftImgHint.setText(leftHint);
        }
        leftImgHint.setTextColor(leftHintColor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) ;
        layoutParams.gravity = Gravity.CENTER ;
        layoutParams.rightMargin = ViewTool.dip2px(16) ;
        leftImgHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        leftLayout.addView(leftImgHint, layoutParams);

    }

    //初始化左边提示按钮
    private void initLeftImg() {
        this.leftImg = new ImageView(getContext()) ;
        this.leftImg.setScaleType(ImageView.ScaleType.FIT_XY);
        if(leftImgResId >= 0){
            this.leftImg.setImageResource(leftImgResId);
        }
        int margin = ViewTool.dip2px(16) ;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.oriHeight-margin*2, this.oriHeight-margin*2);
        layoutParams.topMargin = margin ;
        layoutParams.leftMargin = margin ;
        layoutParams.bottomMargin = margin ;
        layoutParams.rightMargin = margin/8 ;
        leftLayout.addView(this.leftImg, layoutParams);
    }

    //初始化工具栏容器
    private void initToolbarLayout() {
        toolbarLayout = new LinearLayout(getContext());
        toolbarLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.container.addView(toolbarLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    //初始化标题
    private void initTitle() {
        titleView = new TextView(getContext()) ;
        titleView.getPaint().setFakeBoldText(true);
        titleView.setTextColor(titleColor);
        if(title != null){
            titleView.setText(title);
        }
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER ;
        this.container.addView(titleView, layoutParams);
    }

    private void initContainer() {
        container = new FrameLayout(getContext()) ;
        FrameLayout.LayoutParams containerLayout = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, this.oriHeight);
        containerLayout.gravity = Gravity.BOTTOM  ;
        container.setLayoutParams(containerLayout) ;
        this.addView(container, containerLayout);
    }

    private void initToolbar() {
        this.toolbar = new Toolbar(this.getContext()) ;
        toolbar.setTitle("");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) ;
        this.toolbarLayout.addView(this.toolbar, layoutParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(firstMeasure){
            this.oriHeight = this.getMeasuredHeight() ;
            firstMeasure = false ;
        }
        this.adjustHeight();
    }


    //调整高度
    public void adjustHeight() {
        if(this.includeStatusBar){
            container.getLayoutParams().height = this.oriHeight  ;
            container.setY(ScreenTool.getStatusBarHeight());
            this.getLayoutParams().height = this.oriHeight + ScreenTool.getStatusBarHeight() ;
        }else {
            this.getLayoutParams().height = this.oriHeight ;
        }
    }

    public Toolbar getToolbar(){
        return this.toolbar ;
    }
    public TextView getTitleView(){
        return this.titleView;
    }
    public ImageView getLeftImg(){
        return this.leftImg ;
    }

    public TextView getLeftImgHint(){
        return this.leftImgHint ;
    }

    public void setLeftOnClickListener(View.OnClickListener listener){
        this.leftLayout.setOnClickListener(listener);
    }
}
