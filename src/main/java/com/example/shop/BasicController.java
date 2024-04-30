package com.example.shop;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Controller
public class BasicController {
    @GetMapping("/date")
    @ResponseBody
    String hello(){
        String now = String.valueOf(LocalDate.now()) + " " + String.valueOf(LocalTime.now());
        String now2 = String.valueOf(LocalDateTime.now());

        return now + "<br>" + now2;
    }
}
