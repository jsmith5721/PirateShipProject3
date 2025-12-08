package edu.westga.cs3211.pirateship.model.serializers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.westga.cs3211.pirateship.model.Roles;
import edu.westga.cs3211.pirateship.model.Ship;
import edu.westga.cs3211.pirateship.model.User;

/**
 * Serializer for User objects.
 * @author Justin Smith
 * @version Fall 2025
 */
public class UserSerializer {
    /**
     * Save users.
     *
     * @param ship the ship
     * @param file the file to save to
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void saveUsers(Ship ship, String file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {

            for (User user : ship.getCrew()) {
                writer.println(
                        sanitize(user.getName()) + ","
                        + sanitize(user.getUsername()) + ","
                        + sanitize(user.getPassword()) + ","
                        + user.getRole().name()
                );
            }
        }
    }

    /**
     * Load users.
     *
     * @param ship the ship
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void loadUsers(Ship ship) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ShipSerializer.USERS_TXT_FILE))) {
			String line;
        	while ((line = reader.readLine()) != null) {
        		String[] userText = line.split(",", -1);
                String name = unsanitize(userText[0]);
                String username = unsanitize(userText[1]);
                String password = unsanitize(userText[2]);
                Roles role = Roles.valueOf(userText[3]);

                ship.addCrewMember(new User(name, username, password, role));
			}
		} catch (IOException ex) {
			throw ex;
		}
    }

    /**
     * Sanitize.
     *
     * @param text the text
     * @return the string
     */
    private static String sanitize(String text) {
        return text.replace(",", "%COMMA%");
    }

    /**
     * Unsanitize.
     *
     * @param text the text
     * @return the string
     */
    private static String unsanitize(String text) {
        return text.replace("%COMMA%", ",");
    }
}
