import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    // Enkelt testprogram:
    //
    // * Hashlengde gis som input på kommandolinjen
    //
    // * Leser tekststrenger linje for linje fra standard input
    //   og lagrer dem i hashtabellen
    //
    // * Skriver ut litt statistikk etter innsetting
    //
    // * Tester om søk fungerer for et par konstante verdier
    //
    public static void main(String argv[])
    {
        // Hashlengde leses fra kommandolinjen
        int hashLengde = 50;

        // Lager ny hashTabell
        HashLinear hL = new HashLinear(hashLengde);
        //Les input fra fil da det er mindre stress enn den innebygde måten
        File input = new File("input.txt");
        try{
            Scanner scn = new Scanner(input);
            while (scn.hasNext())
            {
                hL.insert(scn.nextLine());
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }


        // Leser input og hasher alle linjer


        // Skriver ut hashlengde, antall data lest, antall kollisjoner
        // og load factor
        System.out.println("Hashlengde  : " + hashLengde);
        System.out.println("Elementer   : " + hL.antData());
        System.out.printf( "Load factor : %5.3f\n",  hL.loadFactor());
        System.out.println("Probes      : " + hL.antProbes());
        // Et par enkle søk
        String S = "Volkswagen Karmann Ghia";
        if (hL.search(S))
            System.out.println("\"" + S + "\"" + " finnes i hashtabellen");
        S = "Il Tempo Gigante";
        if (!hL.search(S))
            System.out.println("\"" + S + "\"" + " finnes ikke i hashtabellen");

    }
}
