package ru.startandroid.messagelist.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CollectionUtils {

    public static Collection<Long> getSet(Long id) {
        Set<Long> ids = new HashSet<>();
        ids.add(id);
        return ids;
    }

}
