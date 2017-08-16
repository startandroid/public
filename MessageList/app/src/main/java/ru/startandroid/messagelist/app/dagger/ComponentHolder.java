package ru.startandroid.messagelist.app.dagger;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import ru.startandroid.messagelist.app.dagger.module.AppModule;
import ru.startandroid.messagelist.base.dagger.BaseComponent;
import ru.startandroid.messagelist.base.dagger.BaseComponentBuilder;
import ru.startandroid.messagelist.base.dagger.BaseModule;

public class ComponentHolder {

    private final Context context;

    @Inject
    Map<Class<?>, Provider<BaseComponentBuilder>> builders;

    private final Map<Class<?>, BaseComponent> components = new HashMap<>();
    private AppComponent appComponent;

    public ComponentHolder(Context context) {
        this.context = context;
    }

    public void init() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(context)).build();
        appComponent.injectComponentsHolder(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public BaseComponent getActivityComponent(Class<?> cls) {
        return getActivityComponent(cls, null);
    }

    public BaseComponent getActivityComponent(Class<?> cls, BaseModule module) {
        BaseComponent component = components.get(cls);
        if (component == null) {
            BaseComponentBuilder builder = builders.get(cls).get();
            if (module != null) {
                builder.module(module);
            }
            component = builder.build();
            components.put(cls, component);
        }
        return component;
    }

    public void releaseActivityComponent(Class<?> cls) {
        components.put(cls, null);

    }


}
