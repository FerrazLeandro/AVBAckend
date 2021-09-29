package br.com.tech4me.produtosms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tech4me.produtosms.compartilhado.ProdutoDto;
import br.com.tech4me.produtosms.model.Produto;
import br.com.tech4me.produtosms.repository.ProdutoRepositorio;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    @Autowired
    private ProdutoRepositorio repo;

    @Override
    public ProdutoDto cadastrarProduto(ProdutoDto produto) {
        return salvarProduto(produto);
    }

    @Override
    public List<ProdutoDto> obterProdutos() {
        List<Produto> produtos = repo.findAll();

        return produtos.stream()
            .map(produto -> new ModelMapper().map(produto, ProdutoDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<ProdutoDto> obterProdutoPorId(String id) {
        Optional<Produto> produto = repo.findById(id);

       if(produto.isPresent()) {
           return Optional.of(new ModelMapper().map(produto.get(), ProdutoDto.class));
       }

       return Optional.empty();
    }

    @Override
    public void apagarProduto(String id) {
        repo.deleteById(id);
    }


    @Override
    public ProdutoDto atualizarProduto(String id, ProdutoDto produto) {
        produto.setId(id);
        return salvarProduto(produto);
    }

    private ProdutoDto salvarProduto(ProdutoDto produto) {
        ModelMapper mapper = new ModelMapper();
        Produto produtoEntidade = mapper.map(produto, Produto.class);
        produtoEntidade = repo.save(produtoEntidade);

        return mapper.map(produtoEntidade, ProdutoDto.class);
    }
    
}
