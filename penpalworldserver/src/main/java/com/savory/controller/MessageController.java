package com.savory.controller;

import com.savory.dto.MessageDto;
import com.savory.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public MessageDto sendMessage(@RequestBody MessageDto messageDto){
        return messageService.sendMessage(messageDto);
    }
}
