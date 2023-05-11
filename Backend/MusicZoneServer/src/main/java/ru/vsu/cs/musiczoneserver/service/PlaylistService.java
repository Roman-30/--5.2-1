package ru.vsu.cs.musiczoneserver.service;

import org.springframework.stereotype.Service;
import ru.vsu.cs.musiczoneserver.dto.PlaylistDto;
import ru.vsu.cs.musiczoneserver.entity.Playlist;
import ru.vsu.cs.musiczoneserver.mapper.PlaylistMapper;
import ru.vsu.cs.musiczoneserver.repository.MusicRepository;
import ru.vsu.cs.musiczoneserver.repository.PersonRepository;
import ru.vsu.cs.musiczoneserver.repository.PlaylistRepository;

import java.security.cert.PKIXParameters;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PersonRepository personRepository;

    private final MusicRepository musicRepository;

    private final PlaylistMapper mapper;

    public PlaylistService(PlaylistRepository playlistRepository, PersonRepository personRepository, MusicRepository musicRepository, PlaylistMapper mapper) {
        this.playlistRepository = playlistRepository;
        this.personRepository = personRepository;
        this.musicRepository = musicRepository;
        this.mapper = mapper;
    }

    public Playlist savePlayList(PlaylistDto dto) {
        return new Playlist();
    }

    public Playlist save(PlaylistDto dto, String email) {
        Optional<Playlist> oldPlaylist = playlistRepository.findByName(dto.getName());
        if (oldPlaylist.isPresent()) {
            return null;
        } else {
            Playlist playlist = mapper.toEntity(dto);
            playlist.getPeople().add(personRepository.findByEmail(email)
                    .orElseThrow());

            return playlistRepository.save(mapper.toEntity(dto));
        }
    }

    public Playlist updatePlaylist(Integer id, PlaylistDto dto) {
        Optional<Playlist> oldPlaylist = playlistRepository.findById(id);
        if (oldPlaylist.isPresent()) {
            Playlist playlist = oldPlaylist.orElseThrow();
            playlist.setName(dto.getName());
            playlist.setDescription(dto.getDescription());

            return playlistRepository.save(playlist);
        } else {
            return null;
        }
    }

    public Playlist deleteMusicOnPlaylist(Integer pl, Integer tr) {
        Optional<Playlist> oldPlaylist = playlistRepository.findById(pl);
        if (oldPlaylist.isPresent()) {
            Playlist playlist = oldPlaylist.orElseThrow();
            playlist.getMusics().remove(musicRepository.findById(tr)
                    .orElseThrow());

            return playlistRepository.save(playlist);
        } else {
            return null;
        }
    }

    public Playlist addMusicOnPlaylist(Integer pl, Integer tr) {
        Optional<Playlist> oldPlaylist = playlistRepository.findById(pl);
        if (oldPlaylist.isPresent()) {
            Playlist playlist = oldPlaylist.orElseThrow();
            playlist.getMusics().add(musicRepository.findById(tr)
                    .orElseThrow());

            return playlistRepository.save(playlist);
        } else {
            return null;
        }
    }


    public Playlist deletePlaylist(Integer id) {
        Optional<Playlist> oldPlaylist = playlistRepository.findById(id);
        if (oldPlaylist.isPresent()) {
            Playlist playlist = oldPlaylist.orElseThrow();
            playlistRepository.delete(playlist);
            return playlist;
        } else {
            return null;
        }
    }

    public List<PlaylistDto> findAll() {
        return playlistRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
