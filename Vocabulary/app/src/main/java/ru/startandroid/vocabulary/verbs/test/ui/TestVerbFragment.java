package ru.startandroid.vocabulary.verbs.test.ui;

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
import ru.startandroid.vocabulary.data.verb.Verb;

public class TestVerbFragment extends Fragment {

    public static TestVerbFragment createInstance(Verb verb) {
        TestVerbFragment testFragment = new TestVerbFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_RECORD, verb);
        testFragment.setArguments(args);
        return testFragment;
    }

    private Unbinder unbinder;
    private Verb verb;

    @BindView(R.id.first)
    TextView textViewFirst;

    @BindView(R.id.second)
    TextView textViewSecond;

    @BindView(R.id.third)
    TextView textViewThird;

    @BindView(R.id.translate)
    TextView textViewTranslate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_verb_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        verb = getArguments().getParcelable(Constants.EXTRA_RECORD);

        textViewFirst.setText(verb.getFirst());
        textViewSecond.setText(verb.getSecond());
        textViewThird.setText(verb.getThird());
        textViewTranslate.setText(verb.getTranslate());

    }

    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.first)
    void onFirstClick() {
        textViewSecond.setVisibility(View.VISIBLE);
        textViewThird.setVisibility(View.VISIBLE);
        textViewTranslate.setVisibility(View.VISIBLE);
    }

}
