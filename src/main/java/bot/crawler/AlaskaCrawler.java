package bot.crawler;

import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Classe que obtém os dados de rendimento do fundo Alaska Black.
 */
public class AlaskaCrawler {

    /**
     * Construtor privado para evitar que a classe seja instanciada.
     */
    private AlaskaCrawler() {
    }

    private static final String ALASKA_URL = "https://www.alaska-asset.com.br/";

    /**
     * Obtém o último rendimento do fundo.
     *
     * @return uma string com o valor
     * @throws IOException exceção caso não seja possível se conectar ao site
     */
    public static String getLatestValue() throws IOException {
        Element valueRow = bot.crawler.UrlCrawler.getElement(ALASKA_URL,".wpb_wrapper table .row-3");
        return "A rentabilidade do dia "
                + valueRow.select(".column-2").text()
                + " foi "
                + valueRow.select(".column-4").text();

    }

}
