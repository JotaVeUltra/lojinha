package br.com.joao.lojinha.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Wishlist extends GenericDomain {

    @OneToOne
    @JoinColumn
    private Usuario usuario;

    @OneToOne
    @JoinColumn
    private Produto produto;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
