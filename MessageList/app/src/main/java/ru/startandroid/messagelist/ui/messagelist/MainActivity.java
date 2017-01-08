package ru.startandroid.messagelist.ui.messagelist;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.startandroid.messagelist.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            Fragment fragment = new MessageListFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
    }
}
