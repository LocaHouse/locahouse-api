package br.com.locahouse.repository;

import br.com.locahouse.model.Imovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImovelCustomRepository {

    Page<Imovel> buscar(Pageable pageable, Integer usuarioId, Integer status);
}
