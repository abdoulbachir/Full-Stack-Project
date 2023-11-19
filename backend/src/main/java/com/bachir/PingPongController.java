package com.bachir;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PingPongController {
    record PingPong(String result){}

    @GetMapping("/ping")
    public PingPong PingPongController() {
        return new PingPong("Pong");
    }
}
