package br.com.locahouse.service;

import br.com.locahouse.model.ComodoDoImovel;

public interface ComodoDoImovelService {

    ComodoDoImovel cadastrar(Integer imovelId, ComodoDoImovel comodoDoImovel);

    ComodoDoImovel buscarPeloId(Integer id);

    ComodoDoImovel atualizar(Integer id, ComodoDoImovel comodoDoImovel);

    void deletar(Integer id);
}
