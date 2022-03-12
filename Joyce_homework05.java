import java.util.Scanner;

public class Joyce_homework05 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        sc.close();
        int a, b = 0;
        while (input/1 > 0) {
            a = input % 10 ;
            b = b * 10 + a;
            input = input / 10;
        }
        // for (; input/1 > 0;) {
        //     a = input % 10 ;
        //     b = b * 10 + a;
        //     input = input / 10;
        // }
        System.out.println(b);
    }
}
