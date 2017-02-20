package ru.startandroid.vocabulary.dictionary.recordlist.dagger;

import dagger.Subcomponent;
import ru.startandroid.vocabulary.dictionary.recordlist.ui.RecordListFragment;

@RecordListScope
@Subcomponent(modules = RecordListModule.class)
public interface RecordListComponent {
    void injectRecordListFragment(RecordListFragment recordListFragment);
}
