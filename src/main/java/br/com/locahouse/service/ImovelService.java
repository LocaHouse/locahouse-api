package br.com.locahouse.service;

import br.com.locahouse.model.Imovel;

public interface ImovelService {

    Imovel cadastrar(Integer usuarioId, Imovel imovel, String numeroCep);

    Imovel buscarPeloId(Integer id);

    Imovel atualizar(Integer id, Imovel imovel, String numeroCep);

    void deletar(Integer id);
}
