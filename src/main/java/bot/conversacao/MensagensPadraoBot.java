package bot.conversacao;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.SendChatAction;

/**
 * Classe responsável por enviar mensagens padrão.
 */
public class MensagensPadraoBot extends TransportaMensagens {

    /**
     * Instancia a classe.
     *
     * @param bot o objeto atual do bot
     */
    public MensagensPadraoBot(TelegramBot bot) {
        super.bot = bot;
    }

    /**
     * Retorna a resposta padrão.
     *
     * @param chatId o id do chat atual
     */
    public void retornaRespostaPadrao(long chatId) {
        enviarMensagem(chatId, "Não tenho uma resposta para isso");
    }

    /**
     * Retorna a resposta de boas vindas.
     *
     * @param chatId o id do chat atual
     */
    public void retornaRespostaBoasVindas(long chatId) {

        StringBuilder boasVindasMsg = new StringBuilder("Seja bem-vindo! As opções atuais são: \n");

        for(FuncionalidadesEnum funcionalidade : FuncionalidadesEnum.values()) {
            boasVindasMsg.append(funcionalidade.getComando())
                    .append(" ")
                    .append(funcionalidade.getDescricao())
                    .append("\n");
        }

        enviarMensagem(chatId, boasVindasMsg.toString());
    }

    /**
     * Mostra a mensagem que o bot está digitando.
     *
     * @param chatId o id do chat atual
     */
    public void mensagemDigitando(long chatId) {
        bot.execute(new SendChatAction(chatId, ChatAction.typing.name()));
    }
}
