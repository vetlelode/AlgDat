import java.util.*;

/**
 * @author  Vetle Mæland Lode
 */

public class Main {
    public static void main(String[] args) {
        //Que for planes waiting to land
        Queue<Plane> arrivalQue = new PriorityQueue<>();
        //List containing all arriving planes that have landed
        ArrayList<Plane> arrivalHistory = new ArrayList<>();
        //Queue for planes waiting to depart
        Queue<Plane> takeoffQue = new PriorityQueue<>();
        //List containing all planes that have departed
        ArrayList<Plane> takeoffHistory = new ArrayList<>();
        //Define the unique ID ticker that will be used for all of the planes
        int uniqueID = 0;
        //Defines the current "Tick" of the simulation
        int simulationIteration = 0;
        //Variable representing the fuel reserves of arriving planes
        int startFuel = 3;
        //Scanner for reading user input
        Scanner userParamReader = new Scanner(System.in);
        //Define the simulations runway
        Runway runway = new Runway();

        System.out.println("How many iterations do you want the simulation to run for ?");
        //Define a "roof" for how long the simulation can keep adding planes, after it reaches this cap it stops adding new planes
        int simulationCap = 0;
        if(userParamReader.hasNextInt())
            simulationCap = userParamReader.nextInt();

        //Take the given input as the mean arrival rate.
        System.out.println("What do you want the mean arrival rate to be ? (Must be between 0 and 1)");
        double meanArrival = 0.5;
        if(userParamReader.hasNextFloat())
            meanArrival = userParamReader.nextDouble();

        //Scanner for reading user input
        Scanner userParamReaderTakeOff = new Scanner(System.in);
        //Take the given input as the mean arrival rate.
        System.out.println("What do you want the mean departure rate to be ? (Must be between 0 and 1)");
        double meanDeparture = 0.5;
        if(userParamReaderTakeOff.hasNextFloat())
            meanDeparture = userParamReaderTakeOff.nextDouble();

        //Loop that represents the operation of the airport.
        while(simulationIteration < simulationCap){
            simulationIteration++;
            //Define the runaway as clear at the start of one time unit
            runway.setTaken(false);
            //Add the planes from the random Poisson distribution.
            for(int j = 0; j < getPoissonRandom(meanArrival); j++){
                uniqueID++;
                arrivalQue.add(new Plane(simulationIteration, uniqueID, startFuel));
            }
            //Add planes to the departure que based on the rate set by the user
            for(int j = 0; j < getPoissonRandom(meanDeparture); j++) {
                uniqueID++;
                takeoffQue.add(new Plane(simulationIteration, uniqueID, startFuel));
            }

            //Land the first plane if available
            if(arrivalQue.size() >= 1){
                if(arrivalQue.peek().getRemainingFuel() <= 5) {
                    runway.setTaken(true);
                    Plane plane = arrivalQue.poll();
                    System.out.println(plane+ " Is landing " +runwayStatus(arrivalQue,takeoffQue));
                    arrivalHistory.add(plane);
                }
            }

            //A plane takes off if there is no landing plane
            if(takeoffQue.size() >= 1 & !runway.isTaken()) {
                runway.setTaken(true);
                Plane plane = takeoffQue.poll();
                System.out.println(plane + " Is departing " +runwayStatus(arrivalQue,takeoffQue));
                takeoffHistory.add(plane);
            }

            //Print a message if the runway is idle
            if(!runway.isTaken())
                System.out.println("The runaway is unused " +runwayStatus(arrivalQue,takeoffQue));

            //Subtract and add one unit of fuel from airborne planes on each tick
            for(Plane plane : arrivalQue){
                plane.detractRemainingFuel();
                //Write out an error if a plane crashes
                if(plane.getRemainingFuel() == 0){
                    System.out.println(plane + " Go boom");
                }
            }
        }
        System.out.println("\nSimulation finished.");
        System.out.println(arrivalHistory.size()+" Planes landed; " + takeoffHistory.size() +" Planes departed over " + simulationCap + " time units");
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
