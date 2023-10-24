package org.example;


import java.io.*;
import java.util.*;

public class Main {
    List<String> atoms = new ArrayList<>();

    Map<String, Integer> syntaxElements = new HashMap<>();

//    Map<String, FIP> fip = new HashMap<>();

    List<FIP> fip = new ArrayList<>();

    BinarySearchTree identifiers = new BinarySearchTree(0);

    BinarySearchTree constants = new BinarySearchTree(100);

    public void readProgramAndCreateLexicalAtoms() {
        try {
            BufferedReader fin = new BufferedReader(new FileReader("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab03\\src\\main\\resources\\program.txt"));
            BufferedWriter fout = new BufferedWriter(new FileWriter("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab03\\src\\main\\resources\\atoms.txt"));

            String line;
            int lineNo = 1;
            while((line = fin.readLine()) != null) {
                String[] elements = line.split(" ");
                for(String element : elements){
                    if(isIdentifier(element) && element.length() > 250) {
                        System.err.println("Identifier " + element + " on line " + lineNo + " is too long.");
                        System.exit(1);
                    }
                    lineNo++;
                }
                Collections.addAll(atoms, elements);
            }
            fin.close();
            atoms = atoms.stream().filter(atom -> !atom.equals("")).toList();
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
            Scanner scanner = new Scanner(new File("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab03\\src\\main\\resources\\table.csv"));

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
//                    fip.put(atom, new FIP(syntaxElements.get(atom), "-"));
                    fip.add(new FIP(atom, syntaxElements.get(atom), "-"));
                } else {
                    if(atom.length() > 250 && isIdentifier(atom)) {
//                        fip.put(atom, new FIP(-1, "ERROR"));
                        fip.add(new FIP(atom, -1, "ERROR"));
                    } else {
//                        fip.put(atom, new FIP(isIdentifier(atom) ? syntaxElements.get("ID") : syntaxElements.get("CONST"),
//                            "?"));
                        fip.add(new FIP(atom, isIdentifier(atom) ? syntaxElements.get("ID") : syntaxElements.get("CONST"),
                            "?"));
                    }
                }
            });
        generateSymbolTables();
    }

    private void generateSymbolTables() {
        fip.stream()
            .forEach(entry -> {
                if(entry.getCode() == 0 || entry.getCode() == 1) {
                    if(isIdentifier(entry.getAtom())) {
                        identifiers.insert(new BinarySearchTree.Pair(entry.getAtom()));
                    } else {
                        constants.insert(new BinarySearchTree.Pair(entry.getAtom()));
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
        identifiers.saveToCSV("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab03\\src\\main\\resources\\id.csv");
        constants.saveToCSV("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab03\\src\\main\\resources\\const.csv");
    }

    private void saveFIPInCSV() {
        try {
            BufferedWriter fout = new BufferedWriter(new FileWriter("C:\\Users\\OTI\\OneDrive\\Desktop\\Facultate\\Materii\\LFTC\\lab03\\src\\main\\resources\\fip.csv"));

            fip.stream()
                .forEach(entry -> {
                    try {
                        fout.write(entry.getAtom() + ":" + entry.getCode() + ":" + entry.getTsCode());
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