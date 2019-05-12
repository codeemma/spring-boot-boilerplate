package com.springboilerplate.springboilerplate.utils.reverseGeocodingApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by bionic on 7/13/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressComponent {
    @JsonProperty("long_name")
    private String longName;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("types")
    private List<String> types;

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
