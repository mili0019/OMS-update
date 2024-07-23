package onlinemediastore.dao;

import onlinemediastore.DatabaseConnection;
import onlinemediastore.Track;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO implements GenericDAO<Track> {

    @Override
    public void insert(Track track) throws SQLException {
        String sql = "INSERT INTO tracks (id, title, length) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, track.getId());
            pstmt.setString(2, track.getTitle());
            pstmt.setInt(3, track.getLength());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Track findById(int id) throws SQLException {
        String sql = "SELECT * FROM tracks WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("title");
                    int length = rs.getInt("length");
                    return new Track(id, title, length);
                }
            }
        }
        return null;
    }

    @Override
    public List<Track> findAll() throws SQLException {
        String sql = "SELECT * FROM tracks";
        List<Track> tracks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int length = rs.getInt("length");
                tracks.add(new Track(id, title, length));
            }
        }
        return tracks;
    }

    @Override
    public void update(Track track) throws SQLException {
        String sql = "UPDATE tracks SET title = ?, length = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, track.getTitle());
            pstmt.setInt(2, track.getLength());
            pstmt.setInt(3, track.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM tracks WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
