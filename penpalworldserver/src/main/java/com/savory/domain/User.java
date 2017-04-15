package com.savory.domain;


import lombok.*;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@EqualsAndHashCode(exclude ={"profile", "messages", "penpals", "penpalsOf"} )
@NoArgsConstructor
@AllArgsConstructor
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
        if(penpals.contains(user)){
            return true;
        }
        return false;
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
    @Override
    public String toString() {

        return this.email + "'s teammates => "
                + Optional.ofNullable(this.penpals).orElse(
                Collections.emptySet()).stream()
                .map(person -> person.email)
                .collect(Collectors.toList());
    }
}
