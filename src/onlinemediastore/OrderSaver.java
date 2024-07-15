package onlinemediastore;

import java.io.*;

public class OrderSaver {
	public static void saveOrder(Order order, String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(order);
            System.out.println("Order saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving order: " + e.getMessage());
        }
    }

    public static Order restoreOrder(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            Order order = (Order) inputStream.readObject();
            System.out.println("Order restored successfully.");
            return order;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error restoring order: " + e.getMessage());
            return null;
        }
    }
}
