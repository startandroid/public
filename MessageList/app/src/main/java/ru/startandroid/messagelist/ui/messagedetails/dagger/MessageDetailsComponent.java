package ru.startandroid.messagelist.ui.messagedetails.dagger;

import dagger.Subcomponent;
import ru.startandroid.messagelist.ui.messagedetails.MessageDetailsFragment;

@Subcomponent()
public interface MessageDetailsComponent {
    void injectView(MessageDetailsFragment messageDetailsFragment);
}
