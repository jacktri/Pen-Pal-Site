package com.savory.service.impl;

import com.savory.converter.Converter;
import com.savory.domain.Message;
import com.savory.domain.User;
import com.savory.dto.MessageDto;
import com.savory.repository.UserRepository;
import com.savory.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@Transactional
public class MessageServiceImpl implements MessageService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter converter;

    @Override
    public MessageDto sendMessage(MessageDto messageDto) {
        Message message = converter.convert(messageDto, Message.class);
        User owner = userRepository.findByEmail(messageDto.getOwner().getEmail());
        User from = userRepository.findByEmail(messageDto.getFrom().getEmail());
        message.setOwner(owner);
        message.setFrom(from);
        owner.receiveMessage(from, message);
        userRepository.saveAndFlush(owner);
        return converter.convert(message, MessageDto.class);
    }
}
