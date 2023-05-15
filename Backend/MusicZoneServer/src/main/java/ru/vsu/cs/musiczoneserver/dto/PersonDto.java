package ru.vsu.cs.musiczoneserver.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@ApiModel
public class PersonDto {

    private Integer id;

    @NotEmpty
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
            message = "Не соответствует формату email!")
    private String email;
    @NotEmpty(message = "Password cannot be null")
    @Min(value = 5, message = "Age should not be less than 5")
    @Max(value = 65, message = "Age should not be greater than 65")
    private String password;
    @NotEmpty(message = "Name cannot be null")
    @ApiModelProperty(notes = "name of the user")
    private String name;
    @NotEmpty(message = "Nickname cannot be null")
    private String nickName;
    @NotEmpty(message = "Phone cannot be null")
    @Pattern(regexp = "\\d{11}",
            message = "Не соответствует формату номера телефона!")
    private String phone;
    @NotEmpty(message = "Surname cannot be null")
    private String surname;
}
