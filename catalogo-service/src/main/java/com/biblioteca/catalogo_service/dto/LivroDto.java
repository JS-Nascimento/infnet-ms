package com.biblioteca.catalogo_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDto {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private Boolean emprestado;
}
