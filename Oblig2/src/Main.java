import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int n = 5;
    public static void main(String [] args){
        //Big sorry in advance to whoever has to correct this code.

        // Unorthodox solution to oblig 2 using a lot of randomisation.
        // Probably way slower than any proper algorithm,
        // but can solve up to n=7 in a okeyish time frame.
        // It also solved n=8 during testing, but this took a very long time :/

        Scanner scanner = new Scanner(System.in);
        System.out.print("N? ");
        n = scanner.nextInt();
        System.out.println("Starting X?(starts at 0)");
        int startingX = scanner.nextInt();
        System.out.println("Starting Y?(starts at 0)");
        int startingY = scanner.nextInt();
        int[][] brett = new int[n][n];
        for(int X = 0; X < n; X++){
            for(int Y = 0; Y < n; Y++){
                brett[X][Y] = 0;
            }
        }
        int[] currentCord = {startingX,startingY};
        brett[currentCord[0]][currentCord[1]] = -1;
        System.out.println(printBoard(brett));


        int stepBackCounter = 0;
        int totalIterations = 0;
        //Keep running until solved
        int iter = 1;
        while(iter < n*n){
            //Find the possible moves
            ArrayList<Integer> currentlyPossible = new ArrayList<>();
            for (int possibleMove = 0; possibleMove < 8; possibleMove++) {
                int[] possibleCord = {possibleMovesX[possibleMove], possibleMovesY[possibleMove]};
                //Check that move won't put you outside the board
                if (moveLegal(currentCord, possibleCord)) {
                    //Check that the coordinate is not taken
                    if (brett[currentCord[0] + possibleCord[0]][currentCord[1] + possibleCord[1]] == 0)
                        currentlyPossible.add(possibleMove);
                }
            }
            if(currentlyPossible.size() == 0 ){
                System.out.println(printBoard(brett));
                Random rand = new Random();
                int moveBackTo = rand.nextInt(iter);
                if(moveBackTo == 0)
                    moveBackTo = 1;
                stepBackCounter++;
                for(int X = 0; X < n; X++){
                    for(int Y = 0; Y < n; Y++){
                        if(brett[X][Y] > moveBackTo){
                            brett[X][Y] = 0;
                        }
                        else if(brett[X][Y] == moveBackTo) {
                            currentCord[0] = X;
                            currentCord[1] = Y;
                        }

                    }
                }
                totalIterations += iter;
                System.out.println("Dumping back to "+moveBackTo + " " + currentCord[0] +" " + currentCord[1]);
                iter = moveBackTo+1;
                continue;
            }

            Random rand = new Random();
            int i = rand.nextInt(currentlyPossible.size());
            int newX = currentCord[0] + possibleMovesX[currentlyPossible.get(i)];
            int newY = currentCord[1] + possibleMovesY[currentlyPossible.get(i)];
            System.out.println(iter+": Moving from [" + currentCord[0] + " " + currentCord[1]  + "] To ["
                    + newX + " "
                    + newY + "] "
                    + currentlyPossible.size() + " Currently possible moves");
            currentCord[0] = newX;
            currentCord[1] = newY;
            brett[currentCord[0]][currentCord[1]] = iter;
            iter++;
        }
        System.out.println(printBoard(brett));
        System.out.println("Solved with "+stepBackCounter + " attempts and " + totalIterations + " total operations");
    }

    public static boolean moveLegal(int[] current,int[] want){
        int newX = current[0] + want[0];
        int newY = current[1] + want[1];

        if(newX >= 0 && newY >= 0 && newX < n && n > newY) {
            return true;
        }
        return false;
    }
    public static String printBoard(int[][]board){
        String boardText = "";
        for(int row = 0; row < board.length; row++){
            boardText += "\n";
            for(int cell = 0; cell < board[row].length; cell++){
                if(board[row][cell] <= 9 && board[row][cell] >= 0 )
                    boardText += " [0" +board[row][cell] +"] ";
                else
                    boardText += " [" +board[row][cell] +"] ";
            }
        }
        return boardText;
    }



    public static int[] possibleMovesX = {2,2,-2,-2,1,1,-1,-1};
    public static int[] possibleMovesY = {1,-1,1,-1,2,-2,2,-2};

}
