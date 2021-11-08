public class Size {
    private String size;
    private Double price;

    Size(String size, Double price){
        this.size = size;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString(){
        return String.format("%s ($%s)", this.size, App.FORMATTER.format(this.price));
    }
}
