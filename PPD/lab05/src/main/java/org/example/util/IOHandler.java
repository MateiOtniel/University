package org.example.util;

import org.example.datastructure.MyBlockingLinkedList;
import org.example.domain.Node;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class IOHandler {

    public static List<String> generateFileNames() {
        List<String> files = new LinkedList<>();
        String format = "RezultateC%d_P%d";
        for (int i = 1; i <= 5; ++i) {
            for (int j = 1; j <= 10; ++j) {
                files.add(String.format(format, i, j));
            }
        }
        return files;
    }

    public static void validate(String outputFilename, String validFilename) {
        int line = 1;
        try(BufferedReader br1 = new BufferedReader(new FileReader(outputFilename));
            BufferedReader br2 = new BufferedReader(new FileReader(validFilename))) {
            String line1;
            String line2;
            while((line1 = br1.readLine()) != null && (line2 = br2.readLine()) != null) {
                if(!line1.equals(line2)) {
                    int score1 = Integer.parseInt(line1.split(",")[1]);
                    int score2 = Integer.parseInt(line2.split(",")[1]);
                    if(score1 != score2) {
                      System.err.println("Output is not valid at line " + line + "!");
                    }
                }
                line++;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void printListToFile(MyBlockingLinkedList list, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/" + filename));

            Node node = list.head.next;
            if (node == list.tail) {
                return;
            }
            while (node.isNotLastNode() && node.getParticipant().getScore() != -1) {
                bw.write(node.getParticipant().toString());
                node = node.next;
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
