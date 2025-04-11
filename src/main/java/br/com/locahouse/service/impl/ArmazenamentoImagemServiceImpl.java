package br.com.locahouse.service.impl;

import br.com.locahouse.service.ArmazenamentoImagemService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ArmazenamentoImagemServiceImpl implements ArmazenamentoImagemService {

    private static final String CAMINHO_IMAGENS_IMOVEIS = System.getenv("CAMINHO_IMAGENS_IMOVEIS");

    @Override
    public String gerarCaminho(MultipartFile arquivo) {
        if (CAMINHO_IMAGENS_IMOVEIS == null || CAMINHO_IMAGENS_IMOVEIS.isEmpty())
            throw new RuntimeException("Variável de ambiente CAMINHO_IMAGENS_IMOVEIS não está configurada.");

        Path diretorioImagens = Paths.get(CAMINHO_IMAGENS_IMOVEIS);
        if (!Files.exists(diretorioImagens)) {
            try {
                Files.createDirectories(diretorioImagens);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar o diretório para armazenar imagens.");
            }
        }

        return diretorioImagens.resolve(UUID.randomUUID() + "-" + arquivo.getOriginalFilename()).toString();
    }

    @Override
    public void gravarNoDisco(MultipartFile arquivo, String caminho) {
        try {
            arquivo.transferTo(new File(caminho));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer a gravação da imagem.", e);
        }
    }

    @Override
    public void apagarDoDIsco(String caminho) {
        try {
            Files.delete(Path.of(caminho));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer a remoção da imagem.", e);
        }
    }
}
