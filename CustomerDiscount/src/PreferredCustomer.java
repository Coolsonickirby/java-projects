public class PreferredCustomer extends Customer {
    private double discountAmount;
    private double totalAmount;

    PreferredCustomer(String name, String address, String phoneNumber, boolean isOnMailingList, double totalAmount) {
        super(name, address, phoneNumber, isOnMailingList);
        this.totalAmount = totalAmount;
        calcDiscountAmount();
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        this.calcDiscountAmount();
    }

    public void add(double amount) {
        this.totalAmount += amount;
        this.calcDiscountAmount();
    }

    private void calcDiscountAmount() {
        if (PreferredCustomer.CheckBetweenRange(this.totalAmount, 500, 999.99))
            this.discountAmount = 0.10;
        else if (PreferredCustomer.CheckBetweenRange(this.totalAmount, 1000, 1999.99))
            this.discountAmount = 0.15;
        else if (PreferredCustomer.CheckBetweenRange(this.totalAmount, 2000, Double.MAX_VALUE))
            this.discountAmount = 0.25;
        else
            this.discountAmount = 0.00;
    }

    public double getDiscountAmount() { return this.discountAmount; }

    private static boolean CheckBetweenRange(double value, double min, double max) {
        return value <= max && value >= min;
    }
}
