public class App {
    public static void main(String[] args) throws Exception {
        PreferredCustomer customer = new PreferredCustomer(
            "name",
            "address",
            "phoneNumber",
            true,
            0.00
        );

        for(int i = 0; i <= 1500; i += 500){
            customer.add(i);
            System.out.format("Total Purchase Amount: $%s - Discount Amount: %s\n", customer.getTotalAmount(), customer.getDiscountAmount());
        }
    }
}