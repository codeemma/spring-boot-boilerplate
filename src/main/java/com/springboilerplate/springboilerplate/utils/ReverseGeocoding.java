package com.springboilerplate.springboilerplate.utils;

import com.springboilerplate.springboilerplate.exceptions.AddressNotFoundException;
import com.springboilerplate.springboilerplate.utils.reverseGeocodingApi.Address;
import com.springboilerplate.springboilerplate.utils.reverseGeocodingApi.AddressComponent;
import com.springboilerplate.springboilerplate.utils.reverseGeocodingApi.ReverseGeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.*;

/**
 * Created by bionic on 7/10/17.
 */
@Component
public class ReverseGeocoding{

    private final static String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng={latitude},{longitude}&key=AIzaSyAqB9mVviPIo132my5tkqtYvsXHYgGL8pM";

    private RestOperations restOperations;

    @Autowired
    public ReverseGeocoding(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    private List<AddressComponent> addressComponents = new ArrayList<>();
    private Map<String, String> location;

//    @Override
    public Map<String, String> getAddressFromCoordinate(double latitude, double longitude) throws AddressNotFoundException {
        ReverseGeocodingResult reverseGeocodingResult = restOperations.getForObject(
                BASE_URL, ReverseGeocodingResult.class, latitude, longitude);
        Address addressBody = reverseGeocodingResult.getAddress();
        if(addressBody == null ){
            throw new AddressNotFoundException("The address with latitude: " + latitude + " and longitude: " + longitude +
                    " could not be retrieved from google api: make sure the coordinates are valid.");
        }
        addressComponents = addressBody.getAddressComponents();
        return getLocation();
    }

    private Map<String, String> getLocation(){
        location = new HashMap<>();
        addressComponents.forEach(addressComponent -> {
            List<String> addressComponentTypes = addressComponent.getTypes();
            addStreetToLocation(addressComponentTypes, addressComponent);
            addLgaToLocation(addressComponentTypes, addressComponent);
            addStateToLocation(addressComponentTypes, addressComponent);
        });
        return location;
    }
    private void addStreetToLocation(List<String> addressComponentTypes,
                                     AddressComponent addressComponent){
        if (hasTypeOfRoute(addressComponentTypes)) {
            location.put("STREET", getLongName(addressComponent));
        }
    }
    private boolean hasTypeOfRoute(List<String> types) {
        return types.get(0).equalsIgnoreCase("route");
    }

    private void addLgaToLocation(List<String> addressComponentTypes,
                                  AddressComponent addressComponent){
        if(hasTypeOfAdministrativeLevel2(addressComponentTypes)){
            location.put("LGA", getLongName(addressComponent));
        }
    }
    private boolean hasTypeOfAdministrativeLevel2(List<String> types){
        return types.get(0).equalsIgnoreCase("administrative_area_level_2");
    }

    private void addStateToLocation(List<String> addressComponentTypes,
                                    AddressComponent addressComponent){
        if (hasTypeOfAdministrativeLevel1(addressComponentTypes)) {
            location.put("STATE", getLongName(addressComponent));
        }
    }
    private boolean hasTypeOfAdministrativeLevel1(List<String> types){
        return types.get(0).equalsIgnoreCase("administrative_area_level_1");
    }

    private String getLongName(AddressComponent addressComponent) {
        return addressComponent.getLongName().toUpperCase(Locale.ENGLISH);
    }
}