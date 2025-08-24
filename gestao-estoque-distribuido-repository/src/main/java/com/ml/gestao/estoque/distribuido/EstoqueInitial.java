package com.ml.gestao.estoque.distribuido;

import com.ml.gestao.estoque.distribuido.entidade.EstoqueMovimento;
import com.ml.gestao.estoque.distribuido.entidade.Loja;
import com.ml.gestao.estoque.distribuido.entidade.Produto;
import com.ml.gestao.estoque.distribuido.entidade.Usuario;
import com.ml.gestao.estoque.distribuido.enumerated.PerfilUsuario;
import com.ml.gestao.estoque.distribuido.enumerated.TipoMovimento;
import com.ml.gestao.estoque.distribuido.repository.EstoqueMovimentacaoRepository;
import com.ml.gestao.estoque.distribuido.repository.LojaRepository;
import com.ml.gestao.estoque.distribuido.repository.ProdutoRepository;
import com.ml.gestao.estoque.distribuido.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class EstoqueInitial implements CommandLineRunner {

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueMovimentacaoRepository estoqueMovimentoRepository;


    @Override
    public void run(String... args) throws Exception {

        // Criação de lojas
        Loja loja1 = new Loja();
        loja1.setNome("Loja Central");
        loja1.setEndereco("Rua das Flores, 500");
        Loja lojaSalva1 = lojaRepository.salvaLoja(loja1);

        Loja loja2 = new Loja();
        loja2.setNome("Loja Norte");
        loja2.setEndereco("Avenida Brasil, 1000");
        Loja lojaSalva2 = lojaRepository.salvaLoja(loja2);

        Loja loja3 = new Loja();
        loja3.setNome("Loja Sul");
        loja3.setEndereco("Rua das Palmeiras, 200");
        Loja lojaSalva3 = lojaRepository.salvaLoja(loja3);

        // Criação de usuários
        Usuario usuario1 = new Usuario();
        usuario1.setNome("João da Silva");
        usuario1.setEmail("joao.silva@email.com");
        usuario1.setSenhaHash("123456");
        usuario1.setPerfil(PerfilUsuario.ADMIN);
        Usuario usuarioSalvo1 = usuarioRepository.salvaUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNome("Maria Oliveira");
        usuario2.setEmail("maria.oliveira@email.com");
        usuario2.setSenhaHash("123456");
        usuario2.setPerfil(PerfilUsuario.FUNCIONARIO);
        Usuario usuarioSalvo2 = usuarioRepository.salvaUsuario(usuario2);

        Usuario usuario3 = new Usuario();
        usuario3.setNome("Carlos Pereira");
        usuario3.setEmail("carlos.pereira@email.com");
        usuario3.setSenhaHash("123456");
        usuario3.setPerfil(PerfilUsuario.CLIENTE);
        Usuario usuarioSalvo3 = usuarioRepository.salvaUsuario(usuario3);


        // =========================
        // Criação de produtos (30 produtos)
        // =========================
        List<Produto> produtos = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            Produto produto = new Produto();
            produto.setNome("Produto " + i);
            produto.setDescricao("Descrição do Produto " + i);
            produto.setSku("SKU" + String.format("%03d", i));
            produto.setCategoria("Categoria " + ((i % 5) + 1)); // 5 categorias
            produto.setPreco(BigDecimal.valueOf(50 + i * 10));
            produto.setEstoqueAtual(100);
            Produto produtoSalvo = produtoRepository.salvaProduto(produto);
            produtos.add(produtoSalvo);
        }

        // =========================
        // Criação de movimentos de estoque
        // =========================
        for (Produto p : produtos) {
            if (p != null && p.getProdutoID() != null &&
                    lojaSalva1 != null && lojaSalva1.getLojaID() != null &&
                    usuarioSalvo1 != null && usuarioSalvo1.getUsuarioID() != null) {

                EstoqueMovimento movimento = new EstoqueMovimento();
                movimento.setProduto(p);
                movimento.setLoja(lojaSalva1);
                movimento.setUsuario(usuarioSalvo1);
                movimento.setTipoMovimento(TipoMovimento.ENTRADA);
                movimento.setQuantidade(20);
                movimento.setDataHora(LocalDateTime.now());
                estoqueMovimentoRepository.salvaEstoqueMovimento(movimento);

                System.out.println("Movimento de estoque criado com sucesso!");
            } else {
                System.out.println("Não foi possível criar o movimento de estoque. Verifique Produto, Loja e Usuário.");
            }
        }
    }
}
