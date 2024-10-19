package br.com.locahouse.enums;

public enum StatusImovelEnum {

    DISPONIVEL(0, "Disponível."),
    ALUGADO(1, "Alugado."),
    INDISPONIVEL(2, "Indisponível.");

    private final int codigo;
    private final String descricao;

    StatusImovelEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
