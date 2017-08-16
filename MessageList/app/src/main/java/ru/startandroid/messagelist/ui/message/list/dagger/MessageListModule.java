package ru.startandroid.messagelist.ui.message.list.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.messagelist.app.dagger.scope.FragmentScope;
import ru.startandroid.messagelist.base.dagger.BaseModule;
import ru.startandroid.messagelist.data.message.UiMessageMapper;
import ru.startandroid.messagelist.domain.usecase.message.GetMessagesUseCase;
import ru.startandroid.messagelist.domain.usecase.message.UpdateMessageFavoriteUseCase;
import ru.startandroid.messagelist.ui.message.list.MessageListBinding;
import ru.startandroid.messagelist.ui.message.list.MessageListContract;
import ru.startandroid.messagelist.ui.message.list.MessageListPresenter;

@Module
public class MessageListModule extends BaseModule {

    @FragmentScope
    @Provides
    MessageListContract.Presenter providePresenter(GetMessagesUseCase getMessagesUseCase,
                                                   UpdateMessageFavoriteUseCase updateMessageFavoriteUseCase,
                                                   MessageListBinding messageListBinding,
                                                   UiMessageMapper uiMessageMapper) {
        return new MessageListPresenter(getMessagesUseCase, updateMessageFavoriteUseCase, messageListBinding, uiMessageMapper);
    }

    @Provides
    MessageListBinding provideData(Context context) {
        return new MessageListBinding(context);
    }

}
