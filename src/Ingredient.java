package cs202_program5;

/**
 * Ryan Hoover
 * CS 202 - Fant
 * Program 5 - 06/14/2017
 *
 * Abstract base class for pizza ingredients (topping, sauce, crust
 */

abstract public class Ingredient extends Name {
    //protected String name;
    protected boolean isVegan;
    //protected int price;

    public Ingredient() {
        //this.name = new String();
        super();
        this.isVegan = false;
        this.price = 0;
    }

    public Ingredient(String name, boolean isVegan, int price) {
        //this.name = new String(name);
        super(name, price);
        this.isVegan = isVegan;
        //this.price = price;
    }

    public Ingredient(Ingredient source) {
        //this.name = new String(source.name);
        super(source);
        this.isVegan = source.isVegan;
        this.price = source.price;
    }


    public boolean equals(String toMatch) {
        boolean isMatch = false;
        if (this.name.equals(toMatch)) {
            isMatch = true;
        }
        return isMatch;
    }

    public boolean isVegan() {return this.isVegan;}

    public int getPrice() {return this.price;}



}
