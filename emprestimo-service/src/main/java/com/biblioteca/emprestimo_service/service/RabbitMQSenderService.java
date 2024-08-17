package com.biblioteca.emprestimo_service.service;

import com.biblioteca.emprestimo_service.dto.LivroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.biblioteca.emprestimo_service.config.RabbitMQConfig.EXCHANGE;
import static com.biblioteca.emprestimo_service.config.RabbitMQConfig.ROUTING_KEY;

@Service
@RequiredArgsConstructor
public class RabbitMQSenderService {

    private final RabbitTemplate rabbitTemplate;

    public void sendLivroAtualizacao(LivroDto livro) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, livro);
    }
}