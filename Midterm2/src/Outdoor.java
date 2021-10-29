public class Outdoor extends Plant {
    private int minHardiness;
    private int maxHardiness;

    Outdoor(String name, double price) {
        super(name, price);
    }

    @Override
    public void setData(){
        super.setData();
        this.minHardiness = Integer.parseInt(App.prompt("Enter the min hardiness zone: "));
        this.maxHardiness = Integer.parseInt(App.prompt("Enter the max hardiness zone: "));
    }

    @Override
    public String toString(){
        return super.toString() + " Zones: " + this.minHardiness + " - " + this.maxHardiness;
    }
}
