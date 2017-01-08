package ru.startandroid.messagelist.ui.messagelist.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import rx.Observable;

public class TryAgainHolder extends RecyclerView.ViewHolder {

    private final Observable<Void> clicks;

    public TryAgainHolder(View itemView) {
        super(itemView);
        clicks = RxView.clicks(itemView);
    }

    public Observable<Void> getTryAgainClicks() {
        return clicks;
    }
}
