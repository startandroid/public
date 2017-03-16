package ru.startandroid.vocabulary.verbs.test.ui;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
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
    private Random rnd;

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
        verbController.updateItem(currentRecord).subscribe();
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

        Verb rec = null;

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
            Log.d("qwe", "use random word i = " + index + ", rec = " + rec.getFirst() + " " + rec.getRememberedCount());
        };

        if (rec == null) {
            for (int i = 0; i < data.size(); i++) {
                rec = data.get(i);
                Log.d("qwe", "get from data i = " + i + ", rec = " + rec.getFirst() + " " + rec.getRememberedCount());
                if (!stack.contains(rec)) {
                    Log.d("qwe", "get from data break");
                    break;
                }
            }
        }
        currentRecord = rec;
    }

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

    Comparator<Verb> comparator = new Comparator<Verb>() {
        @Override
        public int compare(Verb o1, Verb o2) {
            // TODO make rememberCount int
            return (int) (o1.getRememberedCount() - o2.getRememberedCount());
        }
    };
}
