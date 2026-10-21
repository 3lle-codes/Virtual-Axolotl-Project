import java.util.Scanner;

public class Main {
    private static final int MAX_ACTIONS_PER_DAY = 3;
    private static final int MAX_STEPS_PER_WALK = 3000;

    // step input
    private static int askForSteps(Scanner scanner) {
        while (true) {
            System.out.print("How many steps (1-" + MAX_STEPS_PER_WALK + ")? ");
            String input = scanner.nextLine().trim();

            try {
                int steps = Integer.parseInt(input);
                if (steps >= 1 && steps <= MAX_STEPS_PER_WALK) {
                    return steps;
                }
                System.out.println("Enter a number from 1 to " + MAX_STEPS_PER_WALK + ".");
            } catch (NumberFormatException error) {
                System.out.println("Please enter a whole number, not a word or decimal.");
            }
        }
    }

    private static VirtualAxolotl createPet(String name, Scanner scanner) {
        while (true) {
            System.out.print("Starting money ($0-$150, or Enter for $150): ");
            String input = scanner.nextLine().trim();

            if (input.equals("")) {
                return new VirtualAxolotl(name);
            }

            try {
                double amount = Double.parseDouble(input);
                if (amount >= 0 && amount <= 150) {
                    return new VirtualAxolotl(name, amount);
                }
                System.out.println("Please choose an amount between $0 and $150.");
            } catch (NumberFormatException error) {
                System.out.println("Please enter a valid amount, like 75 or 75.50.");
            }
        }
    }

    private static void printDayHeading(VirtualAxolotl pet) {
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println("DAY " + pet.getDay());
        System.out.println("--------------------------------");
    }

