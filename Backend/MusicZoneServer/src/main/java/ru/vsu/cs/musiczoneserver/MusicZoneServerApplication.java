package ru.vsu.cs.musiczoneserver;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vsu.cs.musiczoneserver.mapper.MusicMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootApplication
public class MusicZoneServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicZoneServerApplication.class, args);
    }
}
