package ru.vsu.cs.musiczoneserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.musiczoneserver.service.CloudService;

import java.io.IOException;

@RestController
public class CloudController {

    private final CloudService service;

    public CloudController(CloudService service) {
        this.service = service;
    }

    @GetMapping("/cloud/file")
    public void saveFileToServer(@RequestParam String name) throws IOException {
        service.main(name);
    }
}
