package ee.bcs.valiit.tasks;

import java.util.*;

public class Lesson3Hard {
    public static void main(String[] args) {
        evenFibonacci(8);
        randomGame();
        System.out.println("Sinu tekst morsekoodis: " + morseCode("appi"));
    }
//0 1 1 2 3 5 8 13 21 34
    public static int evenFibonacci(int x) {
        // TODO liida kokku kõik paaris fibonacci arvud kuni numbrini x
        List<Integer> list = new ArrayList<>();
        if (x <= 0) {
            System.out.println("1");
        } else {
            int f1 = 1;
            int f2 = 1;
            for (int i = 1; i < x-2; i++) {
                int ex = f1;
                f1 = f2;
                f2 = f2 + ex;
                list.add(f2);
            }
        }
        System.out.println("Your fibonacci list: " + list);
        int summa = 0;
        for (int i : list) {
            if(i%2==0) {
                summa = summa + i;
            }
        }
        System.out.println("Sum on equals: "+summa);
        return summa;
    }

    public static void randomGame(){
        // TODO kirjuta mäng mis võtab suvalise numbri 0-100, mille kasutaja peab ära arvama
        // iga kord pärast kasutaja sisestatud täis arvu peab programm ütlema kas number oli suurem või väiksem
        // ja kasutaja peab saama uuesti arvata
        // numbri ära aramise korral peab programm välja trükkima mitu katset läks numbri ära arvamiseks
        int randomNumber = (int) (Math.random() * 100) + 1;
        boolean hasWon = false;
        System.out.println("\n"+"Arva ära suvaline number vahemikus 1-100");
        Scanner scanner = new Scanner(System.in);
        for(int i = 10; i > 0; i--) {
            System.out.println("Sul on veel "+ i + " korda võimalik proovida. Proovi!");
            int guess = scanner.nextInt();
            System.out.println("Sinu valik oli: " + guess);

            if (guess > randomNumber) {
                System.out.println("Sinu number oli liiga suur.");
            } else if (guess < randomNumber) {
                System.out.println("Sinu number oli liiga väike.");
            } else {
                hasWon = true;
                System.out.println("Hurraa! Arvasid õige vastuse ära " + (11-i) + ". korral.");
                break;
            }
        }
        if (hasWon) {
            System.out.println("Oled geenius!");
        } else {
            System.out.println("Sinu katsed on otsas ja sa ei arvanud ära! Õige vastus on: "+randomNumber);
        }

    }

    public static String morseCode(String text){
        // TODO kirjuta programm, mis tagastab sisestatud teksti morse koodis (https://en.wikipedia.org/wiki/Morse_code)
        // Kasuta sümboleid . ja -
        StringBuilder morse = new StringBuilder();
        HashMap<Character, String> map = new HashMap<>();
        map.put('a', ". -   ");
        map.put('b', "- . . .   ");
        map.put('c', "- . - .   ");
        map.put('d', "- . .   ");
        map.put('e', ".   ");
        map.put('f', ". . - .   ");
        map.put('g', "- - .   ");
        map.put('h', ". . . .   ");
        map.put('i', ". .   ");
        map.put('j', ". - - -   ");
        map.put('k', "- . -   ");
        map.put('l', ". - . .   ");
        map.put('m', "- -   ");
        map.put('n', "- .   ");
        map.put('o', "- - -   ");
        map.put('p', ". - - .   ");
        map.put('q', "- - . -   ");
        map.put('r', ". - .   ");
        map.put('s', ". . .   ");
        map.put('t', "-   ");
        map.put('u', ". . -   ");
        map.put('v', ". . . -   ");
        map.put('w', ". - -   ");
        map.put('x', "- . . -   ");
        map.put('y', "- . - -   ");
        map.put('z', "- - . .   ");
        map.put(' ', "       ");
        for (int i = 0; i < text.length(); i++) {
            morse.append(map.get(text.charAt(i)));
        }
        return morse.toString();
    }
}
