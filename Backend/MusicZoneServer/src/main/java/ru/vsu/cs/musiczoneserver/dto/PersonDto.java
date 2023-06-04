package ru.vsu.cs.musiczoneserver.dto;

import lombok.Data;
import ru.vsu.cs.musiczoneserver.entity.model.Role;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class PersonDto {

    private Integer id;

    @NotEmpty
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
            message = "Does not match the format email!")
    private String email;

    private String password;
    @NotEmpty(message = "Name cannot be null")
    private String name;
    @NotEmpty(message = "Nickname cannot be null")
    private String nickname;
    @NotEmpty(message = "Phone cannot be null")
    private String phone;
    @NotEmpty(message = "Surname cannot be null")
    private String surname;

    private Set<Role> roles;
}
