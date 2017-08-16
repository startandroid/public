package ru.startandroid.messagelist.data.webapi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.startandroid.messagelist.data.messages.ApiMessage;

public interface MessagesApi {

    @GET("messages{page}.json")
    Observable<List<ApiMessage>> messages(@Path("page") int page);

}
