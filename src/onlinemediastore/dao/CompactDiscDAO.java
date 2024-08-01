package onlinemediastore.dao;

import onlinemediastore.CompactDisc;
import onlinemediastore.DataSourceManager;
import onlinemediastore.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompactDiscDAO {
    public void insert(CompactDisc cd) throws SQLException {
        String sql = "INSERT INTO compact_discs (id, title, category, cost, artist) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cd.getId());
            stmt.setString(2, cd.getTitle());
            stmt.setString(3, cd.getCategory());
            stmt.setDouble(4, cd.getCost());
            stmt.setString(5, cd.getArtist());
            stmt.executeUpdate();
        }
    }

    public void update(CompactDisc cd) throws SQLException {
        String sql = "UPDATE compact_discs SET title=?, category=?, cost=?, artist=? WHERE id=?";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cd.getTitle());
            stmt.setString(2, cd.getCategory());
            stmt.setDouble(3, cd.getCost());
            stmt.setString(4, cd.getArtist());
            stmt.setInt(5, cd.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM compact_discs WHERE id=?";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public CompactDisc get(int id) throws SQLException {
        String sql = "SELECT * FROM compact_discs WHERE id=?";
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("title");
                    String category = rs.getString("category");
                    double cost = rs.getDouble("cost");
                    String artist = rs.getString("artist");
                    CompactDisc cd = new CompactDisc(id, title, category, cost, artist);
                    return cd;
                }
            }
        }
        return null;
    }

    public List<CompactDisc> getAll() throws SQLException {
        String sql = "SELECT * FROM compact_discs";
        List<CompactDisc> cds = new ArrayList<>();
        try (Connection conn = DataSourceManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String category = rs.getString("category");
                double cost = rs.getDouble("cost");
                String artist = rs.getString("artist");
                CompactDisc cd = new CompactDisc(id, title, category, cost, artist);
                cds.add(cd);
            }
        }
        return cds;
    }
}
