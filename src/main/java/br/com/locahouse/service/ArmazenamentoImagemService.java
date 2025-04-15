package br.com.locahouse.service;

import org.springframework.web.multipart.MultipartFile;

public interface ArmazenamentoImagemService {

    String gerarCaminho(MultipartFile arquivo);

    void gravarNoDisco(MultipartFile arquivo, String caminho);

    void apagarDoDIsco(String caminho);
}
