package database;

import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class HibernateConfiguration {
    private Configuration configuration;

    public HibernateConfiguration(){
        configuration = new Configuration();
        configuration.addResource("hibernate.cfg.xml");

        try {
            List<String> credentials = readSettingsFile();
            configuration.setProperty("hibernate.connection.username", credentials.get(0));
            configuration.setProperty("hibernate.connection.password", credentials.get(1));
        } catch (IOException e) {
            System.err.println("Hibernate configuration error: " + e.getMessage());
        }
    }

    public Configuration getConfiguration() { return configuration; }

    private List<String> readSettingsFile() throws IOException {
        try {
            String path = Paths.get("").toAbsolutePath().normalize().toString() + "\\settings";
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new IOException("Failed to get db settings file: " + e.getMessage());
        }
    }
}
