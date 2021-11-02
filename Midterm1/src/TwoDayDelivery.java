public class TwoDayDelivery extends Package {
    private double flatFee;

    TwoDayDelivery(Person sender, Person recipient, double weight, double costPerOunce, double shippingCost, double flatFee) {
        super(sender, recipient, weight, costPerOunce, shippingCost);
        this.flatFee = flatFee;
    }

    public double getFlatFee(){ return this.flatFee; }
    public void setFlatFee(double flatFee){ this.flatFee = flatFee; }

    @Override
    public double getCost(){
        return ((this.getWeight() * this.getCostPerOunce()) + this.flatFee) + this.getShippingCost();
    }
}
