package ru.vsu.cs.musiczoneserver.service;

import org.springframework.stereotype.Service;
import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.entity.Music;
import ru.vsu.cs.musiczoneserver.entity.Playlist;
import ru.vsu.cs.musiczoneserver.mapper.MusicMapper;
import ru.vsu.cs.musiczoneserver.repository.MusicRepository;
import ru.vsu.cs.musiczoneserver.repository.PlaylistRepository;

@Service
public class MusicService {

    private static final String name = "APP_MUSIC";

    private final MusicMapper mapper;

    private final MusicRepository musicRepository;
    private final PlaylistRepository playlistRepository;

    public MusicService(MusicMapper mapper, MusicRepository musicRepository, PlaylistRepository playlistRepository) {
        this.mapper = mapper;
        this.musicRepository = musicRepository;
        this.playlistRepository = playlistRepository;
    }

    public Music saveMusic(MusicDto musicDto) {
        if (musicRepository.findByLink(musicDto.getLink()).isPresent()) {
            return null;
        }

        var playlist = playlistRepository.findByName(name).orElseThrow();

        var music = mapper.toEntity(musicDto);

        music.getPlaylists().add(playlist);

        var cur = musicRepository.save(music);

//        playlist.getMusics().add(cur);

        return cur ;
    }

    public Music deleteMusic(MusicDto musicDto) {
        var music = musicRepository.findByLink(musicDto.getLink()).
                orElseThrow();

        musicRepository.delete(music);

        return music;
    }

}
