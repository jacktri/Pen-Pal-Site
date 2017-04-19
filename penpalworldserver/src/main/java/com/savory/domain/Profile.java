package com.savory.domain;

import com.savory.domain.embeddable.ProfileStats;
import com.savory.domain.enums.Country;
import com.savory.domain.enums.Gender;
import lombok.*;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(exclude ={"user"} )
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Column
    @GeneratedValue
    @Id
    private Long id;

    @Version
    private Integer version;

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
