package onlinemediastore;

import java.util.ArrayList;

public class Book extends Media{
    private ArrayList<String> authors;

    public Book(String title, String category, double cost, ArrayList<String> authors) {
        super(title, category, cost);
        this.authors = authors;
    }

    // Getters
    public ArrayList<String> getAuthors() {
        return authors;
    }

    // Setters
    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", cost=" + cost +
                ", authors=" + authors +
                '}';
    }
}
