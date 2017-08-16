package ru.startandroid.messagelist.data.messages;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import ru.startandroid.messagelist.domain.messages.DbMessage;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.startandroid.messagelist.data.utils.TestFactory.createFakeApiMessages;
import static ru.startandroid.messagelist.data.utils.TestFactory.createFakeDbMessages;

@RunWith(MockitoJUnitRunner.class)
public class MessageRepositoryImplTest {

    @Mock
    MessageDataSourceLocal messageDataSourceLocal;

    @Mock
    MessageDataSourceRemote messageDataSourceRemote;

    @Mock
    DbMessageMapper dbMessageMapper;

    private List<ApiMessage> fakeApiMessages;
    private List<DbMessage> fakeDbMessages;
    private TestObserver<List<DbMessage>> testGetMessageObserver;
    private MessageRepositoryImpl messageRepository;

    private final Throwable apiError = new Exception("Test api error");
    private final int page = 1;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testGetMessageObserver = new TestObserver<List<DbMessage>>();
        fakeApiMessages = createFakeApiMessages();
        fakeDbMessages = createFakeDbMessages();
        when(dbMessageMapper.mapToDbMessages(fakeApiMessages)).thenReturn(fakeDbMessages);
        messageRepository = new MessageRepositoryImpl(messageDataSourceLocal, messageDataSourceRemote,
                Schedulers.trampoline(), Schedulers.trampoline(), Schedulers.trampoline(), dbMessageMapper);
    }


    @Test
    public void whenDbIsEmpty_thenGetMessagesFromApi() throws Exception {
        when(messageDataSourceLocal.getMessages(page)).thenReturn(Observable.just(Collections.EMPTY_LIST));
        when(messageDataSourceRemote.getMessages(page)).thenReturn(Observable.just(fakeApiMessages));
        when(messageDataSourceLocal.insertMessages(fakeDbMessages)).thenReturn(Completable.complete());

        messageRepository.getMessages(page).subscribe(testGetMessageObserver);

        verify(messageDataSourceRemote).getMessages(page);
        testGetMessageObserver.assertValue(fakeDbMessages);
    }

    @Test
    public void whenGetMessagesFromApi_thenInsertToDb() throws Exception {
        when(messageDataSourceLocal.getMessages(page)).thenReturn(Observable.just(Collections.EMPTY_LIST));
        when(messageDataSourceRemote.getMessages(page)).thenReturn(Observable.just(fakeApiMessages));
        when(messageDataSourceLocal.insertMessages(fakeDbMessages)).thenReturn(Completable.complete());

        messageRepository.getMessages(page).subscribe();

        verify(messageDataSourceLocal).insertMessages(fakeDbMessages);
    }

    @Test
    public void whenDbIsNotEmpty_thenGetMessagesFromDb() throws Exception {
        when(messageDataSourceLocal.getMessages(page)).thenReturn(Observable.just(fakeDbMessages));

        messageRepository.getMessages(page).subscribe(testGetMessageObserver);

        verify(messageDataSourceLocal).getMessages(page);

        testGetMessageObserver.assertValue(fakeDbMessages);
    }


    @Test
    public void whenDbIsNotEmpty_thenDoNotGetMessagesFromApi() throws Exception {
        when(messageDataSourceLocal.getMessages(page)).thenReturn(Observable.just(fakeDbMessages));

        messageRepository.getMessages(page).subscribe();

        verify(messageDataSourceRemote, never()).getMessages(anyInt());
    }

    @Test
    public void whenErrorInApi_thenGetItOnError() throws Exception {
        when(messageDataSourceLocal.getMessages(page)).thenReturn(Observable.just(Collections.EMPTY_LIST));
        when(messageDataSourceRemote.getMessages(page)).thenReturn(Observable.error(apiError));

        messageRepository.getMessages(page).subscribe(testGetMessageObserver);

        testGetMessageObserver.assertError(apiError);
    }

    @Test
    public void whenUpdateMessageFavorite_thenUpdateMessageInDb() throws Exception {
        long id = 1;
        boolean favorite = true;
        when(messageDataSourceLocal.updateMessageFavorite(id, favorite)).thenReturn(Completable.complete());

        messageRepository.updateMessageFavorite(id, favorite).subscribe();

        verify(messageDataSourceLocal).updateMessageFavorite(id, favorite);
    }

}