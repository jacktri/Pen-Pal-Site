package com.savory.converter;

import com.savory.domain.Profile;
import com.savory.dto.ProfileDto;
import com.savory.utils.PhotoUtils;
import org.modelmapper.PropertyMap;

public class ProfileMap extends PropertyMap<Profile,ProfileDto>{

    @Override
    protected void configure() {
        map().setProfilePhoto(PhotoUtils.findPhoto(source.getProfileFileLocation()));
    }
}
