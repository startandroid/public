package ru.startandroid.vocabulary.test.ui;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.app.App;
import ru.startandroid.vocabulary.data.record.Record;

public class TestActivity extends AppCompatActivity implements TestActivityContract.View {

    @Inject
    TestActivityContract.Presenter presenter;

    private boolean reCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        initView();
        App.getApp(this).getComponentHolder().getTestActivityComponent().injectTestActivity(this);
        presenter.attachView(this);
        if (savedInstanceState == null) {
            presenter.showData();
        }
    }

    private void initView(){
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
    public void showRecord(Record record) {
        //Toast.makeText(this, "Record " + record.getWord() + ", " + record.getRememberedCount(), Toast.LENGTH_LONG).show();
        Fragment fragment = TestFragment.createInstance(record);
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
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
        }
    }
}
