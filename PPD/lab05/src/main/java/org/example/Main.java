package org.example;

import org.example.datastructure.MyBlackList;
import org.example.datastructure.MyBlockingLinkedList;
import org.example.datastructure.MyBlockingQueue;
import org.example.domain.Node;
import org.example.domain.Participant;
import org.example.util.Constants;
import org.example.util.IOHandler;
import org.example.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


public class Main {
    private static final MyBlockingLinkedList resultList = new MyBlockingLinkedList();
    private static final MyBlackList blackList = new MyBlackList();
    private static final ExecutorService executor = Executors.newFixedThreadPool(Constants.NUMBER_OF_PRODUCERS);
    private static final AtomicInteger totalProducerTasks = new AtomicInteger(50);
    private static final MyBlockingQueue myBlockingQueue = new MyBlockingQueue(totalProducerTasks);
    private static final Map<String, ReentrantLock> locks = new HashMap<>();

    public static void main(String[] args) {
        List<String> files = IOHandler.generateFileNames();

        for (int i = 0; i < 1000; ++i) {
            locks.put(String.valueOf(i), new ReentrantLock());
        }

        //start timer
        long startTime = System.currentTimeMillis();
        Thread[] consumerThreads = new Thread[Constants.NUMBER_OF_CONSUMERS];

        for (int i = 0; i < Constants.NUMBER_OF_CONSUMERS; ++i) {
            Thread thread = new Consumer();
            consumerThreads[i] = thread;
        }

        Arrays.stream(consumerThreads).forEach(Thread::start);

        files.forEach(file -> executor.execute(() -> {
            try {
                Producer.start(file);
            } catch (FileNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));


        Arrays.stream(consumerThreads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executor.shutdown();

        resultList.sort();
        IOHandler.printListToFile(resultList, "output");
        IOHandler.validate("src/main/resources/output", "src/main/resources/valid");
        //end timer
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }

    static class Consumer extends Thread {

        @Override
        public void run() {
            while (totalProducerTasks.get() != 0 || !myBlockingQueue.isEmpty()) {
                Participant participant = null;
                try {
                    participant = myBlockingQueue.take();
                } catch (InterruptedException ignored) {
                }

                if (participant == null) {
                    myBlockingQueue.finish();
                    continue;
                }

                locks.get(participant.getId()).lock();

                if (!blackList.contains(new Pair(participant.getId(), participant.getCountry()))) {
                    if (participant.getScore() == -1) {
                        resultList.delete(participant);
                        blackList.add(new Pair(participant.getId(), participant.getCountry()));
                   } else {
                        Node current = resultList.update(participant);

                        if (current == null) {
                            resultList.add(participant);
                        }
                    }
                }

                locks.get(participant.getId()).unlock();
            }
        }
    }

    static class Producer {
        public static void start(String file) throws FileNotFoundException, InterruptedException {
            String country = "C" + file.charAt(file.indexOf('C') + 1);
            File obj = new File("src/main/resources/input_files/" + file);
            Scanner scanner = new Scanner(obj);

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                myBlockingQueue.put(new Participant(data[0], Integer.parseInt(data[1]), country));
            }

            if (totalProducerTasks.decrementAndGet() == 0) {
                myBlockingQueue.finish();
            }
        }
    }
}
