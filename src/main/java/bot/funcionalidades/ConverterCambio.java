package bot.funcionalidades;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Classe responsável por obter o câmbio do dia.
 */
public class ConverterCambio {

    /**
     * Construtor privado para evitar que a classe seja instanciada.
     */
    private ConverterCambio() {
    }

    private static final String CHAVE_SERVICO_CONVERSAO = "1d706d9f5b54df059c5f8467e21fe44a";

    /**
     * Função que chama a API de conversão de moedas.
     *
     * @param moeda a moeda de origem
     * @return uma string com o resultado do câmbio do dia
     */
    public static String obterCambioDia(String moeda) {

        String urlService = new StringBuilder()
                .append("http://data.fixer.io/api/latest?access_key=")
                .append(CHAVE_SERVICO_CONVERSAO)
                .append("&symbols=BRL,")
                .append(moeda)
                .toString();

        HttpGet request = new HttpGet(urlService);

        try(CloseableHttpClient client = HttpClients.createDefault()) {

            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            HashMap<String,Object> valorConversao = new ObjectMapper().readValue(EntityUtils.toString(entity), HashMap.class);
            String resultado = ((LinkedHashMap<String, Double>)valorConversao.get("rates")).get("BRL").toString();

            return "R$" + resultado.substring(0, resultado.length()-2);

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            return "Erro ao obter o câmbio do dia";
        }

    }

}
