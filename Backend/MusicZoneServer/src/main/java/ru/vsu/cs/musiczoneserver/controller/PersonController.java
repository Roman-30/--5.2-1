package ru.vsu.cs.musiczoneserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.musiczoneserver.dto.PersonDto;
import ru.vsu.cs.musiczoneserver.entity.jwt.JwtRequest;
import ru.vsu.cs.musiczoneserver.entity.jwt.JwtResponse;
import ru.vsu.cs.musiczoneserver.service.PersonService;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody PersonDto person) {
        var user = service.savePerson(person);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Registration Successful!", HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePersonData(@RequestBody @Valid PersonDto dto) {
        var user = service.updateData(dto);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Update Successful!", HttpStatus.OK);
        }
    }

    @PutMapping("/pass/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestParam String pass) {
        var user = service.updatePassword(id, pass);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Update Successful!", HttpStatus.OK);
        }
    }

    @GetMapping("/send/{email}")
    public ResponseEntity<?> sendMessage(@PathVariable String email, @RequestParam String code) {
        service.sendMail(email, code);
        return ResponseEntity.ok("OK!");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = service.login(authRequest);
        return ResponseEntity.ok(token);
    }
    @GetMapping("/get/user")
    public ResponseEntity<?> getPersonByEmail(@RequestParam String email) {
        var user = service.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }
}
