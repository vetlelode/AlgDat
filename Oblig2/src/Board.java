import java.util.ArrayList;

public class Board {
    private int n;
    private final int startX;
    private final int startY;
    private int currentX;
    private int currentY;

    private ArrayList<ArrayList<Cell>> cellList = new ArrayList<ArrayList<Cell>>();
    private ArrayList<Cell> moveHistory = new ArrayList<Cell>();
    public Board(int n, int startX, int startY) {
        this.n = n;
        this.startX = startX;
        this.startY = startY;
        for(int i = 0; i < n; i++){
            ArrayList<Cell> cellRow = new ArrayList<Cell>();
            for(int j = 0; j <n; j++){
                cellRow.add(new Cell(j, i, "FREE",-1 ));
            }
            this.cellList.add(cellRow);
        }
        this.currentX = startX;
        this.currentY = startY;
        moveToPos(new Cell(currentX,currentY,"TAKEN", 0 ), 0);
    }
    public boolean anyLeft(){
        for (ArrayList<Cell> cellRow : cellList){
            for (Cell cell : cellRow){
                return (cell.getMoveStatus() == -1);
            }
        }
    }

    public boolean notPreviouslyUsed(Cell cell){
        return (this.cellList.get(cell.getY()).get(cell.getX()).getMoveStatus() == -1);
    }
    //Check simply if the move is legal in terms of direction
    public boolean moveLegal(Cell cell){
        return (Math.abs(cell.getX() - this.currentX) == 2 && Math.abs(cell.getY() - this.currentY) == 1) || (Math.abs(cell.getX() - currentX) == 1 && Math.abs(cell.getY() - this.currentY) == 2);
    }

    public void moveToPos(Cell cell, int iter){
        //This is has a lot of abstractions
        if((moveLegal(cell) && notPreviouslyUsed(cell)) || iter == 0){
            //Change coords
            this.currentX = cell.getX();
            this.currentY = cell.getY();
            //My coordinate system is reversed :((
            this.cellList.get(currentY).get(currentX).setMoveStatus(cell.getMoveStatus());
            this.cellList.get(currentY).get(currentX).setStatus(cell.getStatus());
            moveHistory.add(getCurrentCell());
        } else {
            System.out.println("MOVE IS ILLEGAL, THE CHESS POLICE HAVE BEEN INFORMED AND ARE CURRENTLY EN ROUTE TO ARREST YOU");
        }
    }

    public Cell getCurrentCell(){
        return this.cellList.get(currentY).get(currentX);
    }

    @Override
    public String toString() {
        String board = "\n";
        for(int j = 0; j < n; j++)
            board += "----";
        for(int i = 0; i < n; i++){
            ArrayList<Cell> cellRow = cellList.get(i);
            board += "\n";
            for(Cell cell : cellRow)
                board += cell;
            board += "\n";
            for(int j = 0; j < n; j++)
                board += "----";
        }
        return "Board{" +
                "n=" + n +
                ", startX=" + startX +
                ", startY=" + startY +
                ", currentX=" + currentX +
                ", currentY=" + currentY +
                board;
    }
}
