package com.bachir;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {
    record PingPong(String result){}
    private static int COUNTER = 1;

    @GetMapping("/ping")
    public PingPong getPingPong() {
        return new PingPong("Pong: %S ".formatted(COUNTER++));
    }
}
