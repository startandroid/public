package ru.startandroid.vocabulary.sentences.test.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.app.App;
import ru.startandroid.vocabulary.data.sentence.Sentence;

public class TestSentencesFragment extends Fragment implements TestSentencesContract.View {

    @Inject
    TestSentencesContract.Presenter presenter;

    private boolean reCreate = false;
    private Unbinder unbinder;

    // TODO move to presenter
    Sentence sentence;

    @BindView(R.id.russian)
    TextView textViewRussian;

    @BindView(R.id.english)
    TextView textViewEnglish;

    @BindView(R.id.count)
    TextView textViewCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp(getActivity()).getComponentHolder().getTestSentencesFragmentComponent().injectTestSentencesFragment(this);
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_sentences_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.next();
    }

    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @OnClick(R.id.next)
    void onNextClick() {
        presenter.next();
    }

    @OnClick(R.id.showAgain)
    void onShowAgainClick() {
        presenter.showAgain();
    }

    @OnClick(R.id.remove)
    void onRemoveClick() {
        presenter.remove();
    }

    @OnClick(R.id.english)
    void onEnglishClick() {
        textViewEnglish.setText(sentence.getEnglish());
    }

    @Override
    public void showSentence(Sentence sentence) {
        this.sentence = sentence;
        textViewEnglish.setText("");
        textViewRussian.setText(sentence.getRussian());
    }

    @Override
    public void showCount(int count) {
        textViewCount.setText(Integer.toString(count));
    }

    @Override
    public void closeScreen() {
        getActivity().finish();
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
            App.getApp(getActivity()).getComponentHolder().releaseTestSentencesComponent();
        }
    }
}
