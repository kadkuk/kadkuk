package ee.bcs.valiit.tasks;

import java.util.Scanner;

public class Lesson2 {

    public static void main(String[] args) {
        exercise1();
        exercise2(7);
        exercise3(3, 3);
        exercise4(3);
        System.out.println(exercise5(2,1));
    }

    // TODO loo 10 elemendile täisarvude massiv1
    // TODO loe sisse konsoolist 10 täisarvu
    // TODO trüki arvud välja vastupidises järiekorras
    public static void exercise1() {
        int[] numbers = new int[10];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Please enter your number.");
            numbers[i] = scanner.nextInt();
        }
        for (int i = 9; i >= 0; i--) {
            System.out.print(numbers[i] + " ");
        }
    }

    // TODO prindi välja x esimest paaris arvu
    // Näide:
    // Sisend 5
    // Väljund 2 4 6 8 10
    public static void exercise2(int x) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" +
                "Please enter your one number.");
        x = scanner.nextInt();
        int equals = 2;
        while (equals <= x*2) {
            System.out.println(equals);
            equals +=2;
        }
    }

    // TODO trüki välja korrutustabel mis on x ühikut lai ja y ühikut kõrge
    // TODO näiteks x = 3 y = 3
    // TODO väljund
    // 1 2 3
    // 2 4 6
    // 3 6 9
    // TODO 1 trüki korrutustabeli esimene rida (numbrid 1 - x)
    // TODO 2 lisa + " " print funktsiooni ja kasuta print mitte println
    // TODO 3 trüki seda sama rida y korda
    // TODO 4 Kuskile võiks kirjutada System.out.println(),
    //  et saada taebli kuju
    // TODO 5 võrdle ridu. Kas on mingi seaduspärasus ridade vahel,
    // mis on ja mis võiks olla. Äkki tuleb mõni idee
    public static void exercise3(int x, int y) {
        for (int i = 1; i <= x; i++) {
            for (int j = 1; j <= y; j++) {
                System.out.print(i * j + " ");
            }
        System.out.println();
        }

    }

    // TODO
    // Fibonacci jada on fib(n) = fib(n-1) + fib(n-2);
    // 0, 1, 1, 2, 3, 5, 8, 13, 21
    // Tagasta fibonacci jada n element
    public static int exercise4(int n) {
        if (n <=2) {
            System.out.println("1");
        } else {
            int f1 = 1;
            int f2 = 1;
            int i = 1;
            while (i <= n-2) {
                int x = f1;
                f1 = f2;
                f2 = f2 + x;
                i++;            }
                System.out.println(f2);
            }
        return n;
    }


    public static String exercise5(int x, int y) {
        int maxLength = 0;
        for(int i = x; i <= y; i++){
            int seqLength = seqLength(i);
            if(seqLength > maxLength){
                maxLength = seqLength;
            }
        }
        return x + " " + y + " " + maxLength;
    }

    public static int seqLength(int n) {
        int count = 1;
        while(n > 1){
            count++;
            if(n%2 == 0){
                n = n / 2;
            } else {
                n = n*3 + 1;
            }
        }
        return count;
    }


}