package com.ml.gestao.estoque.distribuido.recurso;

import com.ml.gestao.estoque.distribuido.dto.ProdutoDTO;
import com.ml.gestao.estoque.distribuido.filterDTO.ProdutoFiltroDTO;
import com.ml.gestao.estoque.distribuido.util.constates.Resource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Produto", description = "Operações para os recursos de produto")
public interface ProdutoSwagger extends Resource {

    @Operation(
            summary = "Retorna um produto pelo ID",
            description = "Retorna os detalhes de um produto cadastrado",
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(description = "Objeto Json retornado na busca do produto",
                                            implementation = ProdutoDTO.class))),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Código do produto inválido", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity getProduto(@Parameter(description = "Código identificador do produto") Long produtoID);

    @Operation(
            summary = "Retorna lista de produtos",
            description = "Retorna uma lista de todos os produtos cadastrados",
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    array = @ArraySchema(schema = @Schema(description = "Objeto Json retornado na busca de produtos",
                                            implementation = ProdutoDTO.class)))),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity getProdutos(@Parameter(description = "Filtros permitidos para a busca de produtos") ProdutoFiltroDTO filtro);

    @Operation(
            summary = "Cria um novo produto",
            description = "Cria um novo produto e adiciona ao cadastro",
            requestBody = @RequestBody(
                    description = "Dados necessários para a criação de um novo produto",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = {
                            @ExampleObject(
                                    name = "Exemplo de produto",
                                    summary = "Um exemplo de requisição para criar produto",
                                    description = "Exemplo de payload JSON para criação de um produto",
                                    value = """
                                                            {
                                                               "nome": "Notebook Dell Inspiron",
                                                               "descricao": "Notebook Dell Inspiron 15 polegadas, i7, 16GB RAM, SSD 512GB",
                                                               "sku": "SKU031",
                                                               "categoria": "Informática",
                                                               "preco": 4500.00,
                                                               "estoqueAtual": 50
                                                             }
                                            """
                            )
                    })),
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(description = "Objeto Json retornado na criação do produto",
                                            implementation = ProdutoDTO.class))),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Erro ao criar novo produto", content = @Content)
            })
    ResponseEntity criaProduto(ProdutoDTO dto);

    @Operation(
            summary = "Atualiza um produto",
            description = "Atualiza os dados de um produto cadastrado",
            requestBody = @RequestBody(description = "Dados necessários para atualização do produto", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            responses = {
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(description = "Objeto Json retornado na atualização do produto",
                                            implementation = ProdutoDTO.class))),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Erro ao atualizar o produto", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity atualizaProduto(@Parameter(description = "Código identificador do produto") Long produtoID, ProdutoDTO dto);

    @Operation(
            summary = "Remove um produto",
            description = "Remove um produto cadastrado",
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_NO_CONTENT, description = "Produto removido com sucesso"),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Erro ao remover produto", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity removeProduto(@Parameter(description = "Código identificador do produto") Long produtoID);

}
