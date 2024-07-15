package onlinemediastore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library<T extends Media> implements Serializable {
    private List<T> items;

    public Library() {
        this.items = new ArrayList<>();
    }

    public void addItem(T item) {
        this.items.add(item);
    }

    public void removeItem(T item) {
        this.items.remove(item);
    }

    public List<T> getItems() {
        return items;
    }

    public List<T> getPlayableItems() {
        return items.stream()
                .filter(item -> item instanceof Playable)
                .collect(Collectors.toList());
    }

    public List<T> getNonPlayableItems() {
        return items.stream()
                .filter(item -> !(item instanceof Playable))
                .collect(Collectors.toList());
    }

    public List<T> getItemsByCategory(String category) {
        return items.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<String> getAvailableCategories(boolean playable) {
        return items.stream()
                .filter(item -> playable ? item instanceof Playable : !(item instanceof Playable))
                .map(Media::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Library{" +
                "items=" + items +
                '}';
    }
}
