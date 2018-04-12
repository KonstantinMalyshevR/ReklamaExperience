package com.reklamar.reklamaexperience;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applovin.adview.AppLovinAdView;
import com.applovin.adview.AppLovinAdViewDisplayErrorCode;
import com.applovin.adview.AppLovinAdViewEventListener;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;

public class BannerActivity extends AppCompatActivity {

    String textViewString;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.banner_name);

        textView = findViewById(R.id.textView);

        textViewString = "Banner грузится, скоро появится...";
        textView.setText(textViewString);

        final AppLovinAdView adView = new AppLovinAdView( AppLovinAdSize.BANNER, this );

        SupportClass.sendRequestAppMetrica("Banner");

        adView.setAdLoadListener( new AppLovinAdLoadListener()
        {
            @Override
            public void adReceived(final AppLovinAd ad)
            {
                textViewString = textViewString + "\nBanner loaded";
                textView.setText(textViewString);
            }

            @Override
            public void failedToReceiveAd(final int errorCode)
            {
                textViewString = textViewString + "\nBanner failed to load with error code " + errorCode;
                textView.setText(textViewString);
            }
        } );

        adView.setAdDisplayListener( new AppLovinAdDisplayListener()
        {
            @Override
            public void adDisplayed(final AppLovinAd ad)
            {
                SupportClass.sendShowAppMetrica("Banner");

                textViewString = textViewString + "\nBanner Displayed";
                textView.setText(textViewString);
            }

            @Override
            public void adHidden(final AppLovinAd ad)
            {
                SupportClass.sendCloseAppMetrica("Banner");

                textViewString = textViewString + "\nBanner Hidden";
                textView.setText(textViewString);
            }
        } );

        adView.setAdClickListener( new AppLovinAdClickListener()
        {
            @Override
            public void adClicked(final AppLovinAd ad)
            {
                SupportClass.sendClickAppMetrica("Banner");

                textViewString = textViewString + "\nBanner Clicked";
                textView.setText(textViewString);
            }
        });

        adView.setAdViewEventListener( new AppLovinAdViewEventListener() {
            @Override
            public void adOpenedFullscreen(final AppLovinAd ad, final AppLovinAdView adView)
            {
                textViewString = textViewString + "\nBanner opened fullscreen";
                textView.setText(textViewString);
            }

            @Override
            public void adClosedFullscreen(final AppLovinAd ad, final AppLovinAdView adView)
            {
                textViewString = textViewString + "\nBanner closed fullscreen";
                textView.setText(textViewString);
            }

            @Override
            public void adLeftApplication(final AppLovinAd ad, final AppLovinAdView adView)
            {
                textViewString = textViewString + "\nBanner left application";
                textView.setText(textViewString);
            }

            @Override
            public void adFailedToDisplay(final AppLovinAd ad, final AppLovinAdView adView, final AppLovinAdViewDisplayErrorCode code)
            {
                textViewString = textViewString + "\nBanner failed to display with error code " + code;
                textView.setText(textViewString);
            }
        });

        final RelativeLayout bannerContainer = findViewById( R.id.banner_container );
        bannerContainer.addView( adView, new android.widget.FrameLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER ) );

        adView.loadNextAd();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}