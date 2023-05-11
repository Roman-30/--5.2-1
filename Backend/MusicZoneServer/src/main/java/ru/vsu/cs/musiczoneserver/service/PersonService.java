package ru.vsu.cs.musiczoneserver.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.cs.musiczoneserver.dto.PersonDto;
import ru.vsu.cs.musiczoneserver.entity.Person;
import ru.vsu.cs.musiczoneserver.entity.model.Role;
import ru.vsu.cs.musiczoneserver.mapper.PersonMapper;
import ru.vsu.cs.musiczoneserver.repository.PersonRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class PersonService implements UserDetailsService {
    private final PersonRepository repository;

    private final PersonMapper mapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PersonService(PersonRepository repository, PersonMapper mapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public Person savePerson(PersonDto user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            return null;
        }

        Person person = mapper.toEntity(user);
        person.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        person.setRoles(Collections.singleton(Role.USER));

        return repository.save(person);
    }

    public Person updateData(Integer id, PersonDto dto) {
        Optional<Person> person = repository.findById(id);
        if (person.isPresent()) {

            Person oldPerson = person.orElseThrow();
            oldPerson.setName(dto.getName());
            oldPerson.setNickName(dto.getNickName());
            oldPerson.setSurname(dto.getSurname());
            oldPerson.setEmail(dto.getEmail());
            oldPerson.setPhone(dto.getPhone());

            return repository.save(oldPerson);
        } else {
            return null;
        }
    }

    public Person updatePassword(Integer id, String pass) {
        Optional<Person> person = repository.findById(id);
        if (person.isPresent()) {

            Person oldPerson = person.orElseThrow();
            oldPerson.setPassword(bCryptPasswordEncoder.encode(pass));

            return repository.save(oldPerson);
        } else {
            return null;
        }
    }
}
