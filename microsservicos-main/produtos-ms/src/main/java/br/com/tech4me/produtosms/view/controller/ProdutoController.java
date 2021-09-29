package br.com.tech4me.produtosms.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import br.com.tech4me.produtosms.compartilhado.ProdutoDto;
import br.com.tech4me.produtosms.service.ProdutoService;
import br.com.tech4me.produtosms.view.model.ProdutoModeloAlteracao;
import br.com.tech4me.produtosms.view.model.ProdutoModeloInclusao;
import br.com.tech4me.produtosms.view.model.ProdutoModeloResponse;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService service;

    @GetMapping(value="/status")
    public String statusServico(@Value("${local.server.port}") String porta) {
        return String.format("Serviço ativo e executando na porta %s", porta);
    }    

    @PostMapping
    public ResponseEntity<ProdutoModeloResponse> cadastrarPoduto(@RequestBody @Valid ProdutoModeloInclusao Produto) {
        ModelMapper mapper = new ModelMapper();
        ProdutoDto dto = mapper.map(Produto, ProdutoDto.class);
        dto = service.cadastrarProduto(dto);
        return new ResponseEntity<>(mapper.map(dto, ProdutoModeloResponse.class), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<ProdutoModeloResponse>> obterTodos() {
        List<ProdutoDto> dtos = service.obterProdutos();

        if(dtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ModelMapper mapper = new ModelMapper();
        List<ProdutoModeloResponse> resp = dtos.stream()
                    .map(dto -> mapper.map(dto, ProdutoModeloResponse.class))
                    .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
/*
    @GetMapping(value="/{dono}/lista")
    public ResponseEntity<List<ProdutoModeloResponse>> obterPorDono(@PathVariable String dono) {
        List<ProdutoDto> dtos = service.obterPorDono(dono);

        if(dtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ModelMapper mapper = new ModelMapper();
        List<ProdutoModeloResponse> resp = dtos.stream()
                    .map(dto -> mapper.map(dto, ProdutoModeloResponse.class))
                    .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
*/    
    @GetMapping(value="/{id}")
    public ResponseEntity<ProdutoModeloResponse> obterPorId(@PathVariable String id) {
        Optional<ProdutoDto> Produto = service.obterProdutoPorId(id);

        if(Produto.isPresent()) {
            return new ResponseEntity<>(
                new ModelMapper().map(Produto.get(), ProdutoModeloResponse.class), 
                HttpStatus.OK
            );
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<ProdutoModeloResponse> atualizarProduto(@PathVariable String id,
        @Valid @RequestBody ProdutoModeloAlteracao Produto) {
        ModelMapper mapper = new ModelMapper();
        ProdutoDto dto = mapper.map(Produto, ProdutoDto.class);
        dto = service.atualizarProduto(id, dto);

        return new ResponseEntity<>(mapper.map(dto, ProdutoModeloResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> apagarProduto(@PathVariable String id) {
        service.apagarProduto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
