public class OvernightDelivery extends Package {
    private double overnightFeePerOunce;

    OvernightDelivery(Person sender, Person recipient, double weight, double costPerOunce, double shippingCost, double overnightFeePerOunce) {
        super(sender, recipient, weight, costPerOunce, shippingCost);
        this.overnightFeePerOunce = overnightFeePerOunce;
    }
    
    public double getOvernightFeePerOunce(){ return this.overnightFeePerOunce; }
    public void setOvernightFeePerOunce(double overnightFeePerOunce){ this.overnightFeePerOunce = overnightFeePerOunce; }

    @Override
    public double getCost(){
        return (this.getWeight() * (this.getCostPerOunce() + this.overnightFeePerOunce)) + this.getShippingCost();
    }
}

