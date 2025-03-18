package br.com.locahouse.repository.impl;

import br.com.locahouse.model.Imovel;
import br.com.locahouse.repository.ImovelCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class ImovelCustomRepositoryImpl implements ImovelCustomRepository {

    private final EntityManager entityManager;

    @Autowired
    public ImovelCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Imovel> buscar(Pageable pageable, Integer idUsuario, Integer status) {
        TypedQuery<Long> quantidadePaginas = entityManager.createQuery("SELECT COUNT(i) FROM Imovel i", Long.class);

        String query = "SELECT i FROM Imovel i";
        String condicao = " WHERE ";
        if (idUsuario != null) {
            query += condicao + "i.usuario.id = :idUsuario";
            condicao = " AND ";
        }
        if (status != null)
            query += condicao + "i.status = :status";

        TypedQuery<Imovel> typedQuery = entityManager.createQuery(query, Imovel.class);
        if (idUsuario != null)
            typedQuery.setParameter("idUsuario", idUsuario);
        if (status != null)
            typedQuery.setParameter("status", status);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(typedQuery.getResultList(), pageable, quantidadePaginas.getSingleResult());
    }
}
