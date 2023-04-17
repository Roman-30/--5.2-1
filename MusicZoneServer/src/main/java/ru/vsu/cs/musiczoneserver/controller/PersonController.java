package ru.vsu.cs.musiczoneserver.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.musiczoneserver.service.PersonService;

@RestController
public class PersonController {
    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }
}
