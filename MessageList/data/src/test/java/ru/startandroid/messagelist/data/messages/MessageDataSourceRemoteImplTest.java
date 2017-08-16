package ru.startandroid.messagelist.data.messages;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import ru.startandroid.messagelist.data.webapi.MessagesApi;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.startandroid.messagelist.data.utils.TestFactory.createFakeApiMessages;

@RunWith(MockitoJUnitRunner.class)
public class MessageDataSourceRemoteImplTest {

    @Mock
    MessagesApi messagesApi;

    private MessageDataSourceRemoteImpl messageDataSourceRemote;
    private TestObserver<List<ApiMessage>> testGetMessageObserver;

    private final int page = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        messageDataSourceRemote = new MessageDataSourceRemoteImpl(messagesApi);
        testGetMessageObserver = new TestObserver<List<ApiMessage>>();
    }

    @Test
    public void getMessagesSuccessful() throws Exception {
        List<ApiMessage> fakeApiMessages = createFakeApiMessages();
        when(messagesApi.messages(page)).thenReturn(Observable.just(fakeApiMessages));

        messageDataSourceRemote.getMessages(page).subscribe(testGetMessageObserver);

        verify(messagesApi).messages(page);
        testGetMessageObserver.assertValue(fakeApiMessages);
    }

    @Test
    public void getMessagesFailed() throws Exception {
        Throwable error = new Exception("Test api error");
        when(messagesApi.messages(page)).thenReturn(Observable.error(error));

        messageDataSourceRemote.getMessages(page).subscribe(testGetMessageObserver);

        verify(messagesApi).messages(page);
        testGetMessageObserver.assertError(error);
    }

}