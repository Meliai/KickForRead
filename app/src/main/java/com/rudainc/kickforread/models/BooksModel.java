package com.rudainc.kickforread.models;

public class BooksModel {
    private int id;
    private String title;
    private long start_date;
    private String category;
    private int label;

    public BooksModel(int id, String title, long start_date, String category, int label) {
        this.id = id;
        this.title = title;
        this.start_date = start_date;
        this.category = category;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getStart_date() {
        return start_date;
    }

    public String getCategory() {
        return category;
    }

    public int getLabel() {
        return label;
    }
}
