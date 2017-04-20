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
import java.util.function.*;

import static java.util.stream.Collectors.toSet;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter converter;

    private Function<String, User> findByEmail = x -> userRepository.findByEmail(x);
    private Function<UserDto, User> findByUserDto = x -> findByEmail.apply(x.getEmail());
    private Consumer<User> saveUser = x -> userRepository.saveAndFlush(x);
    private BiConsumer<User, UserDto> updateUserValuesFunction = (x, y) -> updateUserValues(x,y);
    private BiConsumer<User, UserDto> updateFriendsFunction = (x, y) -> updateFriends(x,y);
    private BiConsumer<User, UserDto> updateUserAndFriends = (x, y) -> updateUserValuesFunction.andThen(updateFriendsFunction).accept(x,y);

    @Override
    public UserDto findUserByEmail(String email) {
        return converter.convert(findByEmail.apply(email), UserDto.class);
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
        saveUser.accept(user);
        return converter.convert(user, UserDto.class);
    }

    @Override
    public void deleteUser(UserDto userDto) {
        userRepository.delete(findByUserDto.apply(userDto));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = findByUserDto.apply(userDto);
        updateUserAndFriends.accept(user,userDto);

        saveUser.accept(user);

        return converter.convert(user, UserDto.class);
    }

    private User updateFriends(User existingUser, UserDto newUser){
        if(null == newUser.getPenpalEmails()){
            return existingUser;
        }
        Set<User> users = newUser.getPenpalEmails().stream()
                .map(email -> userRepository.findByEmail(email))
                .collect(toSet());
        users.forEach(user -> existingUser.addFriend(user));
        return existingUser;
    }

    private User updateUserValues(User existingUser, UserDto newUser){

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
        return existingUser;
    }
}
