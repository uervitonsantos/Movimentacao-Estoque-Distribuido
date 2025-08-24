package com.ml.gestao.estoque.distribuido.recurso;

import com.ml.gestao.estoque.distribuido.dto.UsuarioDTO;
import com.ml.gestao.estoque.distribuido.filterDTO.UsuarioFiltroDTO;
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

@Tag(name = "Usuário", description = "Operações para os recursos de usuário")
public interface UsuarioSwagger extends Resource {

    @Operation(
            summary = "Retorna um usuário pelo ID",
            description = "Retorna os detalhes de um usuário cadastrado",
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(description = "Objeto Json retornado na busca do usuário",
                                            implementation = UsuarioDTO.class))),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Código do usuário inválido", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity getUsuario(@Parameter(description = "Código identificador do usuário") Long usuarioID);

    @Operation(
            summary = "Retorna lista de usuários",
            description = "Retorna uma lista de todos os usuários cadastrados",
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    array = @ArraySchema(schema = @Schema(description = "Objeto Json retornado na busca de usuários",
                                            implementation = UsuarioDTO.class)))),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity getUsuarios(@Parameter(description = "Filtros permitidos para a busca de usuários") UsuarioFiltroDTO filtro);

    @Operation(
            summary = "Cria um novo usuário",
            description = "Cria um novo usuário e adiciona ao cadastro",
            requestBody = @RequestBody(
                    description = "Dados necessários para a criação de um novo usuário",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = {
                            @ExampleObject(
                                    name = "Exemplo de Usuário",
                                    summary = "Um exemplo de requisição para criar usuário",
                                    description = "Exemplo de payload JSON para criação de um usuário",
                                    value = """
                                                            {
                                                              "nome": "Antonio da Silva",
                                                              "email": "antonio.silva@email.com",
                                                              "senhaHash": "123456",
                                                              "perfil": "ADMIN"
                                                            }
                                            """
                            )
                    }

                    )),
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(description = "Objeto Json retornado na criação do usuário",
                                            implementation = UsuarioDTO.class))),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Erro ao criar novo usuário", content = @Content)
            })
    ResponseEntity criaUsuario(UsuarioDTO dto);

    @Operation(
            summary = "Atualiza um usuário",
            description = "Atualiza os dados de um usuário cadastrado",
            requestBody = @RequestBody(description = "Dados necessários para atualização do usuário", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            responses = {
                    @ApiResponse(
                            responseCode = Resource.RESPONSE_OK,
                            description = "Sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(description = "Objeto Json retornado na atualização do usuário",
                                            implementation = UsuarioDTO.class))),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Erro ao atualizar o usuário", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity atualizaUsuario(@Parameter(description = "Código identificador do usuário") Long usuarioID, UsuarioDTO dto);

    @Operation(
            summary = "Remove um usuário",
            description = "Remove um usuário cadastrado",
            responses = {
                    @ApiResponse(responseCode = Resource.RESPONSE_NO_CONTENT, description = "Usuário removido com sucesso"),
                    @ApiResponse(responseCode = Resource.RESPONSE_BAD_REQUEST, description = "Erro ao remover usuário", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_UNAUTHORIZED, description = "Não autorizado", content = @Content),
                    @ApiResponse(responseCode = Resource.RESPONSE_FORBIDDEN, description = "Acesso negado", content = @Content)
            })
    ResponseEntity removeUsuario(@Parameter(description = "Código identificador do usuário") Long usuarioID);

}
