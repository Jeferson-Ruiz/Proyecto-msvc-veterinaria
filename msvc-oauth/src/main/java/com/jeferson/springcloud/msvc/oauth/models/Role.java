package com.jeferson.springcloud.msvc.oauth.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {
    private Long id;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Roles name;
}
