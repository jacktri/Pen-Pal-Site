package com.savory.repository;

import com.savory.EntityHelper;
import com.savory.domain.Message;
import com.savory.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    private EntityHelper entityHelper = new EntityHelper();

    @Test
    @Transactional
    public void shouldSaveMessage(){
        assertEquals(0,messageRepository.findAll().size());
        Message message = entityHelper.createMessage("message");
        messageRepository.saveAndFlush(message);
        assertEquals(1,messageRepository.findAll().size());
        Message result = messageRepository.findOne(1L);
        assertEquals(result, message);
    }

//    @Test
//    @Transactional
//    public void should
}