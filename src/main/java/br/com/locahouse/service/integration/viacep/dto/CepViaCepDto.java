package br.com.locahouse.service.integration.viacep.dto;

public record CepViaCepDto(

        String cep,

        String uf,

        String localidade,

        String bairro,

        String logradouro,

        String erro
) {
}
