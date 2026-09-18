public class virtualDog {
    // global variables
    String name = "";
    int happinessLevel = 0;
    int hunger = 0;
    int thirst = 0;
    int steps = 0;

    public virtualDog(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
    
    public static void main(String[] args) {
        virtualDog Blossom = new virtualDog("Blossom");
        System.out.println(Blossom.name);

    }
}