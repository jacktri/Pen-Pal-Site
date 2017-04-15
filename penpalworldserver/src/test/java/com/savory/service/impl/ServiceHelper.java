package com.savory.service.impl;

import com.savory.domain.enums.Country;
import com.savory.domain.enums.Gender;
import com.savory.dto.MessageDto;
import com.savory.dto.ProfileDto;
import com.savory.dto.UserDto;

import java.time.LocalDateTime;

public class ServiceHelper {

    public MessageDto createMessageDto(){
        return MessageDto.builder()
                .content("test")
                .sentDateTime(LocalDateTime.MIN)
                .from(createUserDto("emailA"))
                .owner(createUserDto("emailB"))
                .build();
    }

    public UserDto createUserDto(String email){
        return UserDto.builder()
                .email(email)
                .password("password")
                .profile(createProfileDto())
                .build();
    }

    public ProfileDto createProfileDto(){
        return ProfileDto.builder()
                .age(22)
                .bio("test bio")
                .currentCountry(Country.FRANCE)
                .homeCountry(Country.GERMANY)
                .gender(Gender.MALE)
                .profilePhoto("test")
                .build();
    }
}
