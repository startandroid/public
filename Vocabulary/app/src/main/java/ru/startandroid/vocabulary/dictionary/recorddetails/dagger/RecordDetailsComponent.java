package ru.startandroid.vocabulary.dictionary.recorddetails.dagger;

import dagger.Subcomponent;
import ru.startandroid.vocabulary.dictionary.recorddetails.ui.RecordDetailsFragment;

@RecordDetailsScope
@Subcomponent(modules = RecordDetailsModule.class)
public interface RecordDetailsComponent {
    void injectRecordDetailsFragment(RecordDetailsFragment recordDetailsFragment);
}
