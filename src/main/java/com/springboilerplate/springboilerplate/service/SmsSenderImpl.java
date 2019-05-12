package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.exceptions.SmsException;
import com.twilio.exception.TwilioException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by emmanuel on 3/24/18.
 */
@Component
public class SmsSenderImpl implements SmsSender{
    private final TwilioRestClient client;
    @Value("${twilio.phone}")
    private String senderPhone;
    @Value("${panic.number}")
    private String panicPhone;

    @Autowired
    public SmsSenderImpl(TwilioRestClient client) {
        this.client = client;
    }

    @Override
    public void sendSms(String message) throws SmsException {
        MessageCreator messageCreator = new MessageCreator(new PhoneNumber(panicPhone),
                new PhoneNumber(senderPhone), message);
        try {
            messageCreator.create(this.client);
        }catch (TwilioException ex){
            throw new SmsException("could not complete sending sms to "+ panicPhone);
        }
    }
}
