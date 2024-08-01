package onlinemediastore.dao;

import onlinemediastore.Book;
import onlinemediastore.DataSourceManager;
import onlinemediastore.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public void insert(Book book) throws SQLException {
        String sql = "INSERT INTO books (id, title, category, cost, authors) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getCategory());
            stmt.setDouble(4, book.getCost());
            stmt.setString(5, String.join(",", book.getAuthors()));
            stmt.executeUpdate();
        }
    }

    public void update(Book book) throws SQLException {
        String sql = "UPDATE books SET title=?, category=?, cost=?, authors=? WHERE id=?";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getCategory());
            stmt.setDouble(3, book.getCost());
            stmt.setString(4, String.join(",", book.getAuthors()));
            stmt.setInt(5, book.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id=?";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Book get(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id=?";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("title");
                    String category = rs.getString("category");
                    double cost = rs.getDouble("cost");
                    String authorsStr = rs.getString("authors");
                    List<String> authorsList = List.of(authorsStr.split(","));
                    return new Book(id, title, category, cost, authorsList);
                }
            }
        }
        return null;
    }

    public List<Book> getAll() throws SQLException {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String category = rs.getString("category");
                double cost = rs.getDouble("cost");
                String authorsStr = rs.getString("authors");
                List<String> authorsList = List.of(authorsStr.split(","));
                books.add(new Book(id, title, category, cost, authorsList));
            }
        }
        return books;
    }
}
