package ru.startandroid.vocabulary.app.dagger

import dagger.Component
import ru.startandroid.vocabulary.app.ComponentHolder

@AppScope
@Component(modules = arrayOf(AppModule::class, AppSubComponentsModule::class, DataModule::class, UseCaseModule::class))
interface AppComponent {
    fun injectComponentsHolder(componentHolder: ComponentHolder) : Unit
}