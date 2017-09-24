package ru.startandroid.vocabulary.app.dagger

import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.startandroid.vocabulary.base.dagger.BaseComponent
import ru.startandroid.vocabulary.base.dagger.BaseModule
import ru.startandroid.vocabulary.base.dagger.ComponentBuilder
import ru.startandroid.vocabulary.ui.dictionary.details.dagger.WordDetailsDagger
import ru.startandroid.vocabulary.ui.dictionary.details.mvp.WordDetailsFragment
import ru.startandroid.vocabulary.ui.dictionary.list.dagger.WordListDagger
import ru.startandroid.vocabulary.ui.dictionary.list.mvp.WordListFragment

@Module(subcomponents = arrayOf(WordListDagger.WordListComponent::class, WordDetailsDagger.WordDetailsComponent::class))
class AppSubComponentsModule {

    @Provides
    @IntoMap
    @ClassKey(WordListFragment::class)
    fun provideWordListFragmentBuilder(builder: WordListDagger.WordListComponent.Builder): ComponentBuilder<out BaseComponent<*>, out BaseModule> {
        return builder
    }


    @Provides
    @IntoMap
    @ClassKey(WordDetailsFragment::class)
    fun provideWordDetailsFragmentBuilder(builder: WordDetailsDagger.WordDetailsComponent.Builder): ComponentBuilder<out BaseComponent<*>, out BaseModule> {
        return builder
    }

}