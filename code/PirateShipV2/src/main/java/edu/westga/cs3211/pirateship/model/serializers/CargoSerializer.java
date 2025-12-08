package edu.westga.cs3211.pirateship.model.serializers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import edu.westga.cs3211.pirateship.model.CargoHull;
import edu.westga.cs3211.pirateship.model.Conditions;
import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.SpecialQualities;
import edu.westga.cs3211.pirateship.model.Stock;
import edu.westga.cs3211.pirateship.model.StockType;

/**
 * Serializer for CargoHull data.
 * 
 * @author Justin Smith
 * @version Fall 2025
 */
public class CargoSerializer {
    /**
	 * Serializes the given CargoHull to a file.
	 * 
	 * @param hull the CargoHull to serialize
	 * @param file the file to serialize to
	 * @throws IOException if an I/O error occurs
	 */
    public static void saveCargo(CargoHull hull, String file) throws IOException {
        String data = serializeHull(hull);
        try (FileWriter out = new FileWriter(file)) {
            out.write(data);
        }
    }
    
    /**
	 * Loads a CargoHull from a file.
	 * 
	 * @return the loaded CargoHull or null if the file is empty
	 * @throws IOException if an I/O error occurs
	 */
    public static CargoHull loadCargo() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(ShipSerializer.CARGO_TXT_FILE));
        if (lines.isEmpty()) {
			return null;
		}
        int capacity = parseCapacity(lines);

        CargoHull hull = new CargoHull(capacity);
        parseContainers(lines, hull);
        updateContainerIdCounter(hull);

        return hull;
    }

    private static String serializeHull(CargoHull hull) {
        StringBuilder builder = new StringBuilder();
        builder.append("CARGOHULL ").append(hull.getCapacity());

        for (Container container : hull.getContainers()) {
            builder.append("\n");
            builder.append(writeContainer(container));
        }
        return builder.toString();
    }

    private static String writeContainer(Container container) {
        StringBuilder block = new StringBuilder();
        block.append("BEGIN-CONTAINER\n");

        block.append("CONTAINER ")
             .append(container.getContainerID()).append(" ")
             .append(container.getSize()).append(" ")
             .append(container.getStockType().name()).append(" ")
             .append(joinQualities(container.getSpecialQualities()))
             .append("\n");

        for (Stock stock : container.getStockItems()) {
            block.append(writeStock(stock)).append("\n");
        }

        block.append("END-CONTAINER");
        return block.toString();
    }

    private static String writeStock(Stock stock) {
        String name = sanitize(stock.getName());
        int qty = stock.getQuantity();
        int indivSize = stock.getTotalSize() / qty;
        String condition = stock.getCondition().name();
        String stockType = stock.getStockType().name();

        String expiration;
        if (stock.getExpirationDate() == null) {
            expiration = "null";
        } else {
            expiration = stock.getExpirationDate().toString();
        }

        String qualities = joinQualities(stock.getSpecialQualities());

        String line = "STOCK "
                + name + " "
                + qty + " "
                + indivSize + " "
                + condition + " "
                + expiration + " "
                + stockType + " "
                + qualities;

        return line;
    }

    private static int parseCapacity(List<String> lines) {
        String header = lines.get(0);
        String[] parts = header.split(" ");
        return Integer.parseInt(parts[1]);
    }

    private static void parseContainers(List<String> lines, CargoHull hull) {

        int id = 0;
        int size = 0;
        StockType stockType = StockType.OTHER;
        Collection<SpecialQualities> qualities = new ArrayList<>();
        Collection<Stock> stocks = new ArrayList<>();

        boolean reading = false;

        for (String raw : lines) {
            String line = raw.trim();

            if (line.equals("BEGIN-CONTAINER")) {
                reading = true;
                id = 0;
                size = 0;
                qualities = new ArrayList<>();
                stocks = new ArrayList<>();
                continue;
            }

            if (line.equals("END-CONTAINER")) {
                Container container = new Container(size, stocks, id, qualities, stockType);
                hull.addContainer(container);
                reading = false;
                continue;
            }

            if (!reading) {
                continue;
            }

            if (line.startsWith("CONTAINER")) {
                String[] parts = line.split(" ", 5);

                id = Integer.parseInt(parts[1]);
                size = Integer.parseInt(parts[2]);
                stockType = StockType.valueOf(parts[3]);

                if (parts.length > 4) {
                    qualities = parseQualities(parts[4]);
                } else {
                    qualities = new ArrayList<>();
                }
                continue;
            }

            if (line.startsWith("STOCK")) {
                Stock stock = parseStock(line);
                stocks.add(stock);
            }
        }
    }

    private static Stock parseStock(String line) {

        String[] parts = line.split(" ", 8);

        String name = unsanitize(parts[1]);
        int qty = Integer.parseInt(parts[2]);
        int indivSize = Integer.parseInt(parts[3]);
        Conditions condition = Conditions.valueOf(parts[4]);

        LocalDate expiration = null;
        String expText = parts[5];
        if (!expText.equals("null")) {
            expiration = LocalDate.parse(expText);
        }
        
        StockType stockType = StockType.valueOf(parts[6]);

        Collection<SpecialQualities> qualities;
        if (parts.length > 7) {
            qualities = parseQualities(parts[7]);
        } else {
            qualities = new ArrayList<>();
        }
        
        Stock stock;
        if (qualities.contains(SpecialQualities.PARISHABLE)) {
        	if (expiration == null) {
				throw new IllegalArgumentException("Parishable stock must have expiration date.");
			}
			stock = new Stock(name, qty, indivSize, qualities, condition, stockType, expiration);
		} else {
			stock = new Stock(name, qty, indivSize, qualities, condition, stockType);
		}

        return stock;
    }

    private static void updateContainerIdCounter(CargoHull hull) {
        Collection<Integer> ids = new ArrayList<>();
        for (Container container : hull.getContainers()) {
            ids.add(container.getContainerID());
        }
        Container.updateIdCounter(ids);
    }

    private static String joinQualities(Collection<SpecialQualities> qualities) {
        if (qualities == null || qualities.isEmpty()) {
            return "NONE";
        }

        StringBuilder builder = new StringBuilder();
        for (SpecialQualities sq : qualities) {
            if (builder.length() > 0) {
                builder.append(";");
            }
            builder.append(sq.name());
        }
        return builder.toString();
    }

    private static Collection<SpecialQualities> parseQualities(String text) {
        Collection<SpecialQualities> list = new ArrayList<>();

        if (text == null) {
            return list;
        }
        if (text.equals("NONE")) {
            return list;
        }

        String[] parts = text.split(";");
        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                list.add(SpecialQualities.valueOf(part.trim()));
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
