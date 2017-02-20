package ru.startandroid.vocabulary.dictionary.recordlist.ui;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.vocabulary.base.mvp.PresenterBase;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.data.record.RecordController;
import ru.startandroid.vocabulary.data.record.storage.SqlSpecificationRawAllRecords;
import ru.startandroid.vocabulary.events.Event;
import ru.startandroid.vocabulary.events.EventBus;
import ru.startandroid.vocabulary.events.EventType;
import rx.Subscription;
import rx.functions.Action1;

public class RecordListPresenter extends PresenterBase<RecordListContract.View>
        implements RecordListContract.Presenter, Action1<Event> {

    private final RecordController recordController;
    private final EventBus eventBus;

    private boolean notLoadedYet = true;
    private List<Record> data = new ArrayList<>();

    private Subscription loadSubscription;


    public RecordListPresenter(RecordController recordController, EventBus eventBus) {
        this.recordController = recordController;
        this.eventBus = eventBus;

        addSubscription(eventBus.getEventsObservable(EventType.RECORDS_UPDATED).subscribe(this));
    }

    @Override
    public void showData() {
        if (notLoadedYet) {
            loadRecords();
        } else {
            showRecordsToView();
        }
    }

    private void showRecordsToView() {
        if (isViewAttached()) {
            getView().setData(data);
        }
    }

    private void loadRecords() {
        notLoadedYet = false;
        removeSubscription(loadSubscription);
        loadSubscription = recordController.getRecords(new SqlSpecificationRawAllRecords()).subscribe(new Action1<List<Record>>() {
            @Override
            public void call(List<Record> records) {
                data = records;
                showRecordsToView();
            }
        });
        addSubscription(loadSubscription);
    }


    @Override
    public void call(Event event) {
        if (event.getType() == EventType.RECORDS_UPDATED) {
            loadRecords();
        }
    }
}
