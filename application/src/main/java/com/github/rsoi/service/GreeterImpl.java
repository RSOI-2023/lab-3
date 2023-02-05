package com.github.rsoi.service;

import com.github.rsoi.domain.Greeting;
import com.github.rsoi.repository.GreetingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GreeterImpl implements Greeter {
    private final GreetingRepository greetingRepository;

    @Override
    public void sayHello() {
        //Getting first greeting, expecting only 1 greeting here
        var greetings = greetingRepository.findAll().stream().findFirst().orElseThrow();
        System.out.println(greetings.getText());
    }

    /**
     * Saving our Hello world greeting to the database, @PostConstruct executes only once on context creation
     */
    @PostConstruct
    void init() {
        var greetings = greetingRepository.findAll();
        if (greetings.isEmpty()) {
            var greeting = new Greeting();
            greeting.setText("Hello world from Spring Service Bean!");
            greetingRepository.save(greeting);
            System.out.println("Oh, it's first start of application, I created one for start");
        } else {
            System.out.println("Cool, there are already some greetings in the DB");
        }
    }
}
