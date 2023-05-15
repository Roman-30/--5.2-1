package ru.vsu.cs.musiczoneserver.controller;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.musiczoneserver.dto.PersonDto;
import ru.vsu.cs.musiczoneserver.service.PersonService;

import javax.validation.Valid;

@RestController
@RequestMapping("/person")
@Api(value = "User Resource REST Endpoint", description = "Shows the user info")
public class PersonController {
    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody PersonDto person) {
        var user = service.savePerson(person);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Registration Successful!", HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public String login() {
        return "login";
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonData(@PathVariable Integer id, @RequestBody PersonDto dto) {
        var user = service.updateData(id, dto);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Update Successful!", HttpStatus.OK);
        }
    }

    // TODO: 11.05.2023
    @PutMapping("/pass/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestParam String pass) {
        var user = service.updatePassword(id, pass);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Update Successful!", HttpStatus.OK);
        }
    }
}
