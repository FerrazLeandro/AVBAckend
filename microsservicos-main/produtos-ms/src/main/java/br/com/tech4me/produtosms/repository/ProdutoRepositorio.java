package br.com.tech4me.produtosms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.tech4me.produtosms.model.Produto;

@Repository
public interface ProdutoRepositorio extends MongoRepository<Produto, String> {
    
}
