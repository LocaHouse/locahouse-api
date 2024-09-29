package br.com.locahouse.repository;

import br.com.locahouse.model.ComodoDoImovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComodoDoImovelRepository extends JpaRepository<ComodoDoImovel, Integer> {
}
