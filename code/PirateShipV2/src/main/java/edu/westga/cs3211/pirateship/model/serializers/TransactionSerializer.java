package edu.westga.cs3211.pirateship.model.serializers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.StockType;
import edu.westga.cs3211.pirateship.model.Transaction;
import edu.westga.cs3211.pirateship.model.User;

/**
 * Serializer for Transaction history.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class TransactionSerializer {
	/**
	 * Serializes the transaction history of the given CargoHull to a file.
	 * 
	 * @param ship the ship whose transaction history to serialize
	 * @param file the file to serialize to
	 * @throws IOException if an I/O error occurs
	 */
	public static void saveTransactionHistory(Ship ship, String file) {
		String data = writeAll(ship.getCargoHull().getTransactionHistory());
		try (FileWriter out = new FileWriter(file)) {
			out.write(data);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Loads the transaction history from a file.
	 * 
	 * @param file the file to load from
	 * @return the loaded list of Transactions
	 * @throws IOException if an I/O error occurs
	 */
	public static List<Transaction> loadTransactionHistory(String file) {
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(Paths.get(file));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		if (lines.isEmpty()) {
			return null;
		}
		return parseAll(lines);
	}

	private static String writeAll(List<Transaction> list) {
		StringBuilder builder = new StringBuilder();

		for (Transaction transaction : list) {
			if (builder.length() > 0) {
				builder.append("\n");
			}
			builder.append(writeTransaction(transaction));
		}

		return builder.toString();
	}

	private static String writeTransaction(Transaction transaction) {

		StringBuilder block = new StringBuilder();

		block.append("BEGIN-TRANSACTION\n");
		block.append("DATE ").append(transaction.getDate().getTime()).append("\n");
		block.append("STOCKNAME ").append(sanitize(transaction.getStockName())).append("\n");
		block.append("QUANTITY ").append(transaction.getQuantity()).append("\n");

		appendCrew(block, transaction.getCrewmember());
		block.append("STOCKTYPE ").append(transaction.getStockType().name()).append("\n");
		appendQualities(block, transaction.getSpecialQualities());

		block.append("END-TRANSACTION");

		return block.toString();
	}

	private static void appendCrew(StringBuilder block, User user) {
		block.append("CREWMEMBER ").append(sanitize(user.getName())).append(" ").append(sanitize(user.getUsername()))
				.append(" ").append(sanitize(user.getPassword())).append(" ").append(user.getRole().name()).append("\n");
	}

	private static void appendQualities(StringBuilder block, Collection<SpecialQualities> qualities) {
		StringBuilder list = new StringBuilder();

		for (SpecialQualities quality : qualities) {
			if (list.length() > 0) {
				list.append(",");
			}
			list.append(quality.name());
		}

		block.append("QUALITIES ").append(sanitize(list.toString())).append("\n");
	}

	private static List<Transaction> parseAll(List<String> lines) {

		List<Transaction> result = new ArrayList<>();

		boolean reading = false;
		long millis = 0;
		String name = "";
		int qty = 0;
		User crew = null;
		StockType stockType = null;
		ArrayList<SpecialQualities> qualities = new ArrayList<>();

		for (String raw : lines) {

			String line = raw.trim();

			if (line.equals("BEGIN-TRANSACTION")) {
				reading = true;
				qualities = new ArrayList<>();
				continue;
			}

			if (line.equals("END-TRANSACTION")) {
				Date date = new Date(millis);
				Transaction transaction = new Transaction(date, name, qty, crew, stockType, qualities);
				result.add(transaction);
				reading = false;
				continue;
			}

			if (!reading) {
				continue;
			}

			if (line.startsWith("DATE")) {
				String[] parts = line.split(" ");
				millis = Long.parseLong(parts[1]);
			} else if (line.startsWith("STOCKNAME")) {
				String encoded = line.split(" ", 2)[1];
				name = unsanitize(encoded);
			} else if (line.startsWith("QUANTITY")) {
				qty = Integer.parseInt(line.split(" ")[1]);
			} else if (line.startsWith("CREWMEMBER")) {
				crew = parseUser(line);
			} else if (line.startsWith("STOCKTYPE")) {
				String[] parts = line.split(" ");
				stockType = StockType.valueOf(parts[1]);
			} else if (line.startsWith("QUALITIES")) {
				qualities = parseQualitiesList(line);
			}
		}

		return result;
	}

	private static User parseUser(String line) {
		String[] parts = line.split(" ");
		String nm = unsanitize(parts[1]);
		String un = unsanitize(parts[2]);
		String pw = unsanitize(parts[3]);
		Roles role = Roles.valueOf(parts[4]);
		return new User(nm, un, pw, role);
	}

	private static ArrayList<SpecialQualities> parseQualitiesList(String line) {
		ArrayList<SpecialQualities> list = new ArrayList<>();

		String encoded = line.split(" ", 2)[1];
		String decoded = unsanitize(encoded);

		if (!decoded.isEmpty()) {
			String[] parts = decoded.split(",");
			for (String part : parts) {
				if (!part.trim().isEmpty()) {
					list.add(SpecialQualities.valueOf(part.trim()));
				}
			}
		}
		return list;
	}

	private static String sanitize(String text) {
		return text.replace(" ", "_");
	}

	private static String unsanitize(String text) {
		return text.replace("_", " ");
	}
}
