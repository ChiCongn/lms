package edu.lms.models.book;


import java.util.List;

public class Book {
    private int ID;
    private String title;
    private List<String> authors;
    private String description;
    private String publishedYear;
    private String coverImage;
    private int maxBorrowDays;
    private String author;

    public Book() {};

    public Book(int ID, String title, List<String> authors, String description, String publishedYear, String coverImage) {
        this.ID = ID;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.publishedYear = publishedYear;
        this.coverImage = coverImage;
    }

    public Book(String title, List<String> authors, String description, String publishedYear, String coverImage) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.publishedYear = publishedYear;
        this.coverImage = coverImage;
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getMaxBorrowDays() {
        return maxBorrowDays;
    }

    public void setMaxBorrowDays(int maxBorrowDays) {
        this.maxBorrowDays = maxBorrowDays;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

