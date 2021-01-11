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
        //Count the total amount of rejected planes
        int rejectedCount = 0;
        //Count the idle time of the runway
        int idleTime = 0;

        System.out.println("How many iterations do you want the simulation to run for ?");
        //Define a "roof" for how long the simulation can keep adding planes, after it reaches this cap it stops adding new planes
        int simulationCap = 0;
        if(userParamReader.hasNextInt())
            simulationCap = userParamReader.nextInt();

        //Take the given input as the mean arrival rate.
        System.out.println("What do you want the mean arrival rate to be ? (Should be between 0 and 1)");
        double meanArrival = 0.5;
        if(userParamReader.hasNextFloat())
            meanArrival = userParamReader.nextDouble();

        //Scanner for reading user input
        Scanner userParamReaderTakeOff = new Scanner(System.in);
        //Take the given input as the mean arrival rate.
        System.out.println("What do you want the mean departure rate to be ? (Should be between 0 and 1)");
        double meanDeparture = 0.5;
        if(userParamReaderTakeOff.hasNextFloat())
            meanDeparture = userParamReaderTakeOff.nextDouble();

        //Loop that represents the operation of the airport.
        while(simulationIteration < simulationCap){
            simulationIteration++;
            //Define the runaway as clear at the start of one time unit
            runway.setTaken(false);

            //Add the planes from the random Poisson distribution, of the mean set by the user
            for(int j = 0; j < getPoissonRandom(meanArrival); j++){
                if(arrivalQue.size() < startFuel) {
                    //Only add to the arrival que if the que is small enough that the arriving plane can land.
                    uniqueID++;
                    arrivalQue.add(new Plane(simulationIteration, uniqueID, startFuel));
                }else{
                    rejectedCount++;
                }
            }

            //Add planes to the departure que based on the rate set by the user
            for(int j = 0; j < getPoissonRandom(meanDeparture); j++) {
                if(takeoffQue.size() < startFuel) {
                    //Reject the plane if the takeoff queue is larger than the start fuel variable
                    uniqueID++;
                    takeoffQue.add(new Plane(simulationIteration, uniqueID, startFuel));
                }else{
                    rejectedCount++;
                }
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
            if(!runway.isTaken()) {
                idleTime++;
                System.out.println("The runaway is unused " + runwayStatus(arrivalQue, takeoffQue));
            }

            //Subtract one unit of fuel from airborne planes on each tick
            for(Plane plane : arrivalQue){
                plane.detractRemainingFuel();
                //Write out an error if a plane crashes, and break the loop as something is wrong
                if(plane.getRemainingFuel() == 0){
                    System.err.println(plane + " Has crashed!");
                    break;
                }
            }
        }
        //Calculate the average wait time for an arriving plane
        double avgTakeoffWait = 0;
        for(Plane plane : arrivalHistory){
            avgTakeoffWait += startFuel - plane.getRemainingFuel();
        }
        avgTakeoffWait = avgTakeoffWait/arrivalHistory.size();

        //After the simulation loop is finished, print out a summary.
        System.out.println("\nSimulation finished.");
        System.out.println("Simulation ran for " + simulationIteration + " time units");
        System.out.println(arrivalHistory.size()+" Planes landed");
        System.out.println(takeoffHistory.size() +" Planes departed");
        System.out.println(takeoffQue.size() + " Planes did not get to takeoff");
        System.out.println(rejectedCount + " Planes were rejected");
        System.out.println(idleTime + " Time units the runway was idle");
        System.out.println(avgTakeoffWait+ " time units was the average arrival wait time");
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
