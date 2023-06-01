package ru.vsu.cs.musiczoneserver.service;

import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.dto.PlaylistDto;
import ru.vsu.cs.musiczoneserver.entity.Music;
import ru.vsu.cs.musiczoneserver.entity.Playlist;
import ru.vsu.cs.musiczoneserver.exception.MyException;
import ru.vsu.cs.musiczoneserver.mapper.MusicMapper;
import ru.vsu.cs.musiczoneserver.mapper.PlaylistMapper;
import ru.vsu.cs.musiczoneserver.repository.MusicRepository;
import ru.vsu.cs.musiczoneserver.repository.PlaylistRepository;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicService {

    private static final String NAME = "APP_MUSIC";
    private final MusicMapper mapper;
    private final PlaylistMapper playlistMapper;
    private final MusicRepository musicRepository;
    private final PlaylistRepository playlistRepository;

    public MusicService(MusicMapper mapper, PlaylistMapper playlistMapper, MusicRepository musicRepository, PlaylistRepository playlistRepository) {
        this.mapper = mapper;
        this.playlistMapper = playlistMapper;
        this.musicRepository = musicRepository;
        this.playlistRepository = playlistRepository;
    }

    public MusicDto saveMusic(MusicDto musicDto) {
        if (musicRepository.findByLink(musicDto.getLink()).isPresent()) {
            return null;
        }

        var playlist = playlistRepository.findByName(NAME);
        Playlist playlist1;

        if (playlist.isEmpty()) {
            playlist1 = playlistRepository.save(playlistMapper
                    .toEntity(new PlaylistDto(NAME, "All app music")));
        } else {
            playlist1 = playlist.orElseThrow();
        }

        var music = mapper.toEntity(musicDto);

        music.getPlaylists().add(playlist1);

        return mapper.toDto(musicRepository.save(music));
    }

    public byte[] getFileByLink(String link) {
        String srcFile = new File(link).getAbsolutePath();


        byte[] byteArray = new byte[(int) (new File(link).length())];

        try (FileInputStream fileInputStream = new FileInputStream(srcFile)) {
            fileInputStream.read(byteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArray;
    }

    public List<MusicDto> findAll() {
        return musicRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public String downloadFileToServer(String fileName) throws IOException {
        String url = "https://storage.yandexcloud.net/musik/" + fileName + ".mp3";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InputStream stream;
        try {
            stream = response.getEntity().getContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        String path = "MusicZoneServer\\src\\main\\resources\\music\\" + fileName + ".mp3";

        try (FileOutputStream fos = new FileOutputStream(
                new File(path).getAbsolutePath())) {
            fos.write(buffer.toByteArray());
            fos.close();
        }

        return path;
    }

    @SneakyThrows
    public void deleteMusic(Integer id) {
        var music = musicRepository.findById(id);
        if (music.isEmpty()) {
            throw new MyException("Трек не найден!");
        } else {
            Music music1 = music.orElseThrow();

            if (deleteMusicFile(music1.getLink())) {
                musicRepository.delete(music1);
            } else {
                throw new MyException("Ошибка удаления!");
            }
        }
    }

    private boolean deleteMusicFile(String link) {
        try {
            return new File(link).delete();
        } catch (NullPointerException e) {
            e.fillInStackTrace();
            return false;
        }
    }

    public Music updateMusic(MusicDto dto) {
        var oldMusic = musicRepository.findById(dto.getId());
        if (oldMusic.isPresent()) {
            Music music = oldMusic.orElseThrow();
            music.setName(dto.getName());
            music.setLink(dto.getLink());
            music.setCopyright(dto.getCopyright());
            music.setGenre(dto.getGenre());
            return musicRepository.save(music);
        } else {
            return null;
        }
    }
}
