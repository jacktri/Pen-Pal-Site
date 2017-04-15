package com.savory.service.impl;

import com.savory.converter.ConverterImpl;
import com.savory.domain.User;
import com.savory.dto.UserDto;
import com.savory.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl testee;

    @Mock
    private UserRepository userRepository;

    @Spy
    private ConverterImpl converter;

    private ConverterImpl testConverter = new ConverterImpl();

    private ServiceHelper serviceHelper = new ServiceHelper();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createNewUser() {
        UserDto userDto = serviceHelper.createUserDto("email");
        User user = testConverter.convert(userDto, User.class);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        UserDto actual = testee.createUser(serviceHelper.createUserDto("email"));
        assertEquals(userDto, actual);
    }

    @Test
    public void deleteUser() {
        UserDto userDto = serviceHelper.createUserDto("email");
        User user = testConverter.convert(userDto, User.class);
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(user);
        testee.deleteUser(userDto);
        verify(userRepository).delete(user);
    }

    @Test
    public void updateUser() {
        UserDto userDto = serviceHelper.createUserDto("email");
        User user = User.builder()
                .email("email")
                .build();
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(user);
        User updatedUser = testConverter.convert(userDto, User.class);
        when(userRepository.saveAndFlush(updatedUser)).thenReturn(updatedUser);
        UserDto result = testee.updateUser(userDto);
        assertEquals(userDto, result);
    }

    @Test
    public void updateUserWithFriends() {
        UserDto userDto = serviceHelper.createUserDto("email");
        User user = User.builder()
                .email("email")
                .build();
        Set<String> emails = new HashSet<>();
        emails.add("test2");
        emails.add("test3");
        userDto.setPenpalEmails(emails);
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(user);
        when(userRepository.findByEmail("test2")).thenReturn(User.builder()
                .email("test2")
                .build());
        when(userRepository.findByEmail("test3")).thenReturn(User.builder()
                .email("test3")
                .build());
        User updatedUser = testConverter.convert(userDto, User.class);
        Set<User> users = new HashSet<>();
        users.add(User.builder()
                .email("test2")
                .build());
        users.add(User.builder()
                .email("test3")
                .build());
        updatedUser.setPenpals(users);
        when(userRepository.saveAndFlush(updatedUser)).thenReturn(updatedUser);
        UserDto result = testee.updateUser(userDto);
        userDto.setPenpalEmails(null);
        assertEquals(userDto, result);
    }

}