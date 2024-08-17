package com.biblioteca.catalogo_service.service.impl;

import com.biblioteca.catalogo_service.model.Livro;
import com.biblioteca.catalogo_service.repository.LivroRepository;
import com.biblioteca.catalogo_service.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;

    @Override
    public List<Livro> getAllLivros() {
        return livroRepository.findAll();
    }

    @Override
    public Livro createLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    @Override
    public Livro getLivroById(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }
}
