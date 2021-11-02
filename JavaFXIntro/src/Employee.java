import javafx.beans.property.*;

public class Employee {
    public SimpleIntegerProperty id;
    public SimpleStringProperty name;
    public SimpleStringProperty dateOfBirth;
    public SimpleStringProperty phoneNumber;
    public SimpleStringProperty role;

    Employee(int id, String name, java.time.LocalDate dateOfBirth, String phoneNumber, String role){
        this.id = new SimpleIntegerProperty(id);
        this.name =  new SimpleStringProperty(name);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.role = new SimpleStringProperty(role);
    }

    public int getId(){
        return this.id.get();
    }

    public String getName(){
        return this.name.get();
    }

    public String getDateOfBirth(){
        return this.dateOfBirth.get();
    }

    public String getPhoneNumber(){
        return this.phoneNumber.get();
    }

    public String getRole(){
        return this.role.get();
    }
}