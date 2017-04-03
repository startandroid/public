package ru.startandroid.vocabulary.sentences.exerciselist.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.startandroid.vocabulary.R;

public class ExerciseListActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ExerciseListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_list_activity);

        if (savedInstanceState == null) {
            Fragment fragment = new ExerciseListFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }

    }
}
