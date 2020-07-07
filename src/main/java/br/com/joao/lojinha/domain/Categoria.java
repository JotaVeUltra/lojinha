package br.com.joao.lojinha.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Categoria extends GenericDomain {

    @Column
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
