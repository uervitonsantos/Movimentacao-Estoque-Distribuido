package com.ml.gestao.estoque.distribuido.util.constates;

import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.filtro.Paginacao;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

import java.util.Optional;

import static jakarta.ws.rs.core.HttpHeaders.CONTENT_DISPOSITION;

public interface Resource {

    String RESPONSE_OK = "200";
    String RESPONSE_BAD_REQUEST = "400";
    String RESPONSE_UNAUTHORIZED = "401";
    String RESPONSE_FORBIDDEN = "403";
    String RESPONSE_NO_CONTENT = "204";
    String RESPONSE_CREATED = "201";
    String RESPONSE_PARCIAL = "206";

    String P_QUANTIDADE_TOTAL = "quantidadeTotal";
    String ATTACHMENT = "attachment; filename=%s";
    String P_QUANTIDADE_REGISTROS = "quantidadeRegistros";
    String P_PAGINA = "pagina";

    String P_LOJA_ID = "lojaID";
    String P_PRODUTO_ID = "produtoID";
    String P_USUARIO_ID = "usuarioID";
    String P_ESTOQUE_MOVIMENTO_ID = "movimentoID";
    String P_TIPO_MOVIMENTO = "tipoMovimento";
    String P_DATA_MOVIMENTO = "data";
    String EMAIL_USUARIO = "emailUsuario";
    String P_QUANTIDADE_MAX = "quantidadeMax";
    String P_QUANTIDADE_MIN = "quantidadeMin";


    default ResponseBuilder respostaPaginada(FiltroWrapper wrapper) {
        return respostaPaginada(wrapper.getPaginacaoOptional());
    }

    default ResponseBuilder respostaPaginada(Optional<Paginacao> paginacao) {
        ResponseBuilder builder = Response.ok();
        if (paginacao.isPresent()) {
            if (paginacao.get().hasMais()) {
                builder.status(Status.PARTIAL_CONTENT);
            }
            builder.header(P_QUANTIDADE_TOTAL, paginacao.get().getTotalRegistros());
        }
        return builder;
    }

    default ResponseBuilder respostaArquivo(byte[] conteudo, String nomeArquivo) {
        return Response.ok(conteudo).header(CONTENT_DISPOSITION, String.format(ATTACHMENT, nomeArquivo));
    }
}
