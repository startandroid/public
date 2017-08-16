package ru.startandroid.messagelist.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class Matchers {

    public static Matcher<View> hasBlackText() {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("has black text color");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (view instanceof TextView) {
                    return ((TextView)view).getCurrentTextColor() == Color.BLACK;
                }
                return false;
            }
        };
    }
}
