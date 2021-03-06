## Avaliação Spring - MBA+ Fiap

## Execução
É necessário apenas dar executar o método main para que a aplicação seja executada.

## Banco de Dados
Não é necessário configurar banco de dados, já que a aplicação usa um banco H2 que é criado na primeira execução.

A escolha pelo H2 se deu pela facilidade de configuração e execução da aplicação, além de ser leve para uma aplicação simples como a desenvolvida.

## Massa para teste
Após executar a aplicação, copie os arquivos da pasta _massa-testes_ para a pasta _files_ na seguinte ordem:
1. Alunos
2. Cartoes
3. Compras

O gatilho do batch está configurado para executar a cada 5 segundos. Logo, serão feitas três execuções, uma para cada arquivo.

## Swagger
Pode ser acessado através da seguinte URL: localhost:8080/swagger-ui.html

## Testes dos endpoints
Na raiz do projeto existe uma collection do Postman salva em json com todos os endpoints da aplicação configurados para serem chamados.

## Extrato
A url pode ser chamada pelo navegador para que o arquivo _.xlsx_ com o extrato seja baixado.
Exemplo de url: localhost:8080/aluno/1/cartao/1/extrato

## Testes Unitários
Foram executados testes unitários nos métodos e classes cabíveis. Os models foram excluídos pelos métodos serem gerados através da dependência _lombok_.

## Testes Integrados
Foi criada a classe de testes _IntegrationTests_, que testa o fluxo da aplicação. Nela, são executadas as operações em um banco de dados de teste, e é validado se as operações foram executadas com sucesso.
