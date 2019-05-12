package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.exceptions.AddressNotFoundException;
import com.springboilerplate.springboilerplate.exceptions.SmsException;
import com.springboilerplate.springboilerplate.utils.AddressHelper;
import com.springboilerplate.springboilerplate.utils.PushNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AlertServiceImpl implements AlertService {
    @Autowired
    private AddressHelper addressHelper;
    @Autowired
    private SmsSender smsSender;
    @Autowired
    private PushNotification pushNotification;
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    private

    private String adminEmail = "olabisisulaiman@gmail.com";

    @Override
    public void panicAlert(Double latitude, Double longitude) throws AddressNotFoundException, SmsException {
        String address = addressHelper.getAddressFromCoordinate(latitude, longitude);

        String message = generateMessage(address, latitude, longitude);
        smsSender.sendSms(message);
    }

    @Override
    public void sendMessage(String message) throws IOException, SmsException {
        pushNotification.send(message);
        smsSender.sendSms(message);
    }

    private String generateMessage(String address, Double latitude, Double longitude ) {
        return "There is an emergency from a driver at "+ address +", lat: "+latitude+
                " long: "+ longitude;
    }
}
