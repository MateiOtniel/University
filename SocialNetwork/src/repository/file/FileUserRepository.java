package repository.file;

import domain.User;
import repository.abstractrepo.AbstractUserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUserRepository extends AbstractUserRepository {

    private final String filepath;
    /***
     * Constructor that sets the filepath to memorize info.
     * @param filepath
     */
    public FileUserRepository(String filepath) {
        super();
        this.filepath = filepath;
        read();
    }
    /***
     * This function reads all the data from file and populate the Vector with users.
     */

    @Override
    public void read() {
        Path path = Paths.get(filepath);
        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(path);
            for (String line : allLines) {
                String[] info = line.split(",");
                users.add(new User(Integer.parseInt(info[0]), info[3], info[1], info[2], info[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * This function writes all data to the specified file.
     */
    @Override
    public void save() {
        Path path = Paths.get(filepath);
        StringBuilder data = new StringBuilder();
        for (User user : users) {
            data.append(user.getId()).append(",").append(user.getFirstName()).append(",")
                    .append(user.getLastName()).append(",").append(user.getEmail()).append(",")
                    .append(user.getPassword()).append("\n");
        }
        try {
            Files.writeString(path, data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
