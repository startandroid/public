package ru.startandroid.messagelist.app;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private ComponentsHolder componentsHolder;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        componentsHolder = new ComponentsHolder(this);
        componentsHolder.init();
    }

    public ComponentsHolder getComponentsHolder() {
        return componentsHolder;
    }

}
