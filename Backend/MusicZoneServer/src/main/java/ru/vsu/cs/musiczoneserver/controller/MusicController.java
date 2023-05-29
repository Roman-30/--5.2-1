package ru.vsu.cs.musiczoneserver.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.service.MusicService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final MusicService service;

    public MusicController(MusicService service) {
        this.service = service;
    }

    @PostMapping("/file/add")
    public ResponseEntity<?> saveMusicFile(@RequestParam String name) throws IOException {
        String link = service.downloadFileToServer(name);
        if (link == null) {
            return new ResponseEntity<>("Download error", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(link, HttpStatus.OK);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMusic(@RequestBody @Valid MusicDto dto) {
        var music = service.saveMusic(dto);
        if (music == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Added is successful", HttpStatus.OK);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<MusicDto>> getAllMusic() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getMusic(@RequestParam String link) {
        return ResponseEntity.ok().body(service.getFileByLink(link));
    }

    @DeleteMapping("/file/delete")
    public ResponseEntity<?> deleteMusicFile(@RequestParam Integer id) {
        service.deleteMusic(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMusic(@RequestBody MusicDto dto) {
        var music = service.updateMusic(dto);
        if (music == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Update is successful", HttpStatus.OK);
        }
    }
}
