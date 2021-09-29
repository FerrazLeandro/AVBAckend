package br.com.tech4me.vendas.service;

import java.util.List;
import java.util.Optional;

import br.com.tech4me.vendas.compartilhado.VendaDto;

public interface VendaService {
    VendaDto realizarVenda(VendaDto venda);
    List<VendaDto> obterVendas();
    Optional<VendaDto> obterVendaPorId(String id);
    void apagarVenda(String id);
    VendaDto atualizarVenda(String id, VendaDto venda);
}
