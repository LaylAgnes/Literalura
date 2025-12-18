package com.alura.Literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(

        String name,

        @JsonAlias("birth_year")
        Integer anoNascimento,

        @JsonAlias("death_year")
        Integer anoFalecimento

) {}
