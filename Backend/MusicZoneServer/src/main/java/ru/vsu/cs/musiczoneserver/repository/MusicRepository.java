package ru.vsu.cs.musiczoneserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.musiczoneserver.entity.Music;

import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {

    Optional<Music> findByLink(String link);
}
