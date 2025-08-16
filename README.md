# Literalura 📚

Este projeto é uma aplicação de console desenvolvida em Java que funciona como um catálogo de livros e faz parte do Challenge Literalura da formação ONE 2025. Ele permite ao usuário buscar livros através da API Gutendex, que fornece dados de livros do Project Gutenberg, e registrar tanto os livros quanto seus autores em um banco de dados local.


## ✨ Funcionalidades
* **Busca de Livros por Título:** Pesquise livros na API Gutendex pelo título e salve-os no banco de dados.
* **Listagem de Livros Registrados:** Exibe todos os livros que foram salvos no banco de dados.
* **Listagem de Autores Registrados:** Mostra todos os autores salvos.
* **Listagem de Autores Vivos:** Permite pesquisar e listar autores que estavam vivos em um determinado ano.
* **Listagem de Livros por Idioma:** Filtra e exibe os livros registrados em um idioma específico (inglês ou português).

## 📈 Melhorias Futuras
* **Tratamento de Erros:** Aprimorar a validação das entradas do usuário e o tratamento de falhas de conexão com a API para tornar a aplicação mais robusta.
* **Seleção de Livros:** Melhorar a função de busca para exibir múltiplos resultados e permitir que o usuário escolha qual livro deseja salvar, em vez de salvar automaticamente o primeiro encontrado.
* **Interface de Idiomas:** Tornar a seleção de idiomas para busca mais dinâmica, permitindo que o usuário digite o idioma desejado ou escolha de uma lista maior.

## 🛠️ Tecnologias Utilizadas
* **Java 24**
* **Spring Boot e Spring Data JPA**
* **Maven**
* **PostgreSQL**
* **Java `HttpClient`:** Utilizado para fazer as requisições HTTP para a API Gutendex.
* **Jackson:** A classe `ResponseMapper` utiliza a biblioteca Jackson para converter as respostas JSON da API em objetos Java.

## 🔗 API
Este projeto consome os dados da Gutendex, uma API pública para o Project Gutenberg. Acesse em: [Gutendex API](https://gutendex.com/)

## 🚀 Como Executar
1.  Clone este repositório para a sua máquina local.
2.  Certifique-se de ter um servidor PostgreSQL em execução.
3.  Crie um banco de dados chamado `literalura_db`.
4.  Configure seu usuário e senha do PostgreSQL no arquivo `src/main/resources/application.properties`.
5.  Compile e execute a classe principal `LiteraluraApplication`.
6.  Siga as instruções exibidas no terminal para interagir com o catálogo!
