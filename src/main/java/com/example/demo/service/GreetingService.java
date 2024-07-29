package com.example.demo.service;

import com.example.demo.model.Greeting;
import com.example.demo.repository.GreetingRepository;
import com.example.demo.exception.GreetingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {
    @Autowired
    private GreetingRepository repository;

    public Greeting saveGreeting(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new GreetingException("Name cannot be empty");
        }
        Greeting greeting = new Greeting(name, "Hello, " + name + "!");
        return repository.save(greeting);
    }

    public List<Greeting> getAllGreetings() {
        return repository.findAll();
    }

    public Optional<Greeting> getGreetingById(Long id) {
        return repository.findById(id);
    }

    public Optional<Greeting> updateGreeting(Long id, Greeting greetingDetails) {
        return repository.findById(id)
                .map(greeting -> {
                    greeting.setName(greetingDetails.getName());
                    greeting.setMessage("Hello, " + greetingDetails.getName() + "!");
                    return repository.save(greeting);
                });
    }

    public boolean deleteGreeting(Long id) {
        return repository.findById(id)
                .map(greeting -> {
                    repository.delete(greeting);
                    return true;
                })
                .orElse(false);
    }
}