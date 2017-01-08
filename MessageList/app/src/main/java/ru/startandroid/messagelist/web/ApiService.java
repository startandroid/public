package ru.startandroid.messagelist.web;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.startandroid.messagelist.data.message.Message;
import rx.Observable;

public interface ApiService {

    @GET("messages{page}.json")
    Observable<List<Message>> messages(@Path("page") int page);

}
