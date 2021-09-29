package br.com.tech4me.vendas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tech4me.vendas.clienteHTTP.ProdutosFeignClient;
import br.com.tech4me.vendas.compartilhado.VendaDto;
import br.com.tech4me.vendas.model.Venda;
import br.com.tech4me.vendas.repository.ProdutoRepository;

@Service
public class VendaServiceImpl implements VendaService {
    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private ProdutosFeignClient produtosMsClient;

    @Override
    public VendaDto realizarVenda(VendaDto venda) {
        return salvarVenda(venda);
    }

    @Override
    public List<VendaDto> obterVendas() {
        List<Venda> vendas = repo.findAll();

        return vendas.stream()
            .map(venda -> new ModelMapper().map(venda, VendaDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<VendaDto> obterVendaPorId(String id) {
       Optional<Venda> venda = repo.findById(id);

        
       if(venda.isPresent()) {
           VendaDto dto = new ModelMapper().map(venda.get(), VendaDto.class);
           dto.setProdutos(produtosMsClient.obterProdutos(id));
           return Optional.of(dto);
       }

       return Optional.empty();
    }

    @Override
    public void apagarVenda(String id) {
        repo.deleteById(id);
    }

    @Override
    public VendaDto atualizarVenda(String id, VendaDto venda) {
        venda.setId(id);
        return salvarVenda(venda);
    }

    private VendaDto salvarVenda(VendaDto venda) {
        ModelMapper mapper = new ModelMapper();
        Venda vendaEntidade = mapper.map(venda, Venda.class);
        vendaEntidade = repo.save(vendaEntidade);

        return mapper.map(vendaEntidade, VendaDto.class);
    }
}
