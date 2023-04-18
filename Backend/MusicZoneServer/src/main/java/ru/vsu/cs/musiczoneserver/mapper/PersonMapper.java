package ru.vsu.cs.musiczoneserver.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.musiczoneserver.dto.PersonDto;
import ru.vsu.cs.musiczoneserver.entity.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDto toDto(Person person);

    Person toEntity(PersonDto dto);
}
