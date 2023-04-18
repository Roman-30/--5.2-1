package ru.vsu.cs.musiczoneserver.service;

import org.springframework.stereotype.Service;
import ru.vsu.cs.musiczoneserver.repository.PlaylistRepository;

@Service
public class PlaylistService {
    private final PlaylistRepository repository;

    public PlaylistService(PlaylistRepository repository) {
        this.repository = repository;
    }


}
