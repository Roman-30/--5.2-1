package ru.vsu.cs.musiczoneserver.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vsu.cs.musiczoneserver.IntegrationEnvironment;
import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.dto.PersonDto;
import ru.vsu.cs.musiczoneserver.dto.PlaylistDto;
import ru.vsu.cs.musiczoneserver.entity.Music;
import ru.vsu.cs.musiczoneserver.entity.Playlist;
import ru.vsu.cs.musiczoneserver.mapper.MusicMapper;
import ru.vsu.cs.musiczoneserver.mapper.PersonMapper;
import ru.vsu.cs.musiczoneserver.mapper.PlaylistMapper;
import ru.vsu.cs.musiczoneserver.repository.MusicRepository;
import ru.vsu.cs.musiczoneserver.repository.PersonRepository;
import ru.vsu.cs.musiczoneserver.repository.PlaylistRepository;

import javax.transaction.Transactional;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PlayListTest extends IntegrationEnvironment {

    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private PlaylistMapper playlistMapper;
    @Autowired
    private MusicMapper musicMapper;

    @Autowired
    private MusicService musicService;
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Test
    @Rollback
    @Transactional
    public void testFindAll() {
        PlaylistDto dto1 = new PlaylistDto();
        dto1.setName("test");
        dto1.setDescription("Description");
        dto1.setNickname("FOR_CODING");

        PlaylistDto dto2 = new PlaylistDto();
        dto2.setName("test");
        dto2.setDescription("Description");
        dto2.setNickname("FOR_CODING");

        List<PlaylistDto> list = List.of(dto1, dto2);

        playlistRepository.saveAndFlush(playlistMapper.toEntity(dto1));
        playlistRepository.saveAndFlush(playlistMapper.toEntity(dto2));

        List<PlaylistDto> response = playlistService.findAll();

        for (int i = 0; i < list.size(); i++) {
            assertThat(response.get(i), is(notNullValue()));
            assertThat(response.get(i).getName(), is(list.get(i).getName()));
            assertThat(response.get(i).getDescription(), is(list.get(i).getDescription()));
            assertThat(response.get(i).getNickname(), is(list.get(i).getNickname()));

        }
    }

    @Test
    @Rollback
    @Transactional
    public void testSavePlayList() {
        PersonDto personDto = new PersonDto();
        personDto.setId(1);
        personDto.setName("User");
        personDto.setPassword("1234567");
        personDto.setEmail("test@test.tes");

        MusicDto musicDto = new MusicDto();
        musicDto.setId(1);
        musicDto.setLink("/s/s/s/");
        musicDto.setName("Hello");
        musicDto.setGenre("Roc");
        musicDto.setCopyright("Test");

        PlaylistDto dto = new PlaylistDto();
        dto.setId(1);
        dto.setName("test");
        dto.setDescription("Description");
        dto.setNickname("FOR_CODING");
        dto.setIds(Collections.singleton(1));

        musicRepository.save(musicMapper.toEntity(musicDto));
        playlistRepository.saveAndFlush(playlistMapper.toEntity(dto));

        playlistService.savePlayList(dto, personDto.getEmail());

        List<MusicDto> playlist = playlistService.findPlayListMusicByName(dto.getName());

        assertThat(playlist, is(notNullValue()));

        for (MusicDto playlistDto : playlist) {
            assertThat(musicDto, is(playlistDto));
        }

        assertThat(musicDto.getName(), is(musicDto.getName()));
        assertThat(musicDto.getLink(), is(musicDto.getLink()));
        assertThat(musicDto.getGenre(), is(musicDto.getGenre()));
        assertThat(musicDto.getCopyright(), is(musicDto.getCopyright()));
    }

}
