package br.com.joao.lojinha.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Venda extends GenericDomain {

    @OneToOne
    @JoinColumn(nullable = false)
    private Usuario vendedor;

    @OneToOne
    @JoinColumn(nullable = false)
    private Usuario comprador;

    @OneToOne
    @JoinColumn(nullable = false)
    private Produto produtos;

    @Column(length = 8)
    private String data;

    @Column(length = 10)
    private Long quantidate;

    @Column(length = 7)
    private Double valor;
}
