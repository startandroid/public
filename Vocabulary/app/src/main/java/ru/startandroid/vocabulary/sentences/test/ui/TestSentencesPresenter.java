package ru.startandroid.vocabulary.sentences.test.ui;

import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;
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
    private LinkedList<Sentence> sentences;
    //private Queue<Integer> againSentences;
    private Subscription subscription;
    private Random rnd;
    private Sentence lastSentence;
    //private int lastIndex = -1;

    public TestSentencesPresenter(SentenceController sentenceController) {
        this.sentenceController = sentenceController;
        sentences = new LinkedList<>();
        //againSentences = new LinkedList();
        rnd = new Random(System.currentTimeMillis());
    }

    @Override
    public void next() {
        if (notLoadedYet) {
            notLoadedYet = false;
            loadData();
        } else {
            if (lastSentence != null) {
                sentences.addLast(lastSentence);
            }
            showNext();
        }
    }

    @Override
    public void showAgain() {
        if (sentences.isEmpty()) {
            return;
        }
        int r = 0;
        if (sentences.size() > 10) {
            r = rnd.nextInt(5);
        }
        sentences.add(5 + r, lastSentence);
        showNext();
    }

    @Override
    public void remove() {
        lastSentence = null;
        next();
    }

    private void showNext() {
        if (sentences.isEmpty()) {
            getView().closeScreen();
            return;
        }
        Log.d("qweee", "list " + sentences);
        lastSentence = sentences.poll();
        Log.d("qweee", "showNext " + lastSentence.getSentenceId());
        getView().showSentence(lastSentence);
        getView().showCount(sentences.size());
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
                TestSentencesPresenter.this.sentences.clear();
                TestSentencesPresenter.this.sentences.addAll(sentences);
                Collections.shuffle(TestSentencesPresenter.this.sentences);
                showNext();
            }
        });
        addSubscription(subscription);
    }
}
