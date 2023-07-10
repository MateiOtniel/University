package problem;

import utils.Pair;

import java.util.ArrayList;

public class Problems {

    /***
     * Să se determine ultimul (din punct de vedere alfabetic) cuvânt care poate apărea într-un text care conține
     * mai multe cuvinte separate prin ” ” (spațiu). De ex. ultimul (dpdv alfabetic) cuvânt din ”Ana are mere rosii si galbene”
     * este cuvântul "si".
     * @param text
     * @return
     */
    public static String task1(String text) {
        //words reprezinta array-ul de cuvinte
        String[] words = text.split(" ");
        String ans = "";
        //trecem prin fiecare cuvant si gasim ultimul dpdv alfabetic
        for (String word : words) {
            if (isLastWord(word, ans)) ans = word;
        }
        return ans;
    }

    private static boolean isLastWord(String word, String ans) {
        //Daca ans la inceput e gol, atunci automat se returneaza true
        if (ans.equals("")) return true;
        //Altfel se verifica daca cuvantul curent este mai mare decat ans
        return word.toLowerCase().compareTo(ans.toLowerCase()) > 0;
    }

    /***
     * Să se determine distanța Euclideană între două locații identificate prin perechi de numere.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double task2(double x1, double y1, double x2, double y2) {
        //Am folosit formula distantei euclidiene
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /***
     * Să se determine produsul scalar a doi vectori rari care conțin numere reale. Un vector este rar atunci când
     * conține multe elemente nule. Vectorii pot avea oricâte dimensiuni.
     * De ex. produsul scalar a 2 vectori unisimensionali [1,0,2,0,3] și [1,2,0,3,1] este 4.
     * @param v1
     * @param v2
     * @return
     */
    public static double task3(double[] v1, double[] v2) {
        double ans = 0;
        //iteram prin ambii vectori si aplicam produsul scalar
        for (int i = 0; i < v1.length; i++)
            ans += v1[i] * v2[i];
        return ans;
    }

    /***
     * Să se determine cuvintele unui text care apar exact o singură dată în acel text. De ex. cuvintele care
     * apar o singură dată în ”ana are ana are mere rosii ana" sunt: 'mere' și 'rosii'.
     * @param text
     * @return
     */
    public static ArrayList<String> task4(String text) {
        ArrayList<String> ans = new ArrayList<>();
        //words reprezinta array-ul de cuvinte
        String[] words = text.split(" ");
        //verificam daca un cuvant apare doar si il adaugam in array-ul rezultat
        for (String word : words) {
            if (appearsJustOnce(words, word)) ans.add(word);
        }
        return ans;
    }

    /***
     * Verifica daca un cuvant apare doar o data intr-un array de cuvinte.
     * @param words
     * @param word
     * @return
     */
    private static boolean appearsJustOnce(String[] words, String word) {
        int nr = 0;
        for (String currentWord : words)
            if (currentWord.compareTo(word) == 0) nr++;
        return nr == 1;
    }

    /***
     * Pentru un șir cu n elemente care conține valori din mulțimea {1, 2, ..., n - 1}
     * astfel încât o singură valoare se repetă de două ori, să se identifice acea valoare care se repetă.
     * De ex. în șirul [1,2,3,4,2] valoarea 2 apare de două ori.
     * @param list
     * @return
     */
    public static long task5(int[] list) {
        long sum = 0; // suma efectiva a sirului
        for (int number : list) {
            sum += number;
        }
        long sumaNElemente = (long) (list.length - 1) * (list.length) / 2; //suma gauss a primelor n-1 elemente
        return sum - sumaNElemente; //diferenta dintre cele doua reprezinta chiar elementul ce lipseste
    }

    /***
     * Pentru un șir cu n numere întregi care conține și duplicate, să se determine elementul majoritar
     * (care apare de mai mult de n / 2 ori). De ex. 2 este elementul majoritar în șirul [2,8,7,2,2,5,2,3,1,2,2].
     * @param list
     * @return
     */

    public static int task6(int[] list) {
        //Am invatat algoritmul lui Moore pt problema asta
        int cand = -1, k = 0, n = list.length;
        //Cerinta spunand ca acel element majoritar exista, din acest for sigur variabila 'cand'
        // va avea valoarea acestui element cautat.
        for (int number : list) {
            if (k == 0) {
                cand = number;
                k = 1;
            } else if (cand == number) {
                k++;
            } else {
                k--;
            }
        }
        //insa pentru verificare se mai poate parcurge o data lista de elemente
        k = 0; // cu acesta numaram nr de aparitii a candidat
        for (int number : list) {
            if (number == cand) k++;
        }
        return k >= list.length / 2 ? cand : -1;
    }

    /***
     * Să se determine al k-lea cel mai mare element al unui șir de numere cu n elemente (k < n).
     * De ex. al 2-lea cel mai mare element din șirul [7,4,6,3,9,1] este 7.
     * @param list
     * @param k
     * @return
     */
    public static int task7(int[] list, int k) {
        //sortam lista crescator, apoi alegem al n - k lea element din ea
        mergeSort(list, list.length);
        return list[list.length - k];
    }

