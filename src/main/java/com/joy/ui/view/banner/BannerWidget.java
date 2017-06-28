package com.joy.ui.view.banner;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;

import com.joy.ui.R;
import com.joy.ui.utils.DimenCons;
import com.joy.ui.view.banner.indicator.CircleIndicator;
import com.joy.ui.widget.ExBaseWidget;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

//import com.ToxicBakery.viewpager.transforms.ABaseTransformer;

/**
 * Created by KEVIN.DAI on 15/12/17.
 */
public class BannerWidget extends ExBaseWidget implements DimenCons {

    private BannerView mBannerView;

    public BannerWidget(Activity activity, PagerAdapter adapter) {
        super(activity);
        setContentView(R.layout.lib_view_banner, new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));

        mBannerView = findViewById(R.id.banner);
        mBannerView.setLayoutParams(new LayoutParams(MATCH_PARENT, SCREEN_WIDTH / 9 * 5));
        mBannerView.setAdapter(adapter);
        mBannerView.startAutoScroll();

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mBannerView);
    }

    @Override
    public void onResume() {
        mBannerView.startAutoScroll();
    }

    @Override
    public void onPause() {
        mBannerView.stopAutoScroll();
    }

    public void setHeight(int dp) {
        mBannerView.setLayoutParams(new LayoutParams(MATCH_PARENT, DP_1_PX * dp));
    }

//    public void setPageTransformer(ABaseTransformer transformer) {
//        mBannerView.setPageTransformer(true, transformer);
//    }

    public void setInterval(long interval) {
        mBannerView.setInterval(interval);
    }
}
