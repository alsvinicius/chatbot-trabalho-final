package bot.conversacao;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import bot.funcionalidades.ConverterCambio;

/**
 * Classe responsável pelas mensagens de conversão de moedas.
 */
public class ConversaoCambioBot extends TransportaMensagens {

    /**
     * Inicializa um objeto.
     *
     * @param bot o bot atual
     */
    public ConversaoCambioBot(TelegramBot bot) {
        super.bot = bot;
    }

    /**
     * Envia mensagem com o câmbio do dia.
     *
     * @param chatId o id do chat atual
     * @param moeda  a moeda de origem da conversão
     */
    public void respondeCambioDia(long chatId, String moeda) {
        bot.execute(new SendMessage(chatId, "A cotação do dólar hoje foi " + ConverterCambio.obterCambioDia(moeda)));
    }

}
