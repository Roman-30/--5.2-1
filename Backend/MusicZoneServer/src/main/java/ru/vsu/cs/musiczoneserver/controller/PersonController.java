package ru.vsu.cs.musiczoneserver.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.musiczoneserver.dto.PersonDto;
import ru.vsu.cs.musiczoneserver.service.PersonService;

import javax.validation.Valid;

@RestController
public class PersonController {
    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<PersonDto> registration(@Valid @RequestBody PersonDto person) {
        var user = service.savePerson(person);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
    }
}
