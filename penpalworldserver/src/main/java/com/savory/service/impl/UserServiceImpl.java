package com.savory.service.impl;

import com.savory.converter.Converter;
import com.savory.domain.Profile;
import com.savory.domain.User;
import com.savory.domain.embeddable.ProfileStats;
import com.savory.dto.ProfileDto;
import com.savory.dto.UserDto;
import com.savory.repository.UserRepository;
import com.savory.service.UserService;
import com.savory.utils.PhotoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter converter;

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return converter.convert(user, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPenpalEmails(null);
        userDto.setId(null);
        User user = converter.convert(userDto, User.class);
        user.setProfile(new Profile());
        user.getProfile().setProfileStats(ProfileStats.builder()
                .startDate(LocalDateTime.now())
                .profileViews(0)
                .build());
        user.getProfile().setUser(user);
        userRepository.saveAndFlush(user);
        return converter.convert(user, UserDto.class);
    }

    @Override
    public void deleteUser(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        userRepository.delete(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        updateUserValues(user, userDto);
        updateFriends(user, userDto);
        userRepository.saveAndFlush(user);
        return converter.convert(user, UserDto.class);
    }

    private void updateFriends(User existingUser, UserDto newUser){
        if(null == newUser.getPenpalEmails()){
            return;
        }
        Set<User> users = newUser.getPenpalEmails().stream()
                .map(email -> userRepository.findByEmail(email))
                .collect(toSet());
        users.forEach(user -> existingUser.addFriend(user));
    }

    private void updateUserValues(User existingUser, UserDto newUser){

        if(null != newUser.getPassword()){
            existingUser.setPassword(newUser.getPassword());
        }
        if(null != newUser.getProfile()){
            ProfileDto profileDto = newUser.getProfile();
            Profile profile;
            if(null != existingUser.getProfile()){
                 profile = existingUser.getProfile();
            }
            else{
                profile = new Profile();
            }
            if(null != profileDto.getAge()){
                profile.setAge(profileDto.getAge());
            }
            if(null != profileDto.getBio()){
                profile.setBio(profileDto.getBio());
            }
            if(null != profileDto.getCurrentCountry()){
                profile.setCurrentCountry(profileDto.getCurrentCountry());
            }
            if(null != profileDto.getHomeCountry()){
                profile.setHomeCountry(profileDto.getHomeCountry());
            }
            if(null != profileDto.getGender()){
                profile.setGender(profileDto.getGender());
            }
            if(null != newUser.getProfile().getProfilePhoto()){
                profile.setProfileFileLocation(PhotoUtils.savePhotoReturnLocation(newUser.getProfile().getProfilePhoto()));
            }
            profile.setUser(existingUser);
            existingUser.setProfile(profile);
        }
    }
}
