package ru.startandroid.messagelist.app.dagger.module;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import ru.startandroid.messagelist.base.dagger.BaseComponentBuilder;
import ru.startandroid.messagelist.ui.message.list.MessageListFragment;
import ru.startandroid.messagelist.ui.message.list.dagger.MessageListComponent;

@Module(subcomponents = {MessageListComponent.class})
public class AppSubComponentsModule {

    @Provides
    @IntoMap
    @ClassKey(MessageListFragment.class)
    BaseComponentBuilder provideMessageListComponentBuilder(MessageListComponent.Builder builder) {
        return builder;
    }

}
