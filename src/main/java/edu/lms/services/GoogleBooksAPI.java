package edu.lms.services;

import edu.lms.models.book.Book;
import edu.lms.services.database.BookDataService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleBooksAPI {
    private static final String API_KEY = System.getenv("GG_BOOKS_API_KEY");
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
                book.displayInfo();
                System.out.println("----------");
            }
        } else {
            System.out.println("No results found.");
        }
    }

    private Book parseBook(JsonObject bookJson) {
        JsonObject volumeInfo = bookJson.getAsJsonObject("volumeInfo");

        String title = volumeInfo.get("title").getAsString();
        List<String> authors = new ArrayList<>();
        String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description available";

        if (volumeInfo.has("authors")) {
            JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
            for (int j = 0; j < authorsArray.size(); j++) {
                authors.add(authorsArray.get(j).getAsString());
            }
        }

        String publishedYear = volumeInfo.has("publishedYear") ? volumeInfo.get("publishedYear").getAsString() : "Unknown";
        String coverImageUrl = null;
        if (volumeInfo.has("imageLinks")) {
            JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
            coverImageUrl = imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString() : null;
        }

        Book searchedBook = new Book(title, authors, description, publishedYear, coverImageUrl);
        BookDataService.addBook(searchedBook);
        return searchedBook;
    }


    public static void main(String[] args) {
        GoogleBooksAPI googleBooksAPI = new GoogleBooksAPI();
        String searchQuery = "algorithm";
        googleBooksAPI.searchBooks(searchQuery);
    }
}
