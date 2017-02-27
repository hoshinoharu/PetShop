package shop.mgzj.petshop.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.why168.LoopViewPagerLayout;
import com.github.why168.listener.OnLoadImageViewListener;
import com.github.why168.modle.BannerInfo;
import com.github.why168.modle.IndicatorLocation;
import com.github.why168.modle.LoopStyle;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import shop.mgzj.petshop.R;
import shop.mgzj.petshop.harubase.base.AutoInjecter;
import shop.mgzj.petshop.harubase.base.HaruActivity;
import shop.mgzj.petshop.harubase.components.HaruAppBarLayout;
import shop.mgzj.petshop.harubase.components.HaruScrollView;
import shop.mgzj.petshop.harubase.tools.ScreenTool;
import shop.mgzj.petshop.service.ImageService;
import shop.mgzj.petshop.service.Service;

/**
 * Created by 星野悠 on 2017/2/25.
 */

public class HomePageActivity extends HaruActivity {

    /*视图控件*/
    @AutoInjecter.ViewInject(R.id.bannerLayout) private LoopViewPagerLayout bannerLayout ;
    @AutoInjecter.ViewInject(R.id.scrollView) private HaruScrollView scrollView ;
    @AutoInjecter.ViewInject(R.id.tabLot_tabs) private TabLayout tabLot_tabs ;
    @AutoInjecter.ViewInject(R.id.toolbar) private Toolbar toolbar ;
    @AutoInjecter.ViewInject(R.id.collapseLayout) private CollapsingToolbarLayout collapseLayout ;
    @AutoInjecter.ViewInject(R.id.appbarLayout) private HaruAppBarLayout appbarLayout ;
    @AutoInjecter.ViewInject(R.id.txtVw_rightTab) private TextView txtVw_rightTab ;
    @AutoInjecter.ViewInject(R.id.searchInput) private MaterialEditText searchInput ;
    @AutoInjecter.ViewInject(R.id.searchLayout) private ViewGroup searchLayout ;
    /*数据*/
    private ArrayList<BannerInfo> bannerInfoArrayList;
    private float oriWidth = Integer.MIN_VALUE ;

    public static final float BANNER_SCALE = 3.13f ;


    @Override
    public int getContentViewId() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenTool.noStatusBar(this);
        initTabs();
        initToolbar();
        queryBanners();
    }

    private void initTabs() {
        for (int i = 0; i <= 20; i++){
            tabLot_tabs.addTab(tabLot_tabs.newTab().setText("标签"+i));
        }
    }

    private void initToolbar() {
        this.setSupportActionBar(this.toolbar);
        this.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float fraction = Math.abs(verticalOffset)*1f/ScreenTool.getActionBarSize() ;
                txtVw_rightTab.setX(0-txtVw_rightTab.getWidth()*fraction);
                searchInput.setTranslationX(0-txtVw_rightTab.getWidth()*fraction);
            }
        });
    }

    private void queryBanners() {
        ImageService.getBanners(new Service.CallBack<ArrayList<BannerInfo>>() {
            @Override
            public void onResponse(ArrayList<BannerInfo> bannerInfos) {
                bannerInfoArrayList = bannerInfos ;
                initBanner();
            }

            @Override
            public void onFail(Exception e) {
                errorToast(getString(R.string.server_error));
            }
        });
    }


    private void initBanner() {
        if(bannerInfoArrayList == null){
            return;
        }
        this.bannerLayout.setLoop_ms(2000);
        this.bannerLayout.setLoop_duration(1000);
        this.bannerLayout.setLoop_style(LoopStyle.Depth);
        this.bannerLayout.setIndicatorLocation(IndicatorLocation.Left);
        this.bannerLayout.initializeData(this);
        this.bannerLayout.setOnLoadImageViewListener(new OnLoadImageViewListener() {
            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context) ;
                imageView.setLayoutParams(new LoopViewPagerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void onLoadImageView(ImageView imageView, Object parameter) {
                Glide.with(imageView.getContext()).load(parameter).into(imageView);
            }
        });
        this.bannerLayout.setLoopData(bannerInfoArrayList);

        //确保图片分辨率
        this.bannerLayout.getLayoutParams().height = (int) (ScreenTool.getScreenSize().widthPixels/BANNER_SCALE);

        this.bannerLayout.startLoop();
    }
}
