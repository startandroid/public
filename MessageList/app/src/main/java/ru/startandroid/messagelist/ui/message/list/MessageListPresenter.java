package ru.startandroid.messagelist.ui.message.list;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.startandroid.messagelist.base.adapter.AdapterDataSource;
import ru.startandroid.messagelist.base.mvp.BasePresenter;
import ru.startandroid.messagelist.common.utils.HttpUtils;
import ru.startandroid.messagelist.common.utils.clicks.Click;
import ru.startandroid.messagelist.data.message.UiMessage;
import ru.startandroid.messagelist.data.message.UiMessageMapper;
import ru.startandroid.messagelist.domain.common.Pair;
import ru.startandroid.messagelist.domain.usecase.message.GetMessagesUseCase;
import ru.startandroid.messagelist.domain.usecase.message.UpdateMessageFavoriteUseCase;


public class MessageListPresenter extends BasePresenter<MessageListContract.View> implements MessageListContract.Presenter, AdapterDataSource<UiMessage> {

    private final GetMessagesUseCase getMessagesUseCase;
    private final UpdateMessageFavoriteUseCase updateMessageFavoriteUseCase;
    private final MessageListContract.BindingPresenter binding;
    private final UiMessageMapper uiMessageMapper;
    private int page = 1;
    private boolean noMorePage = false;
    private Disposable disposableLoadData;

    public MessageListPresenter(GetMessagesUseCase getMessagesUseCase, UpdateMessageFavoriteUseCase updateMessageFavoriteUseCase,
                                MessageListBinding binding, UiMessageMapper uiMessageMapper) {
        this.getMessagesUseCase = getMessagesUseCase;
        this.updateMessageFavoriteUseCase = updateMessageFavoriteUseCase;
        this.binding = binding;
        this.uiMessageMapper = uiMessageMapper;
    }

    @Override
    protected void onViewIsReady(boolean firstCall) {
        if (firstCall) {
            loadData();
        }
    }

    protected void loadData() {
        if (noMorePage) return;

        if (disposableLoadData != null && !disposableLoadData.isDisposed()) {
            return;
        }
        removeDisposable(disposableLoadData);
        disposableLoadData = getMessagesUseCase
                .execute(page)
                .map(uiMessageMapper::mapToUiMessages)
                .doOnSubscribe(disposable -> binding.showLoading())
                .doOnError(binding.errorsThrowable())
                .subscribe(
                        messages -> {
                            page++;
                            binding.showNone();
                            addMessages(messages);
                            checkEmpty();

                        },
                        error -> {
                            if (HttpUtils.isHttp404(error)) {
                                noMorePage = true;
                                binding.showNone();
                            } else {
                                binding.showTryAgain();
                            }
                        }
                );
        addDisposable(disposableLoadData);
    }

    private void addMessages(List<UiMessage> messages) {
        List<UiMessage> tmp = new ArrayList<>(binding.messages().getValue());
        tmp.addAll(messages);
        binding.messages().accept(tmp);
    }

    private void checkEmpty() {
        if (binding.messages().getValue().isEmpty()) {
            binding.showEmpty();
        }
    }

    @Override
    public UiMessage getItem(int position) {
        return binding.messages().getValue().get(position);
    }

    @Override
    public int getItemCount() {
        return binding.messages().getValue().size();
    }

    @Override
    public MessageListContract.BindingView getDataView() {
        return (MessageListContract.BindingView) binding;
    }

    @Override
    public void onScrollToEnd() {
        loadData();
    }

    @Override
    public void tryAgain() {
        loadData();
    }

    @Override
    public void click(Click<Integer> click) {
        switch (click.getType()) {
            case TRY_AGAIN:
                tryAgain();
                break;
            case ITEM:
                int position = click.getItem();
                UiMessage message = binding.messages().getValue().get(position);
                message.setFavorite(!message.isFavorite());
                updateMessageFavoriteUseCase.execute(new Pair<>(message.getId(), message.isFavorite())).subscribe();
                binding.notifyDataChanged().accept(position);
                break;
        }
    }

}
