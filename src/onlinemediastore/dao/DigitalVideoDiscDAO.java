package onlinemediastore.dao;

import onlinemediastore.DataSourceManager;
import onlinemediastore.DigitalVideoDisc;
import onlinemediastore.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DigitalVideoDiscDAO {
    public void insert(DigitalVideoDisc dvd) throws SQLException {
        String sql = "INSERT INTO digital_video_discs (id, title, category, cost, director, length) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dvd.getId());
            stmt.setString(2, dvd.getTitle());
            stmt.setString(3, dvd.getCategory());
            stmt.setDouble(4, dvd.getCost());
            stmt.setString(5, dvd.getDirector());
            stmt.setInt(6, dvd.getLength());
            stmt.executeUpdate();
        }
    }

    public void update(DigitalVideoDisc dvd) throws SQLException {
        String sql = "UPDATE digital_video_discs SET title=?, category=?, cost=?, director=?, length=? WHERE id=?";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dvd.getTitle());
            stmt.setString(2, dvd.getCategory());
            stmt.setDouble(3, dvd.getCost());
            stmt.setString(4, dvd.getDirector());
            stmt.setInt(5, dvd.getLength());
            stmt.setInt(6, dvd.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM digital_video_discs WHERE id=?";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public DigitalVideoDisc get(int id) throws SQLException {
        String sql = "SELECT * FROM digital_video_discs WHERE id=?";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("title");
                    String category = rs.getString("category");
                    double cost = rs.getDouble("cost");
                    String director = rs.getString("director");
                    int length = rs.getInt("length");
                    return new DigitalVideoDisc(id, title, category, cost, director, length);
                }
            }
        }
        return null;
    }

    public List<DigitalVideoDisc> getAll() throws SQLException {
        String sql = "SELECT * FROM digital_video_discs";
        List<DigitalVideoDisc> dvds = new ArrayList<>();
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String category = rs.getString("category");
                double cost = rs.getDouble("cost");
                String director = rs.getString("director");
                int length = rs.getInt("length");
                dvds.add(new DigitalVideoDisc(id, title, category, cost, director, length));
            }
        }
        return dvds;
    }
}
