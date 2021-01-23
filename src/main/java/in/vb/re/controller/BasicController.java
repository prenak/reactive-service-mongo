package in.vb.re.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/basic")
public class BasicController {

    @GetMapping(value = "/nos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Integer> getNumbers(){
        return Flux.just(1,2,3,4,5,6).log();
    }

    @GetMapping(value = "/noStream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> getNumbersAsStream(){
        return Flux.just(1,2,3,4,5,6,7,8,9,10).delayElements(Duration.ofMillis(2000)).log();
    }
}
