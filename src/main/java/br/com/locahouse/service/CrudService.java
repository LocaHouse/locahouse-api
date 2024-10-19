package br.com.locahouse.service;

interface CrudService<T, ID> {

    T buscarPeloId(ID id);

    void deletar(ID id);
}
