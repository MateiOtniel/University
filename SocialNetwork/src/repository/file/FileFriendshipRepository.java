package repository.file;

import domain.Friendship;
import repository.abstractrepo.AbstractFriendshipRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class FileFriendshipRepository extends AbstractFriendshipRepository {

    private final String filepath;
    /***
     * Constructor with the specified filepath to remember the network
     * between users.
     * @param filepath
     */
    public FileFriendshipRepository(String filepath) {
        super();
        this.filepath = filepath;
        this.read();
    }

    /***
     * Writes all the data to a specified file.
     */
    @Override
    public void save() {
        Path path = Paths.get(filepath);
        StringBuilder data = new StringBuilder();
        for (Map.Entry<Integer, List<Friendship>> set : network.entrySet()) {
            data.append(set.getKey()).append(";");
            for (Friendship friendship : set.getValue())
                data.append(friendship.toString()).append('~');
            if(!set.getValue().isEmpty())
                data = new StringBuilder(data.substring(0, data.length() - 1));
            data.append("\n");
        }
        try {
            Files.writeString(path, data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Reades all the data from the specified file.
     */
    @Override
    public void read() {
        Path path = Paths.get(filepath);
        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(path);
            for (String line : allLines) {
                String[] users = line.split(";");
                Integer mainUser = Integer.parseInt(users[0]);
                network.put(mainUser, new LinkedList<>());
                if(users.length > 1) {
                    String[] friendships = users[1].split("~");
                    for (String friendship : friendships) {
                        String[] data = friendship.split(",");
                        network.get(mainUser).add(new Friendship(mainUser, Integer.parseInt(data[0]), LocalDateTime.parse(data[1])));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
