package ru.startandroid.messagelist.app.dagger.module;

import android.content.Context;

import com.squareup.picasso.Picasso;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.startandroid.messagelist.app.dagger.scope.AppScope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
    Context provideContext() {
        return context;
    }

    @UIScheduler
    @Provides
    Scheduler provideDbScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @AppScope
    @Provides
    Picasso providePicasso(Context context) {
        return Picasso.with(context);
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface UIScheduler {
    }


}
