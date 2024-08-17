package com.biblioteca.emprestimo_service.repository;

import com.biblioteca.emprestimo_service.dto.LivroDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class CatalogoFeignClientTest {

    @Autowired
    private CatalogoFeignClient catalogoFeignClient;

    @MockBean
    private CatalogoFeignClient catalogoFeignClientMock;

    @Test
    void shouldGetLivroById() {

        LivroDto expectedLivro = new LivroDto(1L, "Livro Teste", "Autor Teste", "ISBN123456", false);
        when(catalogoFeignClientMock.getLivroById(anyLong())).thenReturn(expectedLivro);

        LivroDto actualLivro = catalogoFeignClientMock.getLivroById(1L);

        assertThat(actualLivro).isNotNull();
        assertThat(actualLivro.getId()).isEqualTo(expectedLivro.getId());
        assertThat(actualLivro.getTitulo()).isEqualTo(expectedLivro.getTitulo());
        assertThat(actualLivro.getAutor()).isEqualTo(expectedLivro.getAutor());
        assertThat(actualLivro.getIsbn()).isEqualTo(expectedLivro.getIsbn());
        assertThat(actualLivro.getEmprestado()).isEqualTo(expectedLivro.getEmprestado());

        verify(catalogoFeignClientMock, times(1)).getLivroById(1L);
    }

    @Test
    void shouldUpdateLivro() {

        LivroDto livroToUpdate = new LivroDto(1L, "Livro Atualizado", "Autor Atualizado", "ISBN654321", true);
        doNothing().when(catalogoFeignClientMock).updateLivro(anyLong(), any(LivroDto.class));

        catalogoFeignClientMock.updateLivro(1L, livroToUpdate);

        verify(catalogoFeignClientMock, times(1)).updateLivro(1L, livroToUpdate);
    }
}
