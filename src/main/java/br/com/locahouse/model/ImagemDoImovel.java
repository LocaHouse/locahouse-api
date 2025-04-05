package br.com.locahouse.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "imagens_imoveis")
@Entity
public final class ImagemDoImovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer sequencia;

    @Column(length = 100, nullable = false)
    private String descricao;

    @Column(length = 200, nullable = false)
    private String caminho;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Imovel imovel;
}
