package ru.vsu.cs.musiczoneserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.musiczoneserver.service.PlaylistService;

@RestController
public class PlaylistController {
    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }
}
