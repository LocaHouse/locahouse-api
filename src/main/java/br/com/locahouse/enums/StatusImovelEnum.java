package br.com.locahouse.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusImovelEnum {

    DISPONIVEL(0, "Disponível."),
    ALUGADO(1, "Alugado."),
    INDISPONIVEL(2, "Indisponível.");

    private final int codigo;
    private final String descricao;
}
