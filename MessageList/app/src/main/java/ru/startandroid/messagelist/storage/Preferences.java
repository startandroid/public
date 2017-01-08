package ru.startandroid.messagelist.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private final static String FILE_NAME = "messagelist";

    private final static String PREF_LAST_PAGE = "last_page";
    private final static String PREF_NO_MORE_DATA = "no_more_data";

    private final SharedPreferences preferences;

    public Preferences(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, 0);
    }

    public void setLastPage(int page) {
        preferences.edit().putInt(PREF_LAST_PAGE, page).commit();
    }

    public int getLastPage() {
        return preferences.getInt(PREF_LAST_PAGE, 1);
    }

    public boolean getNoMoreData() {
        return preferences.getBoolean(PREF_NO_MORE_DATA, false);
    }

    public void setNoMoreData(boolean noMoreData) {
        preferences.edit().putBoolean(PREF_NO_MORE_DATA, noMoreData).commit();
    }

}
