package br.com.joao.lojinha.domain;

import javax.persistence.*;

@Entity
public class Produto extends GenericDomain {

    @ManyToOne
    @JoinColumn(nullable = true)
    Wishlist wishlist;

    @ManyToOne
    @JoinColumn(nullable = true)
    Categoria categoria;

    @Column(length = 100)
    private String nome;

    @Column(length = 20)
    private Double valor;

    @Column(length = 100)
    private String descricao;

    @Column(length = 10)
    private Long estoque;

    @Transient
    private String caminhoImg;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario proprietario;

    public Usuario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Usuario proprietario) {
        this.proprietario = proprietario;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getEstoque() {
        return estoque;
    }

    public void setEstoque(Long estoque) {
        this.estoque = estoque;
    }

    public String getCaminhoImg() {
        return caminhoImg;
    }

    public void setCaminhoImg(String caminhoImg) {
        this.caminhoImg = caminhoImg;
    }
}
