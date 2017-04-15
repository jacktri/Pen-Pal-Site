package com.savory.utils;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@UtilityClass
public class PhotoUtils {

    public String savePhotoReturnLocation(String photo){
        return photo;
    }

    public String findPhoto(String photoLocation){
        return photoLocation;
    }
}
