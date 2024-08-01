package onlinemediastore;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
	private ArrayList<Media> items;
    private int itemCount;

    public Order(int maxSize) {
        this.items = new ArrayList<>();
        this.itemCount = 0;
    }

    public void addItem(Media item) {
        items.add(item);
        itemCount++;
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Media item : items) {
            total += item.getCost();
        }
        return total;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Media item : items) {
            builder.append(item.getTitle()).append(": $").append(item.getCost()).append("\n");
        }
        return builder.toString();
    }
}
