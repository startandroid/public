package ru.startandroid.vocabulary.dictionary.recorddetails.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.app.Constants;
import ru.startandroid.vocabulary.data.record.Record;

public class RecordDetailsActivity extends AppCompatActivity {

    public static void createRecord(Context context) {
        Intent intent = new Intent(context, RecordDetailsActivity.class);
        context.startActivity(intent);
    }

    public static void editRecord(Context context, Record record) {
        Intent intent = new Intent(context, RecordDetailsActivity.class);
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_RECORD, record);
        args.putSerializable(Constants.EXTRA_RECORD_DETAILS_ACTION, Constants.RecordDetailsAction.EDIT);
        intent.putExtra(Constants.EXTRA_ARGUMENTS, args);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_details_activity);

        if (savedInstanceState == null) {
            Fragment fragment = new RecordDetailsFragment();
            Bundle args = getIntent().getBundleExtra(Constants.EXTRA_ARGUMENTS);
            fragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
    }
}
