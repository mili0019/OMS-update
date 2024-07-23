package onlinemediastore;

import java.util.List;

public class CompactDisc extends Media implements Playable {
    private String artist;
    private Library<Track> trackLibrary;

    public CompactDisc(int id, String title, String category, double cost, String artist) {
        super(id, title, category, cost);
        this.artist = artist;
        this.trackLibrary = new Library<>();
    }

    public String getArtist() {
        return artist;
    }

    public List<Track> getTracks() {
        return trackLibrary.getItems();
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void addTrack(Track track) {
        trackLibrary.addItem(track);
    }

    public void removeTrack(Track track) {
        trackLibrary.removeItem(track);
    }

    @Override
    public String toString() {
        return "CompactDisc{" +
                "id=" + id +
                ", artist='" + artist + '\'' +
                ", tracks=" + trackLibrary.getItems() +
                "} " + super.toString();
    }

    @Override
    public void play() throws PlayerException {
        System.out.println("Playing CD: " + getTitle());
        System.out.println("Artist: " + artist);
        for (Track track : trackLibrary.getItems()) {
            try {
                track.play();
            } catch (PlayerException e) {
                System.out.println("Error playing track: " + e.getMessage());
            }
        }
    }
}
