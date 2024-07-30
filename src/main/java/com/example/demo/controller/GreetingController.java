package com.example.demo.controller;

import com.example.demo.exception.GreetingException;
import com.example.demo.model.Greeting;
import com.example.demo.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingController {
    @Autowired
    private GreetingService greetingService;

    @PostMapping
    public ResponseEntity<Greeting> createGreeting(@Valid @RequestBody Greeting greeting) {
        Greeting savedGreeting = greetingService.saveGreeting(greeting.getName());
        return new ResponseEntity<>(savedGreeting, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Greeting>> getAllGreetings() {
        List<Greeting> greetings = greetingService.getAllGreetings();
        return new ResponseEntity<>(greetings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Greeting> getGreetingById(@PathVariable Long id) {
        System.out.println("Fetching greeting with ID: " + id); // Debugging
        Greeting greeting = greetingService.findGreetingById(id);
        System.out.println("Greeting found: " + greeting); // Debugging
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Greeting> getGreetingByName(@RequestParam(value = "name") String name) {
        if (name.isEmpty()) {
            throw new GreetingException("Name cannot be empty");
        }
        return greetingService.getGreetingByName(name)
                .map(greeting -> new ResponseEntity<>(greeting, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Greeting> updateGreeting(@PathVariable Long id, @Valid @RequestBody Greeting greeting) {
        return greetingService.updateGreeting(id, greeting)
                .map(updatedGreeting -> new ResponseEntity<>(updatedGreeting, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGreeting(@PathVariable Long id) {
        if (greetingService.deleteGreeting(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/error")
    public ResponseEntity<String> triggerError() {
        throw new RuntimeException("Internal server error");
    }
}
