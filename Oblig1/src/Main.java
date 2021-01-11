import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

/**
 * @author  Vetle Mæland Lode
 */

public class Main {
    public static void main(String[] args) {
        //Define two separate Que's one for planes landing and another for planes taking off
        Queue<Plane> arrivalQue = new PriorityQueue<>();
        Queue<Plane> takeoffQue = new PriorityQueue<>();
        //Define the unique ID ticker that will be used for all of the planes
        int uniqeID = 0;
        //Defines the current "Tick" of the simulation
        int simulationIteration = 0;
        //Scanner for reading user input
        Scanner userParamReader = new Scanner(System.in);

        System.out.println("How many iterations do you want the simulation to run for ?");
        //Define a "roof" for how long the simulation can keep adding planes, after it reaches this cap it stops adding new planes
        int simulationCap = 0;
        if(userParamReader.hasNextInt())
            simulationCap = userParamReader.nextInt();

        Runway runway = new Runway();
        //Take the given input as the mean arrival rate.
        System.out.println("What do you want the mean arrival rate to be ? (Must be between 0 and 1)");
        double meanArrival = 0.5;
        if (userParamReader.hasNextDouble())
            meanArrival = userParamReader.nextDouble();

        //Take the given input as the mean arrival rate.
        System.out.println("What do you want the mean departure rate to be ? (Must be between 0 and 1)");
        double meanDeparture = 0.5;
        if (userParamReader.hasNextDouble())
            meanDeparture = userParamReader.nextDouble();

        //Loop that represents the operation of the airport.
        while(simulationIteration < simulationCap){
            simulationIteration++;
            //Define the runaway as clear at the start of one time unit
            runway.setTaken(false);
            //Add the planes from the random Poisson distribution.
            for(int j = 0; j < getPoissonRandom(meanArrival); j++){
                uniqeID++;
                arrivalQue.add(new Plane(simulationIteration, uniqeID, 3));
            }
            //Add planes to the departure que based on the rate set by the user
            for(int j = 0; j < getPoissonRandom(meanDeparture); j++) {
                uniqeID++;
                takeoffQue.add(new Plane(simulationIteration, uniqeID, 3));
            }

            //Land the first plane if available
            if(arrivalQue.size() >= 1){
                if(arrivalQue.peek().getRemainingFuel() <= 5) {
                    runway.setTaken(true);
                    Plane plane = arrivalQue.poll();
                    System.out.println(plane+ " Is landing " +runwayStatus(arrivalQue,takeoffQue));
                }
            }
            //A plane takes off if there is no landing plane
            if(takeoffQue.size() >= 1 & !runway.isTaken()) {
                runway.setTaken(true);
                Plane plane = takeoffQue.poll();
                System.out.println(plane + " Is taking off " +runwayStatus(arrivalQue,takeoffQue));
            }
            //Print a message if the runway is idle
            if(!runway.isTaken())
                System.out.println("The runaway is unused " +runwayStatus(arrivalQue,takeoffQue));

            //Subtract and add one unit of fuel from airborne planes on each tick
            for(Plane plane : arrivalQue){
                plane.detractRemainingFuel();
                if(plane.getRemainingFuel() == 0){
                    System.out.println(plane + " Go boom");
                }
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
    private static String runwayStatus(Queue<Plane> arrivalQue, Queue<Plane> takeoffQue){
        return "("+arrivalQue.size() + " Plane(s) waiting to land, " + takeoffQue.size() +" Plane(s) waiting to takeoff)";
    }
}
