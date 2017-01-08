package ru.startandroid.messagelist.ui.messagelist.list;

import java.util.Collection;
import java.util.HashSet;

import rx.Observable;
import rx.subjects.PublishSubject;

public class CheckedListHelper {

    private final Collection<String> checkedList = new HashSet<>();
    private CheckedChecker checkedChecker;
    private final PublishSubject<Integer> checkedCountSubject = PublishSubject.create();

    public boolean isChecked(String id) {
        return checkedList.contains(id);
    }

    public void setChecked(String id, boolean checked) {
        if (checked) {
            checkedList.add(id);
        } else {
            checkedList.remove(id);
        }
        countChanged();
    }

    public void toggleChecked(String id) {
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

    public Collection<String> getCheckedIdList() {
        return new HashSet<>(checkedList);
    }
}
