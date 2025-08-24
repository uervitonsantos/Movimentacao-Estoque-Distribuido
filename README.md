# API REST para a recepção de pedidos dos clientes:

Este projeto é uma API REST desenvolvida em Java 17 com Spring Boot 3 para gerenciar a recepção de pedidos dos clientes. 
A API oferece recursos para cadastrar, listar, atualizar e remover clientes e pedidos, além de listar todos os pedidos 
associados a um produto específico. A documentação da API é gerada automaticamente usando o Swagger 3.

![image](https://github.com/user-attachments/assets/6ed80452-0cf7-4b1d-b5da-734bf0e78fe6)



## Tecnologias Utilizadas

- Java 17
- Mavem
- Spring Boot 3
- Lombok
- Swagger 3
- Banco de Dados MySQL/H2 em memoria

 ## Estrutura do Projeto

  projeto segue uma arquitetura modularizada e organizada com os seguintes pacotes:

* entidade: Contém as entidades de dados, como Cliente e Pedido.
* canonico: Define os objetos canônicos para representar as entidades, como ClienteCanonical e PedidoCanonical.
* builder: Para construir objetos complexos, como ClienteBuilder e PedidoBuilder.
* dto: Classes para transferir dados entre a API e o produto, como ClienteDTO e PedidoDTO.
* factory: Classes para criar instâncias de objetos, como ClienteFactory e PedidoFactory.
* repository: Implementações de repositórios para interagir com o banco de dados, como ClienteRepository e PedidoRepository.
* service: Lógica de negócios e manipulação de dados, como ClienteService e PedidoService.
* controller: Controladores REST que definem os endpoints da API, como BeneficiarioController e PedidoController.

  ## Endpoints da API

  A API oferece os seguintes endpoints:

* `POST` /produto: Cadastrar um novo produto.
* `GET` /produto: Listar todos os produto cadastrados juntamente com seus pedidos.
* `GET` /produto/{id}: Retorna um produto específico.
* `PUT` /produto/{id}: Atualizar os dados cadastrais de um produto.
* `DELETE` /produto/{id}: Remover um produto.

* `POST` /estoqueMovimento: Cadastrar um novo estoqueMovimento e relaciona com produto.
* `GET` /estoqueMovimento: Listar todos os estoqueMovimento cadastrados.
* `GET` /estoqueMovimento/{id}: Retorna um estoqueMovimento específico.
* `PUT` /estoqueMovimento/{id}: Atualizar os dados de um estoqueMovimento.
* `DELETE` /estoqueMovimento/{id}: Remover um estoqueMovimento.

## Documentação da API

A documentação da API é gerada automaticamente usando o Swagger 3. Para acessá-la, inicie a aplicação e abra a seguinte URL no seu navegador:

 ```bash
http://localhost:8080/swagger-ui/index.html
 ```

## Configuração e Execução

1. Clone este repositório:
   ```bash
   git clone https://github.com/uervitonsantos/Pedido_Cliente.git
   ```
   
2. Importe o projeto na sua IDE preferida.

3. Execute a aplicação e ela irá criar 10 clientes automaticamente.

4. Acesse a documentação da API conforme descrito acima para obter detalhes sobre os endpoints e como usá-los.

## Exemplo de json

```json
{
    "clienteID": 1,
    "nomeCliente": "João Goncalves Castro",
    "pedidos": [
        {
            "pedidoID": 1,
            "dataCadastroPedido": "2024-08-08T14:38:04.768458",
            "nomeProduto": "Produto 1",
            "clienteID": 1,
            "valorProduto": 100.0,
            "valorTotal": null,
            "quantidadeProduto": 1
        },
        {
            "pedidoID": 2,
            "dataCadastroPedido": "2024-08-08T14:38:04.768458",
            "nomeProduto": "Produto 2",
            "clienteID": 1,
            "valorProduto": 200.0,
            "valorTotal": null,
            "quantidadeProduto": 2
        },
        {
            "pedidoID": 3,
            "dataCadastroPedido": "2024-08-08T14:38:04.768458",
            "nomeProduto": "Produto 3",
            "clienteID": 1,
            "valorProduto": 300.0,
            "valorTotal": null,
            "quantidadeProduto": 3
        },
        {
            "pedidoID": 31,
            "dataCadastroPedido": "2024-08-08T14:38:01.123699",
            "nomeProduto": "Produto 4",
            "clienteID": 1,
            "valorProduto": 500.0,
            "valorTotal": 1500.0,
            "quantidadeProduto": 3
        }
    ]
}
```

