package br.com.locahouse.repository;

import br.com.locahouse.model.ImagemDoImovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemDoImovelRepository extends JpaRepository<ImagemDoImovel, Integer> {
}
