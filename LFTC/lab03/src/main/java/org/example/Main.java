package org.example;


import java.io.*;
import java.util.*;

public class Main {
    Set<String> atoms = new HashSet<>();

    Map<String, Integer> syntaxElements = new HashMap<>();

    Map<String, FIP> fip = new HashMap<>();

    BinarySearchTree identifiers = new BinarySearchTree(0);

    BinarySearchTree constants = new BinarySearchTree(100);

    public void readProgramAndCreateLexicalAtoms() {
        try {
            BufferedReader fin = new BufferedReader(new FileReader("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab3\\src\\main\\resources\\program.txt"));
            BufferedWriter fout = new BufferedWriter(new FileWriter("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab3\\src\\main\\resources\\atoms.txt"));

            String line;
            while((line = fin.readLine()) != null) {
                String[] elements = line.split(" ");
                Collections.addAll(atoms, elements);
            }
            fin.close();
            atoms.remove("");
            atoms.stream()
                .forEach(atom -> {
                    try {
                        fout.write(atom);
                        fout.newLine();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                });
            fout.close();
        } catch(IOException e) {
            System.err.println("Failed to open files.");
            e.printStackTrace();
        }
    }

    private void readTableInputFromCSV() {
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab3\\src\\main\\resources\\table.csv"));

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                if(parts.length == 2) {
                    syntaxElements.put(parts[0], Integer.parseInt(parts[1]));
                }
            }

            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void generateFIP() {
        atoms.stream()
            .forEach(atom -> {
                if(syntaxElements.containsKey(atom)) {
                    fip.put(atom, new FIP(syntaxElements.get(atom), "-"));
                } else {
                    if(atom.length() > 250) {
                        fip.put(atom, new FIP(-1, "ERROR"));
                    } else {
                        fip.put(atom, new FIP(isIdentifier(atom) ? syntaxElements.get("ID") : syntaxElements.get("CONST"),
                            "?"));
                    }
                }
            });
        generateSymbolTables();
    }

    private void generateSymbolTables() {
        fip.entrySet()
            .stream()
            .forEach(entry -> {
                if(entry.getValue().code == 0 || entry.getValue().code == 1) {
                    if(isIdentifier(entry.getKey())) {
                        identifiers.insert(new BinarySearchTree.Pair(entry.getKey()));
                    } else {
                        constants.insert(new BinarySearchTree.Pair(entry.getKey()));
                    }
                }
            });
        identifiers.setOrder();
        constants.setOrder();
        identifiers.setFIP(fip);
        constants.setFIP(fip);
        saveAll();
    }

    private boolean isIdentifier(String key) {
        return key.matches("[a-zA-Z][a-zA-Z0-9]*");
    }

    private void run() {
        readProgramAndCreateLexicalAtoms();
        readTableInputFromCSV();
        generateFIP();
    }

    private void saveAll() {
        saveFIPInCSV();
        identifiers.saveToCSV("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab3\\src\\main\\resources\\id.csv");
        constants.saveToCSV("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab3\\src\\main\\resources\\const.csv");
    }

    private void saveFIPInCSV() {
        try {
            BufferedWriter fout = new BufferedWriter(new FileWriter("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab3\\src\\main\\resources\\fip.csv"));

            fip.entrySet()
                .stream()
                .forEach(entry -> {
                    try {
                        fout.write(entry.getKey() + ":" + entry.getValue().code + ":" + entry.getValue().tsCode);
                        fout.newLine();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                });

            fout.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}