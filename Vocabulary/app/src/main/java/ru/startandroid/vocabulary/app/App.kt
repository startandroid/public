package ru.startandroid.vocabulary.app

import android.app.Application
import android.content.Context

class App : Application() {

    // TODO fix dependencies in gradles
    // - юзать последний способ для версий
    // - убрать все лишнее


    // TODO убрать все лишние классы

    public lateinit var componentHolder: ComponentHolder
        private set

    override fun onCreate() {
        super.onCreate()
        componentHolder = ComponentHolder(this)
        componentHolder.init()
    }

    companion object {
        fun getApp(context: Context): App {
            return context.applicationContext as App
        }
    }

}