package ru.vsu.cs.musiczoneserver.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.musiczoneserver.dto.PlaylistDto;
import ru.vsu.cs.musiczoneserver.entity.Playlist;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    Playlist toEntity(PlaylistDto dto);

    PlaylistDto toDto(Playlist playlist);
}
