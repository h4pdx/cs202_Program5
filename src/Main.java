package cs202_program5;

import java.io.IOException;

public class Main extends Util {

    public static void main(String[] args) throws IOException {
        // store new menu
        Orders Stores = new Orders();
        Stores.Build();
        int menu = 0;
            do {
                System.out.println("\n~~~~~STORE MENU~~~~~");
                menu = Menu();
                switch (menu) {
                    case 1: // order new pizza
                        Stores.Build();
                        break;
                    case 2: // display all pizzas
                        Stores.Display();
                        break;
                }
            } while (menu != 3 && BackToMenu());
    }
}
