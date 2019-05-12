package com.springboilerplate.springboilerplate.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private String topic;
    private Notification notification;
    private Map<String,String> data;

    public Message() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String,String> data) {
        this.data = data;
    }
}
