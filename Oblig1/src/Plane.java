public class Plane implements Comparable<Plane> {
    private int tickID;
    private int uniqeID;
    private int startFuel;
    private int remainingFuel;
    private int waitTime;

    public Plane(int i ,int j,int remainingFuel){
        this.tickID = i;
        this.uniqeID = j;
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

    public int getUniqeID() {
        return uniqeID;
    }

    @Override
    public int compareTo(Plane plane) {
        if (this.uniqeID == plane.getUniqeID()) {
            return 0;
        } else if (this.uniqeID < plane.getUniqeID()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Plane " + uniqeID;
    }
}
