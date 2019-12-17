package bot.conversacao;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

/**
 * Classe que centraliza o envio de mensagens para facilitar a manutenção.
 */
public class TransportaMensagens {

    /**
     * Objeto com o bot.
     */
    protected TelegramBot bot;

    /**
     * Envia a mensagem.
     *
     * @param chatId   o id do chat atual
     * @param mensagem a mensagem que será enviada
     * @return objeto com informação do envio
     */
    protected SendResponse enviarMensagem(long chatId, String mensagem) {
        return bot.execute(new SendMessage(chatId, mensagem));
    }

}
