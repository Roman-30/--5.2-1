package ru.vsu.cs.musiczoneserver.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class MusicDto {

    private Integer id;
    private String name;
    private String copyright;
    private String link;
    private String genre;
}
