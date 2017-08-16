package ru.startandroid.messagelist.data.messages;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import ru.startandroid.messagelist.domain.messages.DbMessage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbMessageMapperTest {

    private DbMessageMapper dbMessageMapper;

    private final long messageId = 1;
    private final String messageText = "message";
    private final String messageImage = "http://fake.com/image.png";
    private final long messageTime = 1000;

    @Mock
    ApiMessage apiMessage;

    @Before
    public void setUp() throws Exception {
        dbMessageMapper = new DbMessageMapper();
        MockitoAnnotations.initMocks(this);
        when(apiMessage.getId()).thenReturn(messageId);
        when(apiMessage.getText()).thenReturn(messageText);
        when(apiMessage.getImage()).thenReturn(messageImage);
        when(apiMessage.getTime()).thenReturn(messageTime);
    }

    @Test
    public void mappingFromDbToUiMessage() throws Exception {
        DbMessage dbMessage = dbMessageMapper.mapToDbMessage(apiMessage);

        assertEquals(dbMessage.getId(), apiMessage.getId());
        assertEquals(dbMessage.getText(), apiMessage.getText());
        assertEquals(dbMessage.getImage(), apiMessage.getImage());
        assertEquals(dbMessage.getTime(), apiMessage.getTime());
        assertFalse(dbMessage.isFavorite());

    }
}