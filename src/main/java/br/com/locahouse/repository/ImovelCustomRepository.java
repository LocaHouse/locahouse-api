package br.com.locahouse.repository;

import br.com.locahouse.model.Imovel;
import br.com.locahouse.model.enums.StatusImovelEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImovelCustomRepository {

    Page<Imovel> buscar(Pageable pageable, Integer usuarioId, StatusImovelEnum status);
}
