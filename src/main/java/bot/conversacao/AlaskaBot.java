package bot.conversacao;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import bot.crawler.AlaskaCrawler;

import java.io.IOException;

/**
 * Classe responsável pelas mensagens do fundo Alaska Black.
 */
public class AlaskaBot extends TransportaMensagens {

    /**
     * Instancia um objeto.
     *
     * @param bot o bot atual
     */
    public AlaskaBot(TelegramBot bot) {
        super.bot = bot;
    }

    /**
     * Envia mensagem com a rentabilidade do dia.
     *
     * @param chatId o id do chat atual
     * @throws IOException exceção caso não seja possível obter os dados do site
     */
    public void respondeRentabilidadeDiaria(long chatId) throws IOException {
        bot.execute(new SendMessage(chatId, AlaskaCrawler.getLatestValue()));
    }

}
