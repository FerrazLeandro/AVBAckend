package br.com.tech4me.vendas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.tech4me.vendas.model.Venda;

@Repository
public interface ProdutoRepository extends MongoRepository<Venda, String> {
    
}
