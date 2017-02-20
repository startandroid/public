package ru.startandroid.vocabulary.events;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class EventBus {

    private final Scheduler returnScheduler = AndroidSchedulers.mainThread();

    private final Subject<Event, Event> subject;

    public EventBus() {
        PublishSubject<Event> publishSubject = PublishSubject.create();
        subject = new SerializedSubject<>(publishSubject);
    }

    public void postEvent(Event event) {
        subject.onNext(event);
    }

    public Observable<Event> getEventsObservable(Integer ... values) {
        return getEventsObservable(new HashSet<>(Arrays.asList(values)));
    }

    public Observable<Event> getEventsObservable(Set<Integer> types) {
        return subject.filter(new FilterByType(types)).observeOn(returnScheduler);
    }

    private class FilterByType implements Func1<Event, Boolean> {

        final Set<Integer> types;

        FilterByType(Set<Integer> types) {
            this.types = types;
            if (types == null) {
                throw new IllegalArgumentException("Set of types must be not null");
            }
        }

        @Override
        public Boolean call(Event event) {
            return types.contains(event.getType());
        }
    }

}
