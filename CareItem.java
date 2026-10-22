public class CareItem {
    // static since  every item shares the store's total purchase count
    private static int totalPurchases = 0;

    // instance variables
    private String name;
    private double price;
    private int quantity;

    // constructor
    public CareItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        setQuantity(quantity); //PRAISE: I appreciate using the method in the constructor rather than simply writing the code the 
                               // method should do.
    }

    // get item name
    public String getName() {
        return name;
    }

    // get item price
    public double getPrice() {
        return price;
    }

    // get how many are left
    public int getQuantity() {
        return quantity;
    }

    // set the item amount
    // Precondition: quantity can be any integer.
    // Postcondition: quantity is never negative.
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            this.quantity = 0;
        } else {
            this.quantity = quantity;
        }
    }

    // buy an item
    public void buyOne() {
        quantity++;
        totalPurchases++;
    }

    // use an item from inventory
    // Precondition => none
    // Postcondition => removes one item or changes nothing
    public boolean useOne() {
        if (quantity <= 0) {
            return false;
        }
        quantity--;
        return true;
    }

    // static because the purchase total belongs to the whole store, not one item
    public static int getTotalPurchases() {
        return totalPurchases;
    }

    // show the item and amount
    public String toString() {
        return name + ": " + quantity;
    }
}
