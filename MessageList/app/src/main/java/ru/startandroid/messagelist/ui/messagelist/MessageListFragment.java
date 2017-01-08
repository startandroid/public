package ru.startandroid.messagelist.ui.messagelist;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.app.App;
import ru.startandroid.messagelist.data.message.Message;
import ru.startandroid.messagelist.ui.messagedetails.MessageDetailsActivity;
import ru.startandroid.messagelist.ui.messagelist.list.CheckedChecker;
import ru.startandroid.messagelist.ui.messagelist.list.MessageListAdapter;
import ru.startandroid.messagelist.ui.messagelist.list.MessageListHolder;
import ru.startandroid.messagelist.ui.messagelist.list.VerticalSpaceItemDecoration;
import ru.startandroid.messagelist.utils.clicks.Click;
import rx.functions.Action1;

public class MessageListFragment extends Fragment implements MessageListContract.View {

    @Inject
    MessageListContract.Presenter presenter;

    @Inject
    Picasso picasso;

    MessageListAdapter messageListAdapter;

    @BindView(R.id.messageList)
    RecyclerView messageList;

    @BindView(R.id.buttonTryAgain)
    Button buttonTryAgain;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindDimen(R.dimen.message_list_separator_height)
    int separatorHeight;

    private LinearLayoutManager layoutManager;

    private Unbinder unbinder;
    private boolean reCreate;
    private android.support.v7.view.ActionMode actionMode;

    public enum DataState {
        NONE, PROGRESS, TRY
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getActivity()).getComponentsHolder().getMessageListComponent().injectView(this);
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_list_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getMessages();
    }

    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);


        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messageList.setLayoutManager(layoutManager);
        messageList.addItemDecoration(new VerticalSpaceItemDecoration(separatorHeight));

        messageListAdapter = new MessageListAdapter(getActivity(), picasso);
        messageListAdapter.getTryAgainClicks().subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.onTryAgainClick();
            }
        });

        messageListAdapter.getMessageClicks().subscribe(new Action1<Click<MessageListHolder>>() {
            @Override
            public void call(Click<MessageListHolder> messageClick) {
                presenter.onMessageClick(messageClick);
            }
        });

        messageList.setAdapter(messageListAdapter);

        messageList.setVerticalScrollBarEnabled(true);

        messageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (layoutManager.findLastVisibleItemPosition() > messageListAdapter.getItemCount() - 30) {
                        presenter.onScrollToEnd();
                    }
                }
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(messageList);
    }

    @OnClick(R.id.buttonTryAgain)
    void onTryAgainClick() {
        presenter.onTryAgainClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setMessages(List<Message> messages) {
        messageListAdapter.setData(messages);
    }

    @Override
    public void setActionModeEnabled(boolean enabled) {
        if (enabled) {
            if (actionMode == null) {
                actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(callback);
            }
        } else {
            if (actionMode != null) {
                actionMode.finish();
                actionMode = null;
            }
        }
    }

    @Override
    public void invalidateActionMode() {
        if (actionMode != null) {
            actionMode.invalidate();
        }
    }

    @Override
    public void setCheckedIds(CheckedChecker checkedChecker) {
        messageListAdapter.setCheckedChecker(checkedChecker);
        messageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void openDetails(MessageListHolder messageListHolder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<Pair<View, String>> pairs = new LinkedList<>();
            pairs.add(Pair.create((View) messageListHolder.textViewId, "messageId"));
            pairs.add(Pair.create((View) messageListHolder.textViewDate, "messageDate"));
            pairs.add(Pair.create((View) messageListHolder.textViewText, "messageText"));

            if (messageListHolder.imageViewImage.getVisibility() == View.VISIBLE) {
                pairs.add(Pair.create((View) messageListHolder.imageViewImage, "messageImage"));
            }
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(getActivity(), pairs.toArray(new Pair[]{}));
            MessageDetailsActivity.openDetails(getActivity(), messageListHolder.getMessage(), options);
        } else {
            MessageDetailsActivity.openDetails(getActivity(), messageListHolder.getMessage());
        }

    }

    @Override
    public void setDataState(DataState dataState) {
        if (messageListAdapter.getItemCount() == 0) {
            setDataStateScreen(dataState);
        } else {
            setDataStateAdapter(dataState);
        }
    }

    private void setDataStateScreen(DataState dataState) {
        switch (dataState) {
            case PROGRESS:
                buttonTryAgain.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                break;
            case TRY:
                progressBar.setVisibility(View.INVISIBLE);
                buttonTryAgain.setVisibility(View.VISIBLE);
                break;
            case NONE:
                progressBar.setVisibility(View.INVISIBLE);
                buttonTryAgain.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void setDataStateAdapter(DataState dataState) {
        messageListAdapter.setDataState(dataState);
        if (dataState == DataState.PROGRESS) {
            if (layoutManager.findLastVisibleItemPosition() >= messageListAdapter.getItemCount() - 2) {
                messageList.scrollToPosition(messageListAdapter.getItemCount() - 1);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        reCreate = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (!reCreate) {
            presenter.destroy();
            App.get(getActivity()).getComponentsHolder().releaseMessageListComponent();
        }
    }


    final ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.START | ItemTouchHelper.END) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            presenter.messageSwiped(viewHolder.getAdapterPosition());
        }

        @Override
        public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return messageListAdapter.canBeSwiped(viewHolder.getAdapterPosition()) ?
                    super.getSwipeDirs(recyclerView, viewHolder) : 0;
        }
    };

    final ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.message_list_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return presenter.onPrepareActionMode(mode, menu);
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_menu_item_delete:
                    presenter.onActionMenuDelete();
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            presenter.onDestroyActionMode();
        }
    };


}
