package br.com.locahouse.repository.impl;

import br.com.locahouse.repository.ImagemDoImovelCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
class ImagemDoImovelCustomRepositoryImpl implements ImagemDoImovelCustomRepository {

    private final EntityManager entityManager;

    @Autowired
    public ImagemDoImovelCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer buscarProximaSequenciaDisponivel(Integer idImovel) {
        TypedQuery<Integer> queryProximaSequencia = entityManager.createQuery("SELECT COALESCE(MAX(i.sequencia), 0) + 1 FROM ImagemDoImovel i WHERE i.imovel.id = :idImovel", Integer.class);
        queryProximaSequencia.setParameter("idImovel", idImovel);
        return queryProximaSequencia.getSingleResult();
    }
}
