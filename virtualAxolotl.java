public class VirtualAxolotl {
    // Instance variables: the axolotl's needs and progress.
    private String name;
    private int happiness;
    private int food;
    private int hydration;
    private int protein;
    private int steps;
    private int day;
    private double money;
    private boolean alive;

    // Inventory: store purchases are saved until the player uses them.
    private int waterBottles;
    private int cucumbers;
    private int shrimps;
    private int proteinMeals;
    private int toys;

    // Constructor 1: default starting money.
    public VirtualAxolotl(String name) {
        this(name, 150.0);
    }

    // Constructor 2 (overloaded): lets the player choose starting money.
    public VirtualAxolotl(String name, double startingMoney) {
        this.name = name;
        food = 100;
        hydration = 100;
        protein = 100;
        happiness = 50;
        steps = 0;
        day = 1;
        money = startingMoney;
        alive = true;

        // A couple of starting supplies; future supplies must be purchased.
        waterBottles = 1;
        cucumbers = 1;
        shrimps = 0;
        proteinMeals = 0;
        toys = 0;
    }

    ////////////////////
    // Getter methods //
    ////////////////////
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

    public int getWaterBottles() {
        return waterBottles;
    }

    public int getCucumbers() {
        return cucumbers;
    }

    public int getShrimps() {
        return shrimps;
    }

    public int getProteinMeals() {
        return proteinMeals;
    }

    public int getToys() {
        return toys;
    }

    ////////////////////
    // Setter methods //
    ////////////////////
    public void setName(String name) {
        if (name != null && name.trim().length() > 0) {
            this.name = name.trim();
        }
    }

    public void setMoney(double money) {
        if (money >= 0) {
            this.money = money;
        }
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

    /////////////////////////////
    // Non-void return methods //
    /////////////////////////////
    public boolean isHealthy() {
        return food > 30 && hydration > 30 && protein > 30;
    }

    // The double cast prevents integer division.
    public double getAverageNeeds() {
        return (double) (food + hydration + protein + happiness) / 4;
    }

    // Reuses the average instead of repeating the same calculation.
    public int calculateHealthScore() {
        return (int) getAverageNeeds();
    }

    // Feed only if the player has purchased (or started with) the food.
    // Returns false when the item is not available.
    public boolean feed(String foodChoice) {
        if (foodChoice.equals("cucumber") && cucumbers > 0) {
            cucumbers--;
            food += 15;
            protein += 5;
        } else if (foodChoice.equals("shrimp") && shrimps > 0) {
            shrimps--;
            food += 25;
            protein += 20;
        } else if (foodChoice.equals("protein") && proteinMeals > 0) {
            proteinMeals--;
            food += 20;
            protein += 30;
        } else {
            return false;
        }

        if (food > 100) {
            food = 100;
        }
        if (protein > 100) {
            protein = 100;
        }
        return true;
    }

    // Water also comes from inventory, not an unlimited free supply.
    public boolean drinkWater() {
        if (waterBottles <= 0) {
            return false;
        }
        waterBottles--;
        hydration += 25;
        if (hydration > 100) {
            hydration = 100;
        }
        return true;
    }

    // One play action. An owned toy is used for an extra happiness boost.
    // Returns how much happiness the activity adds.
    public int play() {
        int gained = 15;
        if (toys > 0) {
            toys--;
            gained = 25;
        }
        setHappiness(happiness + gained);
        food -= 5;
        hydration -= 8;
        checkStats();
        return gained;
    }

    // Walk to earn money; Main enforces the maximum number of steps.
    public double walk(int stepAmount) {
        steps += stepAmount;
        food -= 8;
        hydration -= 10;
        setHappiness(happiness + 5);

        double earnedMoney = stepAmount * 0.002;
        money += earnedMoney;
        checkStats();
        return earnedMoney;
    }

    public void nextDay() {
        day++;
        food -= 15;
        hydration -= 20;
        protein -= 10;
        setHappiness(happiness - 5);
        checkStats();
    }

    // The store uses this boolean return value for every purchase.
    public boolean spendMoney(double price) {
        if (price >= 0 && money >= price) {
            money -= price;
            return true;
        }
        return false;
    }

    // Buying adds an item to inventory; it does not immediately change stats.
    public boolean buyItem(String item) {
        double price;
        if (item.equals("water")) {
            price = 1.50;
        } else if (item.equals("cucumber")) {
            price = 3.00;
        } else if (item.equals("shrimp")) {
            price = 6.00;
        } else if (item.equals("protein")) {
            price = 8.00;
        } else if (item.equals("toy")) {
            price = 15.00;
        } else {
            return false;
        }

        if (!spendMoney(price)) {
            return false;
        }

        if (item.equals("water")) {
            waterBottles++;
        } else if (item.equals("cucumber")) {
            cucumbers++;
        } else if (item.equals("shrimp")) {
            shrimps++;
        } else if (item.equals("protein")) {
            proteinMeals++;
        } else if (item.equals("toy")) {
            toys++;
        }
        return true;
    }

    // One random event per day: 15% money, 10% lonely, 10% snack.
    public String randomEvent() {
        double random = Math.random();
        if (random < 0.15) {
            money += 5;
            return name + " found $5 while exploring!";
        } else if (random < 0.25) {
            setHappiness(happiness - 10);
            checkStats();
            return name + " seems lonely today.";
        } else if (random < 0.35) {
            food += 10;
            if (food > 100) {
                food = 100;
            }
            return name + " found a tasty snack!";
        } else {
            return "Nothing unusual happened today.";
        }
    }

    // Keep stats within their limits and update the alive boolean.
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

        if (food <= 0 || hydration <= 0) {
            alive = false;
        }
    }

    public String getConditionMessage() {
        if (food <= 20 && hydration <= 20) {
            return name + " is EXTREMELY hungry and thirsty!";
        } else if (food <= 20) {
            return name + " is very hungry!";
        } else if (hydration <= 20) {
            return name + " really needs water!";
        } else if (happiness <= 20) {
            return name + " is feeling lonely.";
        } else if (food >= 80 && hydration >= 80 && happiness >= 80) {
            return name + " is thriving!";
        } else {
            return name + " is doing okay.";
        }
    }

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
        System.out.println("INVENTORY");
        System.out.println("Water:      " + waterBottles);
        System.out.println("Cucumber:   " + cucumbers);
        System.out.println("Shrimp:     " + shrimps);
        System.out.println("Protein:    " + proteinMeals);
        System.out.println("Toys:       " + toys);
        System.out.println("--------------------------------");
    }
}