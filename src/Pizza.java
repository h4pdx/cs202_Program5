package cs202_program5;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/** Ryan Hoover
 * CS 202 - Fant
 * Program 4 - 06/02/2017
 *
 * Created by ryan on 6/1/17.
 * hold an array of ingredients, Toppings read in from file
 * This class does the bulk of the work, building the pizza
 * And directing the Ingredient subclasses to fill from input, or assign based on user selection from text file
 * BuildPizza() uses Find() to search array of Toppings and return index number of the item the user selected
 * Ingredient[] currentOrder is what makes up the actual Pizza,
 * as it includes 1 crust, 1 sauce, and user-defined amount of Toppings
 * Topping[] availTops is a list of the available toppings, read in from the toppings.txt file
 * Functions called from within Orders class, add Pizzas to the4
 * list of Orders
 */

public class Pizza extends Name {
    protected Ingredient [] currentOrder; // the array that makes up the contents of the current Pizza
    protected boolean isVegan; // checked against isVegan values in currentOrder[], only true if all are true
    protected int ingredientCount; // incremented when an ingredient is added
    //protected int orderPrice; // accumulated prices of currentOrder[] indices
    protected Topping [] availTops; // array populated by text file data
    protected int toppingCount; // keeps count of available toppings that were read in from the text file

    public Pizza() throws IOException {
        super();
        // initialize our arrays to null
        this.currentOrder = new Ingredient[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            this.currentOrder[i] = null;
        }
        this.availTops = new Topping[SIZE];
        for (int i = 0; i < SIZE; ++i){
            this.availTops[i] = null;
        }
        this.isVegan = false;
        this.ingredientCount = 0;
        //this.orderPrice = 0;
        // read in from the text file after values have been initialized
        ReadFile();
    }

    // copy constructor
    public Pizza(Pizza source) throws IOException {
        super(source);
        this.ingredientCount = source.ingredientCount;
        this.toppingCount = source.toppingCount;
        // this is choppy waters for me, array copy const for dynamically bound derived class refs
        this.currentOrder = new Ingredient[this.ingredientCount];
        this.currentOrder[0] = new Crust((Crust) source.currentOrder[0]);
        this.currentOrder[1] = new Sauce((Sauce) source.currentOrder[1]);
        for (int i = 2; i < ingredientCount; ++i) {
            this.currentOrder[i] = new Topping((Topping) source.currentOrder[i]);
        }
        this.availTops = new Topping[toppingCount];
        for (int i = 0; i < toppingCount; ++i) {
            this.availTops[i] = new Topping(source.availTops[i]);
        }
        this.isVegan = source.isVegan;
        this.price = source.price;
    }

    public int getOrderPrice() {return this.price;}

    // Read toppings in from a file (toppings.txt -> availTops[])
    public int ReadFile() throws IOException {
        try {
            String name = new String(); // temporary variables
            int price = 0, calories = 0; // file data is stored here first, then transferred to Topping ref
            boolean isVegan = false;
            File file = new File("toppings.txt"); // path name
            Scanner fileIn = new Scanner(file); // new scanner object for File
            int i = 0; // index of availTops[]
            // loop continues until the end of the file
            while (fileIn.hasNextLine()) {
                String line = fileIn.nextLine(); // read in first line
                String fileArray[];// = new String[SIZE];
                fileArray = line.split(":", SIZE); // return an array split String at delimiters (":")
                // order of appearance = String name, boolean isVegan, int price, int calories
                name = fileArray[0]; // name will be first index in split array
                isVegan = Boolean.parseBoolean(fileArray[1]); // bool is 2nd index, convert from string
                price = Integer.parseInt(fileArray[2]); // price is 3rd index, convert from string
                calories = Integer.parseInt(fileArray[3]); // calories are 4th index, convert from string
                this.availTops[i] = new Topping(name,isVegan,price,calories); //invoke argument constructor
                ++i; // advance in availTops[]
            }
            this.toppingCount = i; // update number of available toppings
            fileIn.close();
        }
        // IDE put this in, still not sure about exception handling
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return this.toppingCount;
    }

    // call individual ingredient class input functions to add ingredient into our array
    public void Input() throws IOException {
        int i = 0, found;
        super.name = "Custom Order";
        this.currentOrder[i] = new Crust(); // first ingredient needs to be one crust ref
        this.currentOrder[i].Input(); // display file contents and present user with optionsA
        this.currentOrder[i+1] = new Sauce(); // second ingredient is sauce ref
        this.currentOrder[i+1].Input(); // specify what kind of sauce from a list
        i = 2; // start adding topping objects after 1 sauce and 1 crust object have been added
        do {
            do {
                // return index# of availableToppings[] matched to user input
                found = this.Find(availTops);
                if (found == -1) {System.out.println("\nTopping not available! Try again.");}
            } while (found == -1);
            // no input for topping, just copy constructor to copy appropriate topping from available array
            this.currentOrder[i] = new Topping(availTops[found]);
            ++i;
        } while (i < this.currentOrder.length && Again()); // user adds toppings until satisfied
        this.ingredientCount = i;       // update class ingredient counter
        this.price = this.orderPrice(); // update total price of pizza
        this.isVegan = this.isVegan();       // check to see if pizza is vegan or not based on added ingredients
    }

