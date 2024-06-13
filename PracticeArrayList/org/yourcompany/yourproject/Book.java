package org.yourcompany.yourproject;

public class Book {
    private int id;
    private String name;
    private String author;
    private int publishedYear;

    public Book(int id, String name, String author, int publishedYear) 
    {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publishedYear = publishedYear;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }
}