package ru.startandroid.messagelist.ui.message.list;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.app.App;
import ru.startandroid.messagelist.app.dagger.AppComponent;
import ru.startandroid.messagelist.data.common.Constants;
import ru.startandroid.messagelist.domain.messages.DbMessage;
import ru.startandroid.messagelist.domain.messages.MessageRepository;
import ru.startandroid.messagelist.utils.RecyclerViewUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static ru.startandroid.messagelist.utils.Matchers.hasBlackText;
import static ru.startandroid.messagelist.utils.RecyclerViewUtils.hasItemAtPosition;
import static ru.startandroid.messagelist.utils.RecyclerViewUtils.smoothScrollToPosition;
import static ru.startandroid.messagelist.utils.TestFactory.createFakeDbMessages;

@RunWith(AndroidJUnit4.class)
public class MessageListScreenTest {

    final int pageSize = Constants.PAGE_SIZE;
    @Rule
    public ActivityTestRule<MessageListActivity> activityTestRule = new ActivityTestRule<MessageListActivity>(MessageListActivity.class, false, false);
    MessageRepository messageRepositoryMock;
    List<DbMessage> fakeDbMessages;
    List<DbMessage> fakeDbMessages2;

    @Before
    public void setUp() throws Exception {
        fakeDbMessages = createFakeDbMessages(pageSize);
        fakeDbMessages2 = createFakeDbMessages(pageSize + 1, pageSize);
        AppComponent applicationComponent = App.getApp(InstrumentationRegistry.getTargetContext()).getComponentHolder().getAppComponent();
        messageRepositoryMock = applicationComponent.getMessageRepository();
    }

    @Test
    public void afterLoading_hideProgressbar() throws Exception {
        when(messageRepositoryMock.getMessages(anyInt())).thenReturn(Observable.just(fakeDbMessages));

        activityTestRule.launchActivity(null);

        onView(withId(R.id.loading)).check(matches(not(isDisplayed())));
    }

    @Test
    public void afterLoading_showData() throws Exception {
        when(messageRepositoryMock.getMessages(anyInt())).thenReturn(Observable.just(fakeDbMessages));
        activityTestRule.launchActivity(null);
        onView(withId(R.id.messages))
                .check(matches(hasDescendant(withText("message1"))));
    }

    @Test
    public void whenScrollToNextPage_thenShowNextPage() throws Exception {
        when(messageRepositoryMock.getMessages(1)).thenReturn(Observable.just(fakeDbMessages));
        when(messageRepositoryMock.getMessages(2)).thenReturn(Observable.just(fakeDbMessages2));
        activityTestRule.launchActivity(null);
        onView(withId(R.id.messages)).perform(smoothScrollToPosition(pageSize - 1));
        IdlingResource recyclerViewIsScrollingIdlingResource = new RecyclerViewUtils.RecyclerViewScrollingIdlingResource((RecyclerView) activityTestRule.getActivity().findViewById(R.id.messages));
        registerIdlingResources(recyclerViewIsScrollingIdlingResource);
        onView(withId(R.id.empty)).check(matches(not(isDisplayed())));
        unregisterIdlingResources(recyclerViewIsScrollingIdlingResource);
        onView(withId(R.id.messages)).perform(scrollToPosition(pageSize));
        onView(withId(R.id.messages))
                .check(matches(hasItemAtPosition(pageSize, hasDescendant(withText("message" + (pageSize + 1))))));
    }

    @Test
    public void whenClickToMessage_thenChangeFavorite() throws Exception {
        when(messageRepositoryMock.getMessages(anyInt())).thenReturn(Observable.just(fakeDbMessages));
        when(messageRepositoryMock.updateMessageFavorite(anyLong(), anyBoolean())).thenReturn(Completable.complete());
        activityTestRule.launchActivity(null);

        onView(withId(R.id.messages)).perform(actionOnItemAtPosition(5, click()));
        onView(withId(R.id.messages))
                .check(matches(hasItemAtPosition(5, hasDescendant(allOf(withText("message6"), hasBlackText())))));
        onView(withId(R.id.messages)).perform(actionOnItemAtPosition(5, click()));
        onView(withId(R.id.messages))
                .check(matches(hasItemAtPosition(5, hasDescendant(allOf(withText("message6"), not(hasBlackText()))))));
    }

}