package ru.vsu.cs.musiczoneserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Music controller", description = "Music management APIs")
@RequestMapping("/music")
public class MusicController {

    private final MusicService service;

    public MusicController(MusicService service) {
        this.service = service;
    }

    @Operation(
            summary = "Saving music in data base",
            description = "Get a Tutorial object by specifying its id. The response is Tutorial object with id, title, description and published status.",
            tags = {"tutorials", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
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
    public Object getMusic(@RequestParam String link) {
        return service.getFileByLink(link);
//        Byte[][] sd = new Byte[2][];
//        return List.of(sd);
    }

    @DeleteMapping("/file/delete")
    public ResponseEntity<?> deleteMusicFile(@RequestParam String link) {
        return ResponseEntity.ok(link);
    }
}
