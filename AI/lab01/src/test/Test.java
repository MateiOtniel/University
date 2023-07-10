package test;

import org.junit.jupiter.api.Assertions;
import problem.Problems;
import utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public Test() {
    }

    public void testAll() {
        System.out.println("Started tests...");
        testTask1();
        testTask2();
        testTask3();
        testTask4();
        testTask5();
        testTask6();
        testTask7();
        testTask8();
        testTask9();
        testTask10();
        System.out.println("Tests passed...");
    }

    private void testTask1() {
        Assertions.assertEquals(Problems.task1("Ana are mere rosii si galbene"), "si");
        Assertions.assertEquals(Problems.task1("Ana are mere rosii si zi"), "zi");
        Assertions.assertEquals(Problems.task1("Ana are mere rosii rosi galbene"), "rosii");
        Assertions.assertEquals(Problems.task1("Ana are mere rosii rosij galbene"), "rosij");
        Assertions.assertEquals(Problems.task1("Ana ana"), "Ana");
    }


    private void testTask2() {
        Assertions.assertEquals(Problems.task2(1, 5, 4, 1), 5);
        Assertions.assertTrue(Math.abs(Problems.task2(2, 2, 5, 7) - 5.83095) < 0.001);
        Assertions.assertTrue(Math.abs(Problems.task2(22, 22, 5541, 7123) - 8993.53) < 0.01);
    }

    private void testTask3() {
        Assertions.assertEquals(Problems.task3(new double[]{1, 0, 2, 0, 3}, new double[]{1, 2, 0, 3, 1}), 4);
        Assertions.assertEquals(Problems.task3(new double[]{1, 0, 2, 0, 3, 77}, new double[]{1, 2, 0, 3, 0, 2}), 155);
        Assertions.assertEquals(Problems.task3(new double[]{1, 0, 0, 3, 77, 155}, new double[]{1, 0, 3, 0, 2, 80}), 12555);
    }

    private void testTask4() {
        ArrayList<String> test = new ArrayList<>();
        test.add("mere");
        test.add("rosii");
        Assertions.assertEquals(Problems.task4("ana are ana are mere rosii ana"), test);
        Assertions.assertEquals(Problems.task4("rosiii rosiii ana rosi are ana are mere rosii ana rosi"), test);
        Assertions.assertEquals(Problems.task4("ana are ana are mere mer mer meres meres rosii ana"), test);
        Assertions.assertEquals(Problems.task4("ana are ana are mere meere rosii meere ana"), test);
    }

    private void testTask5() {
        Assertions.assertEquals(Problems.task5(new int[]{1, 2, 3, 4, 2}), 2);
        Assertions.assertEquals(Problems.task5(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 1}), 1);
        Assertions.assertEquals(Problems.task5(new int[]{1, 2, 3, 4, 5, 9, 8, 7, 6, 7}), 7);
    }

    private void testTask6() {
        Assertions.assertEquals(Problems.task6(new int[]{2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2}), 2);
        Assertions.assertEquals(Problems.task6(new int[]{2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2, 8, 8, 8, 8, 8, 8, 1, 15}), -1);
        Assertions.assertEquals(Problems.task6(new int[]{2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2, 8, 8, 8, 8, 8, 8, 8, 8, 8, 1, 2, 8, 8}), 8);
    }

    private void testTask7() {
        Assertions.assertEquals(Problems.task7(new int[]{7, 4, 6, 3, 9, 1}, 2), 7);
        Assertions.assertEquals(Problems.task7(new int[]
                {7, 4, 6, 3, 9, 1, 155, 231, 321, 321, 231, 123, 514, 123, 231, 123, 42441, 312, 31257}, 2), 31257);
        Assertions.assertEquals(Problems.task7(new int[]
                {7, 4, 6, 3, 9, 1, 7, 4, 6, 3, 9, 1, 155, 231, 321,
                        321, 231, 123, 514, 123, 231, 123, 42441, 312, 31257,
                        7, 4, 6, 3, 9, 1, 155, 231, 321, 321, 231, 123, 514, 123, 231, 123, 42441, 312, 31257,
                        7, 4, 6, 3, 9, 1, 155, 231, 321, 321, 231, 123, 514, 123, 231, 123, 42441, 312, 31257,
                        7, 4, 6, 3, 9, 1, 155, 231, 321, 321, 231, 123, 514, 123, 231, 123, 42441, 312, 31257,
                        7, 4, 6, 3, 9, 1, 155, 231, 321, 321, 231, 123, 514, 123, 231, 123, 42441, 312, 31257,
                        7, 4, 6, 3, 9, 1, 155, 231, 321, 321, 231, 123, 514, 123, 231, 123, 42441, 312, 31257,
                        7, 4, 6, 3, 9, 1, 155, 231, 321, 321, 231, 123, 514, 123, 231, 123, 42441, 312, 31257}, 2), 42441);
    }

    private void testTask8() {
        ArrayList<String> test = new ArrayList<>();
        test.add("1");
        Assertions.assertEquals(Problems.task8(1), test);
        test.add("10");
        Assertions.assertEquals(Problems.task8(2), test);
        test.add("11");
        Assertions.assertEquals(Problems.task8(3), test);
        test.add("100");
        Assertions.assertEquals(Problems.task8(4), test);
    }

    private void testTask9() {
        int[][] matrix = {{0, 2, 5, 4, 1},
                {4, 8, 2, 3, 7},
                {6, 3, 4, 6, 2},
                {7, 3, 1, 8, 3},
                {1, 5, 7, 9, 4}};
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>[] pairs = new Pair[]{new Pair<>(new Pair<>(1, 1), new Pair<>(3, 3)),
                new Pair<>(new Pair<>(2, 2), new Pair<>(4, 4)),
                new Pair<>(new Pair<>(0, 0), new Pair<>(1, 1))};
        int[] ans = {38, 44, 14};
        Assertions.assertEquals(Arrays.toString(Problems.task9(matrix, pairs)), Arrays.toString(ans));
    }

    private void testTask10() {
        byte[][] matrix = {{0,0,0,1,1},
                {0,1,1,1,1},
                {0,0,1,1,1}};
        Assertions.assertEquals(Problems.task10(matrix), 2);
        byte[][] matrix1 = {{1,1,0,1,1},
                {0,1,1,1,1},
                {0,0,1,1,1}};
        Assertions.assertEquals(Problems.task10(matrix1), 1);
        byte[][] matrix2 = {{1,1,0,1,1},
                {0,1,1,1,1},
                {1,1,1,1,1}};
        Assertions.assertEquals(Problems.task10(matrix2), 3);
    }
}
