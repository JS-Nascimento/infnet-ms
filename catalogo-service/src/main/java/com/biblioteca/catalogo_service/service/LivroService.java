package com.biblioteca.catalogo_service.service;

import com.biblioteca.catalogo_service.model.Livro;

import java.util.List;

public interface LivroService {

    List<Livro> getAllLivros();

    Livro createLivro(Livro livro);

    Livro getLivroById(Long id);
}

