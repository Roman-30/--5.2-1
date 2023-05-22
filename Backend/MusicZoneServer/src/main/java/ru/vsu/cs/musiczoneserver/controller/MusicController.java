package ru.vsu.cs.musiczoneserver.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.entity.Music;
import ru.vsu.cs.musiczoneserver.service.MusicService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
//@Tag(name = "Music controller", description = "Music management APIs")
@RequestMapping("/music")
public class MusicController {

    private final MusicService service;

    public MusicController(MusicService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveMusic(@RequestBody @Valid MusicDto dto) throws IOException {
        String link = service.downloadFileToServer(dto.getName());
        dto.setLink(link);
        var music = service.saveMusic(dto);
        if (music == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(music, HttpStatus.OK);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<MusicDto>> getAllMusic() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMusic(@RequestBody MusicDto dto) {
        var music = service.deleteMusic(dto);
        if (music == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(music, HttpStatus.OK);
        }
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getMusic(@RequestParam String link) {
        return ResponseEntity.ok().body(service.getFileByLink(link));
//        Byte[][] sd = new Byte[2][];
//        return List.of(sd);
    }

    @DeleteMapping("/file/delete")
    public ResponseEntity<?> deleteMusicFile(@RequestParam String link) {
        return ResponseEntity.ok(link);
    }
}
