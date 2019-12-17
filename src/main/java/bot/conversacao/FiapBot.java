package bot.conversacao;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import bot.crawler.FiapCrawler;
import org.apache.commons.text.WordUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Classe que centraliza mensagens do portal da Fiap.
 */
public class FiapBot extends TransportaMensagens {

    /**
     * Instancia um novo objeto.
     *
     * @param bot o bot atual
     */
    public FiapBot(TelegramBot bot) {
        super.bot = bot;
    }

    /**
     * Envia mensagem confirmando que a senha foi salva.
     *
     * @param chatId o id do chat atual
     */
    public void senhaSalva(long chatId) {
        bot.execute(new SendMessage(chatId, "Senha salva com sucesso!"));
    }

    /**
     * Envia mensagem confirmando que o RM foi salvo.
     *
     * @param chatId o id do chat atual
     */
    public void rmSalvo(long chatId) {
        bot.execute(new SendMessage(chatId, "RM salvo com sucesso!"));
    }

    /**
     * Envia mensagem solicitando a senha.
     *
     * @param chatId o id do chat atual
     */
    public void digiteSuaSenha(long chatId) {
        bot.execute(new SendMessage(chatId, "Digite sua senha:"));
    }

    /**
     * Envia mensagem solicitando o RM.
     *
     * @param chatId o id do chat atual
     */
    public void digiteSeuRm(long chatId) {
        bot.execute(new SendMessage(chatId, "Digite seu RM:"));
    }

    /**
     * Verifica se é necessário pedir as credenciais do usuário.
     *
     * @param chatId o id do chat atual
     * @return boolean com o status
     */
    public boolean pedeCredenciais(long chatId) {
        return pedeRm(chatId) || pedeSenha(chatId);
    }

    /**
     * Verifica se é necessário pedir o RM.
     *
     * @param chatId o id do chat atual
     * @return boolean com o status
     */
    public boolean pedeRm(long chatId) {
        if(!FiapCrawler.hasRm()) {
            bot.execute(new SendMessage(chatId, FuncionalidadesEnum.RM.getCustom()));
            return false;
        } else {
            return true;
        }
    }

    /**
     * Verifica se é necessário pedir a senha.
     *
     * @param chatId o id do chat atual
     * @return boolean com o status
     */
    public boolean pedeSenha(long chatId) {
        if(!FiapCrawler.hasPassword()) {
            bot.execute(new SendMessage(chatId, FuncionalidadesEnum.SENHA.getCustom()));
            return false;
        } else {
            return true;
        }
    }

    /**
     * Envia mensagem com informações sobre a próxima aula.
     *
     * @param chatId o id do chat atual
     * @throws IOException exceção caso não seja possível obter os dados do site
     */
    public void respondeProximaAula(long chatId) throws IOException {

        if(!pedeCredenciais(chatId)) {
            return;
        }

        Element proximaAula = FiapCrawler.getProximaAula();

        if(proximaAula == null) {
            new MensagensPadraoBot(bot).retornaRespostaPadrao(chatId);
        } else {

            String retornoProximaAula = new StringBuilder()
                    .append("A próxima aula será dia ")
                    .append(proximaAula.select(".i-calendario-horario").first().text())
                    .append(" no laboratório ")
                    .append(proximaAula.select(".i-calendario-row-sala .i-calendario-title").text())
                    .append(" sobre a matéria ")
                    .append(proximaAula.select(".i-calendario-row-materia .i-calendario-title").text())
                    .append(" com o professor ")
                    .append(proximaAula.select(".i-calendario-row-materia .i-calendario-subtitle").text().trim())
                    .toString();

            bot.execute(new SendMessage(chatId, retornoProximaAula));
        }
    }

    /**
     * Enviar mensagem informando os trabalhos pendentes.
     *
     * @param chatId o id do chat atual
     * @throws IOException exceção caso não seja possível obter os dados do site
     */
    public void respondeTrabalhosPendentes(long chatId) throws IOException {

        if (!pedeCredenciais(chatId)) {
            return;
        }

        bot.execute(new SendMessage(chatId, respondeTrabalhos("PENDENTE")));
    }

    /**
     * Retorna uma string com os trabalhos do status informado.
     * @param status o status do trabalho
     * @return uma string com os trabalhos
     * @throws IOException exceção caso não seja possível obter os dados do site
     */
    private String respondeTrabalhos(String status) throws IOException {

        StringBuilder trabalhosStr = new StringBuilder("");

        Elements trabalhos = FiapCrawler.getTrabalhosPendentes();

        for (Element trabalho : trabalhos) {
            if(trabalho.select(".i-trabalhos-column-date .i-trabalhos-status").text().trim().equalsIgnoreCase(status)) {
                String dataEntrega = trabalho.select(".i-trabalhos-column-date .i-trabalhos-date").text().trim();
                String titulo = trabalho.select(".i-trabalhos-column-title .i-trabalhos-title").text().trim();
                String materia = trabalho.select(".i-trabalhos-column-title .i-trabalhos-subtitle").text().trim();
                materia = WordUtils.capitalize(materia);

                trabalhosStr.append(titulo)
                        .append(" da matéria ")
                        .append(materia)
                        .append(" para ser entregue dia ")
                        .append(dataEntrega)
                        .append(".\n\n");
            }
        }

        if(trabalhosStr.toString().equals("")) {
            return "Nenhum trabalho com o status " + status;
        } else {
            return trabalhosStr.toString();
        }

    }
}
