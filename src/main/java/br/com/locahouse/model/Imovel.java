package br.com.locahouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "imoveis")
@Entity
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @OneToMany(mappedBy = "imovel")
    private List<Locacao> locacoes;
}
