import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Plant {
    private String name;
    private double price;

    Plant(String name, double price){
        this.name = name;
        this.price = price;
    }

    public String getName(){ return this.name; }
    public double getPrice(){ return this.price; }

    public void setName(String name){ this.name = name; }
    public void setPrice(double price){ this.price = price; }

    public void setData(){}

    @Override
    public String toString(){
        NumberFormat formatter = new DecimalFormat("#0.00");
        return String.format("%s $ %s", this.name, formatter.format(this.price));
    }
}