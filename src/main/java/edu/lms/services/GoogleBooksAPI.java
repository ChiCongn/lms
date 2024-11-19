package edu.lms.services;

import edu.lms.models.book.Book;
import edu.lms.services.database.BookDataService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GoogleBooksAPI {
    private static final String API_KEY = Config.GG_BOOKS_API_KEY;
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final OkHttpClient client = new OkHttpClient();;

    public static ObservableList<Book> searchBooks(String query) {
        System.out.println("search book by gg book");
        ObservableList<Book> searchResult = FXCollections.observableArrayList();
        String url = BASE_URL + "?q=" + query + "&key=" + API_KEY;
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                String jsonResponse = response.body().string();
                parseAndDisplayResults(jsonResponse, searchResult);
            } else {
                System.out.println("Error: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    private static void parseAndDisplayResults(String jsonResponse, ObservableList<Book> searchResult) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray books = jsonObject.getAsJsonArray("items");

        if (books != null) {
            for (int i = 0; i < books.size(); i++) {
                JsonObject bookJson = books.get(i).getAsJsonObject();
                Book book = parseBook(bookJson);
                searchResult.add(book);
                System.out.println("----------");
            }
        } else {
            System.out.println("No results found.");
        }
    }

    private static Book parseBook(JsonObject bookJson) {
        JsonObject volumeInfo = bookJson.getAsJsonObject("volumeInfo");

        String title = volumeInfo.get("title").getAsString();
        StringBuilder authors = new StringBuilder();
        if (volumeInfo.has("authors")) {
            JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
            for (int j = 0; j < authorsArray.size(); j++) {
                authors.append(authorsArray.get(j).getAsString()).append(" ,");
            }
        }
        String publishedYear = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "Unknown";
        int pageCount = volumeInfo.has("pageCount") ? volumeInfo.get("pageCount").getAsInt() : 0;
        String language = volumeInfo.has("language") ? volumeInfo.get("language").getAsString() : "Unknown";
        String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description available";
        String coverImageUrl = null;
        if (volumeInfo.has("imageLinks")) {
            JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
            coverImageUrl = imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString() : null;
        }

        BigDecimal rating = volumeInfo.has("averageRating") ?
                BigDecimal.valueOf(volumeInfo.get("averageRating").getAsDouble()) : BigDecimal.ZERO;
        String canonicalVolumeLink = volumeInfo.has("canonicalVolumeLink") ? volumeInfo.get("canonicalVolumeLink").getAsString() : null;

        //String title, String authors, String publishedYear, int pageCount, String language,
        //      String description, BigDecimal rating, String coverImage, String canonicalVolumeLink
        Book searchedBook = new Book(title, authors.toString(), publishedYear, pageCount, language, description, rating, coverImageUrl, canonicalVolumeLink);

        BookDataService.addBook(searchedBook);
        return searchedBook;
    }

}
