package edu.lms.Models;

import java.util.List;

public class Book {
    private String title;
    private List<String> authors;
    private String description;

    public Book(String title, List<String> authors, String description) {
        this.title = title;
        this.authors = authors;
        this.description = description;
    }

    public void displayInfo() {
        System.out.println("Title: " + title);
        if (authors != null && !authors.isEmpty()) {
            System.out.print("Authors: ");
            for (int i = 0; i < authors.size(); i++) {
                System.out.print(authors.get(i));
                if (i < authors.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

