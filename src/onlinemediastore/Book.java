package onlinemediastore;

import java.util.List;

public class Book extends Media {
    private List<String> authors;

    public Book(int id, String title, String category, double cost, List<String> authors) {
        super(id, title, category, cost);
        this.authors = authors;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", cost=" + cost +
                ", authors=" + authors +
                '}';
    }
}
