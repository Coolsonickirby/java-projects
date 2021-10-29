public class Person {
    private String name;
    private String address;
    private String city;
    private String state;
    private int zip;

    Person(String name, String address, String city, String state, int zip){
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getName(){ return this.name; }
    public String getAddress(){ return this.address; }
    public String getCity(){ return this.city; }
    public String getState(){ return this.state; }
    public int getZip(){ return this.zip; }

    public void setName(String name){ this.name = name; }
    public void setAddress(String address){ this.address = address; }
    public void setCity(String city){ this.city = city; }
    public void setState(String state){ this.state = state; }
    public void setZip(int zip){ this.zip = zip; }

    @Override
    public String toString(){
        return String.format("Name: %s\nAddress: %s\nCity: %s\nState: %s\nZip: %s", this.name, this.address, this.city, this.state, this.zip);
    }
}
