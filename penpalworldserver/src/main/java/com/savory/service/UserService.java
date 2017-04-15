package com.savory.service;

import com.savory.dto.UserDto;

public interface UserService {
    UserDto findUserByEmail(String email);
    UserDto createUser(UserDto userDto);
    void deleteUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
}
