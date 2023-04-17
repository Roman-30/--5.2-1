package ru.vsu.cs.musiczoneserver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "music")
@Entity
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String copyright;
    @NonNull
    private String link;
    @NonNull
    private String genre;

    @NonNull
    @ManyToMany(mappedBy = "musics")
    private Set<Playlist> playlists;
}
