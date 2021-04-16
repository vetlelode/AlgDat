import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Hashing av tekststrenger med kjeding i lenket liste
// Bruker Javas innebygde hashfunksjon for strenger
//
// Enkel og begrenset implementasjon:
//
// - Ingen rehashing ved full tabell/lange lister
// - Tilbyr bare innsetting og søking
//
public class HashChained
{
    // Indre klasse:
    // Node med data, kjedes sammen i lenkede lister
    //
    private class HashNode
    {
        // Data, en tekststreng
        String data;
        // Neste node i listen
        HashNode neste;
        // Konstruktør for listenoder
        public HashNode(String S, HashNode hN)
        {
            data = S;
            neste = hN;
        }
    }

    // Hashlengde
    private int hashLengde;

    // Hashtabell, pekere til lister
    private HashNode hashTabell[];

    // Antall elementer lagret i tabellen
    private int n;

    // Antall kollisjoner ved innsetting
    private int antKollisjoner;

    // Konstruktør
    // Sjekker ikke for fornuftig verdi av hashlengden
    //
    public HashChained(int lengde)
    {
        hashLengde = lengde;
        hashTabell = new HashNode[lengde];
        n = 0;
        antKollisjoner = 0;
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

    // Returnerer antall kollisjoner ved innsetting
    public int antKollisjoner()
    {
        return antKollisjoner;
    }

    // Hashfunksjon
    int hash(String S)
    {
        int h = Math.abs(S.hashCode());
        return h % hashLengde;
    }

    // Innsetting av tekststreng med kjeding
    //
    void insert(String S)
    {
        // Beregner hashverdien
        int h = hash(S);

        // Øker antall elementer som er lagret
        n++;

        // Sjekker om kollisjon og om slettet
        if (hashTabell[h] != null)
            antKollisjoner++;

        // Setter inn ny node først i listen
        hashTabell[h] = new HashNode(S, hashTabell[h]);
    }

    void delete(String S){
        // Beregner hashverdien
        int h = hash(S);

        //Søk om ordet finnes i Hashen først
        if(!search(S)){
            return;
        }
        HashNode bucket = hashTabell[h];
        ArrayList<HashNode> nodes = new ArrayList<>();
        //Dette er sikkert feil måte å gjøre dette på men jeg finner ikke en måte å løse dette på uten å unlike hele listen først
        while(bucket.data != null){
            //Inkluder noder videre
            if(bucket.data.compareTo(S) != 0) {
                nodes.add(bucket);
                n--;
            }
            if(bucket.neste != null)
                bucket = bucket.neste;
            else
                break;
        }
        //Gjennopbygg kjeden når vi har fjernet ett element
        for(int i = 0; i < nodes.size()-1; i++){
            nodes.set(i,nodes.get(i).neste = nodes.get(i+1));
        }
        hashTabell[h] = nodes.get(0);

    }

    // Søking etter tekststreng i hashtabell med kjeding
    // Returnerer true hvis strengen er lagret, false ellers
    //
    boolean search(String S)
    {
        // Finner listen som S skal ligge i
        HashNode hN = hashTabell[hash(S)];

        // Leter gjennom listen
        while (hN != null)
        {
            // Har vi funnet tekststrengen?
            if (hN.data.compareTo(S) == 0)
                return true;
            // Prøver neste
            hN = hN.neste;
        }
        // Finner ikke strengen, har kommet til slutten av listen
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
        // Hashlengde leses fra kommandolinjen
        int hashLengde = 2000;

        // Lager ny hashTabell
        HashChained hC = new HashChained(hashLengde);

        //Les input fra fil da det er mindre stress enn den innebygde måten
        File input = new File("input2.txt");
        try{
            Scanner scn = new Scanner(input);
            while (scn.hasNext())
            {
                hC.insert(scn.nextLine());
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }


        // Skriver ut hashlengde, antall data lest, antall kollisjoner
        // og load factor
        System.out.println("Hashlengde  : " + hashLengde);
        System.out.println("Elementer   : " + hC.antData());
        System.out.printf( "Load factor : %5.3f\n",  hC.loadFactor());
        System.out.println("Kollisjoner : " + hC.antKollisjoner());

        // Et par enkle søk
        String S = "test";
        if (hC.search(S))
            System.out.println("\"" + S + "\"" + " finnes i hashtabellen");
        //Sjekk om elementet blir slettet
        hC.delete("test");
        if (hC.search(S))
            System.out.println("\"" + S + "\"" + " finnes i hashtabellen");

    }
}

