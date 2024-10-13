package br.com.locahouse.repository;

import br.com.locahouse.model.Cep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CepRepository extends JpaRepository<Cep, Integer> {

    Optional<Cep> findByNumero(String numero);
}
