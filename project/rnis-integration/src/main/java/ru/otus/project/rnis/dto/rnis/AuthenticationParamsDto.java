package ru.otus.project.rnis.dto.rnis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationParamsDto {
    private String login;
    private String password;
    private String lang;
    private String timezone;
}