package com.reklamar.reklamaexperience;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applovin.nativeAds.AppLovinNativeAd;
import com.applovin.nativeAds.AppLovinNativeAdLoadListener;
import com.applovin.sdk.AppLovinSdk;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class NativeActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progress;
    protected ImageLoader imageLoader;

    List<AppLovinNativeAd> class_objects;
    TextView emptyView;
    RekListAdapter adapter;

    private Handler hSuccess;
    private Handler hError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.native_name);

        listView = findViewById(R.id.list);
        progress = findViewById(R.id.progress);
        emptyView = findViewById(R.id.empty_list);

        progressEnabled(false);

        imageLoader = ImageLoader.getInstance();

        class_objects = new ArrayList<>();
        class_objects.clear();

        adapter = new RekListAdapter(class_objects);
        listView.setAdapter(adapter);

        hSuccess = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                List<AppLovinNativeAd> nativeAds = (List<AppLovinNativeAd>) msg.obj;

                progressEnabled(false);
                class_objects.clear();
                if(nativeAds.size() > 0){
                    SupportClass.sendShowAppMetrica("Native");

                    class_objects.addAll(nativeAds);
                    adapter.notifyDataSetChanged();
                }else{
                    SupportClass.ToastMessage(NativeActivity.this, "List<AppLovinNativeAd> size = 0");
                    listView.setEmptyView(emptyView);
                }

                return true;
            }
        });

        hError = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                progressEnabled(false);
                SupportClass.ToastMessage(NativeActivity.this, "Ошибка, код: " + msg.what);
                listView.setEmptyView(emptyView);

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SupportClass.sendClickAppMetrica("Native");

                AppLovinNativeAd ad = class_objects.get(position);

                ad.launchClickTarget(NativeActivity.this);
            }
        });

        queryNativeADs();
    }

    private void queryNativeADs(){
        progressEnabled(true);

        SupportClass.sendRequestAppMetrica("Native");
        final AppLovinSdk sdk = AppLovinSdk.getInstance(NativeActivity.this);
        sdk.getNativeAdService().loadNativeAds(10, new AppLovinNativeAdLoadListener() {
            @Override
            public void onNativeAdsLoaded(List<AppLovinNativeAd> nativeAds) {
                Message msg = new Message();
                msg.obj = nativeAds;
                hSuccess.sendMessage(msg);
            }

            @Override
            public void onNativeAdsFailedToLoad(final int errorCode) {
                hError.sendEmptyMessage(errorCode);
            }
        });
    }

    private class RekListAdapter extends BaseAdapter {

        List<AppLovinNativeAd> listObjects;

        RekListAdapter(List<AppLovinNativeAd> objects) {
            listObjects = objects;
        }

        private class ViewHolder {
            ImageView imageView;
            ProgressBar progress;
            TextView titleText;
        }

        @Override
        public int getCount() {
            try {
                return listObjects.size();
            } catch(NullPointerException ex) {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.list_item_native_rek, parent, false);
                holder = new ViewHolder();
                holder.imageView = view.findViewById(R.id.item_image);
                holder.progress = view.findViewById(R.id.item_progress);
                holder.titleText = view.findViewById(R.id.item_title);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            AppLovinNativeAd ad = listObjects.get(position);
            holder.titleText.setText(SupportClass.checkStringToNullAndTrim(ad.getTitle()));

            String imageURL = "";
            if(ad.getImageUrl() != null){
                imageURL = ad.getImageUrl();
            }

            imageLoader.displayImage(imageURL, holder.imageView, SupportClass.displayImageOptions(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    holder.progress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    switch (failReason.getType()) {
                        case IO_ERROR:
                            break;
                        case DECODING_ERROR:
                            break;
                        case NETWORK_DENIED:
                            break;
                        case OUT_OF_MEMORY:
                            break;
                        case UNKNOWN:
                            break;
                    }

                    holder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    holder.progress.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    holder.progress.setVisibility(View.GONE);
                }
            });

            return view;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SupportClass.sendCloseAppMetrica("Native");
    }

    private void progressEnabled(Boolean value){
        if(value){
            progress.setVisibility(View.VISIBLE);
        }else{
            progress.setVisibility(View.GONE);
        }
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