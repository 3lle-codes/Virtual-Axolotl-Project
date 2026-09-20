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

    /////////////////////
    // getter methods  //
    /////////////////////

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

    /////////////////////
    // setter methods  //
    /////////////////////

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    // non-void return method
    // use of an explicit type casting
    public double getAverageNeeds() {
        return (double) (food + hydration + protein + happiness) / 4;
    }

    public int calculateHealthScore() {
        return (food + hydration + protein + happiness) / 4;
    }

    // feed method
    public void feed(int foodAmount, int proteinAmount) {
        food += foodAmount;
        protein += proteinAmount;

        if (food > 100) {
            food = 100;
        }
        if (protein > 100) {
            protein = 100;
        }
    }

    // water method
    public void drinkWater(int amount) {
        hydration += amount;

        if (hydration > 100) {
            hydration = 100;
        }
    }

    // play method
    public void play() {
        happiness += 15;
        food -= 5;
        hydration -= 8;

        if (happiness > 100) {
            happiness = 100;
        }

        checkStats();
    } 

    // walk method
    public double walk(int stepAmount) {
        steps += stepAmount;

        food -= 8;
        hydration -= 10;
        happiness += 5;

        // money earned from walking
        double earnedMoney = stepAmount * 0.002;

        money += earnedMoney;

        if (happiness > 100) {
            happiness = 100;
        }
        checkStats();

        return earnedMoney;
    }

    // next day method
    public void nextDay() {
        day++;

        food -= 15;
        hydration -= 20;
        protein -= 10;
        happiness -= 5;

        checkStats();
    }

    // spend money method
    public boolean spendMoney(double price) {
        if (money >= price) {
            money -= price;
            return true;
        }
        return false;
    }

    // random event
    public String randomEvent() {
        double random = Math.random();
        if (random < 0.15) {
            money += 5;
            return name + " found $5 while exploring!";
        }
        else if (random < 0.25) {
            happiness -= 10;
            checkStats();
            return name + " seems lonely today.";
        }
        else if (random < 0.35) {
            food += 10;
            if (food > 100) {
                food = 100;
            }
            return name + " found a tasty snack!";
        }
        else {
            return "Nothing unusual happened today.";
        }
    }

    // check stats
    private void checkStats() {
        if (food < 0) {
            food = 0;
        }
        if (hydration < 0) {
            hydration = 0;
        }
        if (protein < 0) {
            protein = 0;
        }
        if (happiness < 0) {
            happiness = 0;
        }
        // compound logic
        if (food <= 0 || hydration <= 0) {
            alive = false;
        }
    }

    // status message
    public String getConditionMessage() {
        if (food <= 20 && hydration <= 20) {
            return name + " is EXTREMELY hungry and thirsty!";
        }
        else if (food <= 20) {
            return name + " is very hungry!";
        }
        else if (hydration <= 20) {
            return name + " really needs water!";
        }
        else if (happiness <= 20) {

            return name + " is feeling lonely.";
        }
        else if (food >= 80
                && hydration >= 80
                && happiness >= 80) {
            return name + " is thriving!";
        }
        else {
            return name + " is doing okay.";
        }
    }

    // status display
    public void displayStatus() {
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println(name.toUpperCase() + "'S STATUS");
        System.out.println("--------------------------------");
        System.out.println("Day:        " + day);
        System.out.println("Food:       " + food + "/100");
        System.out.println("Hydration:  " + hydration + "/100");
        System.out.println("Protein:    " + protein + "/100");
        System.out.println("Happiness:  " + happiness + "/100");
        System.out.println("Steps:      " + steps);
        System.out.printf("Money:      $%.2f%n", money);
        System.out.println("--------------------------------");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("૮ ˶ᵔ ᵕ ᵔ˶ ა")

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
                    System.out.println("Your axolotl is thirsty! They need water.");
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