public class Indoor extends Plant {
    private String sunlight;
    private int numberOfDaysWatered;

    Indoor(String name, double price) {
        super(name, price);
    }

    @Override
    public void setData(){
        super.setData();
        this.sunlight = App.prompt("Enter the type of sunlight: ");
        this.numberOfDaysWatered = Integer.parseInt(App.prompt("Enter the number of days per week to be watered: "));
    }

    @Override
    public String toString(){
        return super.toString() + " " + this.sunlight + " sunlight and water " + this.numberOfDaysWatered + " day(s) per week";
    }
    
}
