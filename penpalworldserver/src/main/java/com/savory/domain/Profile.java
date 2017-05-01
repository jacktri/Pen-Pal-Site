package com.savory.domain;

import com.savory.domain.embeddable.ProfileStats;
import com.savory.domain.enums.Country;
import com.savory.domain.enums.Gender;
import lombok.*;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(exclude ={"user"} )
@Builder
@NoArgsConstructor
@AllArgsConstructor
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Profile {

    @Column
    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String profileFileLocation;

    @Column
    private Country currentCountry;

    @Column
    private Country homeCountry;

    @Column
    private Gender gender;

    @Column
    private Integer age;

    @Column
    private String bio;

    @Embedded
    private ProfileStats profileStats;

    @OneToOne(mappedBy = "profile")
    private User user;

}
