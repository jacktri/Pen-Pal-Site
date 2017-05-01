package com.savory.domain;


import lombok.*;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Builder
@EqualsAndHashCode(exclude ={"profile", "messages", "penpals", "penpalsOf"} )
@ToString(exclude = {"profile"})
@NoArgsConstructor
@AllArgsConstructor
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class User {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="tbl_friends",
            joinColumns=@JoinColumn(name="personId"),
            inverseJoinColumns=@JoinColumn(name="friendId"))
    private Set<User> penpals;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="tbl_friends",
            joinColumns=@JoinColumn(name="friendId"),
            inverseJoinColumns=@JoinColumn(name="personId"))
    private Set<User> penpalsOf;

    @OneToMany(cascade = CascadeType.ALL, mappedBy= "owner", orphanRemoval = true)
    private List<Message> messages;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL, mappedBy= "from", orphanRemoval = true)
    private List<Message> sentMessages;

    public void addFriend(User user){
        if(penpals == null){
            penpals = new HashSet<>();
        }
        if(!isFriendsWith(user)){
            penpals.add(user);
            user.addFriend(this);
        }
    }

    private boolean isFriendsWith(User user){
        return penpals.contains(user) ? true : false;
    }

    private void sendMessage(Message message){
        if(sentMessages == null) {
            sentMessages = new ArrayList<>();
        }
        sentMessages.add(message);
    }

    public void receiveMessage(User sender, Message message){
        if(messages == null){
            messages = new ArrayList<>();
        }
        message.setFrom(sender);
        message.setOwner(this);
        sender.sendMessage(message);
        messages.add(message);
    }
}
