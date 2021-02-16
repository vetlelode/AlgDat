public class Cell {
    private int Y;
    private int X;
    private String status;
    private int moveStatus;

    public Cell(int y, int x, String status, int moveStatus) {
        Y = y;
        X = x;
        this.status = status;
        this.moveStatus = moveStatus;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMoveStatus() {
        return moveStatus;
    }

    public void setMoveStatus(int moveStatus) {
        this.moveStatus = moveStatus;
    }

    @Override
    public String toString() {
        if(moveStatus < 0 || moveStatus > 9)
            return "["+Integer.toString(moveStatus)+"]";
        else
            return "[ " +Integer.toString(moveStatus)+"]";
    }
}
