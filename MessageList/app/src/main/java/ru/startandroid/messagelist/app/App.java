package ru.startandroid.messagelist.app;

import android.app.Application;
import android.content.Context;

import ru.startandroid.messagelist.app.dagger.ComponentHolder;

public class App extends Application {

    private ComponentHolder componentHolder;

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        componentHolder = new ComponentHolder(this);
        componentHolder.init();
    }

    public ComponentHolder getComponentHolder() {
        return componentHolder;
    }
}
