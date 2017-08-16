package ru.startandroid.messagelist.data.message;

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
public class UiMessageMapperTest {

    private UiMessageMapper uiMessageMapper;

    private final long messageId = 1;
    private final String messageText = "message";
    private final String messageImage = "http://fake.com/image.png";
    private final long messageTime = 1000;
    private final boolean messageFavorite = true;

    @Mock
    DbMessage dbMessage;

    @Before
    public void setUp() throws Exception {
        uiMessageMapper = new UiMessageMapper();
        MockitoAnnotations.initMocks(this);
        when(dbMessage.getId()).thenReturn(messageId);
        when(dbMessage.getText()).thenReturn(messageText);
        when(dbMessage.getImage()).thenReturn(messageImage);
        when(dbMessage.getTime()).thenReturn(messageTime);
        when(dbMessage.isFavorite()).thenReturn(messageFavorite);
    }

    @Test
    public void mappingFromDbApiToDbMessage() throws Exception {
        UiMessage uiMessage = uiMessageMapper.mapToUiMessage(dbMessage);

        assertEquals(uiMessage.getId(), dbMessage.getId());
        assertEquals(uiMessage.getText(), dbMessage.getText());
        assertEquals(uiMessage.getImage(), dbMessage.getImage());
        assertEquals(uiMessage.getTime(), dbMessage.getTime());
        assertEquals(uiMessage.isFavorite(), dbMessage.isFavorite());
    }

}