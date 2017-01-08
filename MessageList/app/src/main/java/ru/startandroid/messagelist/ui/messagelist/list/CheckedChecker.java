package ru.startandroid.messagelist.ui.messagelist.list;

import java.util.Collection;
import java.util.Collections;

public class CheckedChecker {

    public static final CheckedChecker EMPTY = new CheckedChecker(Collections.<String>emptyList());

    private final Collection<String> ids;

    public CheckedChecker(Collection<String> ids) {
        this.ids = ids;
    }

    public boolean isChecked(String id) {
        return ids != null &&  ids.contains(id);
    }
}
