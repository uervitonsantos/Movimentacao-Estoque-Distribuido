package com.ml.gestao.estoque.distribuido.recurso;

import com.ml.gestao.estoque.distribuido.dto.LojaDTO;
import com.ml.gestao.estoque.distribuido.filterDTO.LojaFiltroDTO;
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

@Tag(name = "Loja", description = "Operações para os recursos de loja")
public interface LojaSwagger extends Resource {

    @Operation(
            summary = "Retorna uma loja pelo ID",
            description = "Retorna os detalhes de uma loja cadastrada",
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(description = "Objeto Json retornado na busca da loja",
                                            implementation = LojaDTO.class))),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Código da loja inválido", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity getLoja(@Parameter(description = "Código identificador da loja") Long lojaID);

    @Operation(
            summary = "Retorna lista de lojas",
            description = "Retorna uma lista de todas as lojas cadastradas",
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    array = @ArraySchema(schema = @Schema(description = "Objeto Json retornado na busca da loja",
                                            implementation = LojaDTO.class)))),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity getLojas(@Parameter(description = "Filtros permitidos para a busca de lojas") LojaFiltroDTO filtro);

    @Operation(
            summary = "Cria uma nova loja",
            description = "Cria uma nova loja e adiciona ao cadastro",
            requestBody = @RequestBody(description = "Dados necessários para a criação de uma nova loja", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = {
                            @ExampleObject(
                                    name = "Exemplo de loja",
                                    summary = "Um exemplo de requisição para criar loja",
                                    description = "Exemplo de payload JSON para criação de um loja",
                                    value = """
                                                            {
                                                               "nome": "Loja Central",
                                                               "endereco": "Rua das Flores, 500"
                                                             }
                                            """
                            )
                    })),
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(description = "Objeto Json retornado na criação da loja",
                                            implementation = LojaDTO.class))),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Erro ao criar nova loja", content = @Content)
            })
    ResponseEntity criaLoja(LojaDTO dto);

    @Operation(
            summary = "Atualiza uma loja",
            description = "Atualiza os dados de uma loja cadastrada",
            requestBody = @RequestBody(description = "Dados necessários para atualização da loja", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            responses = {
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(description = "Objeto Json retornado na atualização da loja",
                                            implementation = LojaDTO.class))),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Erro ao atualizar a loja", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity atualizaLoja(@Parameter(description = "Código identificador da loja") Long lojaID, LojaDTO dto);

    @Operation(
            summary = "Remove uma loja",
            description = "Remove uma loja cadastrada",
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_NO_CONTENT, description = "Loja removida com sucesso"),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Erro ao remover loja", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity removeLoja(@Parameter(description = "Código identificador da loja") Long lojaID);

}
