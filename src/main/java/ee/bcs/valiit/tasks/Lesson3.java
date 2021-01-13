package ee.bcs.valiit.tasks;

public class Lesson3 {
    public static void main(String[] args) {
        sum(new int[]{1, 2, 3, 4, 5, 6});
        factorial(5);
        sort(new int[]{5, 3, 7, 1, 100, 655, 9});
        reverseString("Hallooo!");
        isPrime(11);

    }

    public static int sum(int[] x) {
        // Todo liida kokku kõik numbrid massivis x
        int summa = 0;
        for (int i : x) {
            summa = summa + i;
        }
        System.out.println("Summa on: " + summa);
        return summa;
    }

    public static int factorial(int x) {
        // TODO tagasta x faktoriaal.
        int fac = 1;
        int i = 2;
        while (i <= x) {
            fac *= i;
            i++;
        }
        System.out.println(x + "-e faktoriaal on: " + fac);
        return fac;
    }

    public static int[] sort(int[] a) {
        // TODO sorteeri massiiv suuruse järgi.
        // TODO kasuta tsükleid, ära kasuta ühtegi olemasolevat sort funktsiooni
        int[] srt = a;
        for (int i = 0; i< srt.length; i++) {
            for (int j=i+1; j< srt.length; j++) {
                if (srt[i] > srt[j]) {
                    int ajutine = srt[i];
                    srt[i] = srt[j];
                    srt[j] = ajutine;
                }
            }

            System.out.print(srt[i] + " ");
            }
        return srt;
    }

    public static String reverseString(String a) {
        // TODO tagasta string tagurpidi
        char[] sisendSrting = a.toCharArray();
        int i = 0;
        int j = a.length() - 1;
        while (i < j) {
            char ajutine = sisendSrting[i];
            sisendSrting[i] = sisendSrting[j];
            sisendSrting[j] = ajutine;
            i++;
            j--;
        }
        System.out.println("\n");
        System.out.println(sisendSrting);
        return "";
    }

    public static boolean isPrime(int x) {
        // TODO tagasta kas sisestatud arv on primaar arv (jagub ainult 1 ja iseendaga)
        for (int i = 2; i < x; i++) {
            if (x % i == 0) {
                System.out.println(x + " ei ole algarv.");
                return false;
            }
        }
        System.out.println(x + " on algarv.");
        return true;
    }
}