package br.com.locahouse.repository.impl;

import br.com.locahouse.model.Imovel;
import br.com.locahouse.model.enums.StatusImovelEnum;
import br.com.locahouse.repository.ImovelCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
class ImovelCustomRepositoryImpl implements ImovelCustomRepository {

    private final EntityManager entityManager;

    @Autowired
    public ImovelCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Imovel> buscar(Pageable pageable, Integer usuarioId, StatusImovelEnum status) {
        String select = "SELECT i FROM Imovel i";
        String condicao = " WHERE ";
        if (usuarioId != null) {
            select += condicao + "i.usuario.id = :usuarioId";
            condicao = " AND ";
        }
        if (status != null) {
            select += condicao + "i.status = :status";
        }

        TypedQuery<Imovel> queryBuscaImoveis = entityManager.createQuery(select, Imovel.class);
        if (usuarioId != null) {
            queryBuscaImoveis.setParameter("usuarioId", usuarioId);
        }
        if (status != null) {
            queryBuscaImoveis.setParameter("status", status);
        }
        queryBuscaImoveis.setFirstResult((int) pageable.getOffset());
        queryBuscaImoveis.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(queryBuscaImoveis.getResultList(), pageable, entityManager.createQuery("SELECT COUNT(i) FROM Imovel i", Long.class).getSingleResult());
    }
}
