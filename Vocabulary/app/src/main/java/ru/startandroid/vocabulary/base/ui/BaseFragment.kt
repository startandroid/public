package ru.startandroid.vocabulary.base.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.disposables.Disposable
import ru.startandroid.vocabulary.app.App
import ru.startandroid.vocabulary.base.dagger.BaseModule
import ru.startandroid.vocabulary.base.mvp.MvpPresenter
import ru.startandroid.vocabulary.common.utils.DisposableManager
import javax.inject.Inject

abstract class BaseFragment<P : MvpPresenter<*>> : Fragment() {

    var unbinder : Unbinder? = null
    var reCreate = false;
    val disposableManager = DisposableManager()

    @Inject
    lateinit var presenter : P

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        componentHolder().getComponent(javaClass, createModule())?.inject(this)
    }

    protected open fun createModule(): BaseModule? {
        return null
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayoutId(), container, false)
        initView(view)
        return view
    }

    protected open fun initView(view: View?) {
        unbinder = ButterKnife.bind(this, view!!)
    }

    private fun componentHolder() = App.getApp(context).componentHolder


    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind();
        presenter.detachView();
        disposableManager.clear()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        reCreate = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!reCreate) {
            presenter.destroy()
            componentHolder().releaseComponent(javaClass)
        }
    }

    fun addDisposable(disposable : Disposable) = disposableManager.add(disposable)

    fun removeDisposable(disposable : Disposable) = disposableManager.remove(disposable)

}