package ru.startandroid.messagelist.app;

import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.messagelist.BuildConfig;
import ru.startandroid.messagelist.data.message.Message;
import ru.startandroid.messagelist.data.message.MessageController;
import ru.startandroid.messagelist.events.EventBus;
import ru.startandroid.messagelist.storage.Preferences;
import ru.startandroid.messagelist.storage.database.DatabaseScheduler;
import ru.startandroid.messagelist.storage.database.ItemDatabaseRepository;
import ru.startandroid.messagelist.web.ApiService;
import rx.Scheduler;

@Module
 class AppModule {

    private final Context context;

    AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    MessageController provideMessageController(ItemDatabaseRepository<Message> messageDatabaseRepository, ApiService apiService,
                                               @DatabaseScheduler Scheduler scheduler, Preferences preferences)  {
        return new MessageController(messageDatabaseRepository, apiService, scheduler, preferences);
    }

    @AppScope
    @Provides
    EventBus provideEventBus() {
        return new EventBus();
    }

    @AppScope
    @Provides
    Picasso providePicasso(Context context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.loggingEnabled(BuildConfig.DEBUG);
        return builder.build();
    }


}
