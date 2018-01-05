package cs202_program5;
import java.io.IOException;

/**
 * Ryan Hoover
 * CS 202 - Fant
 * Program 5 - 06/14/2017
 * Created by ryan on 6/11/17.
 * Store class - contains a BST of Pizza orders
 */

public class Store extends Name {
    protected String address;
    protected Orders pizzaList;
    protected int orderCount;

    public Store() throws IOException {
        address = new String();
        pizzaList = new Orders();
        orderCount = 0;
    }

    public Store(Store source) throws IOException {
        super(source);
        this.address = new String(source.address);
        this.pizzaList = new Orders(source.pizzaList);
        this.orderCount = source.orderCount;
    }

    public Store(String name, int price, String address, Orders pizzaList, int orderCount) throws IOException {
        super(name, price);
        this.address = new String(address);
        this.pizzaList = new Orders(pizzaList);
        this.orderCount = orderCount;
    }

    // begin by generating specials of the day
    public void Input() throws IOException {
        System.out.print("\nWhat Store are we ordering from? (Name): ");
        this.name = input.nextLine();
        System.out.print("\nEnter the Street address: ");
        this.address = input.nextLine();
        int numSpecials = pizzaList.MakeSpecials(); // calls Orders makeSpecials method
        if (numSpecials != 0) {
            System.out.println("\n(" + numSpecials + ") Specials offered today."); //
            pizzaList.Display();
        }
        else System.out.println("\nNo Specials.");
        // allow user to enter new pizza orders
        int menu = 0;
        do {
            this.Display();
            System.out.println("\n~~~PIZZA MENU~~~");
            menu = Menu();
            switch (menu) {
                case 1: // order new pizza
                    pizzaList.Build();
                    break;
                case 2: // display all pizzas
                    pizzaList.Display();
                    break;
            }
        } while (menu != 3 && BackToMenu());
        orderCount = pizzaList.getPizzaCount();
    }


    public void Display() {
        System.out.println("\nWelcome to " + this.name + " Pizza!" +
                "\nLocated on: " + this.address);
        System.out.println("\nPizzas: " + orderCount);
    }

    // copy from argument, called by Name abstract Copy
    public void Copy(Store source) throws IOException {
        this.name = new String(source.name);
        this.address = new String(source.address);
        this.pizzaList = new Orders(source.pizzaList);
        this.orderCount = source.orderCount;
    }

    // abstract copy function, returns new derived object
    public Name Copy() throws IOException {
        Store newStore = new Store();
        newStore.Copy(this); // call specific Copy function
        return newStore;
    }
}
