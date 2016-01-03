package com.buybuyall.market;

import cn.common.bitmap.cache.disc.naming.FileNameGenerator;
import cn.common.bitmap.core.DisplayImageOptions;
import cn.common.bitmap.core.ImageLoader;
import cn.common.bitmap.core.ImageLoaderConfiguration;
import cn.common.bitmap.core.display.FadeInBitmapDisplayer;
import cn.common.ui.activity.BaseApplication;
import cn.common.utils.DisplayUtil;

public class MarketApplication extends BaseApplication {

    @Override
    protected void onConfig() {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(getContext());
        builder.memoryCacheExtraOptions(DisplayUtil.getSreenDimens().x, DisplayUtil.getSreenDimens().y);
        builder.threadPoolSize(20);
        builder.diskCacheFileNameGenerator(new FileNameGenerator() {

            @Override
            public String generate(String imageUri) {
                return imageUri.substring(imageUri.lastIndexOf("/"), imageUri.length());
            }
        });
        DisplayImageOptions.Builder dBuilder = new DisplayImageOptions.Builder();
        dBuilder.showImageOnLoading(R.drawable.default_img);
        dBuilder.cacheOnDisk(true);
        dBuilder.cacheInMemory(true);
        dBuilder.displayer(new FadeInBitmapDisplayer(800));
        dBuilder.showImageOnFail(R.drawable.default_img);
        builder.defaultDisplayImageOptions(dBuilder.build());
        ImageLoader.getInstance().init(builder.build());

    }

    @Override
    protected void onRelease() {

    }

    @Override
    protected BaseApplication getChildInstance() {
        return this;
    }
}
