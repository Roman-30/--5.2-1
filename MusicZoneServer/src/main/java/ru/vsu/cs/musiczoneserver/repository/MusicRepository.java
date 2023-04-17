package ru.vsu.cs.musiczoneserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.musiczoneserver.entity.Music;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {
}
