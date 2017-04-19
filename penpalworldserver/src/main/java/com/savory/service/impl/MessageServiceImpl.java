package com.savory.service.impl;

import com.savory.converter.Converter;
import com.savory.domain.Message;
import com.savory.domain.User;
import com.savory.dto.MessageDto;
import com.savory.dto.UserDto;
import com.savory.repository.UserRepository;
import com.savory.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;

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
        User owner = findOwner.apply(messageDto);
        User from = findSender.apply(messageDto);
        message.setOwner(owner);
        message.setFrom(from);
        owner.receiveMessage(from, message);
        userRepository.saveAndFlush(owner);
        return converter.convert(message, MessageDto.class);
    }

    private Function<String, User> findByEmail = x -> userRepository.findByEmail(x);
    private Function<MessageDto, User> findOwner = x -> findByEmail.apply(x.getOwner().getEmail());
    private Function<MessageDto, User> findSender = x -> findByEmail.apply(x.getFrom().getEmail());
}
