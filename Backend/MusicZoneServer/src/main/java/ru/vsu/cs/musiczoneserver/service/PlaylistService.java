package ru.vsu.cs.musiczoneserver.service;


import org.springframework.stereotype.Service;

import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.dto.PlaylistDto;
import ru.vsu.cs.musiczoneserver.entity.Playlist;
import ru.vsu.cs.musiczoneserver.mapper.MusicMapper;
import ru.vsu.cs.musiczoneserver.mapper.PlaylistMapper;
import ru.vsu.cs.musiczoneserver.repository.MusicRepository;
import ru.vsu.cs.musiczoneserver.repository.PersonRepository;
import ru.vsu.cs.musiczoneserver.repository.PlaylistRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PersonRepository personRepository;

    private final MusicRepository musicRepository;
    private final MusicMapper musicMapper;

    private final PlaylistMapper mapper;

    public PlaylistService(PlaylistRepository playlistRepository, PersonRepository personRepository, MusicRepository musicRepository, MusicMapper musicMapper, PlaylistMapper mapper) {
        this.playlistRepository = playlistRepository;
        this.personRepository = personRepository;
        this.musicRepository = musicRepository;
        this.musicMapper = musicMapper;
        this.mapper = mapper;
    }

    public Playlist savePlayList(PlaylistDto dto, String email) {
        Optional<Playlist> oldPlaylist = playlistRepository.findByName(dto.getName());
        if (oldPlaylist.isPresent()) {
            return null;
        } else {
            Playlist playlist = mapper.toEntity(dto);
            playlist.getPeople().add(personRepository.findByEmail(email)
                    .orElseThrow());

            addMusicOnPlaylist(playlist, dto.getIds());

            return playlistRepository.save(playlist);
        }
    }

    public List<MusicDto> findPlayListMusicByName(String name) {
        var playlist = playlistRepository.findByName(name);
        if (playlist.isEmpty()) {
            return null;
        } else {
            Playlist cur = playlist.orElseThrow();

            return cur.getMusics()
                    .stream()
                    .map(musicMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    public Playlist updatePlaylist(PlaylistDto dto) {
        Optional<Playlist> oldPlaylist = playlistRepository.findById(dto.getId());
        if (oldPlaylist.isPresent()) {
            Playlist playlist = oldPlaylist.orElseThrow();
            playlist.setName(dto.getName());
            playlist.setDescription(dto.getDescription());

            playlist.getMusics().clear();
            addMusicOnPlaylist(playlist, dto.getIds());

            return playlistRepository.save(playlist);
        } else {
            return null;
        }
    }

    public Playlist deleteMusicOnPlaylist(String id, Integer tr) {
        var user = personRepository.findByEmail(id).orElseThrow();
        Optional<Playlist> oldPlaylist = playlistRepository.findByName(user.getEmail());
        if (oldPlaylist.isPresent()) {
            Playlist playlist = oldPlaylist.orElseThrow();
            playlist.getMusics().remove(musicRepository.findById(tr)
                    .orElseThrow());

            return playlistRepository.save(playlist);
        } else {
            return null;
        }
    }

    public Playlist addMusicOnPlaylist(String id, Integer tr) {
        var user = personRepository.findByEmail(id).orElseThrow();
        Optional<Playlist> oldPlaylist = playlistRepository.findByName(user.getEmail());
        Playlist playlist;
        if (oldPlaylist.isPresent()) {
            playlist = oldPlaylist.orElseThrow();
            playlist.getMusics().add(musicRepository.findById(tr)
                    .orElseThrow());

        } else {
            playlist = playlistRepository.save(
                    mapper.toEntity(new PlaylistDto(user.getNickName(), "User tracks"))
            );


            playlist.getMusics().add(musicRepository.findById(tr)
                    .orElseThrow());

        }

        var s = playlistRepository.save(playlist);

        user.getPlaylists().add(s);
        personRepository.save(user);

        return s;
    }

    private void addMusicOnPlaylist(Playlist playlist, Set<Integer> musicIds) {
        if (musicIds.size() == 0) return;
        for (Integer id : musicIds) {
            playlist.getMusics().add(
                    musicRepository.findById(id).orElseThrow()
            );
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
                .filter(e -> !e.getName().equals("APP_MUSIC"))
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
