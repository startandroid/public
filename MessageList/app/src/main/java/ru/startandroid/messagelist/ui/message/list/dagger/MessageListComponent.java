package ru.startandroid.messagelist.ui.message.list.dagger;

import dagger.Subcomponent;
import ru.startandroid.messagelist.app.dagger.scope.FragmentScope;
import ru.startandroid.messagelist.base.dagger.BaseComponent;
import ru.startandroid.messagelist.base.dagger.BaseComponentBuilder;
import ru.startandroid.messagelist.ui.message.list.MessageListFragment;

@FragmentScope
@Subcomponent(modules = MessageListModule.class)
public interface MessageListComponent extends BaseComponent<MessageListFragment> {

    @Subcomponent.Builder
    interface Builder extends BaseComponentBuilder<MessageListComponent, MessageListModule> {

    }
}
