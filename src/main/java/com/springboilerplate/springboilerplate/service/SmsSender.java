package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.dto.SmsData;
import com.springboilerplate.springboilerplate.exceptions.SmsException;

/**
 * Created by emmanuel on 3/23/18.
 */
public interface SmsSender {
    void sendSms(String phone) throws SmsException;
}
