package ru.startandroid.messagelist.app;

import android.content.Context;

import ru.startandroid.messagelist.ui.messagedetails.dagger.MessageDetailsComponent;
import ru.startandroid.messagelist.ui.messagelist.dagger.MessageListComponent;

public class ComponentsHolder {

    private final Context context;
    private AppComponent appComponent;
    private MessageListComponent messageListComponent;
    private MessageDetailsComponent messageDetailsComponent;

    ComponentsHolder(Context context) {
        this.context = context;
    }


    void init() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(context)).build();
    }

    public MessageListComponent getMessageListComponent() {
        if (messageListComponent == null) {
            messageListComponent = appComponent.createMessageListComponent();
        }
        return messageListComponent;
    }

    public void releaseMessageListComponent() {
        messageListComponent = null;
    }

    public MessageDetailsComponent getMessageDetailsComponent() {
        if (messageDetailsComponent == null) {
            messageDetailsComponent = appComponent.createMessageDetailsComponent();
        }
        return messageDetailsComponent;
    }

    public void releaseMessageDetailsComponent() {
        messageDetailsComponent = null;
    }
}
