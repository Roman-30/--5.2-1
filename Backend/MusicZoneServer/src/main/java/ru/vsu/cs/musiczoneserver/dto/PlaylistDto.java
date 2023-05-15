package ru.vsu.cs.musiczoneserver.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PlaylistDto {

    private Integer id;
    private String name;
    private String description;
    private Set<Integer> ids;

    public PlaylistDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
