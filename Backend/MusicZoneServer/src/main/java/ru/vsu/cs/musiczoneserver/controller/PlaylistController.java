package ru.vsu.cs.musiczoneserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.musiczoneserver.dto.PlaylistDto;
import ru.vsu.cs.musiczoneserver.service.PlaylistService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPlaylist(@RequestBody @Valid PlaylistDto dto,
                                         @RequestParam String email
    ) {
        var playlist = service.savePlayList(dto, email);
        if (playlist == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Create is successful!", HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePlaylist(@RequestBody @Valid PlaylistDto dto) {
        var playlist = service.updatePlaylist(dto);
        if (playlist == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Updating is successful!", HttpStatus.OK);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<PlaylistDto>> findAllPlaylists() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/song/delete")
    public ResponseEntity<?> deleteMusicOnPlaylist(@RequestParam String pl, @RequestParam Integer tr) {
        var playlist = service.deleteMusicOnPlaylist(pl, tr);
        if (playlist == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Deleted is successful!", HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMusicOnPlaylist(@PathVariable Integer id) {
        var playlist = service.deletePlaylist(id);
        if (playlist == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Deleted is successful!", HttpStatus.OK);
        }
    }

    @PostMapping("/song/add")
    public ResponseEntity<?> addMusicOnPlaylist(@RequestParam String pl, @RequestParam Integer tr) {
        var playlist = service.addMusicOnPlaylist(pl, tr);
        if (playlist == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Added is successful!", HttpStatus.OK);
        }
    }

    @GetMapping("/musics/get/all")
    public ResponseEntity<?> getPlaylistMusic(@RequestParam String email) {
        var playlist = service.findPlayListMusicByName(email);
        if (playlist == null) {
            return new ResponseEntity<>("Error!", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(playlist, HttpStatus.OK);
        }
    }

}
