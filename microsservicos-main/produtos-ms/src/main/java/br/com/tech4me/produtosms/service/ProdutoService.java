package br.com.tech4me.produtosms.service;

import java.util.List;
import java.util.Optional;

import br.com.tech4me.produtosms.compartilhado.ProdutoDto;

public interface ProdutoService {
    ProdutoDto cadastrarProduto(ProdutoDto produto);
    List<ProdutoDto> obterProdutos();
    Optional<ProdutoDto> obterProdutoPorId(String id);
   // List<ProdutoDto> obterPorDono(String dono);
    void apagarProduto(String id);
    ProdutoDto atualizarProduto(String id, ProdutoDto produto);
}
