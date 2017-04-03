package ru.startandroid.vocabulary.sentences.test.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.startandroid.vocabulary.R;

public class TestSentencesActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, TestSentencesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_sentences_activity);
        if (savedInstanceState == null) {
            Fragment fragment = new TestSentencesFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
    }
}
