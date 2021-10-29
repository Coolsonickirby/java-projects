/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Midterm #1 - Delivery Assignment                               |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|                                                                |*
 *| Notes: Took longer than I expected, but hey I got it done.     |*
 *|----------------------------------------------------------------|*
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    public static Scanner reader = new Scanner(System.in); // Create globally so main and prompt can reference it
    public static int id = 1;
    public static void main(String[] args) {
        while(generatePackage());
        System.out.println("Thank you for using our program!");
    }

    public static boolean generatePackage(){
        System.out.format("Package #%s\n------------------------------------\n", id);
        int shippmentMethod = Integer.parseInt(choice("Please select a shipping method:\n\t1. Standard\n\t2. Two Day\n\t3. Overnight\nEnter a choice (1|2|3): ", "(1|2|3)"));
        Person sender = getPerson("sender");
        System.out.println("------------------------------------");
        Person recipient = getPerson("recipient");
        System.out.println("------------------------------------");
        double weight = Double.parseDouble(choice("Please enter the item's weight (ounces): ", "^\\d+([.][0-9]+)?$"));
        double costPerOunce = Double.parseDouble(choice("Please enter the item's cost per ounce: ", "^\\d+([.][0-9]+)?$"));
        double shippingCost = Double.parseDouble(choice("Please enter the shipping cost: ", "^\\d+([.][0-9]+)?$"));
        Package pkg;
        if(shippmentMethod == 1){
            pkg = new Package(sender, recipient, weight, costPerOunce, shippingCost);
        }else if(shippmentMethod == 2){
            double flatFee = Double.parseDouble(choice("Please enter the flat fee: ", "^\\d+([.][0-9]+)?$"));
            pkg = new TwoDayDelivery(sender, recipient, weight, costPerOunce, shippingCost, flatFee);
        }else { // If it's not one or two then it can only be three
            double overnightFeePerOunce = Double.parseDouble(choice("Please enter the overnight fee per ounce: ", "^\\d+([.][0-9]+)?$"));
            pkg = new OvernightDelivery(sender, recipient, weight, costPerOunce, shippingCost, overnightFeePerOunce);
        }
        System.out.println("------------------------------------");
        displayShippingInfo(pkg);
        System.out.println("------------------------------------");
        id++;
        String repeat = choice("Would you like to generate another package? (Y|N) ", "(Y|N)");
        System.out.println("------------------------------------");
        return repeat.equals("Y");
    }

    public static void displayShippingInfo(Package pkg){
        System.out.println(pkg);
        if(pkg.getClass() == TwoDayDelivery.class){
            System.out.format("Flat Fee: %s\nTotal Cost: %s", ((TwoDayDelivery) pkg).getFlatFee() , pkg.getCost());
        }else if(pkg.getClass() == OvernightDelivery.class){
            System.out.format("Overnight Cost Per Ounce: %s\nTotal Cost: %s", ((OvernightDelivery) pkg).getOvernightFeePerOunce() , pkg.getCost());
        }else{ // If it's not anything special, then just use a normal package stuff
            System.out.format("Total Cost: %s", pkg.getCost());
        }
        System.out.print("\n");
    }

    public static Person getPerson(String type){
        String name = choice(String.format("Please enter the %s's name: ", type), ".+");
        String address = choice(String.format("Please enter the %s's address: ", type), ".+");
        String city = choice(String.format("Please enter the %s's city: ", type), ".+");
        String state = choice(String.format("Please enter the %s's state: ", type), ".+");
        int zip = Integer.parseInt(choice(String.format("Please enter the %s's zip code: ", type), "^[0-9]{5}(?:-[0-9]{4})?$")); // Zip Code regex from here: https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch04s14.html
        return new Person(name, address, city, state, zip);
    }

    public static String prompt(String prompt) { // Emulate Python input function
        System.out.print(prompt);
        return reader.nextLine();
    }
    public static String choice(String prompt, String patternString){
        Pattern p = Pattern.compile(patternString);
        String choice = prompt(prompt);

        while(!p.matcher(choice).matches()){
            System.out.println("Invalid choice!");
            choice = prompt(prompt);
        }

        return choice;
    }
}