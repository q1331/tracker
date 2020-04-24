package com.wangshangyi.tracker.track;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TrackController {
    @RequestMapping("/track")
    public String track() {
        return "Greetings from Spring Boot!";
    }
}
