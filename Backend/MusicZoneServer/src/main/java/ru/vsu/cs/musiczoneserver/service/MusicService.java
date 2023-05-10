package ru.vsu.cs.musiczoneserver.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.entity.Music;
import ru.vsu.cs.musiczoneserver.entity.Playlist;
import ru.vsu.cs.musiczoneserver.mapper.MusicMapper;
import ru.vsu.cs.musiczoneserver.repository.MusicRepository;
import ru.vsu.cs.musiczoneserver.repository.PlaylistRepository;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicService {

    private static final String name = "APP_MUSIC";

    private final MusicMapper mapper;

    private final MusicRepository musicRepository;
    private final PlaylistRepository playlistRepository;

    public MusicService(MusicMapper mapper, MusicRepository musicRepository, PlaylistRepository playlistRepository) {
        this.mapper = mapper;
        this.musicRepository = musicRepository;
        this.playlistRepository = playlistRepository;
    }

    public Music saveMusic(MusicDto musicDto) {
        if (musicRepository.findByLink(musicDto.getLink()).isPresent()) {
            return null;
        }

        var playlist = playlistRepository.findByName(name).orElseThrow();

        var music = mapper.toEntity(musicDto);

        music.getPlaylists().add(playlist);

        return musicRepository.save(music);
    }

    public Music deleteMusic(MusicDto musicDto) {
        var music = musicRepository.findByLink(musicDto.getLink()).
                orElseThrow();

        musicRepository.delete(music);

        return music;
    }

    public byte[] getFileByLink(String link) throws IOException {
        File srcFile = new File("C:\\Users\\romse\\OneDrive\\Документы\\GitHub\\TP-5.2-1\\Backend\\MusicZoneServer\\src\\main\\resources\\music\\Melnica_-_Dorogi_48002701.wave");

        byte[] byteArray = new byte[(int) srcFile.length()];

        try (FileInputStream fileInputStream = new FileInputStream(srcFile)) {
            fileInputStream.read(byteArray);
        }

        System.out.println(Arrays.toString(byteArray));

        return byteArray;
    }

    public static AudioFormat getAudioFormat(File audioFile) throws Exception {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat audioFormat = audioInputStream.getFormat();
        audioInputStream.close();
        return audioFormat;
    }

    public void saveMusicFile(byte[] audioBytes) throws Exception {
        AudioFormat audioFormat = getAudioFormat(new File("C:\\Users\\romse\\OneDrive\\Документы\\GitHub\\TP-5.2-1\\Backend\\MusicZoneServer\\src\\main\\resources\\music\\Melnica_-_Dorogi_48002701.wave"));

        AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(audioBytes), audioFormat, audioBytes.length);

        // Create AudioFileFormat from AudioInputStream
        AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
        AudioFileFormat targetFileFormat = new AudioFileFormat(targetType, audioFormat, AudioSystem.NOT_SPECIFIED);

        // Write audio to file
        File outputFile = new File("C:\\Users\\romse\\OneDrive\\Документы\\GitHub\\TP-5.2-1\\Backend\\MusicZoneServer\\src\\main\\resources\\music");
        AudioSystem.write(audioInputStream, targetType, outputFile);

        audioInputStream.close();
    }

    public List<MusicDto> findAll() {
        return musicRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
