public class Topping {
    private String type;
    private Double price;

    Topping(String type, Double price){
        this.type = type;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return String.format("%s ($%s)", this.type, App.FORMATTER.format(this.price));
    }
}
