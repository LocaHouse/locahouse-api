package br.com.locahouse.mapper;

import br.com.locahouse.dto.cep.CepGetViaCepDto;
import br.com.locahouse.model.Cep;

public final class CepMapper {

    private CepMapper() {
    }

    public static Cep cepGetViaCepDtoToEntity(CepGetViaCepDto dto) {
        Cep cep = new Cep();
        cep.setNumero(dto.cep());
        cep.setUf(dto.uf());
        cep.setCidade(dto.localidade());
        cep.setBairro(dto.bairro());
        cep.setRua(dto.logradouro());
        return cep;
    }
}
