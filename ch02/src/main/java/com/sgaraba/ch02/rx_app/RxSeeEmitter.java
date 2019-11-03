package com.sgaraba.ch02.rx_app;

import com.sgaraba.ch02.bean.Temperature;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Subscriber;

import java.io.IOException;

public class RxSeeEmitter extends SseEmitter {

    static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private final Subscriber<Temperature> subscriber;

    RxSeeEmitter() {
        super(SSE_SESSION_TIMEOUT);

        this.subscriber = new Subscriber<Temperature>() {
            @Override
            public void onNext(Temperature temperature) {
                try {
                    RxSeeEmitter.this.send(temperature);
                } catch (IOException e) {
                    unsubscribe();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onCompleted() {
            }
        };

        onCompletion(subscriber::unsubscribe);
        onTimeout(subscriber::unsubscribe);
    }

    Subscriber<Temperature> getSubscriber() {
        return subscriber;
    }
}
