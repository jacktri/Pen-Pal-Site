package com.savory.service.impl;

import com.savory.converter.ConverterImpl;
import com.savory.domain.Message;
import com.savory.dto.MessageDto;
import com.savory.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class MessageServiceImplTest {

    @InjectMocks
    private MessageServiceImpl testee;

    @Mock
    private UserRepository userRepository;

    @Spy
    private ConverterImpl converter;

    private ConverterImpl testConverter = new ConverterImpl();

    private ServiceHelper serviceHelper = new ServiceHelper();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendMessage() throws Exception {
        MessageDto messageDto = serviceHelper.createMessageDto();
        Message message = testConverter.convert(messageDto, Message.class);
        when(userRepository.findByEmail(messageDto.getOwner().getEmail())).thenReturn(message.getOwner());
        when(userRepository.findByEmail(messageDto.getFrom().getEmail())).thenReturn(message.getFrom());
        MessageDto result = testee.sendMessage(messageDto);
        assertEquals(messageDto, result);
    }

}