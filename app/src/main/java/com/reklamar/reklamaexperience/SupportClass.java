package com.reklamar.reklamaexperience;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yandex.metrica.YandexMetrica;

import java.util.HashMap;
import java.util.Map;

public class SupportClass {

    public static void ToastMessage(Context context, String value){
        Toast toast = Toast.makeText(context, value, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String checkStringToNullAndTrim(String value){
        if(value != null){
            return value.trim();
        }else{
            return "";
        }
    }

    public static DisplayImageOptions displayImageOptions(){
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(800))
                .build();
    }

    //AppMetrica===========
    public static void sendRequestAppMetrica(String type){
        Map<String, Object> eventAttributes = new HashMap<>();
        eventAttributes.put("Тип рекламы", type);

        YandexMetrica.reportEvent("request_network", eventAttributes);
    }

    public static void sendShowAppMetrica(String type){
        Map<String, Object> eventAttributes = new HashMap<>();
        eventAttributes.put("Тип рекламы", type);

        YandexMetrica.reportEvent("show_network", eventAttributes);
    }

    public static void sendClickAppMetrica(String type){
        Map<String, Object> eventAttributes = new HashMap<>();
        eventAttributes.put("Тип рекламы", type);

        YandexMetrica.reportEvent("click_network", eventAttributes);
    }

    public static void sendCloseAppMetrica(String type){
        Map<String, Object> eventAttributes = new HashMap<>();
        eventAttributes.put("Тип рекламы", type);

        YandexMetrica.reportEvent("close_network", eventAttributes);
    }
}