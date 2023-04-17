package ru.vsu.cs.musiczoneserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Playlist extends JpaRepository<Playlist, Integer> {
}
