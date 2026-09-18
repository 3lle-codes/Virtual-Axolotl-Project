import java.util.*;

public class virtualAxolotl {
    // global variables
    String name = "";
    int happiness = 0;
    int hunger = 0;
    int protein = 0;
    int thirst = 0;
    int steps = 0;
    double money = 0; 

    public virtualAxolotl(String name) {
        this.name = name;
        this.money = 100.0;
        this.happiness = 0;
        this.hunger = 100;
        this.thirst = 100;
        this.protein = 100;
        this.steps = 0;
    }

    public void updateName(String name) {
        this.name = name;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------------------------------------------");
        System.out.print("You just adopted an axolotl! What's its name?: ");
        String newName = scanner.nextLine();
        System.out.println("-----------------------------------------------");

        virtualAxolotl yourPet = new virtualAxolotl(newName.substring(0,1).toUpperCase() + newName.toLowerCase().substring(1));
        System.out.println(yourPet.name);

        while (true) {
            System.out.println("-------------------------------");
            System.out.println("What would you like to do? ");
            System.out.println("feed | water | status | store | quit");
            String response = scanner.nextLine();

            if (response.toLowerCase() == "quit") {
                break;
            }

            else if (response == "status") {
                if (yourPet.thirst <= 20 && yourPet.thirst > 0) {
                    System.out.println("Your dog is thirsty! They need water.");
                }
            }

            else if (response == "store") {
                System.out.println("What would you like to purchase today?");
                System.out.println("Store Catalog (enter one of the following): ");
                System.out.println("water | cucumber | beef | toy");
                String storePurchase = scanner.nextLine();

                if (storePurchase.toLowerCase() == "water") {
                    yourPet.money -= 1.5;
                }
            }

            else {
                break;
            }
        }
    }
}