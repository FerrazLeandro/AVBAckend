package br.com.tech4me.vendas.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech4me.vendas.compartilhado.VendaDto;
import br.com.tech4me.vendas.model.Venda;
import br.com.tech4me.vendas.service.VendaService;
import br.com.tech4me.vendas.view.model.VendasModeloRequest;
import br.com.tech4me.vendas.view.model.VendasModeloResponse;
import br.com.tech4me.vendas.view.model.VendasModeloResponseDetalhes;


@RestController
@RequestMapping("/api/vendas")
public class VendasController {
    @Autowired
    private VendaService service;
    

    @PostMapping
    public ResponseEntity<VendasModeloResponse> realizarVenda(@RequestBody VendasModeloRequest venda) {
        ModelMapper mapper = new ModelMapper();
        VendaDto dto = mapper.map(venda, VendaDto.class);
        dto = service.realizarVenda(dto);
        return new ResponseEntity<>(mapper.map(dto, VendasModeloResponse.class), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<VendasModeloResponse>> obterVendas() {
        List<VendaDto> dtos = service.obterVendas();

        if(dtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ModelMapper mapper = new ModelMapper();
        List<VendasModeloResponse> resp = dtos.stream()
                    .map(dto -> mapper.map(dto, VendasModeloResponse.class))
                    .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<VendasModeloResponseDetalhes> obterPorId(@PathVariable String id) {
        Optional<VendaDto> venda = service.obterVendaPorId(id);

        if(venda.isPresent()) {
            return new ResponseEntity<>(
                new ModelMapper().map(venda.get(), VendasModeloResponseDetalhes.class), 
                HttpStatus.OK
            );
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<VendasModeloResponse> atualizarVenda(@PathVariable String id,
        @Valid @RequestBody Venda venda) {
        ModelMapper mapper = new ModelMapper();
        VendaDto dto = mapper.map(venda, VendaDto.class);
        dto = service.atualizarVenda(id, dto);

        return new ResponseEntity<>(mapper.map(dto, VendasModeloResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> removerVenda(@PathVariable String id) {
        service.apagarVenda(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 
}
