import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Queue<Plane> arrivalQue = new PriorityQueue<Plane>();
        Queue<Plane> takeoffQue = new PriorityQueue<Plane>();
        Runway runway = new Runway();
        for(int i = 0; i <= 10; i++){
            //Define the runaway as clear
            runway.setTaken(false);

            for(int j = 0; j < getPoissonRandom(0.8); j++){
                arrivalQue.add(new Plane(i,5));
            }

            //Land a plane if available
            if(arrivalQue.size() >= 1){
                if(arrivalQue.peek().getRemainingFuel() <= 5) {
                    runway.setTaken(true);
                    Plane plane = arrivalQue.poll();
                    System.out.println(plane + " Is landing");
                    takeoffQue.add(plane);
                    plane.setRemainingFuel(5);
                }
            }
            //A plane takes off if there is no landing plane
            if(takeoffQue.size() >= 1 & !runway.isTaken()) {
                runway.setTaken(true);
                Plane plane = takeoffQue.poll();
                System.out.println(plane + " Is taking off");
            }
            //Print a message if the runway is idle
            if(!runway.isTaken())
                System.out.println("The runaway is unused");

            //Subtract and add one unit of fuel from airborne/grounded planes
            for(Plane plane : arrivalQue){
                plane.detractRemainingFuel();
            }
            for(Plane plane : takeoffQue){
                plane.addRemainingFuel();
            }
        }
    }

    private static int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do
        {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

}
