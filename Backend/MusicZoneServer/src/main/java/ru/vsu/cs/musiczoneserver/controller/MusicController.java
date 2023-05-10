package ru.vsu.cs.musiczoneserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.entity.Music;
import ru.vsu.cs.musiczoneserver.service.MusicService;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

@RestController
public class MusicController {

    private final MusicService service;

    public MusicController(MusicService service) {
        this.service = service;
    }

    @PostMapping("/music/save")
    public ResponseEntity<Music> saveMusic(@RequestBody MusicDto dto) {
        var music = service.saveMusic(dto);
        if (music == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(music, HttpStatus.OK);
        }
    }

    @DeleteMapping("/music/delete")
    public ResponseEntity<Music> deleteMusic(@RequestBody MusicDto dto) {
        var music = service.deleteMusic(dto);
        if (music == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(music, HttpStatus.OK);
        }
    }

    @PostMapping("/music")
    public ResponseEntity<byte[]> getMusic(@RequestParam String link) throws IOException {
        return ResponseEntity.ok(service.getFileByLink(link));
    }

    @PostMapping("music/file/save")
    public ResponseEntity<?> saveFile(/*@RequestBody byte[] bytes*/) throws Exception {
        service.saveMusicFile(new byte[]{9, -7, 10, 102, 80, -60, -54, -115, -122, -117, 36, -83, 118, 119, -38, -93, -58, 44, 117, -107, 68, 5, -124, -93, 112, -91, -63, 126, 101, -40, -87, 1, 9, 18, -54, -29, -37, 65, -71, -115, 99, 81, -120, 0, 22, 53, 82, -23, 99, -39, -116, -72, 23, -101, -21, 100, -59, 30, -108, -109, 110, 67, 0, 19, -48, 49, 93, -74, -116, -44, 41, 87, 90, 96, -88, 14, -88, 115, 121, -32, -110, -46, -68, -32, -86, 98, -90, 19, 3, -31, 51, -102, -58, -55, 108, 34, 103, -50, 34, 19, -9, 81, -67, -60, 107, 42, -10, 53, 89, -25, -72, 106, -59, 113, -73, 126, 103, 43, 114, 31, 46, -40, 124, 92, -91, -33, 43, -121, -120, 6, 78, -118, -79, -41, 81, 14, 68, 99, -75, 51, -16, 125, -84, 96, 75, 23, 30, -58, 7, 73, 86, 98, 14, -71, 105, -12, -104, 123, 112, -110, -29, 89, -114, 70, -83, 82, -51, -64, -75, -83, -4, -61, 65, -106, 93, -65, 102, -66, 85, 44, -38, -112, 86, -34, 19, -78, 63, -3, 101, 42, -25, -1, 59, 86, -59, -58, 94, -56, -32, 73, 53, -24, -111, 46, 79, -1, -49, -86, 35, 10, 1, 13, 4, 56, 104, -120, -63, -126, 9, 14, -61, -62, 49, -86, 90, -45, 73, 4, -85, -15, -62, 79, 11, 62, 85, -56, 52, 114, 77, -53, -84, 51, -88, 49, 93, -71, 100, -58, -84, -55, 97, 26, 103, -29, -59, -25, 119, 94, -68, 16, 16, 71, -97, 122, -49, 108, 32, 64, 43, 59, -103, -54, 97, 47, -41});
        return ResponseEntity.ok("OK!");
    }
}
