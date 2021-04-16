import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class RobinHood {

        // Hashlengde
        private int hashLengde;

        // Hashtabell
        private String hashTabell[];

        // Antall elementer lagret i tabellen
        private int n;

        // Antall probes ved innsetting
        private int antProbes;

        // Konstruktør
        // Sjekker ikke for fornuftig verdi av hashlengden
        //
        public RobinHood(int lengde)
        {
            hashLengde = lengde;
            hashTabell = new String[lengde];
            n = 0;
            antProbes = 0;
        }

        // Returnerer load factor
        public float loadFactor()
        {
            return ((float) n)/hashLengde;
        }

        // Returnerer antall data i tabellen
        public int antData()
        {
            return n;
        }

        // Returnerer antall probes ved innsetting
        public int antProbes()
        {
            return antProbes;
        }

        // Hashfunksjon
        int hash(String S)
        {
            int h = Math.abs(S.hashCode());
            return h % hashLengde;
        }

        // Innsetting av tekststreng med lineær probing
        // Avbryter med feilmelding hvis ledig plass ikke finnes
        //
        void insert(String S)
        {
            // Beregner hashverdien
            int h = hash(S);

            // Lineær probing
            int neste = h;


            if (hashTabell[neste] != null)
            {
                //Endret Kode !
                // Ny probe
                antProbes++;

                int untilFree = 1;
                while(hashTabell[neste+ untilFree] != null) {

                    //Wrap Around
                    if (neste + untilFree >= hashLengde-1) {
                        untilFree = -neste;
                    }else{
                        untilFree++;
                    }
                }

                /*
                Under er måten jeg prøver å implementere Robinhoodsortering,
                jeg var veldig usikker på hvordan jeg kunne verifisere hva orginale hashverdien til en streng var.
                Jeg endte opp med kalkulere hashindeksen til ett element på nytt og sammenligne det med det nye elementet jeg ønsket å sette inn for å oppnå dette.
                Tviler litt på at dette er rette måten, men det var den eneste jeg kunne pønske ut.
                 */
                if(untilFree == 1){
                    //Hvis S > T kjør alternativ 1
                    if(hash(hashTabell[neste])< h)
                        hashTabell[neste+untilFree] = hashTabell[neste];
                    //Alternativ 2
                    else{
                        neste = neste + untilFree;
                    }
                }else if(untilFree > 1){
                    boolean allCanBeShifted = true;
                    for(int i = 0; i < untilFree; i++){
                        /*
                        Siden vi må flytte mer enn ett element til høyre så sjekker vi hvilken av de to alternative vi må bruke fram til neste node.
                        Hvis noen av nodene er lengre vekke fra sin orginale index en noden vi ønsker å kjøre inn så må vi kjøre alternativ 2
                        * */
                        if(hash(hashTabell[neste+i]) > h){
                            neste = neste + untilFree;
                            allCanBeShifted = false;
                            break;
                        }
                    }
                    //Alternativ en hvis
                    //Flytt alle elementer fra orginale neste til neste + untilFree til høyre, og begynn på slutten slik at du ikke overskriver eksisterende data
                    if(allCanBeShifted) {
                        for (int i = untilFree; i != 0; i--) {
                            hashTabell[neste + i] = hashTabell[neste + i - 1];
                        }
                    }
                }
            }

            // Lagrer tekststrengen på funnet indeks
            hashTabell[neste] = S;
            System.out.println(Arrays.toString(hashTabell));

            // Øker antall elementer som er lagret
            n++;
        }

        // Søking etter tekststreng med lineær probing
        // Returnerer true hvis strengen er lagret, false ellers
        //
        boolean search(String S)
        {
            // Beregner hashverdien
            int h = hash(S);

            // Lineær probing
            int neste = h;

            while (hashTabell[neste] != null)
            {
                // Har vi funnet tekststrengen?
                if (hashTabell[neste].compareTo(S) == 0)
                    return true;

                // Prøver neste mulige
                neste++;

                // Wrap-around
                if (neste >= hashLengde)
                    neste = 0;

                // Hvis vi er kommet tilbake til opprinnelig hashverdi,
                // finnes ikke strengen i tabellen
                if (neste == h)
                    return false;
            }

            // Finner ikke strengen, har kommet til en probe som er null
            return false;
        }
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
        //Endret Kode !
        // Hashlengde leses fra kommandolinjen
        int hashLengde = 5000;
        //Endret Kode !
        // Lager ny hashTabell
        //HashLinear hL = new HashLinear(hashLengde);
        RobinHood hL = new RobinHood(hashLengde);
        //Endret Kode !
        //Les input fra fil da det er mindre stress enn den innebygde måten
        File input = new File("input2.txt");
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
        //Endret Kode !
        String S = "grade";
        if (hL.search(S))
            System.out.println("\"" + S + "\"" + " finnes i hashtabellen");
        S = "Il Tempo Gigante";
        if (!hL.search(S))
            System.out.println("\"" + S + "\"" + " finnes ikke i hashtabellen");

    }



}
