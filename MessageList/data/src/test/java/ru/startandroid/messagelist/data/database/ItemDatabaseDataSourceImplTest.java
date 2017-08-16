package ru.startandroid.messagelist.data.database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import ru.startandroid.messagelist.data.messages.DbMessageMapper;
import ru.startandroid.messagelist.data.messages.SqlSpecificationMessageFactory;
import ru.startandroid.messagelist.data.utils.TestFactory;
import ru.startandroid.messagelist.domain.messages.DbMessage;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class ItemDatabaseDataSourceImplTest {

    DbMessageMapper dbMessageMapper = new DbMessageMapper();
    SqlSpecificationMessageFactory specificationFactory = new SqlSpecificationMessageFactory(dbMessageMapper );

    ItemDatabaseDataSourceImpl<DbMessage> itemDatabaseDataSource = new ItemDatabaseDataSourceImpl<>(
            new DBHelper(RuntimeEnvironment.application), dbMessageMapper , MessagesTable.TABLE_NAME);

    @Before
    public void setUp() throws Exception {
        itemDatabaseDataSource.getDatabase().rawQuery("delete from " + MessagesTable.TABLE_NAME, null);
    }

    @Test
    public void insert() throws Exception {
        int count = 2;
        List<DbMessage> dbMessagesToInsert = TestFactory.createFakeDbMessages(count);
        itemDatabaseDataSource.insert(dbMessagesToInsert);

        List<DbMessage> dbMessagesFromQuery = itemDatabaseDataSource.query(specificationFactory.getMessages(1));

        assertEquals(count, dbMessagesFromQuery.size());
    }

    @Test
    public void update() throws Exception {
        List<DbMessage> dbMessagesToInsert = TestFactory.createFakeDbMessages(1);
        DbMessage dbMessage = dbMessagesToInsert.get(0);
        dbMessage.setFavorite(false);
        itemDatabaseDataSource.insert(dbMessagesToInsert);

        itemDatabaseDataSource.update(specificationFactory.updateMessageFavorite(dbMessage.getId(), true));

        List<DbMessage> dbMessagesFromQuery = itemDatabaseDataSource.query(specificationFactory.getMessages(1));
        assertTrue(dbMessagesFromQuery.get(0).isFavorite());
    }
}