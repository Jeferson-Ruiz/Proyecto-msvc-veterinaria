package com.jeferson.msvc.users.dto;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRolesDto {
    private Set<String> roles;
}
