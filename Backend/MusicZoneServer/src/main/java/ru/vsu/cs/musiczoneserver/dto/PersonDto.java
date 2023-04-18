package ru.vsu.cs.musiczoneserver.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class PersonDto {

    @NotEmpty
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
    message = "Не соответствует формату email!")
    private String email;
    @NotEmpty
    @Min(5)
    private String password;
    @NotEmpty
    private String name;
    @NotEmpty
    private String nickName;
    @NotEmpty
    @Pattern(regexp = "\\d{10}",
    message = "Не соответствует формату номера телефона!")
    private String phone;
    @NotEmpty
    private String surname;
}
