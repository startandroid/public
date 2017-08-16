package ru.startandroid.messagelist.base.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import ru.startandroid.messagelist.app.App;
import ru.startandroid.messagelist.base.mvp.MvpPresenter;
import ru.startandroid.messagelist.common.utils.DisposableManager;

public abstract class BaseFragment<P extends MvpPresenter> extends Fragment {

    private Unbinder unbinder;
    private boolean reCreate;
    private final DisposableManager disposableManager = new DisposableManager();

    @Inject
    protected P presenter;

    protected abstract int getLayoutId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp(getActivity()).getComponentHolder().getActivityComponent(getClass()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        reCreate = true;
    }

    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
        disposableManager.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!reCreate) {
            presenter.destroy();
            App.getApp(getActivity()).getComponentHolder().releaseActivityComponent(getClass());
        }
    }

    protected void addDisposable(Disposable disposable) {
        disposableManager.add(disposable);
    }

    protected void removeDisposable(Disposable disposable) {
        disposableManager.remove(disposable);
    }
}
