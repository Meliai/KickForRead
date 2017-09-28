package com.rudainc.kickforread.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BooksModel implements Parcelable{

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

    protected BooksModel(Parcel in) {
        title = in.readString();
        author = in.readString();
        category = in.readString();
        label = in.readString();
        start_date = in.readString();
        isFinished = in.readString();
    }

    public static final Creator<BooksModel> CREATOR = new Creator<BooksModel>() {
        @Override
        public BooksModel createFromParcel(Parcel in) {
            return new BooksModel(in);
        }

        @Override
        public BooksModel[] newArray(int size) {
            return new BooksModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(category);
        parcel.writeString(label);
        parcel.writeString(start_date);
        parcel.writeString(isFinished);
    }
}
