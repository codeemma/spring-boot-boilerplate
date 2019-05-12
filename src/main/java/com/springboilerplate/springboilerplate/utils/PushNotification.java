package com.springboilerplate.springboilerplate.utils;

import java.io.IOException;

public interface PushNotification {
    void send(String text) throws IOException;
}
