package ru.startandroid.vocabulary.dictionary.test.ui;

import android.util.Log;

import java.util.List;
import java.util.Random;

import ru.startandroid.vocabulary.app.Constants;
import ru.startandroid.vocabulary.base.mvp.PresenterBase;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.data.record.RecordController;
import ru.startandroid.vocabulary.data.record.storage.SqlSpecificationRawAllRecords;
import ru.startandroid.vocabulary.utils.Stack;
import rx.Subscription;
import rx.functions.Action1;

public class TestActivityPresenter extends PresenterBase<TestActivityContract.View>
        implements TestActivityContract.Presenter {

    private final RecordController recordController;
    private Record currentRecord;
    private boolean notLoadedYet = true;
    private Subscription loadSubscription;
    private List<Record> data;
    private Stack<Record> stack;

    public TestActivityPresenter(RecordController recordController) {
        this.recordController = recordController;

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
        loadSubscription = recordController
                .getItems(new SqlSpecificationRawAllRecords())
                .subscribe(new Action1<List<Record>>() {
            @Override
            public void call(List<Record> records) {
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
        recordController.updateItem(currentRecord).subscribe();
        showNextRecord();
    }

    @Override
    public void onNoClick() {
        currentRecord.setRememberedCount(currentRecord.getRememberedCount() - 1);
        recordController.updateItem(currentRecord).subscribe();
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

        Random rnd = new Random(System.currentTimeMillis());
        int i1 = 0;
        int i2 = 0;

        do {
            i1 = rnd.nextInt(data.size());
        } while (stack.contains(data.get(i1)));

        do {
            i2 = rnd.nextInt(data.size());
        } while (i2 == i1 || stack.contains(data.get(i2)));
        Log.d("qwe", "random i1 = " + i1 + ", i2 = " + i2);

        Record rec1 = data.get(i1);
        Record rec2 = data.get(i2);
        Log.d("qwe", "random rec1 = " + rec1.getWord() + "(" + rec1.getRememberedCount() + ")"
                + ", rec2 = " + rec2.getWord() + "(" + rec2.getRememberedCount() + ")" );
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
