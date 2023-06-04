package ru.vsu.cs.musiczoneserver.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.musiczoneserver.IntegrationEnvironment;
import ru.vsu.cs.musiczoneserver.dto.PersonDto;
import ru.vsu.cs.musiczoneserver.entity.Person;
import ru.vsu.cs.musiczoneserver.entity.model.Role;
import ru.vsu.cs.musiczoneserver.mapper.PersonMapper;
import ru.vsu.cs.musiczoneserver.repository.PersonRepository;
import ru.vsu.cs.musiczoneserver.security.JwtProvider;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class PersonTest extends IntegrationEnvironment {

    @Autowired
    private PersonService service;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @Rollback
    @Transactional
    public void testGetUserByEmail() {

        PersonDto user = new PersonDto();
        user.setId(2);
        user.setName("Максим");
        user.setSurname("Гончаренко");
        user.setEmail("gmv.vrn@bk.ru");
        user.setPhone("8951801261");
        user.setNickname("Client_dev");
        user.setRoles(Collections.singleton(Role.USER));

        var response = service.getUserByEmail(user.getEmail());

        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(user.getName()));
        assertThat(response.getNickname(), is(user.getNickname()));
        assertThat(response.getPhone(), is(user.getPhone()));
        assertThat(response.getEmail(), is(user.getEmail()));
        assertThat(response.getRoles(), is(user.getRoles()));
        assertThat(response.getSurname(), is(user.getSurname()));
    }

    @Test
    @Rollback
    @Transactional
    public void testUpdateData() {

        PersonDto user = new PersonDto();
        user.setId(2);
        user.setName("Test");
        user.setSurname("test");
        user.setEmail("gmv.vrn@bk.ru");
        user.setPhone("2951201261");
        user.setNickname("Client_dev1");
        user.setRoles(Collections.singleton(Role.USER));

        var response = service.updateData(user);

        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(user.getName()));
        assertThat(response.getNickname(), is(user.getNickname()));
        assertThat(response.getPhone(), is(user.getPhone()));
        assertThat(response.getEmail(), is(user.getEmail()));
        assertThat(response.getRoles(), is(user.getRoles()));
        assertThat(response.getSurname(), is(user.getSurname()));
    }
}
