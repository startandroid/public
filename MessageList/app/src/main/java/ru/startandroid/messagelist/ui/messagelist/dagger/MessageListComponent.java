package ru.startandroid.messagelist.ui.messagelist.dagger;

import dagger.Subcomponent;
import ru.startandroid.messagelist.ui.messagelist.MessageListFragment;

@MessageListScope
@Subcomponent(modules = MessageListModule.class)
public interface MessageListComponent {
    void injectView(MessageListFragment messageListFragment);
}
