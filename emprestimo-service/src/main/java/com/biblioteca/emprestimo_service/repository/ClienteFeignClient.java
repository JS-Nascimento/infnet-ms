package com.biblioteca.emprestimo_service.repository;


import com.biblioteca.emprestimo_service.dto.ClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-service", url = "http://localhost:8081")
public interface ClienteFeignClient {

    @GetMapping("/clientes/{id}")
    ClienteDto getClienteById(@PathVariable("id") Long id);
}