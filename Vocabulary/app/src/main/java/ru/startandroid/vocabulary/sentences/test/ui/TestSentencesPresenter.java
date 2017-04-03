package ru.startandroid.vocabulary.sentences.test.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.startandroid.vocabulary.base.mvp.PresenterBase;
import ru.startandroid.vocabulary.data.sentence.Sentence;
import ru.startandroid.vocabulary.data.sentence.SentenceController;
import ru.startandroid.vocabulary.data.sentence.storage.SqlSpecificationRawEnabledSentences;
import rx.Subscription;
import rx.functions.Action1;

public class TestSentencesPresenter extends PresenterBase<TestSentencesContract.View> implements TestSentencesContract.Presenter {

    private final SentenceController sentenceController;
    boolean notLoadedYet = true;
    private List<Sentence> sentences;
    private Subscription subscription;
    private Random rnd;
    private int lastIndex = -1;

    public TestSentencesPresenter(SentenceController sentenceController) {
        this.sentenceController = sentenceController;
        sentences = new ArrayList<>();
        rnd = new Random(System.currentTimeMillis());
    }

    @Override
    public void next() {
        if (notLoadedYet) {
            loadData();
        } else {
            showNext();
        }
    }

    private void showNext() {
        int i = 0;
        if (sentences.size() == 1) {
            getView().showSentence(sentences.get(0));
            return;
        }

        do {
            i = rnd.nextInt(sentences.size());
        } while (i == lastIndex);

        getView().showSentence(sentences.get(i));
        lastIndex = i;
    }

    private void loadData() {
        removeSubscription(subscription);
        subscription = sentenceController.getItems(new SqlSpecificationRawEnabledSentences()).subscribe(new Action1<List<Sentence>>() {
            @Override
            public void call(List<Sentence> sentences) {
                if (sentences.size() == 0) {
                    getView().closeScreen();
                    return;
                }
                TestSentencesPresenter.this.sentences = sentences;
                showNext();
            }
        });
        addSubscription(subscription);
    }
}
