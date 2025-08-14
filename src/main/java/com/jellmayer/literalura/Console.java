package com.jellmayer.literalura;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jellmayer.literalura.model.BookData;
import com.jellmayer.literalura.model.GutendexResponse;
import com.jellmayer.literalura.service.ApiClient;
import com.jellmayer.literalura.service.ResponseMapper;

import java.io.IOException;
import java.util.Scanner;

public class Console {
    private static final ApiClient client = new ApiClient();
    private static final ResponseMapper mapper = new ResponseMapper();
    private Scanner scanner = new Scanner(System.in);

    private static final String BASE_URL = "https://gutendex.com/books/";

    public void runConsole() throws IOException, InterruptedException {
        System.out.println("Nome do livro: ");
        String livro = scanner.nextLine().replace(" ", "%20");
        String url = BASE_URL + "?search=" + livro;
        System.out.println(url);
        String json = client.fetchData(url);

        GutendexResponse response = mapper.parseJson(json, GutendexResponse.class);
        BookData bookData = response.results().get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(bookData));
    }
}
