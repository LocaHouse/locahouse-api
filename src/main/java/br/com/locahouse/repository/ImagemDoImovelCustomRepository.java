package br.com.locahouse.repository;

public interface ImagemDoImovelCustomRepository {

    Integer buscarProximaSequenciaDisponivel(Integer imovelId);
}
