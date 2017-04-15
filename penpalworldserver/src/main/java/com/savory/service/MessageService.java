package com.savory.service;

import com.savory.dto.MessageDto;

public interface MessageService {

    MessageDto sendMessage(MessageDto messageDto);
}
