package ru.vsu.cs.musiczoneserver.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.musiczoneserver.service.MusicService;

@RestController
public class MusicController {

    private final MusicService service;

    public MusicController(MusicService service) {
        this.service = service;
    }
}
