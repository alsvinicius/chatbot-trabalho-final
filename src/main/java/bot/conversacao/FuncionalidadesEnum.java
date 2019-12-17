package bot.conversacao;

/**
 * Enum com informações das funcionalidades.
 */
public enum FuncionalidadesEnum {

    /**
     * Dados do fundo Alaska Black.
     */
    ALASKA ("/alaska", "para obter a cotação mais recente do fundo Alaska Black", ""),
    /**
     * Informações da aula.
     */
    AULA ("/aula", "para saber qual será a próxima aula", ""),
    /**
     * Cotação do dólar.
     */
    DOLAR ("/dolar", "para obter a cotação do dólar de hoje", ""),
    /**
     * RM do usuário para consulta ao portal da Fiap..
     */
    RM("/rm", "para configurar seu RM", "RM ainda não configurado. Use o comando /rm para configurá-lo"),
    /**
     * Senha do usuário para consulta ao portal da Fiap.
     */
    SENHA("/senha", "para configurar sua senha","Senha ainda não configurada. Use o comando /senha para configurá-la");

    private String comando;
    private String descricao;
    private String custom;

    FuncionalidadesEnum(String comando, String descricao, String custom) {
        this.comando = comando;
        this.descricao = descricao;
        this.custom = custom;
    }

    /**
     * Obtém a informação do comando.
     *
     * @return string com o comando.
     */
    public String getComando() {
        return comando;
    }

    /**
     * Obtém a descrição do comando.
     *
     * @return string com a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Obtém a descrição customizada.
     *
     * @return string com a mensagem
     */
    public String getCustom() {
        return custom;
    }
}
