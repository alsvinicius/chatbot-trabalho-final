package bot.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Classe com métodos para varrer sites.
 */
public class UrlCrawler {

    /**
     * Construtor privado para evitar que a classe seja instanciada.
     */
    private UrlCrawler() {}

    /**
     * Método responsável por varrer uma url.
     *
     * @param url a url que será varrida
     * @return um objeto com o conteúdo do site
     * @throws IOException exceção caso não seja possível se conectar ao site
     */
    public static Document crawl(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    /**
     * Obtém uma lista com os elementos dada uma query para especificar uma área do site.
     *
     * @param url      a url que será varrida
     * @param cssQuery a query para definir o elemento
     * @return um objeto com a lista de elementos
     * @throws IOException exceção caso não seja possível se conectar ao site
     */
    public static Elements getElementList(String url, String cssQuery) throws IOException {
        Document document = crawl(url);
        return document.select(cssQuery);
    }

    /**
     * Obtém um elemento específico do site dada uma query.
     *
     * @param url      a url que será varrida
     * @param cssQuery a query para definir o elemento
     * @return um objeto com o elemento
     * @throws IOException exceção caso não seja possível se conectar ao site
     */
    public static Element getElement(String url, String cssQuery) throws IOException {
        return getElementList(url, cssQuery).first();
    }

}
