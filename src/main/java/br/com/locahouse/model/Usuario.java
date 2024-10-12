package br.com.locahouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "usuarios")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 14, unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 15, unique = true, nullable = false)
    private String telefone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 60, nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario")
    private List<Imovel> imoveis;
}
