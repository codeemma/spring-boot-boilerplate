package com.springboilerplate.springboilerplate.utils;


import com.springboilerplate.springboilerplate.exceptions.AddressNotFoundException;
import com.springboilerplate.springboilerplate.utils.reverseGeocodingApi.Address;

public interface AddressHelper {
    String getAddressFromCoordinate(Double userLatitude, Double userLongitude) throws AddressNotFoundException;
}
