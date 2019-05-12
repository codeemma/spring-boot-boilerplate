package com.springboilerplate.springboilerplate.controller;


import com.springboilerplate.springboilerplate.service.AlertService;
import com.springboilerplate.springboilerplate.utils.PushNotification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AlertController {
    private AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping("/alert")
    public ResponseEntity<?> alert(@RequestParam("latitude") Double latitude,@RequestParam("longitude") Double longitue) throws Exception {

        System.out.println("----- alert called -----");
        alertService.panicAlert(latitude, longitue);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notify")
    public ResponseEntity<?> notify(@RequestParam("message")String message) throws Exception {

        alertService.sendMessage(message);
        return ResponseEntity.ok().build();
    }
}
