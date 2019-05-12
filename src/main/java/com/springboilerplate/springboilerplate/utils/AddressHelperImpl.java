package com.springboilerplate.springboilerplate.utils;

import com.springboilerplate.springboilerplate.exceptions.AddressNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AddressHelperImpl implements AddressHelper {

    private ReverseGeocoding reverseGeocoding;

    @Autowired
    public AddressHelperImpl(ReverseGeocoding reverseGeocoding) {
        this.reverseGeocoding = reverseGeocoding;
    }

    @Override
    public String getAddressFromCoordinate(Double userLatitude, Double userLongitude) throws AddressNotFoundException {
        Map<String, String> userLocation = reverseGeocoding.getAddressFromCoordinate(userLatitude, userLongitude);
        return setAddressValues(userLocation);

    }

    private String setAddressValues(Map<String, String> userLocation){
        return userLocation.get("STREET")+ ", "+
                userLocation.get("LGA")+", "+ userLocation.get("STATE") ;
    }
}
