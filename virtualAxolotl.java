/*
Commenter: William Xiong
Its pretty cool, but kinda boring.
the walk enter integer u don't do error detection there. 
theres no limit to how much u walk
its kinda repetitive doing multiple commands, also u can do infinite actions a day
i dont think u check if u have enough food to feed the axolotl
there's no other indication of how the axolotl is other then the start of the day, then the axolotl immediately dies
the ui is rlly good and i would like it if it had more visuals tho.
Also why is everything in one big file? u can have multiple files for multiple classes, like a main/index java file then 
a separate file for axolotl class w/ all the methods so u can have cleaner code. 
*/

/*
Commenter: Michael
Add a limit to what you can do in a day
Added failsafes to prevent invalid inputs
*/

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

        if (this.happiness > 100) {
            this.happiness = 100;
        }

        if (this.happiness < 0) {
            this.happiness = 0;
        }
    }


    //////////////////////////////
    // non-void return methods  //
    //////////////////////////////

    public boolean isHealthy() {
        return (food > 30 && hydration > 30 && protein > 30);
    }

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
        else if (food >= 80 && hydration >= 80 && happiness >= 80) {
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
        
        System.out.println("-----------------------------------------------");// Cute! - Leo
        System.out.println("                  ૮ ˶ᵔ ᵕ ᵔ˶ ა                  ");
        System.out.println("           Virtual Axolotl Simulator!          ");
        System.out.println("-----------------------------------------------");
        System.out.println();
        
        System.out.print("You just adopted an axolotl! What's its name?: ");
        String newName = scanner.nextLine().trim();

        while (newName.length() == 0) {
            System.out.println("Your axolotl needs a name!");
            System.out.print("Enter a name: ");
            newName = scanner.nextLine().trim();
        }

        newName = newName.substring(0,1).toUpperCase() + newName.toLowerCase().substring(1);
        virtualAxolotl yourPet = new virtualAxolotl(newName);
        System.out.println("Welcome home, " + yourPet.getName() + " :)");

        // while loop that runs continuously as long as your axolotl is alive
        while (yourPet.getAlive()) {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("DAY " + yourPet.getDay());
            System.out.println("--------------------------------"); //This makes is harder to read the code because it pushes the code before up
            System.out.println("What would you like to do?"); //maybe try only printing the day once per day - Leo
            System.out.println();
            System.out.println("feed | water | play | walk | status | store | next day | quit");

            System.out.print("> ");
            
            String response =
                    
                    scanner.nextLine().trim().toLowerCase();

            // quit option
            if (response.equals("quit")) {
                System.out.println("Thanks for taking care of " + yourPet.getName() + "!");
                break;
            }

            // status response
            else if (response.equals("status")) {
                yourPet.displayStatus();
                System.out.println(yourPet.getConditionMessage());

                // non-void method in conditional
                if (!yourPet.isHealthy()) {
                    System.out.println("Your axolotl's health needs attention!");
                }

                // another return method evaluated
                if (yourPet.getAverageNeeds() >= 80) {
                    System.out.println("Overall condition: Excellent!");
                }
                else if (yourPet.getAverageNeeds() >= 50) {
                    System.out.println("Overall condition: Good.");
                }
                else {
                    System.out.println("Overall condition: Poor");
                }
            }

            // water response
            else if (response.equals("water")) {
                yourPet.drinkWater(25);
                System.out.println(yourPet.getName() + " drank some water!");
                System.out.println("+25 hydration");
            }

            // feed response
            else if (response.equals("feed")) {
                //I dont think u check 
                System.out.println("What would you like to feed "+ yourPet.getName() + "?");
                System.out.println("cucumber | shrimp | protein");
                String foodChoice =scanner.nextLine().trim().toLowerCase();

                if (foodChoice.equals("cucumber")) {
                    yourPet.feed(15, 5);
                    System.out.println(yourPet.getName() + " ate cucumber!");
                }
                else if (foodChoice.equals("shrimp")) {
                    yourPet.feed(25, 20);
                    System.out.println(yourPet.getName() + " ate shrimp!");
                }
                else if (foodChoice.equals("protein")) {
                    yourPet.feed(20, 30);
                    System.out.println(yourPet.getName() + " ate a protein meal!");
                }
                else {
                    System.out.println("That food does not exist.");
                }
            }

            // play response
            else if (response.equals("play")) {
                System.out.println();
                System.out.println(yourPet.getName()+ " is going swimming!");

                // definite count-based for loop
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Swimming lap " + i + "...");
                }
                yourPet.play();

                System.out.println();
                System.out.println(yourPet.getName() + " had fun!");
                System.out.println("+15 happiness");
            }

            // walk response
            else if (response.equals("walk")) {
                System.out.print("How many steps should " + yourPet.getName() + " walk? ");
                int walkSteps = scanner.nextInt();
                //use try except or use nextLine and convert to int
                //it thinks its a string
                scanner.nextLine();

                // check if steps is valid
                while (walkSteps <= 0) {
                    System.out.println("Steps must be greater than 0.");
                    System.out.print("Enter number of steps: ");
                    walkSteps = scanner.nextInt();
                    scanner.nextLine();
                }

                double earned =yourPet.walk(walkSteps);

                System.out.println(yourPet.getName() + " walked " + walkSteps + " steps!");
                System.out.printf("You earned $%.2f!%n", earned);
            }

            // store response
            else if (response.equals("store")) {
                boolean shopping = true;

                // dynamic loop
                while (shopping) {
                    System.out.println();
                    System.out.println("You have entered the Axolotl Store!");

                    System.out.println("ITEMS (๑>◡<๑)");
                    System.out.println("water    - $1.50");
                    System.out.println("cucumber - $3.00");
                    System.out.println("shrimp   - $6.00");
                    System.out.println("protein  - $8.00");
                    System.out.println("toy      - $15.00");
                    System.out.println("exit");

                    System.out.printf("Money: $%.2f", yourPet.getMoney());
                    System.out.println();

                    System.out.print("> ");

                    String purchase = scanner.nextLine().trim().toLowerCase();

                    // buy water
                    if (purchase.equals("water")) {
                        if (yourPet.spendMoney(1.50)) {
                            yourPet.drinkWater(25);
                            System.out.println("Purchased water!");
                        }
                        else {
                            System.out.println("You don't have enough money!");
                        }
                    }

                    // buy cucumber
                    else if (purchase.equals("cucumber")) {
                        if (yourPet.spendMoney(3.00)) {
                            yourPet.feed(15, 5);
                            System.out.println("Purchased cucumber!");
                        }
                        else {
                            System.out.println("You don't have enough money!");
                        }
                    }

                    // buy shrimp
                    else if (purchase.equals("shrimp")) {
                        if (yourPet.spendMoney(6.00)) {
                            yourPet.feed(25, 20);
                            System.out.println("Purchased shrimp!");
                        }
                        else {
                            System.out.println("You don't have enough money!");
                        }
                    }

                    // buy protein
                    else if (purchase.equals("protein")) {
                        if (yourPet.spendMoney(8.00)) {
                            yourPet.feed(20, 30);
                            System.out.println("Purchased protein meal!");
                        }
                        else {
                            System.out.println("You don't have enough money!");
                        }
                    }

                    // buy toy
                    else if (purchase.equals("toy")) {
                        if (yourPet.spendMoney(15.00)) {
                            yourPet.setHappiness(yourPet.getHappiness() + 25);
                            if (yourPet.getHappiness() > 100) {
                                yourPet.setHappiness(100);
                            }
                            System.out.println(yourPet.getName() + " loves the new toy!");
                        }
                        else {
                            System.out.println("You don't have enough money!");
                        }
                    }

                    // exit store
                    else if (purchase.equals("exit")) {
                        shopping = false;
                    }
                    else {
                        System.out.println("Invalid store item");
                    }

                }
            }
            
            // next day response
            else if (response.equals("next day")) {
                yourPet.nextDay();
                System.out.println();
                System.out.println("A new day begins...");

                String event = yourPet.randomEvent();
                System.out.println(event);

                // check return value in conditional
                if (yourPet.calculateHealthScore() < 30) {
                    System.out.println("Warning: " + yourPet.getName() + " is in poor condition!");
                }
            }
            else  {
                System.out.println("Invalid command! Please try again.");
                continue;
            }
        }
        // I like this function, It takes around 6 days for the axolotl to reach poor conditions maybe you could make it harder to keep the axolotl alive so the decision you make matter
        // game over 
        if (!yourPet.getAlive()) {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("GAME OVER");
            System.out.println("--------------------------------");
            System.out.println("Your beloved axolotl " + yourPet.getName() + " could no longer continue ;-;");
            System.out.println("RIP " + yourPet.getName() + ", you will be missed.");
            System.out.println("You cared for " + yourPet.getName() + " for "+ yourPet.getDay()+ " days.");
        }
    }
}