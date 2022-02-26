package ru.otus.project.rnis.simulator.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Data
public class AuthenticationModel {
    private String login;
    private String password;
    private String lang;
    private String timezone;
}