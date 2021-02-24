import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static int n = 5;
    public static void main(String [] args){
        n = 6;
        int[][] brett = new int[n][n];
        for(int X = 0; X < n; X++){
            for(int Y = 0; Y < n; Y++){
                brett[X][Y] = 0;
            }
        }
        int[] currentCord = {0,0};
        brett[currentCord[0]][currentCord[1]] = 2;
        boolean notSolved = true;
        int iter = 0;
        int attempts = 0;
        //Keep running until solved
        while (notSolved) {
            iter++;
            //Find the possible moves
            ArrayList<Integer> currentlyPossible = new ArrayList<>();
            for (int possibleMove = 0; possibleMove < 8; possibleMove++) {
                int[] possibleCord = {possibleMovesX[possibleMove], possibleMovesY[possibleMove]};
                //Check that move won't put you outside the board
                if (moveLegal(currentCord, possibleCord)) {
                    //Check that the coordinate is not taken
                    if (brett[currentCord[0] + possibleCord[0]][currentCord[1] + possibleCord[1]] == 0)
                        currentlyPossible.add(possibleMove);
                }else continue;
            }
            if(currentlyPossible.size() == 0 ){
                if(iter == n*n) {
                    System.out.println("Found a path after " + attempts + " tries");
                    break;
                }
                System.out.println("No possible moves Restarting");
                attempts++;
                iter = 0;
                for(int X = 0; X < n; X++){
                    for(int Y = 0; Y < n; Y++){
                        brett[X][Y] = 0;
                    }
                }
                currentCord = new int[]{0,0};
                continue;
            }
            Random rand = new Random();
            int i = rand.nextInt(currentlyPossible.size());
            int newX = currentCord[0] + possibleMovesX[currentlyPossible.get(i)];
            int newY = currentCord[1] + possibleMovesY[currentlyPossible.get(i)];
            System.out.println(iter+" Moving from [" + currentCord[0] + " " + currentCord[1]  + "] To ["
                    + newX + " "
                    + newY + "]" );
            currentCord[0] = newX;
            currentCord[1] = newY;
            brett[currentCord[0]][currentCord[1]] = iter;
        }
    }

    public static boolean moveLegal(int[] current,int[] want){
        int newX = current[0] + want[0];
        int newY = current[1] + want[1];

        if(newX >= 0 && newY >= 0 && newX < n && n > newY) {
            return true;
        }
        return false;
    }
    public static int[] possibleMovesX = {2,2,-2,-2,1,1,-1,-1};
    public static int[] possibleMovesY = {1,-1,1,-1,2,-2,2,2};

}
