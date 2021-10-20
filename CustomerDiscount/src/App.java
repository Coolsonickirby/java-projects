/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #X - Assignment Title                               |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|                                                                |*
 *| Notes: Blah Blah Blah                                          |*
 *|----------------------------------------------------------------|*
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    public static Scanner reader = new Scanner(System.in); // Create globally so main and prompt can reference it
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

    public static void main(String[] args) throws Exception {
        PreferredCustomer customer = new PreferredCustomer(
            "name",
            "address",
            "phoneNumber",
            true,
            0.00
        );

        double purchase;
        double discount;
        while(true){
            purchase = Double.parseDouble(choice("Enter purchase amount: ", "^\\d+([.][0-9]+)?$"));
            discount = customer.getDiscountAmount();
            customer.add(purchase - (purchase * discount));
            System.out.format("Account Number: %s\nPurchase Amount: $%s\nDiscount: $%s\nTotal Purchase Amount: $%s\n---------------------\n",
                customer.getCustomerNumber(),
                purchase,
                discount * purchase,
                customer.getTotalAmount()
            );
        }
    }
}