package com.springboilerplate.springboilerplate.utils.reverseGeocodingApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by bionic on 7/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    @JsonProperty("formatted_address")
    private String formattedAddress;
    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}

