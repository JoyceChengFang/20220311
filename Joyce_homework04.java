import java.util.LinkedList;
import java.util.Scanner;

public class Joyce_homework04 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = 0;
        LinkedList<Integer> list = new LinkedList<>();
        while (a != -1) {
            a = sc.nextInt();
            list.add(a);
        }
        // for (; a != -1; ) {
        //     a = sc.nextInt();
        //     list.add(a);
        // }
        sc.close();
        list.removeLast();
        for (int i = list.size(); i > 0; i--) {
            System.out.println(list.removeLast());
        }
    }
}
