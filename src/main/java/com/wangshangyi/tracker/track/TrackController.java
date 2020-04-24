package com.wangshangyi.tracker.track;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TrackController {
    @RequestMapping("/track")
    public String track() {
        return "Greetings from Spring Boot!";
    }



    @RequestMapping("/api/modify/change/status/headNo")
    public String modify() {
        return "Greetings from Spring Boot!";
    }


}
