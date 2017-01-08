package ru.startandroid.messagelist.ui.messagelist.list;

import java.util.Collection;
import java.util.HashSet;

import rx.Observable;
import rx.subjects.PublishSubject;

public class CheckedListHelper<T> {

    private final Collection<T> checkedList = new HashSet<>();
    private CheckedChecker<T> checkedChecker;
    private final PublishSubject<Integer> checkedCountSubject = PublishSubject.create();

    public boolean isChecked(T id) {
        return checkedList.contains(id);
    }

    public void setChecked(T id, boolean checked) {
        if (checked) {
            checkedList.add(id);
        } else {
            checkedList.remove(id);
        }
        countChanged();
    }

    public void toggleChecked(T id) {
        setChecked(id, !isChecked(id));
    }

    public void clear() {
        if (!isEmpty()) {
            checkedList.clear();
            countChanged();
        }
    }

    private void countChanged() {
        checkedCountSubject.onNext(count());
    }

    public boolean isEmpty() {
        return checkedList.isEmpty();
    }

    public int count() {
        return checkedList.size();
    }

    public CheckedChecker getCheckedChecker() {
        if (checkedChecker == null) {
            checkedChecker = new CheckedChecker(checkedList);
        }
        return checkedChecker;
    }

    public Observable<Integer> getCheckedCountChangeObservable() {
        return checkedCountSubject.asObservable();
    }

    public Collection<T> getCheckedIdList() {
        return new HashSet<>(checkedList);
    }
}
