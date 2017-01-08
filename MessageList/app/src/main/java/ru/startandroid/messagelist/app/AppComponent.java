package ru.startandroid.messagelist.app;

import dagger.Component;
import ru.startandroid.messagelist.storage.StorageModule;
import ru.startandroid.messagelist.ui.messagedetails.dagger.MessageDetailsComponent;
import ru.startandroid.messagelist.ui.messagelist.dagger.MessageListComponent;
import ru.startandroid.messagelist.web.WebModule;

@AppScope
@Component(modules = {AppModule.class, StorageModule.class, WebModule.class})
public interface AppComponent {
    MessageListComponent createMessageListComponent();
    MessageDetailsComponent createMessageDetailsComponent();
}
