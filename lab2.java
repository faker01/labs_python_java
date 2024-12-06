import java.util.Arrays;
import java.util.Scanner;



public class Main {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int N = in.nextInt();
        if (N == 1) {
            task_1();
        }
        else if (N == 2) {
            task_2();
        }
        else if (N == 3) {
            task_3();
        }
        else if (N == 4) {
            task_4();
        }
        else if (N == 5) {
            task_5();
        }
        else if (N == 6) {
            task_6();
        }
        else if (N == 7) {
            task_7();
        }
        else if (N == 8) {
            task_8();
        }
    }


    static void task_1(){
        int k = 0;
        int max = 0;
        String strr = "";
        String inp = in.next();
        String ans = "";
        for (int i = 0; i <= inp.length(); i++) {
            System.out.println(strr);
            System.out.println(k);
            try {
                if (strr.indexOf(inp.substring(i, i + 1)) > -1) {
                    if (k > max) {
                        max = k;
                        ans = strr;
                        strr = inp.substring(i, i + 1);
                        k = 1;
                    }
                    else {
                        strr = inp.substring(i, i + 1);
                        k = 1;
                    }
                }
                else {
                    k += 1;
                    strr = strr + inp.charAt(i);
                }
            }
            catch (Exception e) {
                System.out.println(e);
                if (k > max) {
                    max = k;
                    ans = strr;
                }
            }

        }
        System.out.println(ans);
    }


    static void task_2(){
        System.out.println("для остановки ввода ввести: ` или ё");
        String[] a;
        String[] b;
        String inp = in.next();
        String ans = "";
        while (!inp.equals("`")){
            ans += inp;
            ans += "::";
            inp = in.next();
        }
        a = ans.split("::");

        ans = "";
        inp = in.next();
        while (!inp.equals("`")){
            ans += "::";
            ans += inp;
            inp = in.next();
        }
        b = ans.split("::");
        String[] answer = new String[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            answer[i] = a[i];
        }
        for (int i = a.length; i < a.length + b.length; i++) {
            answer[i] = b[i - a.length];
        }
        Arrays.sort(answer);
        for (int i = 0; i < a.length + b.length; i++) {
            System.out.print(answer[i] + ", ");
        }
    }


    static void task_3() {
        System.out.println("введите длину массива:");
        int n = in.nextInt();
        int[] arr = new int[n];
        System.out.println("введите элементы массива:");
        for (int i = 0; i < n; i++) {
        arr[i] = in.nextInt();
        }
        int max = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = arr[i];
            for (int j = i + 1; j < n; j++) {
                if (sum > max) {
                    max = sum;
                }
                sum += arr[j];
            }

        }
        System.out.println(max);
    }


    static void task_4() {
        System.out.println("введите ширину массива:");
        int h = in.nextInt();
        System.out.println("введите длину массива:");
        int l = in.nextInt();
        int[][] arr = new int[h][l];
        System.out.println("введите элементы массива:");
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                arr[i][j] = in.nextInt();
            }
        }
        int[][] ans = new int[l][h];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < h; j++) {
                ans[i][j] = arr[h - j - 1][i];
            }
        }
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < h; j++) {
                System.out.print(ans[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void task_5() {
        System.out.println("введите длину массива:");
        int n = in.nextInt();
        int[] arr = new int[n];
        System.out.println("введите элементы массива:");
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        Arrays.sort(arr);
        System.out.println("Введите число target");
        int target = in.nextInt();
        int k = 0;
        int new_n = n;
        while (arr[n - k - 1] > target) {
            arr[n - k - 1] = 0;
            k++;
            new_n--;
        }
        int[] new_arr = new int[new_n];
        new_arr = arr;

        int sum = 0;
        boolean flag = false;
        int num1 = 0;
        int num2 = 0;

        for (int i = 0; i < new_n; i++) {
            sum = new_arr[new_n - i - 1];
            for (int j = 0; j < new_n - i; j++) {
                if (sum + new_arr[j] == target) {
                    num2 = new_arr[j];
                    flag = true;
                    break;
                }
                if (sum + new_arr[j] > target) {
                    break;
                }
            }
            if (flag) {
                num1 = new_arr[new_n - i - 1];
                break;
            }
        }
        if (flag) {
            System.out.println(num1);
            System.out.println(num2);
        }
        else {
            System.out.println("null");
        }

    }


    static void task_6() {
        System.out.println("введите ширину массива:");
        int h = in.nextInt();
        System.out.println("введите длину массива:");
        int l = in.nextInt();
        int[][] arr = new int[h][l];
        System.out.println("введите элементы массива:");
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                arr[i][j] = in.nextInt();
            }
        }
        int sum = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                sum += arr[i][j];
            }
        }
        System.out.println(sum);
    }


    static void task_7() {
        System.out.println("введите ширину массива:");
        int h = in.nextInt();
        System.out.println("введите длину массива:");
        int l = in.nextInt();
        int[][] arr = new int[h][l];
        System.out.println("введите элементы массива:");
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                arr[i][j] = in.nextInt();
            }
        }

        int[] ans = new int[h];
        int max;
        for (int i = 0; i < h; i++) {
            max = 0;
            for (int j = 0; j < l; j++) {
                if (max < arr[i][j]) {
                    max = arr[i][j];
                };
            }
            ans[i] = max;
        }
        for (int i = 0; i < h; i++) {
            System.out.println(ans[i]);
        }
    }


    static void task_8() {
        System.out.println("введите ширину массива:");
        int h = in.nextInt();
        System.out.println("введите длину массива:");
        int l = in.nextInt();
        int[][] arr = new int[h][l];
        System.out.println("введите элементы массива:");
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                arr[i][j] = in.nextInt();
            }
        }
        int[][] ans = new int[l][h];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < h; j++) {
                ans[i][j] = arr[j][l - i - 1];
            }
        }
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < h; j++) {
                System.out.print(ans[i][j] + " ");
            }
            System.out.println();
        }
    }
}
