package ru.startandroid.vocabulary.app

import android.content.Context
import android.util.Log
import ru.startandroid.vocabulary.app.dagger.AppComponent
import ru.startandroid.vocabulary.app.dagger.AppModule
import ru.startandroid.vocabulary.app.dagger.DaggerAppComponent
import ru.startandroid.vocabulary.base.dagger.*
import javax.inject.Inject
import javax.inject.Provider

class ComponentHolder (val context: Context) {

    var appComponent: AppComponent? = null
        private set

    private val components: MutableMap<Class<*>, BaseComponent<*>?> = mutableMapOf()

    @Inject
    lateinit var builders: MutableMap<Class<*>, Provider<ComponentBuilder<out BaseComponent<*>, out BaseModule>>>

    // TODO use kotlin init
    fun init() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(context)).build()
        appComponent?.injectComponentsHolder(this)
    }

    fun <C>getComponent(cls: Class<C>, module: BaseModule?) : BaseComponent<C>? {
        Log.d("kotlin!", "getComponent " + cls);
        var component = components[cls]
        if (component == null) {
            val builder = builders?.get(cls)?.get() as ComponentBuilder<BaseComponent<*>, BaseModule>
            // TODO optimize null safety code and generics
            if (module != null) {
                builder?.module(module)
            }
            component = builder?.build()
            components.put(cls, component)
        }
        Log.d("kotlin!", "getComponent " + component);
        return component as BaseComponent<C>
    }

    fun <C>getComponent(cls: Class<C>) : BaseComponent<C>? {
        return getComponent(cls, null)
    }

    fun <C>releaseComponent(cls: Class<C>) : Unit {
        components.put(cls, null)
    }


}