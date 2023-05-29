package ru.vsu.cs.musiczoneserver.service;

import lombok.SneakyThrows;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.cs.musiczoneserver.dto.PersonDto;
import ru.vsu.cs.musiczoneserver.entity.Person;
import ru.vsu.cs.musiczoneserver.entity.jwt.JwtRequest;
import ru.vsu.cs.musiczoneserver.entity.jwt.JwtResponse;
import ru.vsu.cs.musiczoneserver.entity.model.Role;
import ru.vsu.cs.musiczoneserver.exception.MyException;
import ru.vsu.cs.musiczoneserver.mapper.PersonMapper;
import ru.vsu.cs.musiczoneserver.repository.PersonRepository;
import ru.vsu.cs.musiczoneserver.service.jwtcomponent.JwtProvider;

import javax.security.auth.message.AuthException;
import java.util.Collections;
import java.util.Optional;

@Service
public class PersonService implements UserDetailsService {
    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtProvider jwtProvider;


    public PersonService(PersonRepository personRepository, PersonMapper personMapper, BCryptPasswordEncoder bCryptPasswordEncoder, JwtProvider jwtProvider) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final Person user = personRepository.findByEmail(authRequest.getEmail()).orElseThrow();

        if (bCryptPasswordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Invalid password");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Person savePerson(PersonDto user) {
        if (personRepository.findByEmail(user.getEmail()).isPresent()) {
            return null;
        }

        Person person = personMapper.toEntity(user);
        person.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        person.setRoles(Collections.singleton(Role.USER));
        person.setEmail(user.getEmail().toLowerCase());

        return personRepository.save(person);
    }

    @SneakyThrows
    public void sendMail(String email, String code) {
        var person = personRepository.findByEmail(email);

        if (person.isPresent()) {
            MailSender sender = new MailSender(email);
            sender.send("Восстановление пароля", "Код --> " + code);
        } else {
            throw new MyException("Пользователь не найден");
        }

    }

    public Person updateData(PersonDto dto) {
        Optional<Person> person = personRepository.findById(dto.getId());
        if (person.isPresent()) {

            Person oldPerson = person.orElseThrow();
            oldPerson.setName(dto.getName());
            oldPerson.setNickname(dto.getNickname());
            oldPerson.setSurname(dto.getSurname());
            oldPerson.setEmail(dto.getEmail().toLowerCase());
            oldPerson.setPhone(dto.getPhone());

            return personRepository.save(oldPerson);
        } else {
            return null;
        }
    }

    public Person updatePassword(Integer id, String pass) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {

            Person oldPerson = person.orElseThrow();
            oldPerson.setPassword(bCryptPasswordEncoder.encode(pass));

            return personRepository.save(oldPerson);
        } else {
            return null;
        }
    }

    public PersonDto getUserByEmail(String email) {
        var person = personRepository.findByEmail(email);
        if (person.isPresent()) {
            return personMapper.toDto(person.orElseThrow());
        } else {
            return null;
        }
    }
}
