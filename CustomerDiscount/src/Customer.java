public class Customer extends Person {
    private static int latestCustomerNumber = 1000;
    
    private boolean isOnMailingList;
    private int customerNumber;

    Customer(String name, String address, String phoneNumber, boolean isOnMailingList){
        super(name, address, phoneNumber);
        this.isOnMailingList = isOnMailingList;
        this.customerNumber = Customer.getLatestID();
    }

    public int getCustomerNumber(){ return this.customerNumber; }
    public boolean getIsOnMailingList(){ return this.isOnMailingList; }

    public void setIsOnMailingList(boolean isOnMailingList){ this.isOnMailingList = isOnMailingList; }    

    public static int getLatestID(){
        Customer.latestCustomerNumber += 1;
        return Customer.latestCustomerNumber;
    }
}
