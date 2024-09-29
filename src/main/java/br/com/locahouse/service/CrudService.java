package br.com.locahouse.service;

import java.util.List;

public interface CrudService<T, ID> {

    T cadastrar(T entidade);

    List<T> buscarTodos();

    T buscarPeloId(ID id);

    T atualizar(ID id, T entidade);

    void deletar(ID id);
}
