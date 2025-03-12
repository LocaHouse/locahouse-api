package br.com.locahouse.integration.impl;

import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.mapper.CepMapper;
import br.com.locahouse.model.Cep;
import br.com.locahouse.integration.impl.dto.CepViaCepDto;
import br.com.locahouse.integration.ViaCepService;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ViaCepServiceImpl implements ViaCepService {

    private final Gson gson;

    @Autowired
    public ViaCepServiceImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Cep consultar(String numeroCep) throws IOException {
        CepViaCepDto cepViaCepDto = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build()) {
            CloseableHttpResponse response = httpClient.execute(new HttpGet("https://viacep.com.br/ws/" + this.removerHifenCep(numeroCep) + "/json"));
            if (response.getStatusLine().getStatusCode() == HttpStatus.BAD_REQUEST.value())
                throw new BusinessException("CEP inválido.", HttpStatus.BAD_REQUEST);

            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null)
                cepViaCepDto = this.gson.fromJson(EntityUtils.toString(httpEntity), CepViaCepDto.class);
        }

        if (cepViaCepDto != null && cepViaCepDto.erro() != null && cepViaCepDto.erro().equals("true"))
            throw new BusinessException("CEP não encontrado na base de dados do ViaCEP.", HttpStatus.UNPROCESSABLE_ENTITY);

        if (cepViaCepDto == null)
            throw new BusinessException("Erro ao consultar o ViaCEP.", HttpStatus.INTERNAL_SERVER_ERROR);

        return CepMapper.cepConsultaViaCepDtoToEntity(cepViaCepDto);
    }

    private String removerHifenCep(String numeroCep) {
        return numeroCep.replace("-", "");
    }
}
