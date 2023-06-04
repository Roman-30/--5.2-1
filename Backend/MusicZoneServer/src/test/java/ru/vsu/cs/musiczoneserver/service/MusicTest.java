package ru.vsu.cs.musiczoneserver.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.musiczoneserver.IntegrationEnvironment;
import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.mapper.MusicMapper;
import ru.vsu.cs.musiczoneserver.repository.MusicRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class MusicTest extends IntegrationEnvironment {

    @Autowired
    private MusicService service;
    @Autowired
    private MusicMapper mapper;
    @Autowired
    private MusicRepository repository;

    @Test
    @Rollback
    @Transactional
    public void testFindAll() {
        MusicDto musicDto1 = new MusicDto();
        musicDto1.setLink("C:/f/s/t/");
        musicDto1.setName("Hello1");
        musicDto1.setGenre("Roc1");
        musicDto1.setCopyright("Test1");

        List<MusicDto> musicDtoList = List.of(musicDto1);

        repository.saveAndFlush(mapper.toEntity(musicDto1));

        List<MusicDto> list = service.findAll();

        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(musicDtoList.size()));
        for (int i = 0; i < musicDtoList.size(); i++) {
            assertThat(list.get(i).getGenre(), is(musicDtoList.get(i).getGenre()));
            assertThat(list.get(i).getCopyright(), is(musicDtoList.get(i).getCopyright()));
            assertThat(list.get(i).getLink(), is(musicDtoList.get(i).getLink()));
            assertThat(list.get(i).getName(), is(musicDtoList.get(i).getName()));
        }
    }

    @Test
    @Rollback
    @Transactional
    public void testFindAllEmpty() {

        List<MusicDto> musicDtoList = new ArrayList<>();
        List<MusicDto> list = service.findAll();

        assertThat(list, is(musicDtoList));
        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(musicDtoList.size()));
    }

    @Test
    @Rollback
    @Transactional
    public void testSaveMusic() {
        MusicDto musicDto1 = new MusicDto();
        musicDto1.setId(3);
        musicDto1.setLink("C:/f/s/t/");
        musicDto1.setName("Hello1");
        musicDto1.setGenre("Roc1");
        musicDto1.setCopyright("Test1");

        MusicDto saved = service.saveMusic(musicDto1);

        assertThat(saved, is(notNullValue()));
        assertThat(saved, is(musicDto1));
    }

    @Test
    @Rollback
    @Transactional
    public void testGetFileByLink() {
        String basePath = "src/test/java/ru/vsu/cs/musiczoneserver/fiels/test.txt";
        byte[] testBytes = {72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33};

        byte[] fileBytes = service.getFileByLink(basePath);

        assertThat(fileBytes, is(notNullValue()));
        assertThat(fileBytes, is(testBytes));
    }

    @Test
    @Rollback
    @Transactional
    public void testDeleteMusicFile() {
        String basePath = "src/test/java/ru/vsu/cs/musiczoneserver/fiels/test1.txt";
        File testFile = new File(basePath);
        testFile.getParentFile().mkdirs();
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean result = service.deleteMusicFile(basePath);

        assertThat(true, is(result));
        assertThat(false, is(new File(basePath).exists()));

    }

    @Test
    @Rollback
    @Transactional
    public void testUpdateMusic() {
        MusicDto saveDto = new MusicDto();
        saveDto.setId(1);
        saveDto.setLink("C:/f/s/t/");
        saveDto.setName("Hello1");
        saveDto.setGenre("Roc1");
        saveDto.setCopyright("Test1");

        MusicDto updateDto = new MusicDto();
        updateDto.setId(1);
        updateDto.setLink("C:/f");
        updateDto.setName("Tes");
        updateDto.setGenre("Rock");
        updateDto.setCopyright("Test");

        repository.saveAndFlush(mapper.toEntity(updateDto));
        MusicDto dto = mapper.toDto(service.updateMusic(updateDto));

        assertThat(dto, is(notNullValue()));
        assertThat(updateDto, is(dto));
    }
}
