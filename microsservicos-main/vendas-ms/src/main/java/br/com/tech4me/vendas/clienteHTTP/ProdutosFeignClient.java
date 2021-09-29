package br.com.tech4me.vendas.clienteHTTP;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.tech4me.vendas.compartilhado.Produto;

@FeignClient(name= "produtos-ms", fallback = ProdutosFeignClientFallback.class)
public interface ProdutosFeignClient {
    @GetMapping(path = "/api/produtos/{venda}/lista")
    List<Produto> obterProdutos(@PathVariable String venda);   
}

@Component
class ProdutosFeignClientFallback implements ProdutosFeignClient {

    @Override
    public List<Produto> obterProdutos(String venda) {
        return new ArrayList<>();
    }

}
