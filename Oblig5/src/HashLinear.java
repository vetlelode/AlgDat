import java.util.Arrays;

public class HashLinear {
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
    public HashLinear(int lengde)
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
        System.out.println(Arrays.toString(hashTabell));

        if (hashTabell[neste] != null)
        {
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
            System.out.println(untilFree + " " +Arrays.toString(hashTabell));
            if(untilFree == 1){
                //Logic if only one elment needs ot be shifted to the right
                hashTabell[neste+untilFree] = hashTabell[neste];
            }else if(untilFree > 1){
                //Logic for shifting more than one element ot the right
                for(int i = untilFree; i != 0; i--){
                        hashTabell[neste+i] = hashTabell[neste+i-1];
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

}
