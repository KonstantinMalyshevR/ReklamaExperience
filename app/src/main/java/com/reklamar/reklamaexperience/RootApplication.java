package com.reklamar.reklamaexperience;

import android.content.Context;

import com.applovin.sdk.AppLovinSdk;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yandex.metrica.YandexMetrica;

public class RootApplication extends android.app.Application{

    private static RootApplication instance;

    public static RootApplication getInstance() {
        return instance;
    }

    public RootApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppLovinSdk.initializeSdk(this);

        YandexMetrica.activate(getApplicationContext(), "9f9f763c-79a4-4047-a64d-c96612814cb5");
        YandexMetrica.enableActivityAutoTracking(this);

        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }
}