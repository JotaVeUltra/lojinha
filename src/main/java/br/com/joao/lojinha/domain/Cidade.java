package br.com.joao.lojinha.domain;

import javax.persistence.*;

@Entity
public class Cidade extends GenericDomain {

    @Column(length = 100)
    private String nome;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Estado uf;

    @Transient
    private String caminhoImg;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getUf() {
        return uf;
    }

    public void setUf(Estado uf) {
        this.uf = uf;
    }

    public String getCaminhoImg() {
        return caminhoImg;
    }

    public void setCaminhoImg(String caminhoImg) {
        this.caminhoImg = caminhoImg;
    }
}
