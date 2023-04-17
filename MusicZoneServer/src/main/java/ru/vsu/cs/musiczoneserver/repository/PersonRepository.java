package ru.vsu.cs.musiczoneserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.musiczoneserver.entity.Person;

import javax.naming.InsufficientResourcesException;

@Repository
public interface PersonRepository extends JpaRepository<Person, InsufficientResourcesException> {
}
