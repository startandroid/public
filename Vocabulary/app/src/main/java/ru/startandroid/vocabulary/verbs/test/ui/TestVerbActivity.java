package ru.startandroid.vocabulary.verbs.test.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.app.App;
import ru.startandroid.vocabulary.data.verb.Verb;

public class TestVerbActivity extends AppCompatActivity implements TestVerbActivityContract.View {

    @Inject
    TestVerbActivityContract.Presenter presenter;

    @BindView(R.id.currentRemembCount)
    TextView textViewCurrentRemembCount;

    private boolean reCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        initView();
        App.getApp(this).getComponentHolder().getTestVerbActivityComponent().injectTestVerbActivity(this);
        presenter.attachView(this);
        if (savedInstanceState == null) {
            presenter.showData();
        }
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @OnClick(R.id.yes)
    void onYesClick() {
        presenter.onYesClick();
    }

    @OnClick(R.id.no)
    void onNoClick() {
        presenter.onNoClick();
    }

    @Override
    public void showRecord(Verb record) {
        Fragment fragment = TestVerbFragment.createInstance(record);
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        textViewCurrentRemembCount.setText(Long.toString(record.getRememberedCount()));
    }

    @Override
    public void closeScreen() {
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        reCreate = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (!reCreate) {
            presenter.destroy();
            App.getApp(this).getComponentHolder().releaseTestVerbActivityComponent();
        }
    }
}
