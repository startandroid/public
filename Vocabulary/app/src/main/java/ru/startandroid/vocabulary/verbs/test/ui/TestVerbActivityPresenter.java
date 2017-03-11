package ru.startandroid.vocabulary.verbs.test.ui;

import android.util.Log;

import java.util.List;
import java.util.Random;

import ru.startandroid.vocabulary.app.Constants;
import ru.startandroid.vocabulary.base.mvp.PresenterBase;
import ru.startandroid.vocabulary.data.record.storage.SqlSpecificationRawAllRecords;
import ru.startandroid.vocabulary.data.verb.Verb;
import ru.startandroid.vocabulary.data.verb.VerbController;
import ru.startandroid.vocabulary.data.verb.storage.SqlSpecificationRawAllVerbs;
import ru.startandroid.vocabulary.utils.Stack;
import rx.Subscription;
import rx.functions.Action1;


// TODO use general class for TestActivity and TestVerbActivity
public class TestVerbActivityPresenter extends PresenterBase<TestVerbActivityContract.View>
        implements TestVerbActivityContract.Presenter {

    private final VerbController verbController;
    private Verb currentRecord;
    private boolean notLoadedYet = true;
    private Subscription loadSubscription;
    private List<Verb> data;
    private Stack<Verb> stack;
    Random rnd;

    public TestVerbActivityPresenter(VerbController verbController) {
        this.verbController = verbController;
        rnd = new Random(System.currentTimeMillis());
    }

    @Override
    public void showData() {
        if (notLoadedYet) {
            loadData();
        } else {
            showCurrentRecord();
        }
    }

    private void loadData() {
        removeSubscription(loadSubscription);
        loadSubscription = verbController
                .getItems(new SqlSpecificationRawAllVerbs())
                .subscribe(new Action1<List<Verb>>() {
                    @Override
                    public void call(List<Verb> records) {
                        data = records;
                        int stackSize = Math.min(10, data.size() - Constants.RANDOM_LEVEL);
                        stack = new Stack<>(stackSize);
                        showNextRecord();
                    }
                });
        addSubscription(loadSubscription);
    }

    @Override
    public void onYesClick() {
        currentRecord.setRememberedCount(currentRecord.getRememberedCount() + 1);
        verbController.updateItem(currentRecord).subscribe();
        showNextRecord();
    }

    @Override
    public void onNoClick() {
        currentRecord.setRememberedCount(currentRecord.getRememberedCount() - 1);
        verbController.updateItem(currentRecord).subscribe();
        showNextRecord();
    }

    private void showNextRecord() {
        currentRecord = null;
        chooseNextRecord();
        showCurrentRecord();
    }

    private void chooseNextRecord() {
        if (data.size() == 0) {
            return;
        }

        if (data.size() == 1) {
            currentRecord = data.get(0);
            return;
        }


        int i1 = 0;
        int i2 = 0;

        do {
            i1 = rnd.nextInt(data.size());
        } while (stack.contains(data.get(i1)));

        do {
            i2 = rnd.nextInt(data.size());
        } while (i2 == i1 || stack.contains(data.get(i2)));
        Log.d("qwe", "random i1 = " + i1 + ", i2 = " + i2);

        Verb rec1 = data.get(i1);
        Verb rec2 = data.get(i2);
        Log.d("qwe", "random rec1 = " + rec1.getFirst() + "(" + rec1.getRememberedCount() + ")"
                + ", rec2 = " + rec2.getFirst() + "(" + rec2.getRememberedCount() + ")");
        currentRecord = (rec1.getRememberedCount() < rec2.getRememberedCount()) ? rec1 : rec2;
    }

    private void showCurrentRecord() {
        if (currentRecord != null) {
            stack.add(currentRecord);
            getView().showRecord(currentRecord);
        } else {
            getView().closeScreen();
        }
    }
}
