public class Tree extends Outdoor {
    private int minHeight; // feet
    private int maxHeight; // feet

    Tree(String name, double price) {
        super(name, price);
    }

    @Override
    public void setData(){
        super.setData();
        this.minHeight = Integer.parseInt(App.prompt("Enter the minimum height in feet: "));
        this.maxHeight = Integer.parseInt(App.prompt("Enter the maximum height in feet: "));
    }

    @Override
    public String toString(){
        return super.toString() + " Max Height: " + this.maxHeight + " Max Age: " + this.minHeight;
    }
}
