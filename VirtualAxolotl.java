public class VirtualAxolotl {
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

    // inventory
    private CareItem[] inventory;

    // constructor 1
    public VirtualAxolotl(String name) {
        this(name, 150.0);
    }

    // constructor 2 (overloaded)
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

        inventory = new CareItem[5];
        inventory[0] = new CareItem("water", 1.50, 1);
        inventory[1] = new CareItem("cucumber", 3.00, 1);
        inventory[2] = new CareItem("shrimp", 6.00, 0);
        inventory[3] = new CareItem("protein", 8.00, 0); //SUGGESTION: Specify why protein is being included
        inventory[4] = new CareItem("toy", 15.00, 0);
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

    public int getWaterBottles() {
        return getItemQuantity("water");
    }

    public int getCucumbers() {
        return getItemQuantity("cucumber");
    }

    public int getShrimps() {
        return getItemQuantity("shrimp");
    }

    public int getProteinMeals() {
        return getItemQuantity("protein");
    }

    public int getToys() {
        return getItemQuantity("toy");
    }

    // setter methods
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

    // non-void return methods
    public boolean isHealthy() {
        return food > 30 && hydration > 30 && protein > 30;
    }

    private int[] getNeeds() {
        int[] needs = {food, hydration, protein, happiness};
        return needs;
    }

    // static because it only uses the array passed in, not a specific axolotl
    public static double averageStats(int[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }

        int total = 0;
        for (int value : values) {
            total += value;
        }
        return (double) total / values.length;
    }

    public double getAverageNeeds() {
        return averageStats(getNeeds());
    }

    public int calculateHealthScore() {
        return (int) getAverageNeeds();
    }

    public int getLowestNeed() {
        int[] needs = getNeeds();
        int lowest = needs[0];
        for (int i = 1; i < needs.length; i++) {
            if (needs[i] < lowest) {
                lowest = needs[i];
            }
        }
        return lowest;
    }

    public String getLowestNeedName() {
        int[] needs = getNeeds();
        String[] names = {"Food", "Hydration", "Protein", "Happiness"};
        int lowestIndex = 0;
        for (int i = 1; i < needs.length; i++) {
            if (needs[i] < needs[lowestIndex]) {
                lowestIndex = i;
            }
        }
        return names[lowestIndex];
    }

    public int countLowNeeds() {
        int count = 0;
        for (int need : getNeeds()) {
            if (need <= 30) {
                count++;
            }
        }
        return count;
    }

    public CareItem findItem(String itemName) {
        if (itemName == null) {
            return null;
        }
        for (CareItem item : inventory) {
            if (item != null && item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    private int getItemQuantity(String itemName) {
        CareItem item = findItem(itemName);
        if (item == null) {
            return 0;
        }
        return item.getQuantity();
    }

    public void displayStoreItems() {
        for (CareItem item : inventory) {
            if (item != null) {
                System.out.printf("%-9s- $%.2f%n", item.getName(), item.getPrice());
            }
        }
    }

    // feed method
    public boolean feed(String foodChoice) {
        CareItem chosen = findItem(foodChoice);
        if (chosen == null || foodChoice.equals("water") || foodChoice.equals("toy")) {
            return false;
        }
        if (!chosen.useOne()) {
            return false;
        }

        if (foodChoice.equals("cucumber")) {
            food += 15;
            protein += 5;
        } else if (foodChoice.equals("shrimp")) {
            food += 25;
            protein += 20;
        } else if (foodChoice.equals("protein")) {
            food += 20;
            protein += 30;
        }

        if (food > 100) {
            food = 100;
        }
        if (protein > 100) {
            protein = 100;
        }
        return true;
    }

    // water method
    public boolean drinkWater() {
        CareItem waterItem = findItem("water");
        if (waterItem == null || !waterItem.useOne()) {
            return false;
        }
        hydration += 25;
        if (hydration > 100) {
            hydration = 100;
        }
        return true;
    }

    // play method
    public int play() {
        int gained = 15;
        CareItem toy = findItem("toy");
        if (toy != null && toy.useOne()) {
            gained = 25;
        }
        setHappiness(happiness + gained);
        food -= 5;
        hydration -= 8;
        checkStats();
        return gained;
    }

    // walk method
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

    public boolean spendMoney(double price) {
        if (price >= 0 && money >= price) {
            money -= price;
            return true;
        }
        return false;
    }

    public boolean buyItem(String item) {
        CareItem chosen = findItem(item);
        if (chosen == null || !spendMoney(chosen.getPrice())) {
            return false;
        }
        chosen.buyOne();
        return true;
    }

    // random event
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
        for (CareItem item : inventory) {
            if (item != null) {
                System.out.println(item);
            }
        }
        System.out.println("--------------------------------");
    }
}
