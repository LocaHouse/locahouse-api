package br.com.locahouse.service;

import br.com.locahouse.model.ComodoDoImovel;
import br.com.locahouse.model.Imovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImovelService {

    Imovel cadastrar(Integer usuarioId, Imovel imovel, String numeroCep, List<ComodoDoImovel> comodos);

    Page<Imovel> buscar(Pageable pageable, Integer id, Integer status);

    Imovel buscarPeloId(Integer id);

    Imovel atualizar(Integer id, Imovel imovel, String numeroCep, List<ComodoDoImovel> comodos);

    void deletar(Integer id);
}
