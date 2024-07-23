package onlinemediastore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class DataFromProperties {
    private static final String FILE_NAME = "media.properties";

    public static Properties readProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(FILE_NAME)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void writeProperties(String key, String value) {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME, true)) {
            Properties properties = new Properties();
            properties.load(new FileInputStream(FILE_NAME));
            properties.setProperty(key, value);
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DigitalVideoDisc createDigitalVideoDisc(Properties properties, String prefix) {
        int id = Integer.parseInt(properties.getProperty(prefix + ".id"));
        String title = properties.getProperty(prefix + ".title");
        String category = properties.getProperty(prefix + ".category");
        double cost = Double.parseDouble(properties.getProperty(prefix + ".cost"));
        String director = properties.getProperty(prefix + ".director");
        int length = Integer.parseInt(properties.getProperty(prefix + ".length"));
        DigitalVideoDisc dvd = new DigitalVideoDisc(id, title, category, cost, director, length);
        dvd.setId(id);
        return dvd;
    }

    public static Book createBook(Properties properties, String prefix) {
        int id = Integer.parseInt(properties.getProperty(prefix + ".id"));
        String title = properties.getProperty(prefix + ".title");
        String category = properties.getProperty(prefix + ".category");
        double cost = Double.parseDouble(properties.getProperty(prefix + ".cost"));
        String authorsStr = properties.getProperty(prefix + ".authors");
        ArrayList<String> authors = new ArrayList<>(Arrays.asList(authorsStr.split(",")));
        Book book = new Book(id, title, category, cost, authors);
        book.setId(id);
        return book;
    }

    public static CompactDisc createCompactDisc(Properties properties, String prefix) {
        int id = Integer.parseInt(properties.getProperty(prefix + ".id"));
        String title = properties.getProperty(prefix + ".title");
        String category = properties.getProperty(prefix + ".category");
        double cost = Double.parseDouble(properties.getProperty(prefix + ".cost"));
        String artist = properties.getProperty(prefix + ".artist");
        CompactDisc cd = new CompactDisc(id, title, category, cost, artist);
        cd.setId(id);

        int trackCount = Integer.parseInt(properties.getProperty(prefix + ".track.count"));
        for (int i = 1; i <= trackCount; i++) {
            Track track = createTrack(properties, prefix + ".track" + i);
            cd.addTrack(track);
        }
        List<Track> tracks = cd.getTracks();
        Collections.sort(tracks);
        return cd;
    }

    public static Track createTrack(Properties properties, String prefix) {
        int id = Integer.parseInt(properties.getProperty(prefix + ".id"));
        String trackTitle = properties.getProperty(prefix + ".title");
        int trackLength = Integer.parseInt(properties.getProperty(prefix + ".length"));
        Track track = new Track(id, trackTitle, trackLength);
        track.setId(id);
        return track;
    }
}
