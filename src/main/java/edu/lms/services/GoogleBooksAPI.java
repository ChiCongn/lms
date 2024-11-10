package edu.lms.services;

import edu.lms.models.book.Book;
import edu.lms.services.database.BookDataService;
import javafx.fxml.Initializable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GoogleBooksAPI {
    private static final String API_KEY = Config.GG_BOOKS_API_KEY;
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private OkHttpClient client;
    public GoogleBooksAPI() {
        client = new OkHttpClient();
    }

    public void searchBooks(String query) {
        String url = BASE_URL + "?q=" + query + "&key=" + API_KEY;
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                parseAndDisplayResults(jsonResponse);
            } else {
                System.out.println("Error: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseAndDisplayResults(String jsonResponse) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray books = jsonObject.getAsJsonArray("items");

        if (books != null) {
            for (int i = 0; i < books.size(); i++) {
                JsonObject bookJson = books.get(i).getAsJsonObject();
                Book book = parseBook(bookJson);
                System.out.println("----------");
            }
        } else {
            System.out.println("No results found.");
        }
    }

    private Book parseBook(JsonObject bookJson) {
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

        //int bookId, String title, String publishedYear, int pageCount, String language, String description, String coverImage
        Book searchedBook = new Book(title, authors.toString(), publishedYear, pageCount, language, description, coverImageUrl);
        BookDataService.addBook(searchedBook);
        return searchedBook;
    }


    public static void main(String[] args) {
        GoogleBooksAPI googleBooksAPI = new GoogleBooksAPI();
        String searchQuery = "data structure and algorithm";
        googleBooksAPI.searchBooks(searchQuery);
    }
}
