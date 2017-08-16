package ru.startandroid.messagelist.ui.message.list;

import android.support.test.espresso.idling.CountingIdlingResource;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import retrofit2.HttpException;
import ru.startandroid.messagelist.data.message.UiMessage;
import ru.startandroid.messagelist.data.message.UiMessageMapper;
import ru.startandroid.messagelist.domain.messages.DbMessage;
import ru.startandroid.messagelist.domain.usecase.message.GetMessagesUseCase;
import ru.startandroid.messagelist.domain.usecase.message.UpdateMessageFavoriteUseCase;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static ru.startandroid.messagelist.utils.TestFactory.createFakeDbMessages;
import static ru.startandroid.messagelist.utils.TestFactory.createFakeUiMessages;

@RunWith(MockitoJUnitRunner.class)
public class MessageListPresenterTest {

    private final PublishRelay<Throwable> throwablePublishRelay = PublishRelay.create();
    private final BehaviorRelay<List<UiMessage>> dataBehaviourRelay = BehaviorRelay.createDefault(new ArrayList<>());
    private final List<DbMessage> fakeDbMessages = createFakeDbMessages();
    private final List<UiMessage> fakeUiMessages = createFakeUiMessages();
    private final Throwable error = new Exception("Test error");
    @Mock
    GetMessagesUseCase getMessagesUseCase;
    @Mock
    UpdateMessageFavoriteUseCase updateMessageFavoriteUseCase;
    @Mock
    MessageListBinding messageListBinding;
    @Mock
    UiMessageMapper uiMessageMapper;
    @Mock
    HttpException http404Error;

    private MessageListPresenter messageListPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        messageListPresenter = new MessageListPresenter(getMessagesUseCase, updateMessageFavoriteUseCase,
                messageListBinding, uiMessageMapper);
        when(uiMessageMapper.mapToUiMessages(fakeDbMessages)).thenReturn(fakeUiMessages);
        when(getMessagesUseCase.execute(anyInt())).thenReturn(Observable.just(fakeDbMessages));
        when(messageListBinding.errorsThrowable()).thenReturn(throwablePublishRelay);
        when(messageListBinding.messages()).thenReturn(dataBehaviourRelay);
        when(http404Error.code()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);
    }

    @Test
    public void whenViewIsReadyFirstTime_thenLoadData() throws Exception {
        messageListPresenter.onViewIsReady(true);
        verify(getMessagesUseCase).execute(anyInt());
    }

    @Test
    public void whenViewIsReadyNotFirstTime_thenDoNotLoadData() throws Exception {
        messageListPresenter.onViewIsReady(false);
        verify(getMessagesUseCase, never()).execute(anyInt());
    }

    @Test
    public void whenOnScrollToEnd_thenLoadData() throws Exception {
        messageListPresenter.onScrollToEnd();
        verify(getMessagesUseCase).execute(anyInt());
    }

    @Test
    public void whenOnTryAgain_thenLoadData() throws Exception {
        messageListPresenter.tryAgain();
        verify(getMessagesUseCase).execute(anyInt());
    }

    @Test
    public void whenLoadingData_thenShowLoading() throws Exception {
        messageListPresenter.loadData();
        verify(messageListBinding).showLoading();
    }

    @Test
    public void whenFailLoadData_thenShowError() throws Exception {
        when(getMessagesUseCase.execute(anyInt())).thenReturn(Observable.error(error));
        TestObserver<Throwable> testObserver = new TestObserver<>();
        throwablePublishRelay.subscribe(testObserver);

        messageListPresenter.loadData();

        testObserver.assertValue(error);
    }

    @Test
    public void whenFailLoadData_thenShowTryAgain() throws Exception {
        when(getMessagesUseCase.execute(anyInt())).thenReturn(Observable.error(error));

        messageListPresenter.loadData();

        verify(messageListBinding).showTryAgain();
    }

    @Test
    public void whenNoMoreData_thenDoNotShowTryAgain() throws Exception {
        when(getMessagesUseCase.execute(anyInt())).thenReturn(Observable.error(http404Error));

        messageListPresenter.loadData();

        verify(messageListBinding, never()).showTryAgain();
    }

    @Test
    public void whenSuccessLoadData_thenShowData() throws Exception {
        when(getMessagesUseCase.execute(anyInt())).thenReturn(Observable.just(fakeDbMessages));
        TestObserver<List<UiMessage>> testObserver = new TestObserver<>();
        dataBehaviourRelay.skip(1).subscribe(testObserver);

        messageListPresenter.loadData();

        testObserver.assertValue(uiMessages -> uiMessages.containsAll(fakeUiMessages));
    }

}