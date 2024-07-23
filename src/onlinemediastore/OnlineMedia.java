package onlinemediastore;

import onlinemediastore.dao.BookDAO;
import onlinemediastore.dao.CompactDiscDAO;
import onlinemediastore.dao.DigitalVideoDiscDAO;
import onlinemediastore.dao.TrackDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OnlineMedia {

	public static void main(String[] args) {

//		MemoryDaemon memoryDaemon = new MemoryDaemon();
//		Thread memoryThread = new Thread(memoryDaemon);
//		memoryThread.setDaemon(true);
//		memoryThread.start();

		BookDAO bookDAO = new BookDAO();
		CompactDiscDAO compactDiscDAO = new CompactDiscDAO();
		DigitalVideoDiscDAO digitalVideoDiscDAO = new DigitalVideoDiscDAO();
		TrackDAO trackDAO = new TrackDAO();

		Order order = new Order(10);
		Library<Media> mediaLibrary = new Library<>();

		try {

			DigitalVideoDisc dvd1 = new DigitalVideoDisc(3, "DVD 3", "Science Fiction", 15.99, "Director Name", 120);
//			digitalVideoDiscDAO.insert(dvd1);

			DigitalVideoDisc dvd2 = new DigitalVideoDisc(4, "DVD 4", "Horror", 54.99, "Director", 150);
//			digitalVideoDiscDAO.insert(dvd2);

			Book book1 = new Book(2, "Book 2", "Drama", 49.99, List.of("Author One", "Author Two", "Author three"));
//			bookDAO.insert(book1);

			CompactDisc cd1 = new CompactDisc(3, "CD 3", "Pop-Rock", 17.99, "Artist X");
//			compactDiscDAO.insert(cd1);

			Track newTrack = new Track(2, "Another Song", 400);
//			trackDAO.insert(newTrack);

			List<DigitalVideoDisc> dvds = digitalVideoDiscDAO.getAll();
			List<Book> books = bookDAO.getAll();
			List<CompactDisc> cds = compactDiscDAO.getAll();
			List<Track> tracks = trackDAO.findAll();

			System.out.println("\nDigital Video Discs:");
			dvds.forEach(System.out::println);

			System.out.println("\nBooks:");
			books.forEach(System.out::println);

			System.out.println("\nCompact Discs:");
			cds.forEach(System.out::println);

			System.out.println("\nTracks:");
			tracks.forEach(System.out::println);

			dvd1.setTitle("Updated DVD");
			digitalVideoDiscDAO.update(dvd1);

			compactDiscDAO.delete(1);

			dvds = digitalVideoDiscDAO.getAll();
			cds = compactDiscDAO.getAll();

			System.out.println("\nUpdated Digital Video Discs:");
			dvds.forEach(System.out::println);

			System.out.println("\nRemaining Compact Discs:");
			cds.forEach(System.out::println);

			mediaLibrary.addItem(dvd1);
			mediaLibrary.addItem(cd1);
			mediaLibrary.addItem(book1);
			System.out.println("\nPlaying media:");
			try {
				dvd1.play();
				cd1.play();
			} catch (PlayerException e) {
				System.out.println("Error playing media: " + e.getMessage());
			}

			order.addItem(dvd1);
			order.addItem(cd1);
			order.addItem(book1);
			System.out.println("\nOrder:");
			System.out.println(order);
			System.out.println("TOTAL: $" + order.calculateTotal() + "\n");

			OrderSaver.saveOrder(order, "order.ser");
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
			scanner.nextLine();
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

		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}

//		memoryDaemon.stop();
	}
}
