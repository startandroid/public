package ru.startandroid.messagelist.utils;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

public class RecyclerViewUtils {

    public static ViewAction smoothScrollToPosition(int position) {
        return new SmoothScrollToPositionViewAction(position);
    }

    public static final class SmoothScrollToPositionViewAction implements ViewAction {
        private final int position;

        private SmoothScrollToPositionViewAction(int position) {
            this.position = position;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "scroll RecyclerView to position: " + position;
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.smoothScrollToPosition(position);
        }
    }

    public static Matcher<View> hasItemAtPosition(final int position, final Matcher<View> matcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText( String.format("has view at position %d that ", position));
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (view instanceof RecyclerView) {
                    RecyclerView.ViewHolder viewHolder = ((RecyclerView)view).findViewHolderForAdapterPosition(position);
                    View itemView = viewHolder.itemView;
                    return matcher.matches(itemView);
                }
                return false;
            }
        };
    }


    public final static class RecyclerViewScrollingIdlingResource extends RecyclerView.OnScrollListener implements IdlingResource {
        private ResourceCallback resourceCallback;
        private RecyclerView recyclerView;

        public RecyclerViewScrollingIdlingResource(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            recyclerView.addOnScrollListener(this);
        }
        @Override
        public String getName() {
            return RecyclerViewScrollingIdlingResource.class.getName();
        }
        @Override
        public boolean isIdleNow() {
            boolean isIdle = !recyclerView.getLayoutManager().isSmoothScrolling();
            if (isIdle) {
                resourceCallback.onTransitionToIdle();
            }
            return isIdle;
        }
        @Override
        public void registerIdleTransitionCallback(
                ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
        }
    }

}
