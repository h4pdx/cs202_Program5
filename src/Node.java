package cs202_program5;
import java.io.IOException;

/**
 * Ryan Hoover
 * CS 202 - Fant
 * Program 5 - 06/14/2017
 *
 * the list of orders will manipulate node object
 * all setters and getters for left and right references
 * argument constructor is available but not currently used
 * Name data is either Pizza or Store, depending on which tree
 */

public class Node extends Util {
    private Node left;
    private Node right;
    protected Name data; // can be either Store or Pizza

    public Node() throws IOException {
        data = null;
        left = null;
        right = null;
    }

    // copy const
    public Node(Node source) throws IOException {
        this.data = source.data;
        left = null;
        right = null;
    }

    // arg contsr, name object
    public Node(Name source) throws IOException {
        data = source.Copy();
        left = null;
        right = null;
    }


    // setters and getters
    public Node getLeft() {return this.left;} // simple getters, manipulated by list class (Orders)

    public Node getRight() {return this.right;}

    public void setLeft(Node left) {this.left = left;} // simple setters

    public void setRight(Node right) {this.right = right;}


    public int getOrderPrice() {
        return data.price;
    }

    public void Display() {
        data.Display();
    }
}
