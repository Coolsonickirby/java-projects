public class Package  {
    private Person sender;
    private Person recipient;
    private double weight; // in Ounces
    private double costPerOunce;
    private double shippingCost;

    Package(Person sender, Person recipient, double weight, double costPerOunce, double shippingCost){
        this.sender = sender;
        this.recipient = recipient;
        this.weight = weight;
        this.costPerOunce = costPerOunce;
        this.shippingCost = shippingCost;
    }

    public Person getSender(){ return this.sender; }
    public Person getRecipient(){ return this.recipient; }
    public double getWeight(){ return this.weight; }
    public double getCostPerOunce(){ return this.costPerOunce; }
    public double getShippingCost(){ return this.shippingCost; }

    public void setSender(Person sender){ this.sender = sender; }
    public void setRecipient(Person recipient){ this.recipient = recipient; }
    public void setWeight(double weight){ this.weight = weight; }
    public void setCostPerOunce(double costPerOunce){ this.costPerOunce = costPerOunce; }
    public void setShippingCost(double shippingCost){ this.shippingCost = shippingCost; }

    public double getCost(){
        return (this.weight * this.costPerOunce) + this.shippingCost;
    }

    @Override
    public String toString(){
        return String.format("Sender:\n%s\n---------------------------\nRecipient:\n%s\n---------------------------\nItem Weight: %s\nCost Per Ounce: %s\nShipping Cost: %s", this.getSender(), this.getRecipient(), this.getWeight(), this.getCostPerOunce(), this.getShippingCost());
    }
}
