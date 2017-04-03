package ru.startandroid.vocabulary.dictionary.test.ui;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
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
    // TODO use stack for id instead Record
    private Stack<Record> stack;
    private Random rnd;

    public TestActivityPresenter(RecordController recordController) {
        this.recordController = recordController;
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
        loadSubscription = recordController
                .getItems(new SqlSpecificationRawAllRecords())
                .subscribe(new Action1<List<Record>>() {
            @Override
            public void call(List<Record> records) {
                data = records;
                sortData();
                int stackSize = Math.min(Constants.STACK_SIZE, data.size() - 1); // - Constants.RANDOM_LEVEL);
                stack = new Stack<>(stackSize);
                showNextRecord();
            }
        });
        addSubscription(loadSubscription);
    }

    @Override
    public void onYesClick() {
        changeRememberedCountInCurrent(+1);
        showNextRecord();
    }

    @Override
    public void onNoClick() {
        changeRememberedCountInCurrent(-1);
        showNextRecord();
    }

    private void changeRememberedCountInCurrent(int value) {
        int index = data.indexOf(currentRecord);
        currentRecord.setRememberedCount(currentRecord.getRememberedCount() + value);
        recordController.updateItem(currentRecord).subscribe();
        data.set(index, currentRecord);
        sortData();
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

        Record rec = null;

        // usually we take the least remembered word from list
        // but sometimes (10%) take random word
        int useRnd = rnd.nextInt(10);
        Log.d("qwe", "use random word ? " + useRnd);
        if (useRnd == 5) {
            Log.d("qwe", "use random word");
            // will select random words
            int index = 0;
            do {
                index = rnd.nextInt(data.size());
            } while (stack.contains(data.get(index)));
            rec = data.get(index);
            Log.d("qwe", "use random word i = " + index + ", rec = " + rec.getWord() + " " + rec.getRememberedCount());
        };

        if (rec == null) {
            while (rec == null) {
                int index = rnd.nextInt(stack.getSize() + 1);
                rec = data.get(index);
                if (stack.contains(rec)) {
                    rec = null;
                }
            }

//            for (int i = 0; i < data.size(); i++) {
//                rec = data.get(i);
//                Log.d("qwe", "get from data i = " + i + ", rec = " + rec.getWord() + " " + rec.getRememberedCount());
//                if (!stack.contains(rec)) {
//                    Log.d("qwe", "get from data break");
//                    break;
//                }
//            }
        }
        currentRecord = rec;
    }

//
//    private void chooseNextRecord() {
//        if (data.size() == 0) {
//            return;
//        }
//
//        if (data.size() == 1) {
//            currentRecord = data.get(0);
//            return;
//        }
//
//        Random rnd = new Random(System.currentTimeMillis());
//        int i1 = 0;
//        int i2 = 0;
//
//        do {
//            i1 = rnd.nextInt(data.size());
//        } while (stack.contains(data.get(i1)));
//
//        do {
//            i2 = rnd.nextInt(data.size());
//        } while (i2 == i1 || stack.contains(data.get(i2)));
//        Log.d("qwe", "random i1 = " + i1 + ", i2 = " + i2);
//
//        Record rec1 = data.get(i1);
//        Record rec2 = data.get(i2);
//        Log.d("qwe", "random rec1 = " + rec1.getWord() + "(" + rec1.getRememberedCount() + ")"
//                + ", rec2 = " + rec2.getWord() + "(" + rec2.getRememberedCount() + ")" );
//        currentRecord = (rec1.getRememberedCount() < rec2.getRememberedCount()) ? rec1 : rec2;
//    }

    private void showCurrentRecord() {
        if (currentRecord != null) {
            stack.add(currentRecord);
            getView().showRecord(currentRecord);
        } else {
            getView().closeScreen();
        }
    }

    private void sortData() {
        Collections.sort(data, comparator);
    }

    Comparator<Record> comparator = new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            // TODO make rememberCount int
            return o1.getRememberedCount() < o2.getRememberedCount() ? -1 :
                    o1.getRememberedCount() == o2.getRememberedCount() ? 0 : 1
            ;
        }
    };
}
