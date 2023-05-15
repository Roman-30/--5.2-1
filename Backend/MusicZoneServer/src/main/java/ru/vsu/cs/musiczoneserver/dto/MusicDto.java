package ru.vsu.cs.musiczoneserver.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
public class MusicDto {

    private Integer id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Copyright cannot be null")
    private String copyright;

    private String link;
    @NotNull(message = "Genre cannot be null")
    private String genre;
}
