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

/**
 * Classe responsável por obter o câmbio do dia.
 */
public class ConverterCambio {

    /**
     * Construtor privado para evitar que a classe seja instanciada.
     */
    private ConverterCambio() {
    }

    private static final String CHAVE_SERVICO_CONVERSAO = "5cbccfbae19a65641a06";

    /**
     * Função que chama a API de conversão de moedas.
     *
     * @param moeda a moeda de origem
     * @return uma string com o resultado do câmbio do dia
     */
    public static String obterCambioDia(String moeda) {

        String urlService = new StringBuilder()
                .append("https://free.currconv.com/api/v7/convert?q=")
                .append(moeda)
                .append("_BRL&compact=ultra&apiKey=")
                .append(CHAVE_SERVICO_CONVERSAO)
                .toString();

        HttpGet request = new HttpGet(urlService);

        try(CloseableHttpClient client = HttpClients.createDefault()) {

            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            HashMap<String,Double> valorConversao = new ObjectMapper().readValue(EntityUtils.toString(entity), HashMap.class);
            String resultado = valorConversao.get(moeda + "_BRL").toString();

            return "R$" + resultado.substring(0, resultado.length()-2);

        } catch (IOException ioEx) {
            return "Erro ao obter o câmbio do dia";
        }

    }

}
