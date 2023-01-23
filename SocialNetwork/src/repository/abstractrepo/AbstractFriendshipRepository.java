package repository.abstractrepo;

import domain.Friendship;
import exceptions.NetworkException;
import repository.Repository;

import java.util.*;

public abstract class AbstractFriendshipRepository implements Repository<Friendship> {
    protected final HashMap<Integer, List<Friendship>> network;

    /***
     * Constructor with the specified filepath to remember the network
     * between users.
     */
    public AbstractFriendshipRepository() {
        this.network = new HashMap<>();
    }

    /***
     * This function creates a friendship between 2 users
     * and adds them in the network, if they aren't already.
     * @throws NetworkException
     */
    public void add(Friendship friendship) throws NetworkException {
        int id1 = friendship.getFrom();
        int id2 = friendship.getTo();
        if (!network.containsKey(id1))
            addUser(id1);
        if (!network.containsKey(id2))
            addUser(id2);
        if (friendshipExists(id1, id2))
            throw new NetworkException("Friendship already exists!");
        network.get(id1).add(friendship);
        network.get(id2).add(new Friendship(id2, id1, friendship.getTime()));
        save();
    }

    /***
     * Writes all the data to a specified file.
     */
    public abstract void save();

    /***
     * Reades all the data from the specified file.
     */
    public abstract void read();
    /***
     * Checks if the friendship between 2 users exists.
     * @param id1
     * @param id2
     * @return
     */
    protected boolean friendshipExists(int id1, int id2) {
        boolean exists = false;
        for (Friendship identification : network.get(id1))
            if (identification.getTo() == id2) {
                exists = true;
                break;
            }
        return exists;
    }

    /***
     *  Adds a new user to network.
     * @param id1
     */
    public void addUser(int id1) {
        network.put(id1, new LinkedList<Friendship>());
        save();
    }

    /***
     * Removes a friendship between 2 users.
     * @param id1
     * @param id2
     * @throws NetworkException
     */
    public void removeFriend(int id1, int id2) throws NetworkException {
        if (!network.containsKey(id1) || !network.containsKey(id2)
                || !friendshipExists(id1, id2))
            throw new NetworkException("The friendship doesn't exist!");
        network.get(id1).removeIf(x -> x.getTo() == id2);
        network.get(id2).removeIf(x -> x.getTo() == id1);
        save();
    }

    /***
     * Returns the number of communities.
     * @return
     */
    public int numberOfCommunities() {
        int numberOfCommunities = 0;
        boolean[] visited = new boolean[network.size()];
        Map<Integer, Integer> idToPos = new HashMap<>();
        int pos = -1;
        //fiecare key are legat pozitia pt visited.
        for (Map.Entry<Integer, List<Friendship>> set : network.entrySet()) {
            pos++;
            idToPos.put(set.getKey(), pos);
        }
        for (Map.Entry<Integer, Integer> set : idToPos.entrySet()) {
            if (!visited[set.getValue()]) {
                numberOfCommunities++;
                DFS(set.getKey(), idToPos, visited);
            }
        }
        return numberOfCommunities;
    }

    /***
     * DFS to find the connected component.
     * @param key
     * @param idToPos
     * @param visited
     */
    private void DFS(int key, Map<Integer, Integer> idToPos, boolean[] visited) {
        visited[idToPos.get(key)] = true;
        for (Friendship friendKey : network.get(key)) {
            if (!visited[idToPos.get(friendKey.getTo())])
                DFS(friendKey.getTo(), idToPos, visited);
        }
    }

    /***
     * Returns the list of the user's id in the biggest community.
     * @param ids
     */
    public void biggestCommunity(List<Integer> ids) {
        boolean[] visited = new boolean[network.size()];
        HashMap<Integer, Integer> idToPos = new HashMap<>();
        int pos = -1;
        //fiecare key are legat pozitia pt visited.
        for (Map.Entry<Integer, List<Friendship>> set : network.entrySet()) {
            pos++;
            idToPos.put(set.getKey(), pos);
        }

        List<Integer> tempIds = new ArrayList<>();
        for (Map.Entry<Integer, Integer> set : idToPos.entrySet()) {
            if (!visited[set.getValue()]) {
                tempIds.clear();
                DFS(set.getKey(), idToPos, visited, tempIds);
                if (tempIds.size() > ids.size()) {
                    ids.clear();
                    ids.addAll(tempIds);
                }
            }
        }
    }

    /***
     * DFS to find the connected component.
     * @param key
     * @param idToPos
     * @param visited
     * @param tempIds
     */
    private void DFS(Integer key, HashMap<Integer, Integer> idToPos, boolean[] visited, List<Integer> tempIds) {
        visited[idToPos.get(key)] = true;
        tempIds.add(key);
        for (Friendship friendKey : network.get(key)) {
            if (!visited[idToPos.get(friendKey.getTo())]) {
                DFS(friendKey.getTo(), idToPos, visited, tempIds);
            }
        }
    }

    public void remove(int id) {
        for (Map.Entry<Integer, List<Friendship>> set : network.entrySet()) {
            network.get(set.getKey()).removeIf(x -> x.getTo() == id);
        }
        network.remove(id);
        save();
    }
}
