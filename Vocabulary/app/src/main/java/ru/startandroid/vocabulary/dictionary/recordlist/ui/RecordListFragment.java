package ru.startandroid.vocabulary.dictionary.recordlist.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.app.App;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.dictionary.recorddetails.ui.RecordDetailsActivity;
import ru.startandroid.vocabulary.dictionary.recordlist.list.RecordAdapter;
import ru.startandroid.vocabulary.utils.VerticalSpaceItemDecoration;
import ru.startandroid.vocabulary.utils.click.Click;
import rx.functions.Action1;

public class RecordListFragment extends Fragment implements RecordListContract.View, Action1<Click<Record>>{

    @Inject
    RecordListContract.Presenter presenter;

    @BindView(R.id.recordList)
    RecyclerView recordList;

    Unbinder unbinder;
    boolean reCreate = false;
    RecordAdapter recordAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp(getActivity()).getComponentHolder().getRecordListComponent().injectRecordListFragment(this);
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_list_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recordList.setLayoutManager(layoutManager);

        recordAdapter = new RecordAdapter(getActivity());
        recordList.setAdapter(recordAdapter);

        recordList.addItemDecoration(new VerticalSpaceItemDecoration(10));

        recordAdapter.getRecordClicks().subscribe(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.showData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        reCreate = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (!reCreate) {
            presenter.destroy();
            App.getApp(getActivity()).getComponentHolder().releaseRecordListComponent();
        }
    }

    @Override
    public void setData(Collection<Record> records) {
        recordAdapter.setData(records);
    }

    @OnClick(R.id.add)
    void onAddClick() {
        RecordDetailsActivity.createRecord(getActivity());
    }

    @Override
    public void call(Click<Record> recordClick) {
        switch (recordClick.getType()) {
            case ITEM_CLICK:
                RecordDetailsActivity.editRecord(getActivity(), recordClick.getItem());
                break;
        }
    }
}
