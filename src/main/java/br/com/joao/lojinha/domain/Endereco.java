package br.com.joao.lojinha.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Endereco extends GenericDomain {

    @Column(length = 50)
    private String estado;

    @Column(length = 50)
    private String cidade;

    @Column(length = 50)
    private String bairro;

    @Column(length = 100)
    private String rua;

    @Column(length = 4)
    private Long numero;

    @Column(length = 9)
    private String cep;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