    /***
     * Merge sort, nush ce sa mai zic.
     * @param a
     * @param n
     */
    private static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        System.arraycopy(a, 0, l, 0, mid);
        if (n - mid >= 0) System.arraycopy(a, mid, r, 0, n - mid);

        mergeSort(l, mid);
        mergeSort(r, n - mid);
        merge(a, l, r, mid, n - mid);
    }

    /***
     * Interclasarea pentru algoritmul merge sort.
     * @param a
     * @param l
     * @param r
     * @param left
     * @param right
     */
    private static void merge(int[] a, int[] l, int[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    /***
     * Să se genereze toate numerele (în reprezentare binară) cuprinse între 1 și n.
     * De ex. dacă n = 4, numerele sunt: 1, 10, 11, 100.
     * @param n
     * @return
     */
    public static ArrayList<String> task8(int n) {
        ArrayList<String> ans = new ArrayList<>();//pastram rezultatele intr-un array de string-uri
        //parcurgem toate numerele 1..n si le transformam in binar
        for (int i = 1; i <= n; i++)
            ans.add(intToBinary(i));
        return ans;
    }

    /***
     * Transforma numarul in baza 2 si-l returneaza ca string.
     * @param x
     * @return
     */
    private static String intToBinary(int x) {

        StringBuilder binary = new StringBuilder();
        //Rezultat va fi pe dos, deci va trebui inversat
        while (x > 0) {
            int rest = x % 2;
            binary.append(rest);
            x /= 2;
        }

        //Inversam string builderul
        int length = binary.length();
        for (int i = 0; i < length / 2; i++) {
            char temp = binary.charAt(i);
            int j = length - i - 1;
            binary.setCharAt(i, binary.charAt(j));
            binary.setCharAt(j, temp);
        }
        return binary.toString();
    }

    /***
     * Considerându-se o matrice cu n x m elemente întregi și o listă
     * cu perechi formate din coordonatele a 2 căsuțe din matrice ((p,q) și (r,s)), să se calculeze suma elementelor
     * din sub-matricile identificate de fieare pereche.
     * @param matrix
     * @param pairs
     * @return
     */

    public static int[] task9(int[][] matrix, Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>[] pairs) {
        int[] sum = new int[pairs.length];
        int[][] sumsMatrix = sumMatrix(matrix); //se creeaza matricea sumelor pentru a folosi algoritmul sumelor partiale

        int poz = -1;
        //iteram prin toate perechile
        for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> pair : pairs) { //trecem prin toate perechile
            sum[++poz] = submatrixSum(sumsMatrix, pair); // se returneaza suma submatricii
            //System.out.println(i1 + " " + j1 + " " + i2 +  " " + j2);
        }
        return sum;
    }

    /***
     * Algoritmul care returneaza matricea sumelor partiale.
     * @param matrix
     * @return
     */
    private static int[][] sumMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] sums = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sums[i][j] = matrix[i][j];
                if (i > 0) {
                    sums[i][j] += sums[i - 1][j];
                }
                if (j > 0) {
                    sums[i][j] += sums[i][j - 1];
                }
                if (i > 0 && j > 0) {
                    sums[i][j] -= sums[i - 1][j - 1];
                }
            }
        }
        return sums;
    }

    /***
     * Algoritmul care returneaza suma fiecare submatrici.
     * @param sumsMatrix
     * @param pair
     * @return
     */
    private static int submatrixSum(int[][] sumsMatrix, Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> pair) {
        //perechea (i1, j1) si (i2, j2)
        int i1 = pair.first().first(); // i1
        int j1 = pair.first().second(); // j1
        int i2 = pair.second().first(); // i2
        int j2 = pair.second().second(); // j2
        int bottomRight = sumsMatrix[i2][j2];
        int bottomLeft = 0;
        int topLeft = 0;
        int topRight = 0;

        if (j1 - 1 >= 0) bottomLeft = sumsMatrix[i2][j1 - 1];

        if (i1 - 1 >= 0) topRight = sumsMatrix[i1 - 1][j2];

        if (j1 - 1 >= 0 && i1 - 1 >= 0) topLeft = sumsMatrix[i1 - 1][j1 - 1];

        /*
        (i1, j1) ....
        .............
        .............
        .......(i2, j2)
         */
        return bottomRight - bottomLeft - topRight + topLeft;
    }

    /***
     * Considerându-se o matrice cu n x m elemente binare (0 sau 1) sortate crescător pe linii,
     * să se identifice indexul liniei care conține cele mai multe elemente de 1.
     * @param matrix
     * @return
     */
    public static int task10(byte[][] matrix) {
        int currrentLine = 0;
        int maxLine = -1;
        int maxNrOfOnes = 0;
        //parcurg efectiv matricea linie cu linie si retin linia cu cele mai multe valori de 1
        for (byte[] line : matrix) {
            currrentLine++;
            int currentNrOfOnes = 0;
            for (byte element : line) {
                if (element == 1) currentNrOfOnes++;
            }
            if (currentNrOfOnes > maxNrOfOnes) {
                maxNrOfOnes = currentNrOfOnes;
                maxLine = currrentLine;
            }
        }
        return maxLine;
    }

}
