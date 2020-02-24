package bot.conversacao;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import bot.crawler.FiapCrawler;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Classe que centraliza as operações do bot.
 */
public class MyBot {

    private static final String TOKEN_BOT = "1085329822:AAEtNPYEmJ92UYAUXc4ahOMcRswT1ShxWGA";
    private boolean possuiComandoAnterior = false;
    private String comandoAnterior = "";

    private final TelegramBot bot;

    private long currentChatId;

    private String currentMessage;

    private AlaskaBot alaskaBot;
    private ConversaoCambioBot conversaoCambioBot;
    private FiapBot fiapBot;
    private MensagensPadraoBot mensagensPadraoBot;

    /**
     * Instancia e configura um novo bot
     */
    public MyBot() {
        bot = new TelegramBot(TOKEN_BOT);

        alaskaBot = new AlaskaBot(bot);
        conversaoCambioBot = new ConversaoCambioBot(bot);
        fiapBot = new FiapBot(bot);
        mensagensPadraoBot = new MensagensPadraoBot(bot);
    }

    /**
     * Executa o bot.
     */
    public void runBot() {
        bot.setUpdatesListener(this::orquestraRespostas);
        System.out.println("Bot pronto para receber mensagens.");
    }

    /**
     * Orquestra as respostas para saber qual objeto chamar
     * @param updates lista com atualizações enviadas pelo telegram
     * @return objeto com o status
     */
    private int orquestraRespostas(List<Update> updates) {

        for(Update update : updates) {

            try {
                currentChatId = update.message().chat().id();
                currentMessage = StringUtils.stripAccents(update.message().text());
                mensagensPadraoBot.mensagemDigitando(currentChatId);

                if(possuiComandoAnterior) {
                    orquestraComandoAnterior();
                    continue;
                }

                switch (currentMessage) {
                    case "/start":
                        mensagensPadraoBot.retornaRespostaBoasVindas(currentChatId);
                        break;
                    case "/alaska":
                        alaskaBot.respondeRentabilidadeDiaria(currentChatId);
                        break;
                    case "/dolar":
                        conversaoCambioBot.respondeCambioDia(currentChatId, "USD");
                        break;
                    case "/aula":
                        fiapBot.respondeProximaAula(currentChatId);
                        break;
                    case "/rm":
                        fiapBot.digiteSeuRm(currentChatId);
                        possuiComandoAnterior = true;
                        comandoAnterior = FuncionalidadesEnum.RM.getComando();
                        break;
                    case "/senha":
                        fiapBot.digiteSuaSenha(currentChatId);
                        possuiComandoAnterior = true;
                        comandoAnterior = FuncionalidadesEnum.SENHA.getComando();
                        break;
                    case "/trabalhos":
                        fiapBot.respondeTrabalhosPendentes(currentChatId);
                        break;
                    default:
                        mensagensPadraoBot.retornaRespostaPadrao(currentChatId);
                }

            } catch (Exception ex) {
                mensagensPadraoBot.retornaRespostaPadrao(currentChatId);
            }

        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Método que orquestra a execução caso haja um comando anterior que necessita uma próxima ação
     */
    private void orquestraComandoAnterior() {
        switch(comandoAnterior) {
            case "/rm":
                FiapCrawler.setRmFiap(currentMessage.replace("RM", "").trim());
                fiapBot.rmSalvo(currentChatId);
                break;
            case "/senha":
                FiapCrawler.setSenhaFiap(currentMessage);
                fiapBot.senhaSalva(currentChatId);
                break;
            default:
                mensagensPadraoBot.retornaRespostaPadrao(currentChatId);
        }

        comandoAnterior = "";
        possuiComandoAnterior = false;
    }
}
