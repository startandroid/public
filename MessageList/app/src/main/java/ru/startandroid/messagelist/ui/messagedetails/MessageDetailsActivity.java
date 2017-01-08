package ru.startandroid.messagelist.ui.messagedetails;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.app.Constants;
import ru.startandroid.messagelist.data.message.Message;

public class MessageDetailsActivity extends AppCompatActivity {

    public static void openDetails(Context context, Message message) {
        context.startActivity(createIntent(context, message));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void openDetails(Context context, Message message, ActivityOptionsCompat options) {
        context.startActivity(createIntent(context, message), options.toBundle());
    }

    public static Intent createIntent(Context context, Message message) {
        Intent intent = new Intent(context, MessageDetailsActivity.class);
        intent.putExtra(Constants.EXTRA_MESSAGE, message);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_details_activity);
        if (savedInstanceState == null) {
            Fragment fragment = new MessageDetailsFragment();
            Bundle args = new Bundle();
            args.putParcelable(Constants.EXTRA_MESSAGE, getIntent().getParcelableExtra(Constants.EXTRA_MESSAGE));
            fragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
    }
}
