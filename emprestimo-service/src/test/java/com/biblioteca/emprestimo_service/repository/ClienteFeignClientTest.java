package com.biblioteca.emprestimo_service.repository;

import com.biblioteca.emprestimo_service.dto.ClienteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteFeignClientTest {

    @Autowired
    private ClienteFeignClient clienteFeignClient;

    @MockBean
    private ClienteFeignClient clienteFeignClientMock;

    @Test
    void shouldGetClienteById() {

        ClienteDto expectedCliente = new ClienteDto(1L, "Nome Teste", "email@teste.com", "123456789", "123.456.789-00");
        when(clienteFeignClientMock.getClienteById(anyLong())).thenReturn(expectedCliente);


        ClienteDto actualCliente = clienteFeignClientMock.getClienteById(1L);


        assertThat(actualCliente).isNotNull();
        assertThat(actualCliente.getId()).isEqualTo(expectedCliente.getId());
        assertThat(actualCliente.getNome()).isEqualTo(expectedCliente.getNome());
        assertThat(actualCliente.getEmail()).isEqualTo(expectedCliente.getEmail());
        assertThat(actualCliente.getTelefone()).isEqualTo(expectedCliente.getTelefone());
        assertThat(actualCliente.getCpf()).isEqualTo(expectedCliente.getCpf());

        verify(clienteFeignClientMock, times(1)).getClienteById(1L);
    }
}
