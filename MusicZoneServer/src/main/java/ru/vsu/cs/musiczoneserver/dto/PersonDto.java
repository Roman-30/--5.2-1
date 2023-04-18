package ru.vsu.cs.musiczoneserver.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class PersonDto {
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$")
    @ApiModelProperty(notes = "User email", example = "example@yande.ru", required = true)
    private String email;
    @Min(1)
    @Max(10)
    @ApiModelProperty(notes = "User password", example = "S82Ks_1eamASFa", required = true)
    private String password;
    @ApiModelProperty(notes = "User name", example = "Artist", required = true)
    private String name;
    private String username;

}
