package ru.startandroid.messagelist.ui.message.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.base.adapter.AdapterDataSource;
import ru.startandroid.messagelist.base.ui.BaseFragment;
import ru.startandroid.messagelist.ui.message.list.adapter.MessageAdapter;

public class MessageListFragment extends BaseFragment<MessageListContract.Presenter> implements MessageListContract.View {

    @BindView(R.id.messages)
    RecyclerView recyclerViewMessages;

    @BindView(R.id.empty)
    View viewEmpty;

    @BindView(R.id.try_again)
    View viewTryAgain;

    @BindView(R.id.loading)
    View viewLoading;

    @Inject
    Picasso picasso;

    private MessageAdapter messageAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.message_list_fragment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMessages.setLayoutManager(layoutManager);

        messageAdapter = new MessageAdapter((AdapterDataSource) presenter, click -> presenter.click(click), picasso);
        recyclerViewMessages.setAdapter(messageAdapter);

        bindView();

    }

    private void bindView() {
        MessageListContract.BindingView dataView = presenter.getDataView();

        addDisposable(dataView.getErrors().subscribe(s -> Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show()));
        addDisposable(dataView.getEmptyVisibility().subscribe(RxView.visibility(viewEmpty)));

        // loading
        addDisposable(dataView.getLoadingVisibility()
                .filter(aBoolean -> !adapterHasData())
                .subscribe(RxView.visibility(viewLoading)));
        addDisposable(dataView.getLoadingVisibility()
                .filter(aBoolean -> adapterHasData())
                .doOnNext(messageAdapter.getLoadingConsumer())
                .filter(aBoolean -> aBoolean)
                .subscribe(aBoolean -> {
                    if (layoutManager.findLastVisibleItemPosition() >= messageAdapter.getItemCount() - 2) {
                        recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1);
                    }
                }));

        // try again
        addDisposable(dataView.getTryAgainVisibility()
                .filter(aBoolean -> !adapterHasData())
                .subscribe(RxView.visibility(viewTryAgain)));
        addDisposable(dataView.getTryAgainVisibility()
                .filter(aBoolean -> adapterHasData())
                .subscribe(messageAdapter.getTryAgainConsumer()));


        RxRecyclerView.scrollStateChanges(recyclerViewMessages).subscribe(newState -> {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && messageAdapter.isAvailableToLoad()) {
                if (layoutManager.findLastVisibleItemPosition() > messageAdapter.getItemCount() - 30) {
                    presenter.onScrollToEnd();
                }
            }
        });

        addDisposable(dataView.getNotifyDataChanged().subscribe(position -> {
            if (position >= 0) {
                messageAdapter.notifyItemChanged(position);
            } else {
                messageAdapter.notifyDataSetChanged();
            }
        }));
    }

    private boolean adapterHasData() {
        return messageAdapter.getItemCount() > 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @OnClick(R.id.try_again)
    public void onTryAgainClick() {
        presenter.tryAgain();
    }

}
