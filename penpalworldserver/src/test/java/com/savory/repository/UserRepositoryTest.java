package com.savory.repository;

import com.savory.EntityHelper;
import com.savory.domain.Message;
import com.savory.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class UserRepositoryTest{

    @Autowired
    private UserRepository userRepository;

    private EntityHelper entityHelper = new EntityHelper();

    @Before
    public void setUp(){
        entityHelper.resetId();
    }

    @Test
    @Transactional
    public void shouldSaveUser(){
        User user = entityHelper.createUser();
        userRepository.saveAndFlush(user);
        User result = userRepository.findByEmail("test1");
        assertEquals(user, result);
    }

    @Test
    @Transactional
    public void shouldSavePenPals(){
        User user = entityHelper.createUser();
        Set<User> users = entityHelper.createUserSet();
        userRepository.save(users);
        users.forEach(friend -> user.addFriend(friend));
        userRepository.saveAndFlush(user);
        User result = userRepository.findByEmail("test1");
        assertEquals(user, result);
        User otherUser = userRepository.findByEmail("test2");
        assertEquals(1, otherUser.getPenpals().size());
    }

    @Test
    @Transactional
    public void shouldSaveMessages(){
        User user = entityHelper.createUser();
        userRepository.saveAndFlush(user);
        User returnUser = userRepository.findByEmail("test1");
        List<Message> messages = entityHelper.createMessageList();
        messages.forEach(message -> returnUser.receiveMessage(returnUser, message));
        userRepository.saveAndFlush(returnUser);
        User result = userRepository.findByEmail("test1");
        assertEquals(returnUser, result);
    }

}