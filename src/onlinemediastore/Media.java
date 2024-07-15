package onlinemediastore;

import java.io.Serializable;

public abstract class Media implements Serializable{
	protected String title;
    protected String category;
    protected double cost;

    public Media(String title, String category, double cost) {
        this.title = title;
        this.category = category;
        this.cost = cost;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getCost() {
        return cost;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Media{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", cost=" + cost +
                '}';
    }
}
