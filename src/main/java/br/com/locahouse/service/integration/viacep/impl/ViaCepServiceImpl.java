package br.com.locahouse.service.integration.viacep.impl;

import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.model.Cep;
import br.com.locahouse.service.integration.viacep.ViaCepService;
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

    private record ViaCepDto(

            String cep,

            String uf,

            String localidade,

            String bairro,

            String logradouro,

            String erro
    ) {
    }

    private final Gson gson;

    @Autowired
    public ViaCepServiceImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Cep consultar(String numeroCep) {
        try {
            ViaCepDto dto = null;
            try (CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build()) {
                CloseableHttpResponse response = httpClient.execute(new HttpGet("https://viacep.com.br/ws/" + this.removerHifenCep(numeroCep) + "/json"));
                if (response.getStatusLine().getStatusCode() == HttpStatus.BAD_REQUEST.value()) {
                    throw new BusinessException("CEP inválido.", HttpStatus.BAD_REQUEST);
                }

                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    dto = this.gson.fromJson(EntityUtils.toString(httpEntity), ViaCepDto.class);
                }
            }

            if (dto == null) {
                throw new BusinessException("Erro ao consultar o ViaCEP.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (dto.erro() != null && dto.erro().equals("true")) {
                throw new BusinessException("CEP não encontrado na base de dados do ViaCEP.", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            Cep cep = new Cep();
            cep.setNumero(dto.cep());
            cep.setUf(dto.uf());
            cep.setCidade(dto.localidade());
            cep.setBairro(dto.bairro());
            cep.setRua(dto.logradouro());
            return cep;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao consultar o ViaCEP.");
        }
    }

    private String removerHifenCep(String numeroCep) {
        return numeroCep.replace("-", "");
    }
}
