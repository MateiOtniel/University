package user_interface;

import domain.User;
import exceptions.NetworkException;
import exceptions.RepositoryException;
import exceptions.ExitException;
import exceptions.InputException;
import service.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;

import static utils.Constants.*;

public class Console {

    private final Scanner scanner;
    private final UserService service;

    public Console(UserService service) {
        scanner = new Scanner(System.in);
        this.service = service;
        headerInfo();
    }

    public void runLogin() {
        while (true) {
            System.out.print(">>> ");
            try {
                manageTask(getInput());
            } catch (InputException e) {
                System.out.println(e.getMessage());
            } catch (ExitException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private void manageTask(String input) throws InputException, ExitException {
        switch (input) {
            case "add":
                addUser();
                break;
            case "remove":
                removeUser();
                break;
            case "friend":
                addFriend();
                break;
            case "unfriend":
                removeFriend();
                break;
            case "number":
                numberOfCommunities();
                break;
            case "biggest":
                biggestCommunity();
                break;
            case "info":
                showInfo();
                break;
            case "exit":
                throw new ExitException();
            case "show":
                showAll();
                break;
            default:
                throw new InputException("Invalid command!");
        }
    }

    private void biggestCommunity() {
        System.out.println(service.biggestCommunity());
    }

    private void numberOfCommunities() {
        System.out.println(service.numberOfCommunities());
    }

    private void addFriend() {
        try{
            System.out.print("First user's Id: ");
            int id1 = Integer.parseInt(scanner.nextLine());
            System.out.print("Second user's id: ");
            int id2 = Integer.parseInt(scanner.nextLine());
            if(id1 == id2)
                throw new InputException("Ids are equal!");
            service.addFriend(id1, id2);
        } catch (NumberFormatException | InputException | NetworkException | RepositoryException e){
            System.out.println(e.getMessage());
        }
    }

    private void removeFriend(){
        try{
        System.out.print("First user's Id: ");
        int id1 = Integer.parseInt(scanner.nextLine());
        System.out.print("Second user's id: ");
        int id2 = Integer.parseInt(scanner.nextLine());
        if(id1 == id2)
            throw new InputException("Ids are equal!");
        service.removeFriend(id1, id2);
        } catch (NumberFormatException | InputException | NetworkException | RepositoryException e){
            System.out.println(e.getMessage());
        }
    }

    private void removeUser(){
        try {
            System.out.print("Id: ");
            int id = Integer.parseInt(scanner.nextLine());
            service.removeUser(id);
        } catch (InputException | NumberFormatException | RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAll() {
        for(User user : service.getAll()){
            System.out.println(user.toString());
        }
    }

    private void addUser() {
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        try {
            service.addUser(firstName, lastName, email, password);
        } catch (InputException e) {
            System.out.println(e.getMessage());
        } catch (RepositoryException e){
            System.out.println(e.getMessage());
        } catch (NetworkException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showInfo() {
        System.out.println("This is a Social Network program with the following features:");
        System.out.println("...");
    }

    private String getInput() {
        return scanner.nextLine();
    }

    private void headerInfo() {
        System.out.println("\nWelcome!\nThis is" + ANSI_GREEN + " Oti's social network! " + ANSI_RESET + "What would you like to do?\n");
        System.out.println(ANSI_GREEN + "add" + ANSI_RESET + " - add a new user");
        System.out.println(ANSI_GREEN + "remove" + ANSI_RESET + " - remove user by id");
        System.out.println(ANSI_GREEN + "friend" + ANSI_RESET + " - create friendship between 2 users");
        System.out.println(ANSI_GREEN + "unfriend" + ANSI_RESET + " - end friendship between 2 users");
        System.out.println(ANSI_GREEN + "number" + ANSI_RESET + " - show number of communities");
        System.out.println(ANSI_GREEN + "biggest" + ANSI_RESET + " - show the biggest community");
        System.out.println(ANSI_GREEN + "show" + ANSI_RESET + " - show all connected users");
        System.out.println(ANSI_GREEN + "info" + ANSI_RESET + " - get more info about the social network");
        System.out.println(ANSI_GREEN + "exit" + ANSI_RESET + " - exit the program");
    }
}
