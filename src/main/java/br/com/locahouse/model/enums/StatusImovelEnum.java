package br.com.locahouse.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusImovelEnum {

    DISPONIVEL(0, "Disponível."),
    ALUGADO(1, "Alugado."),
    INDISPONIVEL(2, "Indisponível.");

    private final Integer codigo;
    private final String descricao;

    public static StatusImovelEnum bucarEnumPeloCodigo(Integer codigo) {
        for (StatusImovelEnum status : StatusImovelEnum.values())
            if (status.getCodigo().equals(codigo))
                return status;

        throw new IllegalArgumentException("Status do imóvel inválido: " + codigo);
    }

    public static boolean verificarExistencia(Integer codigo) {
        for (StatusImovelEnum status : StatusImovelEnum.values())
            if (status.getCodigo().equals(codigo))
                return true;

        return false;
    }
}
