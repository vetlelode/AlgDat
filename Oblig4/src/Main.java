import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        /*
        I chose to read a input file instead of normal input as it allows more flexibility and testing with larger texts
        The file submitted along with my assignment is just a Lorem Ipsum text, but i have tried everything from a couple of words to the full King James bible with success
         */
        File input = new File("input.txt");
        String txt = "";
        //Read the input file and uppercase it.
        try{
            Scanner scn = new Scanner(input);
            while(scn.hasNextLine()){
                txt += scn.nextLine().toUpperCase() + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Dark regex magic to remove all non word characters like , and . etc..
        txt = txt.replaceAll("[^*a-zA-Z ]", "");
        //Split into words
        String[]words = txt.split(" ");

        //Create a tree and insert all the words (The insert method deals with the sorting)
        Tree tree = new Tree();
        for(String word : words){
            tree.insert(word);
        }
        System.out.println("hei".compareTo("a"));
        tree.printAlphabetically();

    }
}