    private static void showStore(VirtualAxolotl pet, Scanner scanner) {
        boolean shopping = true;

        // store loop
        while (shopping) {
            System.out.println();
            System.out.println("Welcome to the Axolotl Store!");
            System.out.println("water    - $1.50");
            System.out.println("cucumber - $3.00");
            System.out.println("shrimp   - $6.00");
            System.out.println("protein  - $8.00");
            System.out.println("toy      - $15.00");
            System.out.println("exit");
            System.out.printf("Money: $%.2f%n", pet.getMoney());
            System.out.print("> ");

            String purchase = scanner.nextLine().trim().toLowerCase();
            if (purchase.equals("exit")) {
                shopping = false;
            } else if (purchase.equals("water") || purchase.equals("cucumber")
                    || purchase.equals("shrimp") || purchase.equals("protein")
                    || purchase.equals("toy")) {
                if (pet.buyItem(purchase)) {
                    System.out.println("Purchased " + purchase + "! Added to inventory.");
                } else {
                    System.out.println("Not enough money!");
                }
            } else {
                System.out.println("Invalid store item. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------------------------------------------");
        System.out.println("           Virtual Axolotl Simulator!          ");
        System.out.println("-----------------------------------------------");
        System.out.println();

        System.out.print("You just adopted an axolotl! What's its name? ");
        String newName = scanner.nextLine().trim();

        // name input
        while (newName.length() == 0) {
            System.out.println("Your axolotl needs a name!");
            System.out.print("Enter a name: ");
            newName = scanner.nextLine().trim();
        }

        newName = newName.substring(0, 1).toUpperCase()
                + newName.substring(1).toLowerCase();
        VirtualAxolotl yourPet = createPet(newName, scanner);
        System.out.println("Welcome home, " + yourPet.getName() + " :)");
        System.out.println("You have 3 care actions per day.");
        System.out.println("Shopping and checking status do not use an action.");
        System.out.println("Use the store to buy food, water, and toys for your inventory.");

        int actionsUsed = 0;
        printDayHeading(yourPet);

        // main game loop
        while (yourPet.getAlive()) {
            System.out.println();
            System.out.println("Care actions left: " + (MAX_ACTIONS_PER_DAY - actionsUsed));
            System.out.println("feed | water | play | walk | status | store | next day | quit");
            System.out.print("> ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("quit")) {
                System.out.println("Thanks for taking care of " + yourPet.getName() + "!");
                break;
            }

            else if (response.equals("status")) {
                yourPet.displayStatus();
                System.out.println(yourPet.getConditionMessage());

                if (!yourPet.isHealthy()) {
                    System.out.println("Your axolotl's health needs attention!");
                }

                double average = yourPet.getAverageNeeds();
                if (average >= 80) {
                    System.out.println("Overall condition: Excellent!");
                } else if (average >= 50) {
                    System.out.println("Overall condition: Good.");
                } else {
                    System.out.println("Overall condition: Poor.");
                }
            }

            else if (response.equals("store")) {
                showStore(yourPet, scanner);
            }

            else if (response.equals("next day")) {
                yourPet.nextDay();
                actionsUsed = 0;

                if (yourPet.getAlive()) {
                    printDayHeading(yourPet);
                    System.out.println("A new day begins...");
                    System.out.println(yourPet.randomEvent());

                    if (yourPet.calculateHealthScore() < 30) {
                        System.out.println("Warning: " + yourPet.getName()
                                + " is in poor condition!");
                    }
                }
            }

            else if (response.equals("feed")) {
                if (actionsUsed >= MAX_ACTIONS_PER_DAY) {
                    System.out.println("No care actions left! Choose 'next day'.");
                } else {
                    System.out.println("What would you like to feed " + yourPet.getName() + "?");
                    System.out.println("cucumber | shrimp | protein");
                    System.out.print("> ");
                    String choice = scanner.nextLine().trim().toLowerCase();

                    if (yourPet.feed(choice)) {
                        actionsUsed++;
                        System.out.println(yourPet.getName() + " ate " + choice + "!");
                    } else if (choice.equals("cucumber") || choice.equals("shrimp")
                            || choice.equals("protein")) {
                        System.out.println("You don't have any " + choice
                                + " left. Buy some at the store!");
                    } else {
                        System.out.println("That food does not exist.");
                    }
                }
            }

            else if (response.equals("water")) {
                if (actionsUsed >= MAX_ACTIONS_PER_DAY) {
                    System.out.println("No care actions left! Choose 'next day'.");
                } else if (yourPet.drinkWater()) {
                    actionsUsed++;
                    System.out.println(yourPet.getName() + " drank some water!");
                    System.out.println("+25 hydration (up to 100)");
                } else {
                    System.out.println("No water bottles left. Buy water at the store!");
                }
            }

            else if (response.equals("play")) {
                if (actionsUsed >= MAX_ACTIONS_PER_DAY) {
                    System.out.println("No care actions left! Choose 'next day'.");
                } else {
                    System.out.println(yourPet.getName() + " is going swimming!");

                    for (int i = 1; i <= 5; i++) {
                        System.out.println("Swimming lap " + i + "...");
                    }

                    int gained = yourPet.play();
                    actionsUsed++;
                    System.out.println(yourPet.getName() + " had fun!");
                    if (gained == 25) {
                        System.out.println("Used 1 toy for a bigger happiness boost!");
                    }
                    System.out.println("+" + gained + " happiness (up to 100)");
                }
            }

            else if (response.equals("walk")) {
                if (actionsUsed >= MAX_ACTIONS_PER_DAY) {
                    System.out.println("No care actions left! Choose 'next day'.");
                } else {
                    int walkSteps = askForSteps(scanner);
                    double earned = yourPet.walk(walkSteps);
                    actionsUsed++;
                    System.out.println(yourPet.getName() + " walked " + walkSteps + " steps!");
                    System.out.printf("You earned $%.2f!%n", earned);
                }
            }

            else {
                System.out.println("Invalid command! Please try again.");
                continue;
            }
        }

        // game over
        if (!yourPet.getAlive()) {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("GAME OVER");
            System.out.println("--------------------------------");
            System.out.println("Your axolotl " + yourPet.getName() + " could no longer continue.");
            System.out.println("You cared for " + yourPet.getName() + " for "
                    + yourPet.getDay() + " days.");
        }

        scanner.close();
    }
}
