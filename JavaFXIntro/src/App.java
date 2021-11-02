import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
 
public class App extends Application {
    public static boolean VIEW_EMPLOYEES = false;
    public static ObservableList<Employee> EMPLOYEES = FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Manager");
        primaryStage.setResizable(false);

        StackPane root = new StackPane();
        GridPane grid = new GridPane();
        StackPane.setMargin(grid, new Insets(15, 50, 50, 50));
        root.getChildren().add(grid);

        Scene main = new Scene(root, 512, 256);
        primaryStage.setScene(main);

        Label lblName = new Label("Employee Name:");
        TextField txtName = new TextField();
        txtName.setText("");
        
        Label lblDOB = new Label("Date of Birth:");
        DatePicker dpDOB = new DatePicker();
        dpDOB.setValue(LocalDate.now());

        Label lblPhoneNumber = new Label("Phone Number:");
        TextField txtPhoneNumber = new TextField();
        txtPhoneNumber.setText("");

        Label lblRole = new Label("Phone Number:");
        ChoiceBox<String> choRole = new ChoiceBox<String>(FXCollections.observableArrayList(
            "Manager", "Chef", "Enginner", "System Admin"
        ));
        choRole.setValue("Manager");
        
        Button viewEmp = CreateButton("View Employees", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                App.ShowEmployees();
            }
        });
        
        Button addEmp = CreateButton("Add Employee", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                String empName = txtName.getText();
                LocalDate dob = dpDOB.getValue();
                String phoneNumber = txtPhoneNumber.getText();
                String role = choRole.getValue();

                txtName.setText("");
                dpDOB.setValue(LocalDate.now());
                txtPhoneNumber.setText("");
                choRole.setValue("Manager");

                EMPLOYEES.add(new Employee(EMPLOYEES.size() + 1, empName, dob, phoneNumber, role));
            }
        });


        GridPane.setConstraints(lblName, 0, 0);
        GridPane.setConstraints(txtName, 1, 0);

        GridPane.setConstraints(lblDOB, 0, 1);
        GridPane.setConstraints(dpDOB, 1, 1);

        GridPane.setConstraints(lblPhoneNumber, 0, 2);
        GridPane.setConstraints(txtPhoneNumber, 1, 2);
        
        GridPane.setConstraints(lblRole, 0, 3);
        GridPane.setConstraints(choRole, 1, 3);
        
        GridPane.setConstraints(viewEmp, 0, 4);
        GridPane.setConstraints(addEmp, 1, 4);

        grid.getChildren().addAll(lblName, txtName, lblDOB, dpDOB, lblPhoneNumber, txtPhoneNumber, lblRole, choRole, viewEmp, addEmp);

        primaryStage.show();
    }

    public static Button CreateButton(String text, EventHandler<ActionEvent> eHandler){
        Button btn = new Button();
        btn.setText(text);
        btn.setOnAction(eHandler);
        return btn;
    }

    public static void ShowEmployees(){
        if(VIEW_EMPLOYEES){ return; }
        VIEW_EMPLOYEES = true;

        Stage viewEmpStage = new Stage();
        viewEmpStage.setTitle("Employees");
        viewEmpStage.setWidth(500);
        viewEmpStage.setHeight(475);

        TableView<Employee> table = new TableView<Employee>();
        table.setMinWidth(475);
        
        TableColumn<Employee, Integer> idHeader = new TableColumn<Employee, Integer>("ID");
        idHeader.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        
        TableColumn<Employee, String> nameHeader = new TableColumn<Employee, String>("Name");
        nameHeader.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        
        
        TableColumn<Employee, String> dobHeader = new TableColumn<Employee, String>("Date of Birth");
        dobHeader.setCellValueFactory(new PropertyValueFactory<Employee, String>("dateOfBirth"));
        
        TableColumn<Employee, String> phoneNumberHeader = new TableColumn<Employee, String>("Phone Number");
        phoneNumberHeader.setCellValueFactory(new PropertyValueFactory<Employee, String>("phoneNumber"));
        
        TableColumn<Employee, String> roleHeader = new TableColumn<Employee, String>("Role");
        roleHeader.setCellValueFactory(new PropertyValueFactory<Employee, String>("role"));

        table.getColumns().addAll(idHeader, nameHeader, dobHeader, phoneNumberHeader, roleHeader);

        table.setItems(EMPLOYEES);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);

        Group grp = new Group();
        grp.getChildren().addAll(vbox);

        Scene main = new Scene(grp);
        viewEmpStage.setScene(main);
        viewEmpStage.show();

        // Stage on close
        viewEmpStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event){
                VIEW_EMPLOYEES = false;
            }
        });
    }
}