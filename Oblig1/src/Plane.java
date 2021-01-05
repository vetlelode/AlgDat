public class Plane implements Comparable<Plane> {
    private int tickID;
    private int startFuel;
    private int remainingFuel;
    private int waitTime;

    public Plane(int i ,int remainingFuel){
        this.tickID = i;
        this.startFuel = remainingFuel;
        this.remainingFuel = remainingFuel;
    }

    public int getTickID() {
        return tickID;
    }

    public void setTickID(int tickID) {
        this.tickID = tickID;
    }

    public int getStartFuel() {
        return startFuel;
    }

    public void setStartFuel(int startFuel) {
        this.startFuel = startFuel;
    }

    public int getRemainingFuel() {
        return remainingFuel;
    }

    public void setRemainingFuel(int remainingFuel) {
        this.remainingFuel = remainingFuel;
    }

    public void detractRemainingFuel(){
        this.remainingFuel -= 1;
    }

    public void addRemainingFuel(){
        if(this.remainingFuel < 5)
            this.remainingFuel += 1;
    }

    public void addToWaitTime(){
        this.waitTime++;
    }

    public int getWaitTime() {
        return waitTime;
    }

    @Override
    public int compareTo(Plane plane) {
        if (this.tickID == plane.getTickID()) {
            return 0;
        } else if (this.tickID < plane.getTickID()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Plane{" +
                "tickID=" + tickID +
                ", startFuel=" + startFuel +
                ", remainingFuel=" + remainingFuel +
                '}';
    }
}
