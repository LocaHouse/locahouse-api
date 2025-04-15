package br.com.locahouse.service.impl;

import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.model.ImagemDoImovel;
import br.com.locahouse.model.Imovel;
import br.com.locahouse.repository.ImagemDoImovelRepository;
import br.com.locahouse.service.ImovelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImagemDoImovelServiceImplTest {

    private final static Integer IMOVEL_ID = 1;

    private AutoCloseable autoCloseable;

    @Mock
    private ImagemDoImovelRepository repository;

    @Mock
    private ImovelService imovelService;

    @InjectMocks
    private ImagemDoImovelServiceImpl service;

    @BeforeEach
    void setUp() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    void deveCadastrarImagemComSucesso() throws IOException {
        Imovel imovel = new Imovel();
        imovel.setId(IMOVEL_ID);

        when(imovelService.buscarPeloId(IMOVEL_ID)).thenReturn(imovel);
        when(repository.save(any(ImagemDoImovel.class))).thenAnswer(invoc -> invoc.getArgument(0));

        ImagemDoImovel resultado = service.cadastrar(IMOVEL_ID, new ImagemDoImovel(), new MockMultipartFile("imagem", "imagem.png", "image/png", "conteudo".getBytes()));
        assertNotNull(resultado.getCaminho());
        assertEquals(imovel, resultado.getImovel());
        verify(repository).save(resultado);

        Path path = Path.of(resultado.getCaminho());
        if (Files.exists(path))
            Files.delete(path);
    }

    @Test
    void deveLancarBusinessExceptionQuandoFalhaNoUpload() throws IOException {
        Imovel imovel = new Imovel();
        imovel.setId(IMOVEL_ID);
        MockMultipartFile arquivoMock = mock(MockMultipartFile.class);

        when(imovelService.buscarPeloId(IMOVEL_ID)).thenReturn(imovel);
        doThrow(new IOException("Erro simulado.")).when(arquivoMock).transferTo((File) any());

        BusinessException e = assertThrows(BusinessException.class, () -> service.cadastrar(IMOVEL_ID, new ImagemDoImovel(), arquivoMock));
        assertEquals("Erro ao fazer o upload da imagem.", e.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getHttpStatus());
    }
}
