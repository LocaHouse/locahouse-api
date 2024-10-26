package br.com.locahouse.dto.cep;

public record CepConsultaViaCepDto(

        String cep,

        String uf,

        String localidade,

        String bairro,

        String logradouro,

        String erro
) {
}
