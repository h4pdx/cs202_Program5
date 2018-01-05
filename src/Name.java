package cs202_program5;

import java.io.IOException;

/**
 * Ryan Hoover
 * CS 202 - Fant
 * Program 5 - 06/14/2017
 *
 * Base class from Pizza and Ingredient and Store, name will be inherited by all of these so I can
 * shove them into the same tree structure.
 */
abstract public class Name extends Util {
    protected String name;
    protected int price;

    public Name() {
        this.name = new String();
        this.price = 0;
    }

    public Name(Name source) {
        this.name = new String(source.name);
        this.price = source.price;
    }

    public Name(String name, int price) {
        this.name = new String(name);
        this.price = price;
    }

    abstract public Name Copy() throws IOException;

    abstract public void Display();

    abstract public void Input() throws IOException;

}
