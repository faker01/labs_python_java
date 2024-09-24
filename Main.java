import java.util.Scanner;


public class Main {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int N = in.nextInt();
        if (N == 1) {
            syracuse_sequence();
        }
        else if (N == 2) {
            sum_of_a_series();
        }
        else if (N == 3) {
            looking_for_treasure();
        }
        else if (N == 4) {
            logistics_maximin();
        }
        else if (N == 5) {
            twice_even_number();
        }
    }

    static void syracuse_sequence(){
        int k = 0;
        int n = in.nextInt();
        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
            k += 1;
        }
        System.out.println(k);
    }

    static void sum_of_a_series(){
        int n = in.nextInt();
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                sum += in.nextInt();
            }
            else {
                sum -= in.nextInt();
            }
        }
        System.out.println(sum);
    }

    static void looking_for_treasure() {
        int x = 0;
        int y = 0;
        int x_1 = in.nextInt();
        int y_1 = in.nextInt();
        int k = 0;
        String side = in.next();
        int l;
        while (side != "стоп") {
            l = in.nextInt();
            if (side == "север") {
                x += l;
            }
            else if (side == "юг") {
                x -= l;
            }
            else if (side == "запад") {
                y -= l;
            }
            else {
                y += l;
            }
            k += 1;
            if (x == x_1 && y == y_1) {
                break;
            }
            side = in.next();

        }
        System.out.println(k);
    }

    static void logistics_maximin() {
        int n = in.nextInt();
        int num_of_tunnels;
        int h;
        int tunnel = 0;
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            num_of_tunnels = in.nextInt();
            min = Integer.MAX_VALUE;
            for (int j = 1; j <= num_of_tunnels; j++) {
                h = in.nextInt();
                if (h < min) {
                    min = h;
                }
            }
            if (max < min){
                max = min;
                tunnel = i;
            }
        }
        System.out.println(Integer.toString(tunnel) + " " + Integer.toString(max));
    }

    static void twice_even_number() {
        int n = in.nextInt();
        if ((n % 10 + n % 100 / 10 + n / 100) % 2 == 0 && ((n % 10) * (n % 100 / 10) * (n / 100)) % 2 == 0){
            System.out.println("Является");

        }
        else{
            System.out.println("Не является");
        }
    }
}
