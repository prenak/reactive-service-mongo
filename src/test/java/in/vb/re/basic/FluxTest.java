package in.vb.re.basic;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void runFluxOnComplete(){
        Flux<String> stringFlux = Flux.just("A01" , "A02", "A03")
                .log();

        stringFlux.subscribe(System.out::println, (ex) -> System.err.println("Exception is " + ex));
    }

    @Test
    public void runFluxOnCompleteWithConcat(){
        Flux<String> stringFlux = Flux.just("A01" , "A02", "A03")
                .log()
                .concatWith(Flux.just("B01", "B02"));

        stringFlux.subscribe(System.out::println, (ex) -> System.err.println("Exception is " + ex), () -> System.out.println("Completed testFluxOnCompleteWithConcat"));
    }

    @Test
    public void runFluxOnError(){
        Flux<String> stringFlux = Flux
                .just("A01" , "A02", "A03")
                .log()
                .concatWith(Flux.error(new RuntimeException("Some error occurred")));

        stringFlux.subscribe(System.out::println, (ex) -> System.err.println("Exception is " + ex));
    }

    @Test
    public void runFluxAfterError(){
        Flux<String> stringFlux = Flux
                .just("A01" , "A02", "A03")
                .concatWith(Flux.error(new RuntimeException("Some error occurred")))
                .log()
                .concatWith(Flux.just("B01")); // TODO - This is not sent once the onError() is called

        stringFlux.subscribe(System.out::println, (ex) -> System.err.println("Exception is " + ex), () -> System.out.println("Completed testFluxOnCompleteWithConcat"));
    }

    @Test
    public void testFluxElementsOneByOne(){
        Flux<String> stringFlux = Flux.just("A01" , "A02", "A03")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("A01").expectNext("A02").expectNext("A03")
                .verifyComplete();
    }

    @Test
    public void testFluxElementsAll(){
        Flux<String> stringFlux = Flux.just("A01" , "A02", "A03")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("A01", "A02", "A03")
                .verifyComplete();
    }

    @Test
    public void testFluxElementsCount(){
        Flux<String> stringFlux = Flux.just("A01" , "A02", "A03")
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void testFluxElementsError(){
        Flux<String> stringFlux = Flux
                .just("A01" , "A02", "A03")
                .concatWith(Flux.error(new RuntimeException("Some error occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("A01", "A02", "A03")
                .expectError(RuntimeException.class);
    }

    @Test
    public void testFluxElementsErrorMessage(){
        Flux<String> stringFlux = Flux
                .just("A01" , "A02", "A03")
                .concatWith(Flux.error(new RuntimeException("Some error occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("A01", "A02", "A03")
                .expectErrorMessage("Some error occurred");
    }
}
