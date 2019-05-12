package com.springboilerplate.springboilerplate.utils.reverseGeocodingApi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboilerplate.springboilerplate.exceptions.GoogleMapsException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bionic on 7/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReverseGeocodingResult {

    @JsonIgnore
    private static final String STATUS_OK = "OK";
    @JsonIgnore
    private static final String STATUS_UNKNOWN = "UNKNOWN";

    private List<Address> results = new ArrayList<>();
    private String status = STATUS_UNKNOWN;

    public ReverseGeocodingResult() {
    }

    public List<Address> getResults() {
        return results;
    }

    public void setResults(List<Address> results) {
        if(results == null){
            results = new ArrayList<>();
        }
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private boolean isSuccess(){
        return status.equals(STATUS_OK) && !results.isEmpty();
    }

    private boolean hasExceededLimit(){
        return status.equalsIgnoreCase("OVER_QUERY_LIMIT");
    }

    public Address getAddress(){
        if(isSuccess()){
            return results.get(0);
        }else if(hasExceededLimit()){
            throw new GoogleMapsException("The number of query to google maps api has been exceeded");
        }
        return null;
    }
}
