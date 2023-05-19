package ru.vsu.cs.musiczoneserver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vsu.cs.musiczoneserver.mapper.MusicMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Music Zone API", version = "1.0", description = "Music mobile app"))
public class MusicZoneServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicZoneServerApplication.class, args);
    }
}
