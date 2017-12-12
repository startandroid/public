package ru.startandroid.vocabulary.sentences.exerciselist.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.app.App;
import ru.startandroid.vocabulary.data.exercise.Exercise;
import ru.startandroid.vocabulary.sentences.exerciselist.list.ExerciseAdapter;
import ru.startandroid.vocabulary.sentences.test.ui.TestSentencesActivity;
import ru.startandroid.vocabulary.utils.VerticalSpaceItemDecoration;
import ru.startandroid.vocabulary.utils.click.Click;
import rx.functions.Action1;

public class ExerciseListFragment extends Fragment implements ExerciseListContract.View, Action1<Click<Exercise>> {

    @Inject
    ExerciseListContract.Presenter presenter;

    @BindView(R.id.exerciseList)
    RecyclerView recyclerViewExerciseList;

    @BindView(R.id.rangeFrom)
    EditText editTextRangeFrom;

    @BindView(R.id.rangeTo)
    EditText editTextRangeTo;

    private Unbinder unbinder;
    private ExerciseAdapter adapter;
    private boolean reCreate = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp(getActivity()).getComponentHolder().getExerciseListComponent().injectExerciseListFragment(this);
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_list_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewExerciseList.setLayoutManager(layoutManager);

        adapter = new ExerciseAdapter(getActivity());
        recyclerViewExerciseList.setAdapter(adapter);

        recyclerViewExerciseList.addItemDecoration(new VerticalSpaceItemDecoration(10));

        adapter.getClicks().subscribe(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.showData();
    }

    @OnClick(R.id.test)
    void onTestClick() {
        TestSentencesActivity.start(getActivity());
    }

    @OnClick(R.id.select)
    void onSelectClick() {
        presenter.select(editTextRangeFrom.getText().toString(), editTextRangeTo.getText().toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        reCreate = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (!reCreate) {
            presenter.destroy();
            App.getApp(getActivity()).getComponentHolder().releaseExerciseListComponent();
        }
    }

    @Override
    public void setData(Collection<Exercise> data) {
        adapter.setData(data);
    }

    @Override
    public void call(Click<Exercise> click) {
        switch (click.getType()) {
            case ITEM_CLICK:
                presenter.onExerciseClick(click.getItem());
                break;
        }
    }
}
