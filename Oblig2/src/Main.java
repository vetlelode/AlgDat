import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static int n = 5;
    public static void main(String [] args){
        n = 5;
        int[][] brett = new int[n][n];
        for(int X = 0; X < n; X++){
            for(int Y = 0; Y < n; Y++){
                brett[X][Y] = 0;
            }
        }
        int[] currentCord = {0,0};
        brett[currentCord[0]][currentCord[1]] = -1;
        System.out.println(printBoard(brett));

        ArrayList<Integer>possibleMovesHistory = new ArrayList<>();
        ArrayList<Integer[]>coordHistory = new ArrayList<>();
        coordHistory.add(new Integer[]{currentCord[0], currentCord[1]});
        int[] completeResetCounter = new int[]{0, 0};
        //Keep running until solved
        for(int iter = 0; iter < n*n; iter++){
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
                //Find the latest point where there was more than one possible move
                for(int lastPossibleBacktrack = possibleMovesHistory.size(); lastPossibleBacktrack > 0; lastPossibleBacktrack-- ){
                    System.out.print(iter-1+": There are no moves from [" + currentCord[0] + " " + currentCord[1]  + "] backtracking or resetting");
                    System.out.println(printBoard(brett));

                    int backIndex = lastPossibleBacktrack - 1;
                    int earliestOption = possibleMovesHistory.get(backIndex);
                    if(earliestOption > 1){
                        currentCord[0] = coordHistory.get(backIndex)[0];
                        currentCord[1] = coordHistory.get(backIndex)[1];
                        possibleMovesHistory.subList(backIndex, possibleMovesHistory.size()).clear();
                        coordHistory.subList(backIndex, coordHistory.size()).clear();
                        iter = possibleMovesHistory.size();
                        for(int X = 0; X < n; X++){
                            for(int Y = 0; Y < n; Y++){
                                if(brett[X][Y] > backIndex){
                                    brett[X][Y] = 0;
                                }
                            }
                        }
                        break;
                    }

                }

                continue;
            }
            possibleMovesHistory.add(currentlyPossible.size());

            Random rand = new Random();
            int i = rand.nextInt(currentlyPossible.size());
            int newX = currentCord[0] + possibleMovesX[currentlyPossible.get(i)];
            int newY = currentCord[1] + possibleMovesY[currentlyPossible.get(i)];
            coordHistory.add(new Integer[]{newX,newY});
            System.out.println(iter+": Moving from [" + currentCord[0] + " " + currentCord[1]  + "] To ["
                    + newX + " "
                    + newY + "] "
                    + currentlyPossible.size() + " Currently possible moves");
            currentCord[0] = newX;
            currentCord[1] = newY;
            if(!(iter == 0))
                brett[currentCord[0]][currentCord[1]] = iter;
            else
                brett[currentCord[0]][currentCord[1]] = -2;

        }
        System.out.println(printBoard(brett));
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
            boardText = boardText + "\n";
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
    public static int[] possibleMovesY = {1,-1,1,-1,2,-2,2,2};

}
