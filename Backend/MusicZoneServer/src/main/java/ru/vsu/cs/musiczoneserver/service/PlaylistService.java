package ru.vsu.cs.musiczoneserver.service;

import org.springframework.stereotype.Service;
import ru.vsu.cs.musiczoneserver.dto.PlaylistDto;
import ru.vsu.cs.musiczoneserver.entity.Playlist;
import ru.vsu.cs.musiczoneserver.mapper.PlaylistMapper;
import ru.vsu.cs.musiczoneserver.repository.PlaylistRepository;

@Service
public class PlaylistService {
    private final PlaylistRepository repository;

    private final PlaylistMapper mapper;

    public PlaylistService(PlaylistRepository repository, PlaylistMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Playlist savePlayList(PlaylistDto dto) {
        return new Playlist();
    }
}
