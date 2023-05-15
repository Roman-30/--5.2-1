package ru.vsu.cs.musiczoneserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
public class PlaylistDto {

    private Integer id;
    private String name;
    private String description;

    public PlaylistDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
