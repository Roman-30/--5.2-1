package ru.vsu.cs.musiczoneserver.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.musiczoneserver.dto.PlaylistDto;
import ru.vsu.cs.musiczoneserver.service.PlaylistService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @PostMapping("/add")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "The request has succeeded or (your message)"),
//            @ApiResponse(code = 401, message = "The request requires user authentication or (your message)"),
//            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden or (your message)"),
//            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI or (your message)")})
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
            return new ResponseEntity<>("Delete error!", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(List.of("Deleted is successful!"), HttpStatus.OK);
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
            return new ResponseEntity<>("Added error!", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(List.of("Added is successful!"), HttpStatus.OK);
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
