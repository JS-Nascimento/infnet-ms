# Biblioteca Microsserviços

Este repositório contém o código-fonte de uma aplicação de gerenciamento de uma biblioteca, construída em uma arquitetura de microsserviços. A aplicação é composta por diversos serviços que se comunicam entre si para oferecer funcionalidades como gerenciamento de catálogos, empréstimos de livros, e controle de clientes. A infraestrutura de suporte inclui serviços de descoberta, gateways de API, monitoramento, logging e muito mais.

## Índice

- [Microsserviços](#microsserviços)
  - [Catalogo-Service](#catalogo-service)
  - [Emprestimo-Service](#emprestimo-service)
  - [Cliente-Service](#cliente-service)
- [Infraestrutura](#infraestrutura)
  - [Eureka Server](#eureka-server)
  - [Spring Cloud Gateway](#spring-cloud-gateway)
  - [Graylog](#graylog)
  - [Prometheus](#prometheus)
  - [Kibana](#kibana)
- [CI/CD](#cicd)
- [Instruções de Configuração e Execução](#instruções-de-configuração-e-execução)
- [Contribuições](#contribuições)

## Microsserviços

### Catalogo-Service

O `Catalogo-Service` é responsável por gerenciar o catálogo de livros da biblioteca. Ele oferece operações CRUD para livros, como adicionar, editar, listar e remover livros. Este serviço utiliza um banco de dados H2 em memória para fins de teste.

- **Tecnologias**: Spring Boot, JPA, H2 Database
- **Endpoints principais**:
  - `GET /livros`: Lista todos os livros disponíveis no catálogo.
  - `POST /livros`: Adiciona um novo livro ao catálogo.
  - `PUT /livros/{id}`: Atualiza as informações de um livro existente.
  - `DELETE /livros/{id}`: Remove um livro do catálogo.

### Emprestimo-Service

O `Emprestimo-Service` gerencia os empréstimos de livros para os clientes. Ele consulta o `Catalogo-Service` para verificar a disponibilidade dos livros e realiza o processo de empréstimo, registrando quando um cliente pega ou devolve um livro.

- **Tecnologias**: Spring Boot, JPA, RestTemplate (para comunicação entre serviços)
- **Endpoints principais**:
  - `POST /emprestimos`: Realiza o empréstimo de um livro.
  - `PUT /emprestimos/{id}/devolucao`: Registra a devolução de um livro.

### Cliente-Service

O `Cliente-Service` é responsável por gerenciar os dados dos clientes da biblioteca. Ele permite o cadastro, atualização, remoção e consulta dos dados dos clientes.

- **Tecnologias**: Spring Boot, JPA, H2 Database
- **Endpoints principais**:
  - `GET /clientes`: Lista todos os clientes cadastrados.
  - `POST /clientes`: Cadastra um novo cliente.
  - `PUT /clientes/{id}`: Atualiza os dados de um cliente existente.
  - `DELETE /clientes/{id}`: Remove um cliente do sistema.

## Infraestrutura

### Eureka Server

O Eureka Server atua como um servidor de descoberta de serviços. Todos os microsserviços se registram nele, permitindo a descoberta dinâmica e balanceamento de carga. Isso facilita a escalabilidade e a resiliência dos serviços.

- **Tecnologias**: Spring Cloud Netflix Eureka
- **Porta**: 8761
- **Configuração**: Cada serviço configura a propriedade `eureka.client.serviceUrl.defaultZone` para se registrar no Eureka Server.

### Spring Cloud Gateway

O Spring Cloud Gateway é utilizado como um gateway de API para rotear as solicitações para os microsserviços apropriados. Ele fornece roteamento dinâmico, balanceamento de carga e segurança de API.

- **Tecnologias**: Spring Cloud Gateway
- **Porta**: 8080
- **Configuração**: Os serviços são roteados com base em seus nomes registrados no Eureka.

### Graylog

O Graylog é usado para centralizar os logs gerados pelos microsserviços. Ele facilita o monitoramento, a análise e a pesquisa de logs em tempo real, oferecendo uma visão completa das operações dos serviços.

- **Tecnologias**: Graylog, Logback, GELF (Graylog Extended Log Format)
- **Configuração**: Cada serviço envia seus logs para o Graylog via GELF TCP.

### Prometheus

Prometheus é utilizado para monitorar e coletar métricas dos microsserviços, como uso de CPU, memória, e latência de requisições. Ele armazena essas métricas e as torna disponíveis para consulta e visualização.

- **Tecnologias**: Prometheus, Micrometer
- **Configuração**: Os serviços expõem suas métricas através de um endpoint `/actuator/prometheus`, que é consumido pelo Prometheus.

### Kibana

O Kibana é usado para visualizar e explorar os dados de logs armazenados no Graylog. Ele oferece gráficos e dashboards interativos, facilitando a análise e o monitoramento das operações dos serviços.

- **Tecnologias**: Kibana
- **Configuração**: Integra-se diretamente com o Elasticsearch, onde os logs do Graylog são armazenados.

## CI/CD

A integração contínua (CI) e o deployment contínuo (CD) são configurados utilizando o GitHub Actions, que automatiza o processo de construção, teste e deploy dos microsserviços.

- **GitHub Actions**: Configurado para realizar builds, executar testes unitários e, se bem-sucedido, fazer o deploy para a infraestrutura AWS.
- **AWS Kubernetes**: O ambiente de produção está orquestrado em um cluster Kubernetes na AWS, com deploy automático via GitHub Actions.

## Instruções de Configuração e Execução

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio
   ```

2. **Configuração do ambiente**:
   - Certifique-se de ter o Java 21 instalado.
   - Configure o arquivo `application.yml` para cada microsserviço conforme necessário.
   - Instale e configure o Docker para rodar o Graylog, Prometheus e Kibana.

3. **Iniciar os microsserviços**:
   Cada microsserviço pode ser iniciado individualmente via Maven:
   ```bash
   mvn spring-boot:run
   ```

4. **Acessar o Eureka Server**:
   - URL: `http://localhost:8761`

5. **Acessar o Spring Cloud Gateway**:
   - URL: `http://localhost:8080`

6. **Acessar o Graylog**:
   - URL: `http://localhost:9000`

7. **Acessar o Kibana**:
   - URL: `http://localhost:5601`

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues para bugs ou novas funcionalidades. Para contribuir com código:

1. Crie uma nova branch:
   ```bash
   git checkout -b feature/nova-feature
   ```

2. Faça as alterações necessárias.

3. Faça commit das suas mudanças:
   ```bash
   git commit -m "Adiciona nova funcionalidade"
   ```

4. Envie para o repositório remoto:
   ```bash
   git push origin feature/nova-feature
   ```

5. Abra um Pull Request no GitHub.
```
