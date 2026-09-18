import java.util.*;

public class virtualDog {
    // global variables
    String name = "";
    int happinessLevel = 0;
    int hunger = 0;
    int thirst = 0;
    int steps = 0;

    public virtualDog(String name) {
        this.name = name;
        this.happinessLevel = 0;
        this.hunger = 100;
        this.thirst = 100;
        this.steps = 0;
    }

    public void updateName(String name) {
        this.name = name;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        virtualDog dog1 = new virtualDog("Blossom");
        System.out.println("You adopted " + dog1.name);

        String answer = scanner.nextLine();
        

    }
}