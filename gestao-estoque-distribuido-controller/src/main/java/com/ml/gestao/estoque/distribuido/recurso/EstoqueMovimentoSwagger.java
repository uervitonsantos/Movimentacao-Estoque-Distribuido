package com.ml.gestao.estoque.distribuido.recurso;

import com.ml.gestao.estoque.distribuido.dto.EstoqueMovimentoDTO;
import com.ml.gestao.estoque.distribuido.filterDTO.EstoqueMovimentoFiltroDTO;
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

@Tag(name = "Estoque Movimento", description = "Operações para os recursos de movimentação de estoque")
public interface EstoqueMovimentoSwagger extends Resource {

    @Operation(
            summary = "Retorna um movimento de estoque pelo ID",
            description = "Retorna os detalhes de um movimento de estoque cadastrado",
            responses = {
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            description = "Objeto Json retornado na busca do movimento de estoque",
                                            implementation = EstoqueMovimentoDTO.class))),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_BAD_REQUEST,
                            description = "Código do movimento inválido",
                            content = @Content),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_UNAUTHORIZED,
                            description = "Movimento não autorizado",
                            content = @Content),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_FORBIDDEN,
                            description = "Movimento não autorizado",
                            content = @Content)
            })
    ResponseEntity getMovimento(
            @Parameter(description = "Código identificador do movimento de estoque")
            Long movimentoID);

    @Operation(
            summary = "Retorna lista de movimentos de estoque",
            description = "Retorna uma lista de todos os movimentos cadastrados",
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    description = "Objeto Json retornado na busca do movimento de estoque",
                                                    implementation = EstoqueMovimentoDTO.class)))),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_UNAUTHORIZED,
                            description = "Movimento não autorizado", content = @Content),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_FORBIDDEN,
                            description = "Movimento não autorizado", content = @Content)
            })
    ResponseEntity getMovimentos(
            @Parameter(description = "Filtros permitidos para a busca de movimentos")
            EstoqueMovimentoFiltroDTO filtro);

    @Operation(
            summary = "Cria um novo movimento de estoque",
            description = "Cria um novo movimento (entrada/saída) de estoque e adiciona ao histórico",
            requestBody = @RequestBody(
                    description = "Dados necessários para a criação de um novo movimento de estoque",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = {
                            @ExampleObject(
                                    name = "Exemplo de movimento de estoque",
                                    summary = "Um exemplo de requisição para criar movimento de estoque",
                                    description = "Exemplo de payload JSON para criação de um movimento de estoque",
                                    value = """
                                                            {
                                                                 "produto": {
                                                                     "produtoID": 1
                                                                 },
                                                                 "loja": {
                                                                     "lojaID": 1
                                                                 },
                                                                 "usuario": {
                                                                     "usuarioID": 1
                                                                 },
                                                                 "tipoMovimento": "ENTRADA",
                                                                 "quantidade": 20
                                                             }
                                            """
                            )
                    })),
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            description = "Objeto Json retornado na criação do movimento de estoque",
                                            implementation = EstoqueMovimentoDTO.class))),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_BAD_REQUEST,
                            description = "Erro ao criar novo movimento de estoque",
                            content = @Content)
            })
    ResponseEntity criaMovimento(EstoqueMovimentoDTO dto);

    @Operation(
            summary = "Atualiza um movimento de estoque",
            description = "Atualiza os dados de um movimento de estoque cadastrado",
            requestBody = @RequestBody(description = "Dados necessários para atualização do movimento", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            description = "Objeto Json retornado na atualização do movimento",
                                            implementation = EstoqueMovimentoDTO.class))),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_BAD_REQUEST,
                            description = "Erro ao atualizar o movimento", content = @Content),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_UNAUTHORIZED,
                            description = "Movimento não autorizado", content = @Content),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_FORBIDDEN,
                            description = "Movimento não autorizado", content = @Content)
            })
    ResponseEntity atualizaMovimento(
            @Parameter(description = "Código identificador do movimento de estoque")
            Long movimentoID,
            EstoqueMovimentoDTO dto);

    @Operation(
            summary = "Remove um movimento de estoque",
            description = "Remove um movimento de estoque cadastrado",
            responses = {
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_NO_CONTENT,
                            description = "Movimento removido com sucesso"),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_BAD_REQUEST,
                            description = "Erro ao remover movimento de estoque", content = @Content),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_UNAUTHORIZED,
                            description = "Movimento não autorizado", content = @Content),
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_FORBIDDEN,
                            description = "Movimento não autorizado", content = @Content)
            })
    ResponseEntity removeMovimento(
            @Parameter(description = "Código identificador do movimento de estoque")
            Long movimentoID);
}
