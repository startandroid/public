package ru.startandroid.vocabulary.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Utils {


    public static void callGoogleTranslateAppsEnToRu(Context context, String word) {
        callGoogleTranslateApps(context, word, "en", "ru");
    }

    public static void callGoogleTranslateApps(Context context, String word, String fromLang, String toLang) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setPackage("com.google.android.apps.translate");

        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority("translate.google.com")
                .path("/m/translate")
                .appendQueryParameter("q", word)
                .appendQueryParameter("tl", toLang) // target language
                .appendQueryParameter("sl", fromLang) // source language
                .build();
        //intent.setType("text/plain"); //not needed, but possible
        intent.setData(uri);
        context.startActivity(intent);

    }

}
