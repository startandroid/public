package ru.startandroid.vocabulary.dictionary.recordlist.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ru.startandroid.vocabulary.R;

public class RecordListActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, RecordListActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_list_activity);
        if (savedInstanceState == null) {
            Fragment fragment = new RecordListFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
    }
}
