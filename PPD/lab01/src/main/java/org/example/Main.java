package org.example;

import java.util.Random;

public class Main {

    private static class MyThread extends Thread{
        int id;
        int[] A;
        int[] B;
        int[] C;
        int p;

        public MyThread(int id, int p, int[] A, int[] B, int[] C){
            this.id = id;
            this.p = p;
            this.A = A;
            this.B = B;
            this.C = C;
        }

        @Override
        public void run(){
            int start = id * (A.length / p);
            int end = (id + 1) * (A.length / p);

            for(int i = start; i < end; i++){
                C[i] = A[i] + B[i];
            }

            int lastPos = A.length - 1 - A.length / p + id;
            if(lastPos < A.length)
                C[lastPos] = A[lastPos] + B[lastPos];
        }

    }

    private static class MyThread2 extends Thread{
        int id;
        int[] A;
        int[] B;
        int[] C;
        int p;

        public MyThread2(int id, int p, int[] A, int[] B, int[] C){
            this.id = id;
            this.p = p;
            this.A = A;
            this.B = B;
            this.C = C;
        }

        @Override
        public void run(){
            int pos = id;
            while(pos < A.length){
                C[pos] = A[pos] + B[pos];
                pos += p;
            }
        }
    }

    public static void main(String[] args) {
        int p = 8;
        int N = 10000000;
        int[] A = new int[N];
        int[] B = new int[N];
        int[] C = new int[N];

        randomise(A, B, C);
        long start_t = System.currentTimeMillis();
        startThreads(p, A, B, C);
        System.out.println("Parallel time: " + (System.currentTimeMillis() - start_t));
//        printArrays(A, B, C);
    }

    private static void randomise(int[] A, int[] B, int[] C) {
        Random rand = new Random();
        for (int i = 0; i < A.length; i++) {
            A[i] = rand.nextInt(100000) + 1;
            B[i] = rand.nextInt(100000) + 1;
            C[i] = 0;
        }
    }

    private static void startThreads(int p, int[] A, int[] B, int[] C) {
        Thread[] threads = new MyThread2[p];
        for(int i = 0; i < p; i++){
            threads[i] = new MyThread2(i, p, A, B, C);
            threads[i].start();
        }
        for(int i  = 0; i < p; i++){
            try {
                threads[i].join();
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void printArrays(int[] A, int[] B, int[] C) {
        System.out.println("A: ");
        for(int j : A) {
            System.out.print(j + " ");
        }

        System.out.println("\nB: ");
        for(int j : B) {
            System.out.print(j + " ");
        }

        System.out.println("\nC: ");
        for(int j : C) {
            System.out.print(j + " ");
        }
    }
}