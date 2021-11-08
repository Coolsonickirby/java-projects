import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Pizza {
    private SimpleIntegerProperty id;
    private Size pizzaSize;
    private ArrayList<Topping> toppings;
    private SimpleStringProperty extras;

    Pizza(){
        this.id = new SimpleIntegerProperty(0);
        this.pizzaSize = null;
        this.toppings = null;
        this.extras = new SimpleStringProperty("");
    }

    Pizza(int id, Size pizzaSize, ArrayList<Topping> toppings, String extras){
        this.id = new SimpleIntegerProperty(id);
        this.pizzaSize = pizzaSize;
        this.toppings = toppings;
        this.extras = new SimpleStringProperty(extras);
    }

    public Size getPizzaSize() {
        return pizzaSize;
    }

    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    public String getExtras() {
        return extras.get();
    }

    public void setExtras(String extras) {
        this.extras = new SimpleStringProperty(extras);
    }

    public void setPizzaSize(Size pizzaSize) {
        this.pizzaSize = pizzaSize;
    }
    
    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public int getId() {
        return id.get();
    }

    public String getIdString(){
        return Integer.toString(id.get());
    }

    public String getSizeString(){
        return this.pizzaSize.getSize();
    }

    public String getToppingsString(){
        return this.toppings.stream().map(entry -> entry.getType()).collect(Collectors.joining(" - "));
    }

    public double getPrice(){
        return this.toppings.stream().collect(Collectors.summingDouble(Topping::getPrice)) + this.pizzaSize.getPrice();
    }

    public String getPriceString(){
        return App.FORMATTER.format(this.getPrice());
    }

}
