import test.Test;
import exceptions.InputException;
import service.UserService;
import user_interface.Console;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static int returnSaveMethod() {
        String type;
        int c;
        System.out.println("Choose where to save data:");
        System.out.println("file - csv file");
        System.out.println("db - database");
        System.out.println("exit - close program");
        while (true) {
            System.out.print(">>> ");
            Scanner scanner = new Scanner(System.in);
            type = scanner.nextLine();
            if (Objects.equals(type, "file")) return 1;
            else if (Objects.equals(type, "db")) return 2;
            else System.out.println("Invalid input!");
        }
    }

    public static void main(String[] args) throws InputException {
        Test test = new Test();
        test.runAllTests();
        Console console = new Console(UserService.getInstance(returnSaveMethod()));
        console.runLogin();
    }
}