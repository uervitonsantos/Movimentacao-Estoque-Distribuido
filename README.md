# Gestão de Estoque Distribuído

Este projeto tem como objetivo desenvolver uma aplicação simples de **gestão de estoque**, permitindo:

- Cadastro, consulta, atualização e remoção de **lojas**
- Cadastro, consulta, atualização e remoção de **usuários**
- Cadastro, consulta, atualização e remoção de **produtos**
- Registro e gerenciamento de **movimentações no estoque** (entradas e saídas)

---

## 🎯 Proposta e Abordagem

A arquitetura desenvolvida foi pensada para **resolver problemas críticos de sistemas com baixa resiliência**, como:

- **Banco de dados local único**: risco de indisponibilidade, desatualizado e falta de resiliência.
- **Indisponibilidade do sistema**: Uma API em containers traz benefícios como escalabilidade independente, implantação e atualização mais rápidas, isolamento de falhas, flexibilidade tecnológica e ciclos de desenvolvimento mais curtos.

### ✅ Benefícios da Arquitetura
- **Estrutura modular**: separa responsabilidades em módulos independentes (ex.: `etity`, `controller`), trazendo **segurança, manutenibilidade e clareza na evolução do projeto**.
- **Escalabilidade**: o modelo atual pode ser facilmente adaptado para múltiplos **microsserviços**, com um **orquestrador de containers** (ex.: Kubernetes ou Docker Swarm), garantindo **alta disponibilidade e elasticidade**.
- **Segurança**: a separação modular reduz o acoplamento e permite controles mais específicos de acesso e responsabilidade por domínio.
- **Evolução contínua**: cada módulo pode evoluir de forma independente, facilitando futuras integrações com serviços externos ou outros sistemas corporativos.

### ⚡ Argumentos para defesa da proposta
- **Disponibilidade**: a arquitetura desenvolvida mitiga riscos de indisponibilidade, pois a imagem da aplicação pode ser reestabelecia rapidamente.
- **Escalabilidade futura**: a transição para microsserviços é facilmente aplicavel, permitindo distribuir cargas de trabalho entre os serviços tornandos especializados.
- **Flexibilidade**: diferentes bancos de dados podem ser configurados.
- **Preparação para nuvem**: a aplicação pode ser implantada em em uma cloud, melhorando a disponibilidadee segurança.
- **Tecnológica**: a escolha de **Spring Boot 3**, **Maven** e **Docker** garante uma base sólida, bem documentada e com ampla comunidade de suporte.

---

## 🏗️ Arquitetura da Aplicação

A arquitetura escolhida promovendo **organização por responsabilidades** e pode ser escalada:

> A modularização permite melhor manutenção e futura escalabilidade do sistema.

<img width="741" height="362" alt="estoque drawio" src="https://github.com/user-attachments/assets/2861d917-0728-4be8-8595-a7615b75aa45" />

---

## Arquitetura da Aplicação Futura

Apesar da arquitetura escolhida seguir um padrão modular, ele pode facilmente ser evoluida para uma arquitetura de multiplos microsserviços (ex abaixo)

<img width="1712" height="951" alt="novo drawio" src="https://github.com/user-attachments/assets/63220411-bfb6-4e67-82a9-9ca5d5399a54" />


## 🗄️ Banco de Dados

- **Banco atual**: [H2 Database](https://www.h2database.com/)
    - Facilita testes e desenvolvimento local (console disponível em: `http://localhost:8080/h2-console`).
- **Futuro**: pode ser configurado facilmente para **PostgreSQL** ou **MySQL** em produção.

📌 **Diagrama de relacionamento das entidades:**

<img width="528" height="538" alt="diagram" src="https://github.com/user-attachments/assets/f101808c-fea7-41ff-a62a-41298d6c749a" />

---


## 🚀 Tecnologias Utilizadas

- **Java 17**  
- **Spring Boot 3** (Web, Data JPA, Validation)  
- **Maven** (build e gerenciamento de dependências)  
- **H2 Database** (banco em memória para testes e desenvolvimento)  
- **Swagger / OpenAPI** (documentação e teste de endpoints)  
- **Docker** (containerização da aplicação)  

---

## 📌 Endpoints Principais

A API expõe endpoints para gestão das principais entidades:  

- **Usuário** ``localhost:8080/usuario``
- **Produtos** ``localhost:8080/produto``
- **Lojas** ``localhost:8080/loja``
- **Movimentação de Estoque** ``localhost:8080/estoque``

Todos os endpoints estão documentados via **Swagger**, permitindo navegação interativa e testes:

👉 [Acesse a documentação Swagger](http://localhost:8080/swagger-ui/index.html#/)

---

## 🐳 Docker

A aplicação possui **Dockerfile** para build da imagem e **docker-compose.yml** para orquestração.  

### Build da Imagem

```bash
docker build -t gestao-estoque .
```
```bash
docker run -p 8080:8080 gestao-estoque
```
```bash
docker-compose up -d
```

O projeto está preparado para ser deployado em AWS.
Sugestões de serviços:

## 📄 Como Rodar Localmente

Clonar o repositório:

```bash
git clone https://github.com/uervitonsantos/Movimentacao-Estoque-Distribuido.git
```
```bash
cd gestao-estoque-distribuido
```

Build com Maven:
```bash
mvn clean package -DskipTests
```

Rodar a aplicação:
```bash
mvn spring-boot:run -pl gestao-estoque-distribuido-controller
```

Acessar:

* Aplicação: http://localhost:8080
* Swagger: http://localhost:8080/swagger-ui/index.html#/
* H2 Console: http://localhost:8080/h2-console

