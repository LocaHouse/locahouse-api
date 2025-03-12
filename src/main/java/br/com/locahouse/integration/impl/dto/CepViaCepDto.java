package br.com.locahouse.integration.impl.dto;

public record CepViaCepDto(

        String cep,

        String uf,

        String localidade,

        String bairro,

        String logradouro,

        String erro
) {
}
