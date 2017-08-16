package ru.startandroid.messagelist.app.dagger.module;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.startandroid.messagelist.BuildConfig;
import ru.startandroid.messagelist.app.dagger.scope.AppScope;
import ru.startandroid.messagelist.data.webapi.MessagesApi;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Module
public class NetworkModule {

    @Qualifier
    @Retention(RUNTIME)
    public @interface BaseUrl {
    }

    private static final String BASE_URL = "https://rawgit.com/startandroid/data/master/messages/";

    @AppScope
    @Provides
    MessagesApi provideApiService(Retrofit retrofit) {
        return retrofit.create(MessagesApi.class);
    }

    @Provides
    Retrofit provideRetrofit(@BaseUrl String baseUrl, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(client)
                .build();
    }


    @BaseUrl
    @Provides
    String provideBaseUrl() {
        return BASE_URL;
    }

    @Provides
    Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface NetworkScheduler {
    }

    @NetworkScheduler
    @Provides
    Scheduler provideDbScheduler() {
        return Schedulers.io();
    }

}
