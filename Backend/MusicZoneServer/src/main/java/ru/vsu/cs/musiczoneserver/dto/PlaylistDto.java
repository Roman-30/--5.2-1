package ru.vsu.cs.musiczoneserver.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class PlaylistDto {

    private Integer id;
    private String name;
    @NotEmpty(message = "Description cannot be null")
    private String description;
    @NotEmpty(message = "nickname cannot be null")
    private String nickname;
    private Set<Integer> ids;

    public PlaylistDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
