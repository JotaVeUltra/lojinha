package br.com.joao.lojinha.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Estado extends GenericDomain {

    @Column(length = 2)
    private String sigla;

    @Column(length = 100)
    private String nome;

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
