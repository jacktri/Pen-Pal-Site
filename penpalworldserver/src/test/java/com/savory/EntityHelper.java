package com.savory;

import com.savory.domain.Message;
import com.savory.domain.Profile;
import com.savory.domain.User;
import com.savory.domain.embeddable.ProfileStats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.savory.domain.enums.Country.FRANCE;
import static com.savory.domain.enums.Country.GERMANY;
import static com.savory.domain.enums.Gender.MALE;

public class EntityHelper {
    private Long id = 1L;
    private static final String EMAIL = "test";

    public User createUser(){
        User user = User.builder()
                .email(EMAIL+id)
                .password("test")
                .profile(createProfile())
                .build();
        id++;
        user.getProfile().setUser(user);
        return user;
    }

    public Profile createProfile(){
        return Profile.builder()
                .homeCountry(GERMANY)
                .profileStats(createProfileStats())
                .profileFileLocation("c:/somewhere")
                .currentCountry(FRANCE)
                .bio("test bio")
                .age(22)
                .gender(MALE)
                .build();
    }
    public List<Message> createMessageList(){
        List<Message> messages = new ArrayList<>();
        messages.add(createMessage("test1"));
        messages.add(createMessage("test2"));
        messages.add(createMessage("test3"));
        return messages;
    }

    public Message createMessage(String content){
        return Message.builder()
                .sentDateTime(LocalDateTime.MAX)
                .content(content)
                .build();
    }

    private ProfileStats createProfileStats(){
        return ProfileStats.builder()
                .profileViews(1)
                .startDate(LocalDateTime.MIN)
                .build();
    }

    public Set<User> createUserSet(){
        Set<User> users = new HashSet<>();
        users.add(createUser());
        users.add(createUser());
        users.add(createUser());
        return users;
    }

    public void resetId(){
        id = 1L;
    }
}
