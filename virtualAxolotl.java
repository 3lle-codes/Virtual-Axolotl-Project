import java.util.*;

public class virtualAxolotl {
    // instance variables
    private String name;
    private int happiness;

    private int food;
    private int hydration;
    private int protein;
    private int steps;
    private int day; 

    private double money;
    private boolean alive;

    //////////////////
    // constructors //
    //////////////////

    // constructor 1
    public virtualAxolotl(String name) {
        this.name = name;

        happiness = 50; 

        food = 100;
        hydration = 100;
        protein = 100;
        happiness = 50; 

        steps = 0;
        day = 1;

        money = 150.0;
        alive = true;
    }

    // constructor 2 (overloaded)
    public virtualAxolotl(String name, double startingMoney) {
        this.name = name;

        happiness = 50; 

        food = 100;
        hydration = 100;
        protein = 100;
        happiness = 50; 

        steps = 0;
        day = 1;

        money = startingMoney;
        alive = true;
    } 

    // getter methods
    public String getName() {
        return name;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getFood() {
        return food; 
    }

    public int getHydration() {
        return hydration;
    }

    public int getProtein() {
        return protein;
    }

    public int getSteps() {
        return steps;
    }

    public int getDay() {
        return day;
    }

    public double getMoney() {
        return money;
    }

    public boolean getAlive() {
        return alive;
    }


    // setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
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