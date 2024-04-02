package Java;

import Java.restclient.UsersClient;
import model.Test;

public class StartRestClient {

    private final static UsersClient usersClient = new UsersClient();

    public static void main(String[] args) {
        try {
            System.out.println("Start \n Length of test list: " + usersClient.getAll().length);

            System.out.println("Add test with id 12, points 4, difficulty 100, date 2020-01-02");
            usersClient.save(new Test(12, 4, 100, "2020-01-02"));
            System.out.println("Length of test list: " + usersClient.getAll().length);
            Test[] tests = usersClient.getAll();
            int id = tests[tests.length - 1].getId();
            Test testAdded = usersClient.getById(id);
            System.out.println(testAdded);
            testAdded.setPoints(100);
            System.out.println("Update test with id " + id + " to points 100");
            usersClient.update(testAdded);
            System.out.println(usersClient.getById(id));
            System.out.println("Delete test with id " + id);
            usersClient.delete(id);
            System.out.println(usersClient.getAll().length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
