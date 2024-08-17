package com.biblioteca.catalogo_service.amqp;

import com.biblioteca.catalogo_service.dto.LivroDto;
import com.biblioteca.catalogo_service.model.Livro;
import com.biblioteca.catalogo_service.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LivroUpdateConsumer {

    private final LivroRepository livroRepository;

    @RabbitListener(queues = "livro-atualizacao-queue")
    public void receiveMessage(LivroDto livro) {

        Livro livroExistente = livroRepository.findById(livro.getId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        livroExistente.setEmprestado(livro.getEmprestado());
        livroRepository.save(livroExistente);
    }
}
