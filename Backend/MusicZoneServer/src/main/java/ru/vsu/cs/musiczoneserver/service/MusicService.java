package ru.vsu.cs.musiczoneserver.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.dto.PlaylistDto;
import ru.vsu.cs.musiczoneserver.entity.Music;
import ru.vsu.cs.musiczoneserver.entity.Playlist;
import ru.vsu.cs.musiczoneserver.mapper.MusicMapper;
import ru.vsu.cs.musiczoneserver.mapper.PlaylistMapper;
import ru.vsu.cs.musiczoneserver.repository.MusicRepository;
import ru.vsu.cs.musiczoneserver.repository.PlaylistRepository;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicService {

    private static final String name = "APP_MUSIC";

    private final MusicMapper mapper;
    private final PlaylistMapper playlistMapper;

    private final MusicRepository musicRepository;
    private final PlaylistRepository playlistRepository;

    public MusicService(MusicMapper mapper, PlaylistMapper playlistMapper, MusicRepository musicRepository, PlaylistRepository playlistRepository) {
        this.mapper = mapper;
        this.playlistMapper = playlistMapper;
        this.musicRepository = musicRepository;
        this.playlistRepository = playlistRepository;
    }

    public MusicDto saveMusic(MusicDto musicDto) {
        if (musicRepository.findByLink(musicDto.getLink()).isPresent()) {
            return null;
        }

        var playlist = playlistRepository.findByName(name);

        if (playlist.isEmpty()) {
            playlistRepository.save(playlistMapper
                    .toEntity(new PlaylistDto(name, "All app music")));
        }

        Playlist playlist1 = playlistRepository.findByName(name).orElseThrow();

        var music = mapper.toEntity(musicDto);

        music.getPlaylists().add(playlist1);

        return mapper.toDto(musicRepository.save(music));
    }

    public Music deleteMusic(MusicDto musicDto) {
        var music = musicRepository.findByLink(musicDto.getLink()).
                orElseThrow();

        musicRepository.delete(music);

        return music;
    }

    public byte[] getFileByLink(String link) throws IOException {
        File srcFile = new File(link);

        byte[] byteArray = new byte[(int) srcFile.length()];

        try (FileInputStream fileInputStream = new FileInputStream(srcFile)) {
            fileInputStream.read(byteArray);
        }

        return byteArray;
    }

    public List<MusicDto> findAll() {
        return musicRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
