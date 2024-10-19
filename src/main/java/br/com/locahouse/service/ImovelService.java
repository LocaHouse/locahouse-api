package br.com.locahouse.service;

import br.com.locahouse.model.Imovel;

public interface ImovelService extends CrudService<Imovel, Integer> {

    Imovel cadastrar(Integer usuarioId, Imovel imovel, String numeroCep);

    Imovel atualizar(Integer id, Imovel imovel, String numeroCep);
}
