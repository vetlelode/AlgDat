public class Runway {
    private boolean taken;
    private Plane currentPlane;

    public Runway() {
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public Plane getCurrentPlane() {
        return currentPlane;
    }

    public void setCurrentPlane(Plane currentPlane) {
        this.currentPlane = currentPlane;
    }

    @Override
    public String toString() {
        return "Runway{" +
                "taken=" + taken +
                ", currentPlane=" + currentPlane +
                '}';
    }
}
