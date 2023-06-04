package ru.vsu.cs.musiczoneserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
public class PlaylistDto {

    private Integer id;
    private String name;
    private String description;
    private String nickname;
    private Set<Integer> ids;

    public PlaylistDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
