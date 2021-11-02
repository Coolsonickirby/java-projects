public class PreferredCustomer extends Customer {
    private double totalAmount;

    PreferredCustomer(String name, String address, String phoneNumber, boolean isOnMailingList, double totalAmount) {
        super(name, address, phoneNumber, isOnMailingList);
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void add(double amount) {
        this.totalAmount += amount;
    }

    public double getDiscountAmount() {
        if (PreferredCustomer.CheckBetweenRange(this.totalAmount, 500, 999.99))
            return 0.10;
        else if (PreferredCustomer.CheckBetweenRange(this.totalAmount, 1000, 1999.99))
            return 0.15;
        else if (PreferredCustomer.CheckBetweenRange(this.totalAmount, 2000, Double.MAX_VALUE))
            return 0.25;
        else
            return 0.00;
    }

    private static boolean CheckBetweenRange(double value, double min, double max) {
        return value <= max && value >= min;
    }
}
