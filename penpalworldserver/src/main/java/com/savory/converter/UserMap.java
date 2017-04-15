package com.savory.converter;

import com.savory.domain.Profile;
import com.savory.domain.User;
import com.savory.dto.ProfileDto;
import com.savory.dto.UserDto;
import org.modelmapper.PropertyMap;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class UserMap extends PropertyMap<User, UserDto> {
    @Override
    protected void configure() {
//        map().setPenpalEmails(extractEmail(source.getPenpals()));
    }

    private Set<String> extractEmail(Set<User> users){
        if(null == users || users.size() == 0){
            return null;
        }
        else{
            return users.stream()
                    .map(user -> user.getEmail())
                    .collect(toSet());
        }
    }
}
