package ru.startandroid.vocabulary.ui.dictionary.list.dagger

import dagger.Subcomponent
import ru.startandroid.vocabulary.app.dagger.WordListScope
import ru.startandroid.vocabulary.base.dagger.BaseComponent
import ru.startandroid.vocabulary.base.dagger.ComponentBuilder
import ru.startandroid.vocabulary.ui.dictionary.list.mvp.WordListFragment

@WordListScope
@Subcomponent(modules = arrayOf(WordListModule::class))
interface WordListComponent : BaseComponent<WordListFragment> {

    @Subcomponent.Builder
    interface Builder : ComponentBuilder<WordListComponent, WordListModule>

}