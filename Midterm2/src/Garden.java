public class Garden extends Outdoor {
    private int minHeight; // inches
    private int maxHeight; // inches

    Garden(String name, double price) {
        super(name, price);
    }

    @Override
    public void setData(){
        super.setData();
        this.minHeight = Integer.parseInt(App.prompt("Enter the minimum height in inches: "));
        this.maxHeight = Integer.parseInt(App.prompt("Enter the maximum height in inches: "));
    }

    @Override
    public String toString(){
        return super.toString() + " Max Height: " + this.maxHeight + " Min Height: " + this.minHeight;
    }
}
