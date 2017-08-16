package ru.startandroid.messagelist.common.utils;

import java.net.HttpURLConnection;

import retrofit2.HttpException;

public class HttpUtils {

    public static boolean isHttp404(Throwable throwable) {
        return throwable instanceof HttpException
                && ((HttpException)throwable).code() == HttpURLConnection.HTTP_NOT_FOUND;
    }
}
