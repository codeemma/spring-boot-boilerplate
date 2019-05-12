package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.exceptions.AddressNotFoundException;
import com.springboilerplate.springboilerplate.exceptions.SmsException;

import java.io.IOException;

public interface AlertService {
    void panicAlert(Double latitude, Double longitude) throws AddressNotFoundException, SmsException;

    void sendMessage(String message) throws IOException, SmsException;
}
