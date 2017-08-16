package ru.startandroid.messagelist.data.messages;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import ru.startandroid.messagelist.data.database.ItemDatabaseDataSourceRx;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationRaw;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationUpdate;
import ru.startandroid.messagelist.domain.messages.DbMessage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageDataSourceLocalImplTest {

    @Mock
    ItemDatabaseDataSourceRx<DbMessage> messageDatabaseDataSource;

    @Mock
    SqlSpecificationMessageFactory sqlSpecificationMessageFactory;

    private MessageDataSourceLocalImpl messageDataSourceLocal;
    private final int page = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        messageDataSourceLocal = new MessageDataSourceLocalImpl(messageDatabaseDataSource, sqlSpecificationMessageFactory);
    }

    @Test
    public void whenGetMessage_thenUseGetMessagesSpecificationFromFactory() throws Exception {
        SqlSpecificationRaw sqlSpecificationRawGetMessages = Mockito.mock(SqlSpecificationRaw.class);
        when(sqlSpecificationMessageFactory.getMessages(page)).thenReturn(sqlSpecificationRawGetMessages);
        when(messageDatabaseDataSource.query(sqlSpecificationRawGetMessages)).thenReturn(Observable.empty());

        messageDataSourceLocal.getMessages(page).subscribe();

        verify(messageDatabaseDataSource).query(sqlSpecificationRawGetMessages);
    }

    @Test
    public void whenUpdateMessageFavorite_thenUseUpdateMessageFavoriteSpecificationFromFactory() throws Exception {
        long id = 1;
        boolean favorite = true;
        SqlSpecificationUpdate sqlSpecificationUpdateMessageFavorite = Mockito.mock(SqlSpecificationUpdate.class);
        when(sqlSpecificationMessageFactory.updateMessageFavorite(id, favorite)).thenReturn(sqlSpecificationUpdateMessageFavorite);
        when(messageDatabaseDataSource.update(sqlSpecificationUpdateMessageFavorite)).thenReturn(Observable.empty());

        messageDataSourceLocal.updateMessageFavorite(id, favorite).subscribe();

        verify(messageDatabaseDataSource).update(sqlSpecificationUpdateMessageFavorite);
    }

}