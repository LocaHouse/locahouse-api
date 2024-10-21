package br.com.locahouse.service;

import br.com.locahouse.model.Imovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImovelService {

    Imovel cadastrar(Integer usuarioId, Imovel imovel, String numeroCep);

    Page<Imovel> buscar(Pageable pageable, Integer id, Integer status);

    Imovel buscarPeloId(Integer id);

    Imovel atualizar(Integer id, Imovel imovel, String numeroCep);

    void deletar(Integer id);
}
