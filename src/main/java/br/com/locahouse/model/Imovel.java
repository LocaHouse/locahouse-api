package br.com.locahouse.model;

import br.com.locahouse.model.enums.StatusImovelEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "imoveis")
@Entity
public final class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private Integer status;

    @Column(length = 500, nullable = false)
    private String descricao;

    @Column(length = 5, nullable = false)
    private String numero;

    private String complemento;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, precision = 8, scale = 3)
    private BigDecimal tamanho;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cep_id", nullable = false)
    private Cep cep;

    @OneToMany(mappedBy = "imovel")
    private List<ComodoDoImovel> comodos;

    public Imovel() {
    }

    public Imovel(Integer id, StatusImovelEnum status, String descricao, String numero, String complemento, BigDecimal valor, BigDecimal tamanho, Usuario usuario, Cep cep, List<ComodoDoImovel> comodos) {
        this.id = id;
        this.status = status.getCodigo();
        this.descricao = descricao;
        this.numero = numero;
        this.complemento = complemento;
        this.valor = valor;
        this.tamanho = tamanho;
        this.usuario = usuario;
        this.cep = cep;
        this.comodos = comodos;
    }

    public void setStatus(StatusImovelEnum status) {
        this.status = status.getCodigo();
    }
}
