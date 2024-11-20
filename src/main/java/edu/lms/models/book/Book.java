package edu.lms.models.book;


import java.math.BigDecimal;

public class Book {
    private int bookId;
    private String title;
    private String authors;
    private String publishedYear;
    private int pageCount;
    private String categories;
    private String language;
    private String description;
    private BigDecimal rating;
    private BigDecimal price;
    private int totalCopies;
    private int availableCopies;
    private String coverImage;

    private String canonicalVolumeLink;

    public Book() {};

    public Book(String title, String authors, String publishedYear, int pageCount, String categories, String language,
                String description, BigDecimal rating, BigDecimal price, String coverImage, String canonicalVolumeLink) {
        this.title = title;
        this.authors = authors;
        this.publishedYear = publishedYear;
        this.pageCount = pageCount;
        this.categories = categories;
        this.language = language;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.coverImage = coverImage;
        this.canonicalVolumeLink = canonicalVolumeLink;
    }

    public Book(int bookId, String title, String authors, String publishedYear, int pageCount, String language,
                String description, BigDecimal rating, BigDecimal price, int totalCopies, int availableCopies, String coverImage, String canonicalVolumeLink) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.publishedYear = publishedYear;
        this.pageCount = pageCount;
        this.language = language;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.coverImage = coverImage;
        this.canonicalVolumeLink = canonicalVolumeLink;
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

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getCanonicalVolumeLink() {
        return canonicalVolumeLink;
    }

    public void setCanonicalVolumeLink(String canonicalVolumeLink) {
        this.canonicalVolumeLink = canonicalVolumeLink;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}

