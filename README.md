# Gestão de Estoque Distribuído

Este projeto tem como objetivo desenvolver uma aplicação para **gestão de estoque**, permitindo o cadastro, consulta e gerenciamento de produtos, pedidos e clientes de forma modularizada e escalável.  
A aplicação foi estruturada em **múltiplos módulos** (util, controller, etc.), promovendo separação de responsabilidades e facilitando manutenção e evolução.

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

- **Usuário**
- **Produtos**
- **Lojas**
- **Movimentação de Estoque**

Todos os endpoints estão documentados via **Swagger**, permitindo navegação interativa e testes:

👉 [Acesse a documentação Swagger](http://localhost:8080/swagger-ui/index.html#/)

---

## 🏗️ Arquitetura da Aplicação

A arquitetura escolhida promovendo **organização por responsabilidades** e pode ser escalada:  

> A modularização permite melhor manutenção e futura escalabilidade do sistema.

<img width="741" height="362" alt="estoque drawio" src="https://github.com/user-attachments/assets/2861d917-0728-4be8-8595-a7615b75aa45" />

---

## 🗄️ Banco de Dados

- **Banco atual**: [H2 Database](https://www.h2database.com/)  
  - Facilita testes e desenvolvimento local (console disponível em: `http://localhost:8080/h2-console`).  
- **Futuro**: pode ser configurado facilmente para **PostgreSQL** ou **MySQL** em produção.  

📌 **Espaço reservado para o diagrama de relacionamento das entidades:**

<img width="528" height="538" alt="diagram" src="https://github.com/user-attachments/assets/1713bd45-bf4e-4b76-a7ea-1016abb7938f" />

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

