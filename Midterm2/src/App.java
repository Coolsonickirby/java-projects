/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Midterm #2 - Plant Assignment                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|                                                                |*
 *| Notes: This was much easier than the first midterm assignment  |*
 *| prolly thanks to the Sample Output included in the doc         |*
 *|----------------------------------------------------------------|*
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    public static Scanner reader = new Scanner(System.in); // Create globally so main and prompt can reference it
    public static final ArrayList<String> YES_LIST = new ArrayList<>(Arrays.asList(new String[]{
        "YES",
        "Y",
        "YUP",
        "YEAH",
        "YE",
        "YEE"
    }));
    public static void main(String[] args) {
        ArrayList<Plant> plants = new ArrayList<Plant>();
        while(loadInventory(plants));
        System.out.println();
        loadPlantData(plants);
        System.out.println();
        System.out.println("\tCurrent Inventory");
        System.out.println();
        for (Plant plant : plants) { System.out.println(plant); }
    }

    public static boolean loadInventory(ArrayList<Plant> plants){
        String typeOfPlant = choice("Select the type of plant:\n\t1) Indoor\n\t2) Tree\n\t3) Garden\n", "(1|2|3)");
        String name = prompt("Enter the name of the plant: ");
        double price = Double.parseDouble(prompt("Enter the price of the plant: "));
        Plant plant;
        if (typeOfPlant.equals("1")){ plant = new Indoor(name, price); }
        else if (typeOfPlant.equals("2")){ plant = new Tree(name, price); }
        else { plant = new Garden(name, price); } // Can only be 3
        plants.add(plant);
        return YES_LIST.contains(prompt("Do you have another plant to add? ").toUpperCase());
    }

    public static void loadPlantData(ArrayList<Plant> plants){
        for (Plant plant : plants) {
            System.out.format("Enter data for %s\n", plant.getName());
            plant.setData();
            System.out.println();
        }
    }



    public static String prompt(String prompt) { // Emulate Python prompt function
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