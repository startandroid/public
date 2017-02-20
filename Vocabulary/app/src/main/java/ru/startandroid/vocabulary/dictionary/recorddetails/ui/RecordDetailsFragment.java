package ru.startandroid.vocabulary.dictionary.recorddetails.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.Lazy;
import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.app.App;
import ru.startandroid.vocabulary.app.Constants;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.dictionary.recorddetails.dagger.RecordDetailsCreate;
import ru.startandroid.vocabulary.dictionary.recorddetails.dagger.RecordDetailsEdit;

public class RecordDetailsFragment extends Fragment implements RecordDetailsContract.View {

    @Inject
    @RecordDetailsCreate
    Lazy<RecordDetailsContract.Presenter> presenterCreateProvider;

    @Inject
    @RecordDetailsEdit
    Lazy<RecordDetailsContract.Presenter> presenterEditProvider;

    private RecordDetailsContract.Presenter presenter;
    private Unbinder unbinder;
    private boolean reCreate = false;

    @BindView(R.id.word)
    EditText editTextWord;

    @BindView(R.id.translate)
    EditText editTextTranslate;

    @BindView(R.id.sample)
    EditText editTextSample;

    @BindView(R.id.definition)
    EditText editTextDefinition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp(getActivity()).getComponentHolder().getRecordDetailsComponent(getArguments()).injectRecordDetailsFragment(this);
        Constants.RecordDetailsAction action = Constants.RecordDetailsAction.CREATE;
        if (getArguments() != null && getArguments().containsKey(Constants.EXTRA_RECORD_DETAILS_ACTION)) {
            action = (Constants.RecordDetailsAction) getArguments().getSerializable(Constants.EXTRA_RECORD_DETAILS_ACTION);
        }
        if (action == Constants.RecordDetailsAction.CREATE) {
            presenter = presenterCreateProvider.get();
        } else {
            presenter = presenterEditProvider.get();
        }
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_details_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            presenter.showData();
        }
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
            App.getApp(getActivity()).getComponentHolder().releaseRecordDetailsComponent();
        }
    }

    @OnClick(R.id.save)
    void onSaveClick() {
        presenter.onSaveClick();
    }

    @Override
    public void fillRecord(Record record) {
        record.setWord(editTextWord.getText().toString());
        record.setTranslate(editTextTranslate.getText().toString());
        record.setSample(editTextSample.getText().toString());
        record.setDefinition(editTextDefinition.getText().toString());
    }

    @Override
    public void closeScreen() {
        getActivity().finish();
    }

    @Override
    public void showRecord(Record record) {
        editTextWord.setText(record.getWord());
        editTextTranslate.setText(record.getTranslate());
        editTextSample.setText(record.getSample());
        editTextDefinition.setText(record.getDefinition());
    }
}
