package com.biblioteca.emprestimo_service.service.impl;

import com.biblioteca.emprestimo_service.dto.ClienteDto;
import com.biblioteca.emprestimo_service.dto.LivroDto;
import com.biblioteca.emprestimo_service.model.Emprestimo;
import com.biblioteca.emprestimo_service.repository.CatalogoFeignClient;
import com.biblioteca.emprestimo_service.repository.ClienteFeignClient;
import com.biblioteca.emprestimo_service.repository.EmprestimoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmprestimoServiceImplTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private ClienteFeignClient clienteFeignClient;

    @Mock
    private CatalogoFeignClient catalogoFeignClient;

    @InjectMocks
    private EmprestimoServiceImpl emprestimoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar todos os empréstimos")
    void shouldReturnAllEmprestimos() {

        Emprestimo emprestimo1 = new Emprestimo(1L, 1L, 1L, LocalDate.now(), null, false);
        Emprestimo emprestimo2 = new Emprestimo(2L, 2L, 2L, LocalDate.now(), null, false);
        when(emprestimoRepository.findAll()).thenReturn(Arrays.asList(emprestimo1, emprestimo2));


        List<Emprestimo> emprestimos = emprestimoService.getAllEmprestimos();


        assertThat(emprestimos).hasSize(2);
        assertThat(emprestimos).containsExactly(emprestimo1, emprestimo2);
        verify(emprestimoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve criar um novo empréstimo com sucesso")
    void shouldCreateEmprestimoSuccessfully() {

        Long clienteId = 1L;
        Long livroId = 1L;
        ClienteDto clienteDto = new ClienteDto(clienteId, "Cliente", "cliente@exemplo.com", "123456789", "11111111111");
        LivroDto livroDto = new LivroDto(livroId, "Livro", "Autor", "1234567890123", false);
        Emprestimo emprestimo = new Emprestimo(1L, clienteId, livroId, LocalDate.now(), null, false);

        when(clienteFeignClient.getClienteById(clienteId)).thenReturn(clienteDto);
        when(catalogoFeignClient.getLivroById(livroId)).thenReturn(livroDto);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);


        Emprestimo result = emprestimoService.createEmprestimo(clienteId, livroId);


        assertThat(result).isNotNull();
        assertThat(result.getClienteId()).isEqualTo(clienteId);
        assertThat(result.getLivroId()).isEqualTo(livroId);
        assertThat(result.getDevolvido()).isFalse();

        ArgumentCaptor<LivroDto> livroCaptor = ArgumentCaptor.forClass(LivroDto.class);
        verify(catalogoFeignClient, times(1)).updateLivro(eq(livroId), livroCaptor.capture());
        assertThat(livroCaptor.getValue().getEmprestado()).isTrue();

        verify(emprestimoRepository, times(1)).save(any(Emprestimo.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o livro não estiver disponível")
    void shouldThrowExceptionWhenLivroNotAvailable() {

        Long clienteId = 1L;
        Long livroId = 1L;
        ClienteDto clienteDto = new ClienteDto(clienteId, "Cliente", "cliente@exemplo.com", "123456789", "11111111111");
        LivroDto livroDto = new LivroDto(livroId, "Livro", "Autor", "1234567890123", true); // Livro já emprestado

        when(clienteFeignClient.getClienteById(clienteId)).thenReturn(clienteDto);
        when(catalogoFeignClient.getLivroById(livroId)).thenReturn(livroDto);


        RuntimeException exception = assertThrows(RuntimeException.class, () -> emprestimoService.createEmprestimo(clienteId, livroId));
        assertThat(exception.getMessage()).isEqualTo("Livro não disponível para empréstimo");

        verify(emprestimoRepository, never()).save(any(Emprestimo.class));
    }

    @Test
    @DisplayName("Deve retornar um empréstimo por ID")
    void shouldReturnEmprestimoById() {

        Long id = 1L;
        Emprestimo emprestimo = new Emprestimo(id, 1L, 1L, LocalDate.now(), null, false);
        when(emprestimoRepository.findById(id)).thenReturn(Optional.of(emprestimo));

        Emprestimo result = emprestimoService.getEmprestimoById(id);


        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        verify(emprestimoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o empréstimo não for encontrado por ID")
    void shouldThrowExceptionWhenEmprestimoNotFoundById() {

        Long id = 1L;
        when(emprestimoRepository.findById(id)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> emprestimoService.getEmprestimoById(id));
        assertThat(exception.getMessage()).isEqualTo("Empréstimo não encontrado");

        verify(emprestimoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve devolver um livro com sucesso")
    void shouldReturnLivroSuccessfully() {

        Long emprestimoId = 1L;
        Emprestimo emprestimo = new Emprestimo(emprestimoId, 1L, 1L, LocalDate.now(), null, false);
        LivroDto livroDto = new LivroDto(emprestimo.getLivroId(), "Livro", "Autor", "1234567890123", true);

        when(emprestimoRepository.findById(emprestimoId)).thenReturn(Optional.of(emprestimo));
        when(catalogoFeignClient.getLivroById(emprestimo.getLivroId())).thenReturn(livroDto);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);

        emprestimoService.devolverLivro(emprestimoId);


        assertThat(emprestimo.getDevolvido()).isTrue();
        assertThat(emprestimo.getDataDevolucao()).isEqualTo(LocalDate.now());

        ArgumentCaptor<LivroDto> livroCaptor = ArgumentCaptor.forClass(LivroDto.class);
        verify(catalogoFeignClient, times(1)).updateLivro(eq(emprestimo.getLivroId()), livroCaptor.capture());
        assertThat(livroCaptor.getValue().getEmprestado()).isFalse();

        verify(emprestimoRepository, times(1)).save(any(Emprestimo.class));
    }
}
