package com.biblioteca.emprestimo_service.repository;

import com.biblioteca.emprestimo_service.dto.LivroDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "catalogo-service", url = "http://localhost:8080")
public interface CatalogoFeignClient {

    @GetMapping("/livros/{id}")
    LivroDto getLivroById(@PathVariable("id") Long id);

    @PutMapping("/livros/{id}")
    void updateLivro(@PathVariable("id") Long id, LivroDto livro);
}