package com.biblioteca.emprestimo_service.service;

import com.biblioteca.emprestimo_service.dto.LivroDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.biblioteca.emprestimo_service.config.RabbitMQConfig.EXCHANGE;
import static com.biblioteca.emprestimo_service.config.RabbitMQConfig.ROUTING_KEY;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RabbitMQSenderServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQSenderService rabbitMQSenderService;

    @Captor
    private ArgumentCaptor<LivroDto> livroCaptor;

    @Test
    @DisplayName("Deve enviar atualização de livro corretamente")
    void shouldSendBookUpdateCorrectly() {
        LivroDto livroDto = new LivroDto(1L, "Livro", "Autor", "1234567890123", true);
        rabbitMQSenderService.sendLivroAtualizacao(livroDto);


        verify(rabbitTemplate).convertAndSend(eq(EXCHANGE), eq(ROUTING_KEY), eq(livroDto));
    }

}
