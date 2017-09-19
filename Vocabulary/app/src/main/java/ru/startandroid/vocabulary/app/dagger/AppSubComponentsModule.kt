package ru.startandroid.vocabulary.app.dagger

import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.startandroid.vocabulary.base.dagger.BaseComponent
import ru.startandroid.vocabulary.base.dagger.BaseModule
import ru.startandroid.vocabulary.base.dagger.ComponentBuilder
import ru.startandroid.vocabulary.ui.dictionary.list.dagger.WordListDagger
import ru.startandroid.vocabulary.ui.dictionary.list.mvp.WordListFragment

@Module(subcomponents = arrayOf(WordListDagger.WordListComponent::class))
class AppSubComponentsModule {

    @Provides
    @IntoMap
    @ClassKey(WordListFragment::class)
    fun provideWordListFragmentBuilder(builder: WordListDagger.WordListComponent.Builder): ComponentBuilder<out BaseComponent<*>, out BaseModule> {
        return builder
    }

}