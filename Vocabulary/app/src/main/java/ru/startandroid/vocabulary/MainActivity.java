package ru.startandroid.vocabulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.startandroid.vocabulary.dictionary.recordlist.ui.RecordListActivity;
import ru.startandroid.vocabulary.dictionary.test.ui.TestActivity;
import ru.startandroid.vocabulary.sentences.exerciselist.ui.ExerciseListActivity;
import ru.startandroid.vocabulary.verbs.test.ui.TestVerbActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.modeEnRu)
    void onModeEnRuClick() {

    }

    @OnClick(R.id.modeRuEn)
    void onModeRuEnClick() {
        startActivity(new Intent(this, TestActivity.class));
    }

    @OnClick(R.id.dictionary)
    void onDictionaryClick() {
        RecordListActivity.start(this);
    }

    @OnClick(R.id.verbs)
    void onVerbsClick() {
        startActivity(new Intent(this, TestVerbActivity.class));
    }

    @OnClick(R.id.exercises)
    void onExercisesClick() {
        ExerciseListActivity.start(this);
    }

}
