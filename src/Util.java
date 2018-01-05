package cs202_program5;
import java.util.Scanner;
import java.util.Random;

/**
 * Ryan Hoover
 * CS 202 - Fant
 * Program 5 - 06/14/2017
 *
 * Utility methods usable by all classes,
 * loop controls (Again, Confirm, BackToMenu)
 * universal input object
 */

public class Util {
    protected static Scanner input;
    protected Random rand;
    public static int SIZE = 25; // global constant

    public Util() {
        input = new Scanner(System.in);
        rand = new Random();
    }

    // used as loop control for user to add more ingredients, add another Pizza, etc.
    public boolean Again() {
        String response;
        do {
            System.out.print(" >> Again? (y,n): ");
            response = input.next();
            input.nextLine();
        } while (!response.toUpperCase().equals("Y") && !response.toUpperCase().equals("N"));
        return (response.toUpperCase().equals("Y"));
    }

    // used as loop condition for user input, if !Confirm, start loop over
    public boolean Confirm() {
        String response;
        do {
            System.out.print(" >> Confirm? (y,n): ");
            response = input.next();
            input.nextLine();
        } while ((!response.toUpperCase().equals("Y")) && (!response.toUpperCase().equals("N")));
        return (response.toUpperCase().equals("Y"));
    }

    // used in menu system to let user go back to the menu selection, 'No' quits program
    public static boolean BackToMenu() {
        String response;
        do {
            System.out.print(" >> Back to menu? (y,n): ");
            response = input.next();
            input.nextLine();
        } while (!response.toUpperCase().equals("Y") && !response.toUpperCase().equals("N"));
        return (response.toUpperCase().equals("Y"));
    }

    // Capitalize input,
    public String Capitalize(String source) {
        char[] array = source.toCharArray();
        array[0] = Character.toUpperCase((array[0]));
        return new String(array);
    }

    // display a main menu from the program boot
    public static int Menu() {
        //int test = rand.nextInt(50) + 1;
        //System.out.println("\nrand = " + test);
        int userChoice = 0;
        System.out.println("\n+ + Main Menu + + + +" +
                "\nPress > 1 < For new item." +
                "\nPress > 2 < To Display All." +
                "\nPress > 3 < To Return to previous.");
        do {
            System.out.print("\n >> Enter a number: ");
            if (input.hasNextInt())
                userChoice = input.nextInt();
            else
                input.next();
            input.nextLine();
        } while (userChoice > 3);
        return userChoice;
    }

    /*
    public int mainMenu() {
        int userChoice = 0;
        System.out.println("\n+ + Main Menu + + + +" +
                "\nPress > 1 < To Order a Pizza!" +
                "\nPress > 2 < To See All orders, starting from most Recent." +
                "\nPress > 3 < To Quit Program.");
        do {
            System.out.print("\n >> Enter a number: ");
            if (input.hasNextInt())
                userChoice = input.nextInt();
            else
                input.next();
            input.nextLine();
        } while (userChoice > 3);
        return userChoice;
    }
    */
}
