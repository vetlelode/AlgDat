import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Leser størrelsen på labyrinten og prosentandel blokkerte ruter
        Scanner scanner = new Scanner(System.in);
        System.out.print("n?: ");
        int n = scanner.nextInt();
        Board board  = new Board(n, 0,0);
        while(board.anyLeft()){
            
        }

    }
}