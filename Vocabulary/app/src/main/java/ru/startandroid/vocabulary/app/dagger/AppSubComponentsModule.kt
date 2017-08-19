package ru.startandroid.vocabulary.app.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.startandroid.vocabulary.base.dagger.*
import ru.startandroid.vocabulary.ui.dictionary.list.dagger.WordListComponent
import ru.startandroid.vocabulary.ui.dictionary.list.mvp.WordListFragment

@Module(subcomponents = arrayOf(WordListComponent::class))
class AppSubComponentsModule {

    @Provides
    @IntoMap
    @ClassKey(WordListFragment::class)
    fun provideWordListFragmentBuilder(builder: WordListComponent.Builder) : ComponentBuilder<out BaseComponent<*>, out BaseModule> {
        return builder
    }

}