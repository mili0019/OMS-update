package onlinemediastore;

import java.util.Properties;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

public class OnlineMedia {

	public static void main(String[] args) {

//		MemoryDaemon memoryDaemon = new MemoryDaemon();
//		Thread memoryThread = new Thread(memoryDaemon);
//		memoryThread.setDaemon(true);
//		memoryThread.start();

		Properties properties = DataFromProperties.readProperties();

		Order order = new Order(10);

		Library<Media> mediaLibrary = new Library<>();

		try {
			DigitalVideoDisc dvd1 = DataFromProperties.createDigitalVideoDisc(properties, "dvd1");
			mediaLibrary.addItem(dvd1);
			dvd1.play();
			order.addItem(dvd1);

			DigitalVideoDisc dvd2 = DataFromProperties.createDigitalVideoDisc(properties, "dvd2");
			mediaLibrary.addItem(dvd2);
			dvd2.play();
			order.addItem(dvd2);

			Book book1 = DataFromProperties.createBook(properties, "book1");
			mediaLibrary.addItem(book1);
			order.addItem(book1);

			CompactDisc cd1 = DataFromProperties.createCompactDisc(properties, "cd1");
			mediaLibrary.addItem(cd1);
			cd1.play();
			order.addItem(cd1);

			Track newTrack = new Track("New Song", 300);
			System.out.println("\nAdd track:");
			cd1.addTrack(newTrack);
			for (Track track : cd1.getTracks()) {
				System.out.println(track);
			}
			System.out.println("\nRemove track:");
			if (!cd1.getTracks().isEmpty()) {
				cd1.removeTrack(cd1.getTracks().get(0));
			}
			for (Track track : cd1.getTracks()) {
				System.out.println(track);
			}

		} catch (PlayerException e) {
			System.out.println("Error playing media: " + e.getMessage());
		}

		System.out.println();
		System.out.println("Order:");
		System.out.println(order);
		System.out.println("TOTAL: $" + order.calculateTotal() + "\n");

		// Serialization
		OrderSaver.saveOrder(order, "order.ser");
		// Deserialization
		Order restoredOrder = OrderSaver.restoreOrder("order.ser");
		if (restoredOrder != null) {
			System.out.println("Restored Order:");
			System.out.println(restoredOrder);
		}

		Scanner scanner = new Scanner(System.in);
		System.out.println("Select media type to display:");
		System.out.println("1. Playable");
		System.out.println("2. Non-Playable");
		int choice = scanner.nextInt();

		List<Media> selectedItems;
		List<String> availableCategories;

		if (choice == 1) {
			selectedItems = mediaLibrary.getPlayableItems();
			availableCategories = mediaLibrary.getAvailableCategories(true);
		} else if (choice == 2) {
			selectedItems = mediaLibrary.getNonPlayableItems();
			availableCategories = mediaLibrary.getAvailableCategories(false);
		} else {
			System.out.println("Invalid choice.");
			return;
		}

		System.out.println("Available categories:");
		for (String category : availableCategories) {
			System.out.println(category);
		}

		System.out.println("Enter category to filter:");
		scanner.nextLine(); // Consumă newline-ul rămas
		String category = scanner.nextLine();

		List<Media> filteredItems = selectedItems.stream()
				.filter(media -> media.getCategory().equalsIgnoreCase(category))
				.collect(Collectors.toList());

		if (filteredItems.isEmpty()) {
			System.out.println("No media found in category \"" + category + "\".");
		} else {
			System.out.println("Media in category \"" + category + "\":");
			for (Media media : filteredItems) {
				System.out.println(media);
			}
		}

//		memoryDaemon.stop();
	}
}