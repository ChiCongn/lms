package edu.lms.models.book;


import java.math.BigDecimal;
import java.util.List;

public class Book {
    private int bookId;
    private String title;
    private String authors;
    private String publishedYear;
    private int pageCount;
    private String language;
    private String description;
    private BigDecimal rating;
    private int totalCopies;
    private int copiesAvailable;
    private String coverImage;

    public Book() {};

    public Book(String title, String authors, String publishedYear, int pageCount,
                String language, String description, String coverImage) {
        this.title = title;
        this.authors = authors;
        this.publishedYear = publishedYear;
        this.pageCount = pageCount;
        this.language = language;
        this.description = description;
        this.coverImage = coverImage;
    }

    public Book(int bookId, String title, String authors, String publishedYear, int pageCount, String language,
                String description, BigDecimal rating, int totalCopies, int copiesAvailable, String coverImage) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.publishedYear = publishedYear;
        this.pageCount = pageCount;
        this.language = language;
        this.description = description;
        this.rating = rating;
        this.totalCopies = totalCopies;
        this.copiesAvailable = copiesAvailable;
        this.coverImage = coverImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBookId() {
        return bookId;
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

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

}

