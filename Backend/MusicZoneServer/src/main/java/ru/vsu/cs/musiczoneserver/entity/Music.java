package ru.vsu.cs.musiczoneserver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "music_in_playlist",
            joinColumns = {
                    @JoinColumn(name = "music_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "playlist_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Playlist> playlists = new HashSet<>();
}
