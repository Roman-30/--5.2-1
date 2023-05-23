package ru.vsu.cs.musiczoneserver.dto;

import lombok.Data;
import ru.vsu.cs.musiczoneserver.entity.model.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class PersonDto {

    private Integer id;

    @NotEmpty
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
            message = "Не соответствует формату email!")
    private String email;
    @NotEmpty(message = "Password cannot be null")
//    @Min(value = 5, message = "Age should not be less than 5")
//    @Max(value = 65, message = "Age should not be greater than 65")
    private String password;
    @NotEmpty(message = "Name cannot be null")
    private String name;
    @NotEmpty(message = "Nickname cannot be null")
    private String nickName;
    @NotEmpty(message = "Phone cannot be null")
    @Pattern(regexp = "\\d{10}",
            message = "Не соответствует формату номера телефона!")
    private String phone;
    @NotEmpty(message = "Surname cannot be null")
    private String surname;

    private Set<Role> roles;
}
