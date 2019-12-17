package bot.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Classe com métodos para varrer o portal de alunos da Fiap.
 */
public class FiapCrawler {

    private static final String URL_BASE = "https://www2.fiap.com.br";
    private static final String URL_LOGIN = URL_BASE + "/Aluno/LogOn";
    private static final String URL_CALENDARIO = URL_BASE + "/programas/login/alunos_2004/calendarioPos/calendario.asp?titulo_secao=Calendário%20de%20Aulas";
    private static final String URL_TRABALHOS = URL_BASE + "/programas/login/alunos_2004/entregaTrabalho/lista.asp?titulo_secao=Entrega%20de%20Trabalhos";
    private static Connection.Response logonDocument;
    private static Document calendarPage;
    private static LocalDateTime lastCalendarCrawl;
    private static boolean logged = false;
    private static String rmFiap;
    private static String senhaFiap;

    /**
     * Construtor privado para evitar que a classe seja instanciada.
     */
    private FiapCrawler(){}


    /**
     * Realiza o login no portal da Fiap.
     * @throws IOException exceção caso não seja possível obter o elemento
     */
    private static void logIn() throws IOException {

        logonDocument = Jsoup.connect(URL_LOGIN)
                .data("usuario", rmFiap)
                .data("senha", senhaFiap)
                .data("urlRedirecionamento", "")
                .referrer(URL_BASE)
                .followRedirects(true)
                .method(Connection.Method.POST)
                .execute();

        logged = true;
    }

    /**
     * Verifica se o usuário está logado nessa sessão.
     * @throws IOException exceção caso não seja possível obter o elemento
     */
    private static void checkLogin() throws IOException {
        if(!logged) {
            logIn();
        }
    }

    /**
     * Verifica se a senha está configurada.
     *
     * @return boolean com o resultado
     */
    public static boolean hasPassword() {
        return !(senhaFiap == null || senhaFiap.isEmpty());
    }

    /**
     * Verifica se o rm está configurado.
     *
     * @return boolean com o resultado
     */
    public static boolean hasRm() {
        return !(rmFiap == null || rmFiap.isEmpty());
    }

    /**
     * Verifica se é necessário varrer o calendário novamente.
     * @param today o dia de hoje
     * @throws IOException exceção caso não seja possível obter o elemento
     */
    private static void checkStatusCalendario (LocalDate today) throws IOException {
        if(lastCalendarCrawl == null ||
                (today.getDayOfMonth() - lastCalendarCrawl.getDayOfMonth()) > 1) {
            getCalendario();
        }
    }

    /**
     * Varre o portal da Fiap em busca do calendário.
     * @return um objecto com o calendário no site
     * @throws IOException exceção caso não seja possível obter o elemento
     */
    private static Document getCalendario() throws IOException {
        checkLogin();
        calendarPage = Jsoup.connect(URL_CALENDARIO)
                .cookies(logonDocument.cookies())
                .get();

        lastCalendarCrawl = LocalDateTime.now();

        return calendarPage;
    }

    /**
     * Realiza o login no portal da Fiap.
     * @return um objeto com o calendário no site
     * @throws IOException exceção caso não seja possível obter o elemento
     */
    private static Document getPageTrabalhos() throws IOException {
        checkLogin();
        return Jsoup.connect(URL_TRABALHOS)
                .cookies(logonDocument.cookies())
                .get();
    }

    /**
     * Obtém o elemento com as informações da próxima aula.
     *
     * @return um objeto com as informações da próxima aula
     * @throws IOException exceção caso não seja possível obter o elemento
     */
    public static Element getProximaAula() throws IOException {

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        checkStatusCalendario(today);

        for(Element element : calendarPage.select(".i-calendario-row")) {

            LocalDate dataAula = LocalDate.parse(
                    element.select(".i-calendario-horario").first().text(), formatter
            );

            if(dataAula.isEqual(today) || dataAula.isAfter(today)) {
                return element;
            }
        }

        return null;

    }

    /**
     * Obtém a sala da aula do dia.
     *
     * @return uma string com a sala de aula
     * @throws IOException exceção caso não seja possível obter o elemento
     */
    public static String getSalaAulaHoje() throws IOException {

        LocalDate today = LocalDate.now();

        checkStatusCalendario(today);

        String todayStr = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH).format(today);

        for(Element element : calendarPage.select(".i-calendario-row")) {
            if(element.select(".i-calendario-horario").first().text().equals(todayStr)) {
                return element.select(".i-calendario-row-sala .i-calendario-title").text();
            }
        }

        return "";
    }

    /**
     * Obtém uma lista com os trabalhos pendentes.
     *
     * @return Objeto com uma lista de elementos contendo os trabalhos pendentes
     * @throws IOException exceção caso não seja possível obter o elemento
     */
    public static Elements getTrabalhosPendentes() throws IOException {
        Document pageTrabalhos = getPageTrabalhos();
        return pageTrabalhos.select(".i-trabalhos-list .i-trabalhos-item");
    }

    /**
     * Configura a senha.
     *
     * @param senhaFiap a senha do usuário
     */
    public static void setSenhaFiap(String senhaFiap) {
        FiapCrawler.senhaFiap = senhaFiap;
    }

    /**
     * Configura o rm.
     *
     * @param rmFiap o rm do usuário
     */
    public static void setRmFiap(String rmFiap) {
        FiapCrawler.rmFiap = rmFiap;
    }
}
