public class Person {
    private String name;
    private String address;
    private String phoneNumber;

    Person(String name, String address, String phoneNumber){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName(){ return this.name; }
    public String getAddress(){ return this.address; }
    public String getPhoneNumber(){ return this.phoneNumber; }

    public void setName(String name){ this.name = name; }
    public void setAddress(String address){ this.address = address; }
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; }
}
