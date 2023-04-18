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

    @NonNull
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "music_in_playlist",
            joinColumns = {
                    @JoinColumn(name = "music_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "playlist_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Music> musics;

    @NonNull
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_playlist",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "playlist_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Person> people;



}
