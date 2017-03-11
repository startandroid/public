package ru.startandroid.vocabulary.dictionary.test.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.app.Constants;
import ru.startandroid.vocabulary.data.record.Record;

public class TestFragment extends Fragment {

    public static TestFragment createInstance(Record record) {
        TestFragment testFragment = new TestFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_RECORD, record);
        testFragment.setArguments(args);
        return testFragment;
    }

    private Unbinder unbinder;
    private Record record;

    @BindView(R.id.word)
    TextView textViewWord;

    @BindView(R.id.translate)
    TextView textViewTranslate;

    @BindView(R.id.sample)
    TextView textViewSample;

    @BindView(R.id.definition)
    TextView textViewDefinition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        record = getArguments().getParcelable(Constants.EXTRA_RECORD);

        if (TextUtils.isEmpty(record.getDefinition())) {
            textViewDefinition.setVisibility(View.GONE);
            textViewTranslate.setText(record.getTranslate());
        } else {
            textViewDefinition.setText(record.getDefinition());
        }

        if (TextUtils.isEmpty(record.getTranslate())) {
            textViewTranslate.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.word)
    void onWordClick() {
        textViewWord.setText(record.getWord());
    }

    @OnClick(R.id.sample)
    void onSampleClick() {
        textViewSample.setText(record.getSample());
    }


    @OnClick(R.id.translate)
    void onTranslateClick() {
        textViewTranslate.setText(record.getTranslate());
    }
}
