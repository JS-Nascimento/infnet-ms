package com.biblioteca.emprestimo_service.service.impl;

import com.biblioteca.emprestimo_service.dto.ClienteDto;
import com.biblioteca.emprestimo_service.dto.LivroDto;
import com.biblioteca.emprestimo_service.model.Emprestimo;
import com.biblioteca.emprestimo_service.repository.CatalogoFeignClient;
import com.biblioteca.emprestimo_service.repository.ClienteFeignClient;
import com.biblioteca.emprestimo_service.repository.EmprestimoRepository;
import com.biblioteca.emprestimo_service.service.EmprestimoService;
import com.biblioteca.emprestimo_service.service.RabbitMQSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmprestimoServiceImpl implements EmprestimoService {


        private final EmprestimoRepository emprestimoRepository;
        private final ClienteFeignClient clienteFeignClient;
        private final CatalogoFeignClient catalogoFeignClient;
    private final RabbitMQSenderService rabbitMQSenderService;

    @Override
    public List<Emprestimo> getAllEmprestimos() {
        return emprestimoRepository.findAll();
    }

    @Override
    public Emprestimo createEmprestimo(Long clienteId, Long livroId) {

        ClienteDto cliente = clienteFeignClient.getClienteById(clienteId);

        LivroDto livro = catalogoFeignClient.getLivroById(livroId);

        if (livro != null && !livro.getEmprestado()) {
            livro.setEmprestado(true);

            rabbitMQSenderService.sendLivroAtualizacao(livro);

            Emprestimo emprestimo = new Emprestimo(null, cliente.getId(), livroId, LocalDate.now(), null, false);
            return emprestimoRepository.save(emprestimo);
        } else {
            throw new RuntimeException("Livro não disponível para empréstimo");
        }
    }

    @Override
    public Emprestimo getEmprestimoById(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }

    @Override
    public void devolverLivro(Long emprestimoId) {
        Emprestimo emprestimo = getEmprestimoById(emprestimoId);
        emprestimo.setDevolvido(true);
        emprestimo.setDataDevolucao(LocalDate.now());

        LivroDto livro = catalogoFeignClient.getLivroById(emprestimo.getLivroId());
        if (livro != null) {
            livro.setEmprestado(false);

            rabbitMQSenderService.sendLivroAtualizacao(livro);
        }

        emprestimoRepository.save(emprestimo);
    }
}
