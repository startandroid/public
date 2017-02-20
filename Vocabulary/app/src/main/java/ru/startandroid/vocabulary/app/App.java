package ru.startandroid.vocabulary.app;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private ComponentHolder componentHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        componentHolder = new ComponentHolder(this);
        componentHolder.init();
    }

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

    public ComponentHolder getComponentHolder() {
        return componentHolder;
    }

}
