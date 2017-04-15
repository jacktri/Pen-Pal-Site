package com.savory.converter;

import com.savory.domain.Profile;
import com.savory.dto.ProfileDto;
import com.savory.utils.PhotoUtils;
import org.modelmapper.PropertyMap;

public class ProfileDtoMap extends PropertyMap<ProfileDto, Profile> {
    @Override
    protected void configure() {
        map().setProfileFileLocation(PhotoUtils.savePhotoReturnLocation(source.getProfilePhoto()));
    }
}
