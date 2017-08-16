package ru.startandroid.messagelist.ui.message.list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.startandroid.messagelist.R;

public class MessageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list_activity);

        if (savedInstanceState == null) {
            Fragment fragment = new MessageListFragment();
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }
}