    // Display complete information about a given Pizza object
    public void Display() {
        // Display number of toppings (total ingredients, -1 sauce, -1 crust)
        System.out.print("\n" + super.name + " Pizza (" + (ingredientCount -2) + " toppings):");
        System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~"); // banner, to be conspicuous in a list
        // display contents of dynamically bound ingredient array (crust, sauce, toppings)
        // loop conditions can't be SIZE, will throw NullPtrException
        for (int i = 0; i < ingredientCount; ++i) {
            if (i > 1) {System.out.print("\n(" + (i -1) + ") ");} // displays number next to toppings only (flair)
            currentOrder[i].Display(); // overridden Ingredient subclass display methods
        }
        // isVegan() result checked
        if (this.isVegan) {System.out.print("\nPizza is Vegan.");} else {System.out.print("\nPizza is not Vegan.");}
        // accumulated add-on prices of all ingredients
        System.out.print("\n >> Order Total Price: $" + this.price + ".00");
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    // search topping array for match, based on menu selection
    public int Find(Ingredient [] options) {
        int i = 0, fail = -1, match = 1;
        System.out.println("\nMenu:\n~~~~~~~~~~~~~~~~~~~~~~~~"); // banner to easily distinguish options in a list
        // make sure to use toppingCount, don't access empty array elements
        for (int j = 0; j < toppingCount; ++j) {
            // show a menu of available toppings, and a number for choosing
            System.out.print("(" + ( j +1 ) + ") "); // each item will have a number displayed (1) Bacon ...
            options[j].Display(); // options[] can be Ingredients(dynamic), or any of the subclasses individually
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        do {
            // input menu number
            System.out.print("\nWhat will you add to the Pizza?: ");
            // user input
            do {
                if (input.hasNextInt())
                    match = input.nextInt();
                else input.next();
                input.nextLine();
                // do it again if they enter an out-of-bounds number
            } while (match != 0 && match > toppingCount);
            System.out.println("\nYou chose: " + options[match-1].name + ",");
        } while (!Confirm());
        while (i < options.length) {
            if (i == (match -1)) {
                // might as well just return the index number immediately
                System.out.println("\n" + options[match-1].name + " Added to Current Order!\n");
                // user wants option 1, which is at index 0
                return (match -1);
            }
            ++i;
        }
        // if the loop happens to exit without finding a match
        return fail;
    }

    // check to see if our pizza is vegan
    public boolean isVegan() {
        for (int i = 0; i < ingredientCount; ++i) {
            if (!currentOrder[i].isVegan()) {
                // no need to traverse further, this one item renders entire pizza not vegan
                return false;
            }
        }
        // otherwise the array traversed fully, all ingredients are vegan
        return true;
    }

    // get price of entire pizza
    public int orderPrice() {
        for (int i = 0; i < ingredientCount; ++i) {
            // accumulate add-on prices of ingredients
            this.price += currentOrder[i].getPrice();
        }
        return this.price;
    }

    public void Special() {
        int randIndex;
        int i = 0;
        super.name = "Special Order";
        this.currentOrder[i] = new Crust("Classic", false, 14, "Classic", "Large");
        this.currentOrder[i +1] = new Sauce("Classic Tomato", true, 0, "Normal");
        this.ingredientCount = i;
        int toppingAmt = 5;
        this.ingredientCount += toppingAmt;
        for (i = 2; i < toppingAmt; ++i) {
            randIndex = rand.nextInt(19);
            this.currentOrder[i] = new Topping(availTops[randIndex]);
        }
        this.price = orderPrice() - 2;
        this.isVegan = isVegan();
    }

    // make new derived object and assign it to Node's data ref
    public Name Copy() throws IOException {
        Pizza newPizza = new Pizza();
        newPizza.Copy(this);
        return newPizza;
    }

    // copy constructor
    public void Copy(Pizza source) throws IOException {
        //super(source);
        this.name = new String(source.name);
        this.ingredientCount = source.ingredientCount;
        this.toppingCount = source.toppingCount;
        // this is choppy waters for me, array copy const for dynamically bound derived class refs
        this.currentOrder = new Ingredient[this.ingredientCount];
        this.currentOrder[0] = new Crust((Crust) source.currentOrder[0]);
        this.currentOrder[1] = new Sauce((Sauce) source.currentOrder[1]);
        for (int i = 2; i < ingredientCount; ++i) {
            this.currentOrder[i] = new Topping((Topping) source.currentOrder[i]);
        }
        this.availTops = new Topping[toppingCount];
        for (int i = 0; i < toppingCount; ++i) {
            this.availTops[i] = new Topping(source.availTops[i]);
        }
        this.isVegan = source.isVegan;
        this.price = source.price;
    }

}
