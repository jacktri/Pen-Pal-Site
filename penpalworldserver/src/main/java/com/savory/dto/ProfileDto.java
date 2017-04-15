package com.savory.dto;

import com.savory.domain.enums.Country;
import com.savory.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long id;
    private String profilePhoto;
    private Country currentCountry;
    private Country homeCountry;
    private Gender gender;
    private Integer age;
    private String bio;
}
