package in.vb.re.basic;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    public void runMono(){
        Mono<String> stringMono = Mono.just("Hello");
        stringMono.log().subscribe(System.out::println);
    }

    @Test
    public void runMono_WithError(){
        Mono.error(new RuntimeException("Some exception occurred"))
            .log()
            .subscribe(System.out::println);
    }

    @Test
    public void testMono(){
        Mono<String> stringMono = Mono.just("Hello");
        StepVerifier.create(stringMono.log())
                .expectNext("Hello")
                .verifyComplete();
    }

    @Test
    public void testMono_WithError(){
        StepVerifier.create(Mono.error(new RuntimeException("Some exception happened")).log())
                .verifyError(RuntimeException.class);
    }

    @Test
    public void testMono_WithErrorMesssge(){
        StepVerifier.create(Mono.error(new RuntimeException("Some exception happened")).log())
                .verifyErrorMessage("Some exception happened");
    }
}
