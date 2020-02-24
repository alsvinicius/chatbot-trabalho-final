# Trabalho Final - Chatbot

Javadoc disponível na pasta /docs. Também disponível na url https://alsvinicius.github.io/chatbot-trabalho-final/

## Funções

### /alaska

Obtém o último rendimento disponível do fundo de investimentos Alaska Black.

### /dolar

Obtém a cotação do dólar.

### /rm

Configura o RM do usuário para acessar as funcionalidades do portal da Fiap.

### /senha

Configura a senha do usuário para acessar as funcionalidades do portal da Fiap.

### /aula

Obtém as informações sobre a próxima aula.

### /trabalhos

Obtém os trabalhos pendentes

## Token

O token fica configurado na variável *TOKEN_BOT* na classe *MyBot*, no pacote _conversacao_. Para criar um token, acesse o Telegram e inicie uma conversa com o @BotFather. A opção start aparecerá, dando início à conversa. Nela, digite /newbot para criar um novo bot. Siga as instruções seguintes, dando um nome para seu bot. No final da conversa, o @BotFather irá responder com o token de acesso para a API.

## Interação com o bot

### Execução do projeto

Carregue o projeto no Intellij e importe como um projeto Maven, para que as dependências sejam baixadas. Depois de configurar o token, execute o método main até a mensagem _"Bot pronto para receber mensagens."_ aparecer no console. Isso indicará que o chatbot está rodando e pronto para ser chamado.

### Conversação

No Telegram, pesquise pelo nome que você utilizou na hora de criar seu bot com o @BotFather. O nome do bot irá aparecer na busca. Selecione ele e clique em _start_ para iniciar a conversa.