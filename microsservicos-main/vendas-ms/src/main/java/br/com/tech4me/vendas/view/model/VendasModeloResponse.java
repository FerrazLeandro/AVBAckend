package br.com.tech4me.vendas.view.model;

public class VendasModeloResponse {
    private String id;
    private Integer codigo;
    private Integer quantidade;
    private Double valor;

    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getcodigo() {
        return codigo;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public void setcodigo(Integer codigo) {
        this.codigo = codigo;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public Double getValor() {
        return valor;

    }
}

