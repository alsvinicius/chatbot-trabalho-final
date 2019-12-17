package bot;

import bot.conversacao.MyBot;

/**
 * Classe principal para execução da aplicação.
 */
public class BotApplication {

    /**
     * Método inicial da aplicação.
     *
     * @param args argumentos de entrada
     */
    public static void main(String[] args) {
        MyBot myBot = new MyBot();
        myBot.runBot();
    }

}
