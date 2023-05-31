package ru.vsu.cs.musiczoneserver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "playlist")
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;

    private String nickname;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "music_in_playlist",
            joinColumns = {
                    @JoinColumn(name = "playlist_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "music_id", referencedColumnName = "id")})
    private Set<Music> musics = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_playlist",
            joinColumns = {
                    @JoinColumn(name = "playlist_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<Person> people = new HashSet<>();
}
