package ru.startandroid.messagelist.ui.messagelist.list;

import java.util.Collection;
import java.util.Collections;

public class CheckedChecker<T> {

    public static final CheckedChecker EMPTY = new CheckedChecker(Collections.emptyList());

    private final Collection<T> ids;

    public CheckedChecker(Collection<T> ids) {
        this.ids = ids;
    }

    public boolean isChecked(T id) {
        return ids != null &&  ids.contains(id);
    }
}
