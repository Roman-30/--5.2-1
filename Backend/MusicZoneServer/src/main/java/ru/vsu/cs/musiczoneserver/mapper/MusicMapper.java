package ru.vsu.cs.musiczoneserver.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.musiczoneserver.dto.MusicDto;
import ru.vsu.cs.musiczoneserver.entity.Music;

@Mapper(componentModel = "spring")
public interface MusicMapper {
    Music toEntity(MusicDto musicDto);

    MusicDto toDto(Music music);


}
