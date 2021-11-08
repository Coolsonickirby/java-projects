import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
 
public class App extends Application {
    public static final ArrayList<Size> PIZZA_SIZES = new ArrayList<Size>();
    public static final ArrayList<Topping> PIZZA_TOPPINGS = new ArrayList<Topping>();
    public static final ObservableList<Pizza> PIZZAS = FXCollections.observableArrayList();
    public static final NumberFormat FORMATTER = new DecimalFormat("#0.00");
    public static TableView<Pizza> TABLE_VIEW = new TableView<Pizza>();
    public static void main(String[] args) {
        setup();
        launch(args);
    }

    public static void setup(){
        //#region Setup Pizza Sizes
        PIZZA_SIZES.add(new Size("Small", 7.00));
        PIZZA_SIZES.add(new Size("Medium", 9.00));
        PIZZA_SIZES.add(new Size("Large", 15.00));
        PIZZA_SIZES.add(new Size("Extra Large", 19.00));
        //#endregion

        //#region Setup Toppings
        PIZZA_TOPPINGS.add(new Topping("Cheese", 0.00));
        PIZZA_TOPPINGS.add(new Topping("Olives", 1.00));
        PIZZA_TOPPINGS.add(new Topping("Meat", 1.00));
        PIZZA_TOPPINGS.add(new Topping("Pineapple", 1.00));
        PIZZA_TOPPINGS.add(new Topping("Peppers", 1.00));
        PIZZA_TOPPINGS.add(new Topping("Mushrooms", 1.00));
        //#endregion
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pizza Menu");
        Button btnAdd = new Button("Add Pizza");
        Button btnEdit = new Button("Edit Pizza");
        Button btnDel = new Button("Remove Pizza");

        
        TABLE_VIEW.setEditable(false);

        TableColumn<Pizza, String> pizzaID = new TableColumn<Pizza, String>("Pizza ID");
        pizzaID.setCellValueFactory(
            new PropertyValueFactory<Pizza, String>("idString")
        );

        TableColumn<Pizza, String> pizzaSize = new TableColumn<Pizza, String>("Pizza Size");
        pizzaSize.setCellValueFactory(
            new PropertyValueFactory<Pizza, String>("sizeString")
        );

        TableColumn<Pizza, String> pizzaToppings = new TableColumn<Pizza, String>("Pizza Toppings");
        pizzaToppings.setCellValueFactory(
            new PropertyValueFactory<Pizza, String>("toppingsString")
        );

        TableColumn<Pizza, String> pizzaExtras = new TableColumn<Pizza, String>("Extra Instructions");
        pizzaExtras.setCellValueFactory(
            new PropertyValueFactory<Pizza, String>("extras")
        );

        TableColumn<Pizza, String> pizzaTotalPrice = new TableColumn<Pizza, String>("Pizza Total Price");
        pizzaTotalPrice.setCellValueFactory(
            new PropertyValueFactory<Pizza, String>("totalPriceString")
        );

        TABLE_VIEW.setItems(PIZZAS);
        TABLE_VIEW.getColumns().addAll(pizzaID, pizzaSize, pizzaToppings, pizzaExtras, pizzaTotalPrice);

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){               
                showPizzaMenu(0);
            }
        });
        
        btnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){               
                if(TABLE_VIEW.getSelectionModel().selectedItemProperty().get() != null){ showPizzaMenu(TABLE_VIEW.getSelectionModel().selectedItemProperty().get().getId()); }
            }
        });

        btnDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){               
                if(TABLE_VIEW.getSelectionModel().selectedItemProperty().get() != null){ PIZZAS.remove(TABLE_VIEW.getSelectionModel().selectedIndexProperty().get()); }
            }
        });

        HBox btns = new HBox(btnAdd, btnEdit, btnDel);
        btns.setSpacing(10);
    
        VBox container = new VBox();
        container.setPadding(new Insets(10, 10, 10, 10));
        container.setSpacing(10);
        container.getChildren().addAll(btns, TABLE_VIEW);

        primaryStage.setScene(new Scene(container, 512, 512));
        primaryStage.show();
    }

    public static void showPizzaMenu(int id){
        Pizza currentPizza = PIZZAS.stream().filter(entry -> entry.getId() == id).findFirst().orElse(null);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Pizza");

        Label lblSizes = new Label("Select a pizza size:");
        ListView<String> lvSizes = new ListView<String>(FXCollections.observableArrayList(
            PIZZA_SIZES.stream().map(entry -> entry.toString()).collect(Collectors.toList())
        ));

        if(currentPizza != null){ lvSizes.getSelectionModel().select(currentPizza.getPizzaSize().toString()); }

        Label lblToppings = new Label("Select the toppings you'd like:");
        
        FlowPane toppings = new FlowPane();
        toppings.setHgap(10);
        toppings.setVgap(10);
        toppings.setOrientation(Orientation.HORIZONTAL);

        ArrayList<String> selectedToppings = currentPizza != null ? new ArrayList<String>() : null;
        if(currentPizza != null){ currentPizza.getToppings().stream().forEach(entry -> selectedToppings.add(entry.toString()));}

        for (Topping topping : PIZZA_TOPPINGS) {
            CheckBox chkBox = new CheckBox(topping.toString());
            if(selectedToppings != null){ chkBox.setSelected(selectedToppings.contains(topping.toString())); }
            toppings.getChildren().add(chkBox);
        }

        Label lblExtras = new Label("Extra Instructions:");
        TextArea extras = new TextArea("");
        extras.setMinHeight(100);
        if(currentPizza != null) { extras.setText(currentPizza.getExtras()); }

        Button btnSave = new Button("Save Pizza");
        Button btnCancel = new Button("Cancel Pizza");

        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                // Save (re-assign currentPizza into a new variable so it can be edited in this function)
                Pizza pizza = currentPizza;
                
                ArrayList<Topping> pizzaToppings = new ArrayList<Topping>();
                ObservableList<Node> topping = toppings.getChildren();
                
                for(int i = 0; i < topping.size(); i++){
                    CheckBox chk = (CheckBox)topping.get(i);
                    if(chk.isSelected()){ pizzaToppings.add(PIZZA_TOPPINGS.get(i)); }
                }
                
                if(pizza == null){ // Create new pizza
                    int id = PIZZAS.size() > 0 ? PIZZAS.stream().collect(Collectors.maxBy(Comparator.comparing(Pizza::getId))).get().getId() + 1 : PIZZAS.size() + 1;
                    pizza = new Pizza(id, PIZZA_SIZES.get(lvSizes.getSelectionModel().getSelectedIndex()), pizzaToppings, extras.getText());
                    PIZZAS.add(pizza);
                }else { // Edit current pizza
                    pizza.setPizzaSize(PIZZA_SIZES.get(lvSizes.getSelectionModel().getSelectedIndex()));
                    pizza.setToppings(pizzaToppings);
                    pizza.setExtras(extras.getText());
                }
                
                TABLE_VIEW.refresh(); // Refresh

                // Always close after saving
                primaryStage.close();
            }
        });
        
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                primaryStage.close();
            }
        });

        FlowPane options = new FlowPane(btnSave, btnCancel);
        options.setHgap(10);
        options.setVgap(10);
        options.setOrientation(Orientation.HORIZONTAL);

        VBox root = new VBox();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setSpacing(5);
        root.getChildren().addAll(lblSizes, lvSizes, lblToppings, toppings, lblExtras, extras, options);
        primaryStage.setScene(new Scene(root, 575, 350));
        primaryStage.show();
    }
}