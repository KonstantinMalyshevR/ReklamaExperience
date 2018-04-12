package com.reklamar.reklamaexperience;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinSdk;

//Created by Developer on 11.04.18.

public class IntersitialActivity extends AppCompatActivity {

    String textViewString;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intersitial);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.interstitials_name);

        textView = findViewById(R.id.textView);

        textViewString = "Interstitials грузится, скоро появится...";
        textView.setText(textViewString);

        showInterstitial();
    }

    private void showInterstitial() {

        SupportClass.sendRequestAppMetrica("Interstitials");

        final AppLovinSdk sdk = AppLovinSdk.getInstance(IntersitialActivity.this);
        final AppLovinInterstitialAdDialog adDialog = AppLovinInterstitialAd.create(sdk, IntersitialActivity.this);

        adDialog.setAdLoadListener(new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd appLovinAd) {
                textViewString = textViewString + "\nInterstitials Received";
                textView.setText(textViewString);
            }

            @Override
            public void failedToReceiveAd(int i) {
                textViewString = textViewString + "\nInterstitials failedToReceive";
                textView.setText(textViewString);
            }
        });

        adDialog.setAdClickListener(new AppLovinAdClickListener() {
            @Override
            public void adClicked(AppLovinAd appLovinAd) {
                SupportClass.sendClickAppMetrica("Interstitials");

                textViewString = textViewString + "\nInterstitials Clicked";
                textView.setText(textViewString);
            }
        });

        adDialog.setAdDisplayListener(new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(AppLovinAd appLovinAd) {
                SupportClass.sendShowAppMetrica("Interstitials");

                textViewString = textViewString + "\nInterstitials Displayed";
                textView.setText(textViewString);
            }

            @Override
            public void adHidden(AppLovinAd appLovinAd) {
                SupportClass.sendCloseAppMetrica("Interstitials");

                textViewString = textViewString + "\nInterstitials Hidden";
                textView.setText(textViewString);
            }
        });

        adDialog.show();
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