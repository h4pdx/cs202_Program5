package cs202_program5;
import java.io.IOException;

/**
 * Ryan Hoover
 * CS 202 - Fant
 * Program 5 - 06/14/2017
 *
 * Binary Search Tree of Nodes (pizza)
 * Allows user to see list of orders this session, either from the oldest or the most recent
 * Inserts always new Pizza order at head
 */

// doubly linked list
public class Orders extends Util {
    private Node root;
    private int pizzaCount;

    public Orders() {
        root = null;
        pizzaCount = 0;
    }

    // BST copy constructor
    public Orders(Orders source) throws IOException {
        int counter = copyAll(root, source.root);
        if (counter != 0)
            System.out.println("\n(" + counter + ") Items copied.");
        else System.out.println("\nNothing copied.");
        pizzaCount = source.pizzaCount;
    }

    // recurse through source tree, using insert on each node.
    // copies data into calling tree
    public int copyAll(Node dest, Node source) throws IOException {
        if (source == null) return 0;
        int counter = 0;
        //dest = Insert(dest, source);
        Insert(source);
        ++counter; // keep track of how many nodes copied
        counter += copyAll(dest, source.getLeft()) + copyAll(dest, source.getRight());
        return counter;
    }

    // order a Pizza and insert if entry is confirmed
    public void Build() throws IOException {
        int userChoice = 0;
        do {
            // make a new store
            Name newObj;
            Node temp;
            newObj = new Store();
            newObj.Input(); // assign ingredients, Pizza methods
            newObj.Display(); // display newly assembled pizza
            temp = new Node(newObj);
            //} while (!Confirm()); // do again?
            if (Insert(temp)) { // insert if confirm
                System.out.println("\nAdded!");
                ++this.pizzaCount; // keep track of how many pizzas we have orders
            }
            else System.out.println("\nNot Added.");
        } while(Again());
    }

    // quickly access how many Pizzas have been ordered
    public int getPizzaCount() {return this.pizzaCount;}

    // wrapper for recursive insert
    public boolean Insert(Node toAdd) throws IOException {
        root = Insert(root, toAdd);
        if (root != null)
            return true;
        else return false;
    }

    // insert recursive
    private Node Insert(Node root, Node toAdd) throws IOException {
        //boolean success = false;
        if (root == null) {
            root = new Node(toAdd);
        }
        // used to be... root = Insert(root.getLeft(), toAdd); // would overwrite the root
        else {
            if (toAdd.getOrderPrice() < root.getOrderPrice()) {
                root.setLeft(Insert(root.getLeft(), toAdd)); // i suppose this is how i compensate for pass by ref
            }
            else {
                root.setRight(Insert(root.getRight(), toAdd)); // set right is needed, makes sense...
                // pass by reference enables parent ptr to be set implicitly on the recursive call,
                // root.setL_R () makes up for this.
            }
        }
        return root;
    }

    // wrapper for display
    public int Display() {
        int counter = Display(root);
        if (counter != 0)
            System.out.println("(" + pizzaCount + ") Pizzas Ordered.");
        else System.out.println("Nothing to display.");
        return counter;
    }

    // recursive display
    private int Display(Node root) {
        if (root == null) return 0;
        int counter = 0;
        // pre-order display
        counter += Display(root.getLeft());
        root.Display();
        ++counter; // keep track of how many nodes
        counter += Display(root.getRight());
        return counter;
    }

    // makes random pizzas
    public int MakeSpecials() throws IOException {
        // calls Pizza method to randomly generate pizzas
        Pizza special;
        Node temp;
        for (int i = 0; i < 3; ++i) {
            special = new Pizza();
            special.Special(); // randomly generate pizza (Toppings)
            temp = new Node(special);
            if (!Insert(temp)) // insert into tree
                System.out.println("\nError - MakeSpecials not inserted");
            ++pizzaCount;
        }
        return pizzaCount;
    }
}
