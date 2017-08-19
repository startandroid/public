package ru.startandroid.vocabulary.app.dagger

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    @AppScope
    fun provideContext() : Context {
        return context
    }

}