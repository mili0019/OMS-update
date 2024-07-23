package onlinemediastore.dao;

import onlinemediastore.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MediaDAO {

    public void addMedia(Media media) {
        String sql = "INSERT INTO media (title, category, cost) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, media.getTitle());
            pstmt.setString(2, media.getCategory());
            pstmt.setDouble(3, media.getCost());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        media.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Media getMedia(int id) {
        String sql = "SELECT * FROM media WHERE id = ?";
        Media media = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                String category = rs.getString("category");
                double cost = rs.getDouble("cost");

                switch (category) {
                    case "Book":
                        media = new Book(id, title, category, cost, new ArrayList<>());
                        break;
                    case "Compact Disc":
                        media = new CompactDisc(id, title, category, cost, "");
                        break;
                    case "Digital Video Disc":
                        media = new DigitalVideoDisc(id, title, category, cost, "", 0);
                        break;
                    default:
                        media = new Media(id, title, category, cost) {};
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return media;
    }

    public List<Media> getAllMedia() {
        String sql = "SELECT * FROM media";
        List<Media> mediaList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String category = rs.getString("category");
                double cost = rs.getDouble("cost");

                switch (category) {
                    case "Book":
                        mediaList.add(new Book(id, title, category, cost, new ArrayList<>()));
                        break;
                    case "Compact Disc":
                        mediaList.add(new CompactDisc(id, title, category, cost, ""));
                        break;
                    case "Digital Video Disc":
                        mediaList.add(new DigitalVideoDisc(id, title, category, cost, "", 0));
                        break;
                    default:
                        mediaList.add(new Media(id, title, category, cost) {});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mediaList;
    }

    public void updateMedia(Media media) {
        String sql = "UPDATE media SET title = ?, category = ?, cost = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, media.getTitle());
            pstmt.setString(2, media.getCategory());
            pstmt.setDouble(3, media.getCost());
            pstmt.setInt(4, media.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMedia(int id) {
        String sql = "DELETE FROM media WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
