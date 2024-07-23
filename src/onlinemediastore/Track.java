package onlinemediastore;

import java.io.Serializable;

public class Track extends Media implements Playable, Comparable<Track>, Serializable {
    private int length;

    public Track(int id, String title, int length) {
        super(id, title, "Track", 0.0);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", length=" + length +
                '}';
    }

    @Override
    public void play() throws PlayerException {
        if (length == 0) {
            throw new PlayerException("Cannot play the track: " + title + ". Length is 0.");
        } else {
            System.out.println("Playing Track: " + title);
            System.out.println("Length: " + length + " seconds");
        }
    }

    @Override
    public int compareTo(Track other) {
        return Integer.compare(this.length, other.length);
    }
}
