package com.rudainc.kickforread.models;

public class BooksModel {

    private String title;
    private String author;
    private String category;
    private String label;
    private String start_date;
    private String isFinished;

    public BooksModel(String title, String author, String category, String label, String start_date, String isFinished) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.label = label;
        this.start_date = start_date;
        this.isFinished = isFinished;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getLabel() {
        return label;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getIsFinished() {
        return isFinished;
    }
}
