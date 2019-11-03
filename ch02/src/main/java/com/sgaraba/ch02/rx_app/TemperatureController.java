package com.sgaraba.ch02.rx_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TemperatureController {
    private final TemperatureSensor temperatureSensor;

    public TemperatureController(TemperatureSensor temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }

    @GetMapping("/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        RxSeeEmitter emitter = new RxSeeEmitter();
        temperatureSensor.temperatureStream().subscribe(emitter.getSubscriber());
        return emitter;
    }

}
